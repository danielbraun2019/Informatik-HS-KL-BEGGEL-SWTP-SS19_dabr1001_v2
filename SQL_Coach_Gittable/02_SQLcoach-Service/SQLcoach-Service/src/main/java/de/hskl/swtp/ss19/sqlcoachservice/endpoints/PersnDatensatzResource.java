package de.hskl.swtp.ss19.sqlcoachservice.endpoints;

import de.hskl.swtp.ss19.sqlcoachservice.dbconnection.SqlCoachDBFacet;

import de.hskl.swtp.ss19.sqlcoachservice.exception.SqlCoachServiceException;
import de.hskl.swtp.ss19.sqlcoachservice.model.Table_shemas;
import de.hskl.swtp.ss19.sqlcoachservice.model.QueryReturn;
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
 * @author Bastian Beggel, Johannes Schmitt, Daniel Braun
 */
@Path("/v1/dataset/1")
@Singleton
public class PersnDatensatzResource {

    private SqlCoachDBFacet sqlCoachDBFacetTraining;

    /**
     * Stellt eine Verbindung zur Personaldatenbank her und verwendet ein SqlCoachDBFacet um die Daten für die REST-Endpunkte
     * zur Verfügung zu stellen.

     */
    public PersnDatensatzResource() {
        String properties=("postgres_docker_personaldatensatz.properties");
        try {
            URL config =  PersnDatensatzResource.class.getClassLoader().getResource(properties);
            DataSource dataSource = null;
            dataSource = DataSourceFactory.generateFromResourceFile(properties);
            sqlCoachDBFacetTraining = new SqlCoachDBFacet(dataSource);
        } catch (IOException e) {
            throw new SqlCoachServiceException("Error intializing training ");
        }
    }

    @GET
    @Path("/tables")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Table_shemas> getTableDefinition() {
        return (sqlCoachDBFacetTraining.getTableShemas());
    }

    @GET
    @Path("/execute")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<QueryReturn> selectQuery(@QueryParam("query") String query) {
        return (sqlCoachDBFacetTraining.selectQuery(query));
    }
    @POST
    @Path("/execute")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<QueryReturn> insertQuery(@QueryParam("query") String query) {
        return (sqlCoachDBFacetTraining.insertQuery(query));
    }
    @DELETE
    @Path("/execute")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<QueryReturn> deleteQuery(@QueryParam("query") String query) {
        return (sqlCoachDBFacetTraining.deleteQuery(query));

    }

}
