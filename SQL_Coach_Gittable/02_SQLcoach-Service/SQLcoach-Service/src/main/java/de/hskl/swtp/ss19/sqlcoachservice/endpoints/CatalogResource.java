package de.hskl.swtp.ss19.sqlcoachservice.endpoints;

import de.hskl.swtp.ss19.sqlcoachservice.dbconnection.SqlCoachDBFacet;

import de.hskl.swtp.ss19.sqlcoachservice.exception.SqlCoachServiceException;
import de.hskl.swtp.ss19.sqlcoachservice.model.Exercise;
import de.hskl.swtp.ss19.sqlcoachservice.model.Group;
import de.hskl.swtp.ss19.sqlcoachservice.model.Scenario;
import de.hskl.swtp.ss19.sqlcoachservice.util.DataSourceFactory;


import javax.inject.Singleton;
import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URL;
import java.util.List;


/**
 * Diese Klasse definiert die Endpunkte der REST-API der Catalog Verwaltung.
 *
 * Zu Beginn wird eine Datenquelle erzeugt, z.B. eine Datenbankverbindung.
 * Die Endpunkte sind mit ihrem jeweiligen Requesttyp {@code z.B. @GET } annotiert.
 * {@code @Produces} spezifiziert den MIME-Type der zurückgegebenen Ressource. Bei REST i.d.R. application/JSON
 * {@code @Consumes} spezifiziert den dockeMIME-Type der akzeptierten Ressource.
 * {@code @Path} gibt den Pfad relativ zum context-Root (hier cres) an.
 * Root resource (exposed at "myresource" path)
 *
 * @author Bastian Beggel, Johannes Schmitt
 */
@Path("/v1")
@Singleton
public class CatalogResource {

    private SqlCoachDBFacet sqlCoachDBFacet;

    /**
     * Stellt eine Verbindung zur Datenbank her und verwendet ein SqlCoachDBFacet um die Daten für die REST-Endpunkte
     * zur Verfügung zu stellen.
     *
     * @throws an Exception Wirft einen Fehler, wenn die postgress_properties.local nicht vorhanden ist.
     */
    public CatalogResource() {
        String postgresProperties = System.getenv("postgres_properties");
        if (postgresProperties == null) {
            throw new SqlCoachServiceException("postgres_properties not specified!");
        }
        try {
            URL config = CatalogResource.class.getClassLoader().getResource(postgresProperties);
            DataSource dataSource = null;
            dataSource = DataSourceFactory.generateFromResourceFile(postgresProperties);
            sqlCoachDBFacet = new SqlCoachDBFacet(dataSource);
        } catch (IOException e) {
            throw new SqlCoachServiceException("Error intializing datapool for "+ postgresProperties);
        }

    }
    //Get-Befehle für Szenario, Aufgabengruppe und Aufgabe
    @Path("/catalog")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Scenario> getScenarios() {
        return (sqlCoachDBFacet.getScenarios());
    }

    @GET
    @Path("catalog/{scenarioId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Group> getGroups(@PathParam("scenarioId") int scenarioId) {
        return (sqlCoachDBFacet.getGroups(scenarioId));
    }
    @GET
    @Path("catalog{scenarioId}/{groupId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Exercise> getExercises(@PathParam("scenarioId") int scenarioId, @PathParam("groupId") int groupId) {
        return (sqlCoachDBFacet.getExercises(scenarioId, groupId));
    }



    @POST
    @Path("catalog/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Scenario> addScenario(Scenario sc)
    {
        sqlCoachDBFacet.addScenario(new Scenario(-1, sc.getScenarioName(), sc.getScenarioOwner(),sc.getDatasetId(),-1));
        return sqlCoachDBFacet.getScenarios();
    }

    @POST
    @Path("catalog/{scenarioId}/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Group> addGroup(@PathParam("scenarioId") int scenarioId, Group group) {
        group.setScenarioId(scenarioId);
        sqlCoachDBFacet.addGroup(group);
        return sqlCoachDBFacet.getGroups(scenarioId);
    }
    @POST
    @Path("catalog/{scenarioId}/{groupId}/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Exercise> addExercise(@PathParam("scenarioId") int scenarioId, @PathParam("groupId") int groupId, Exercise e) {
        e.setScenarioId(scenarioId);
        e.setGroupId(groupId);
        sqlCoachDBFacet.addExercise(e);
        return sqlCoachDBFacet.getExercises(scenarioId,groupId);
    }



    @PUT
    @Path("catalog/{scenarioId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public List<Scenario> updateScenario(@PathParam("scenarioId") int scenarioId, Scenario sc){

        sc.setScenarioId(scenarioId);
        sqlCoachDBFacet.updateScenario(sc);
        return sqlCoachDBFacet.getScenarios();

    }
    @PUT
    @Path("catalog/{scenarioId}/{groupId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public List<Group> updateGroup(@PathParam("scenarioId") int scenarioId, @PathParam("groupId") int groupId, Group gp) {

        gp.setScenarioId(scenarioId);
        gp.setGroupId(groupId);
        sqlCoachDBFacet.updateGroup(gp);
        return sqlCoachDBFacet.getGroups(scenarioId);

    }

    @PUT
    @Path("catalog/{scenarioId}/{groupId}/{exerciseId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Exercise> updateExercise(@PathParam("scenarioId") int scenarioId, @PathParam("groupId") int groupId, @PathParam("exerciseId") int exerciseId, Exercise e) {

        e.setScenarioId(scenarioId);
        e.setGroupId(groupId);
        e.setExerciseId(exerciseId);
        sqlCoachDBFacet.updateExercise(e);
        return sqlCoachDBFacet.getExercises(scenarioId, groupId);

    }




}
