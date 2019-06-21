package de.hskl.swtp.ss19.sqlcoachservice.dbconnection;

import de.hskl.swtp.ss19.sqlcoachservice.exception.SqlCoachServiceException;
import de.hskl.swtp.ss19.sqlcoachservice.model.Exercise;
import de.hskl.swtp.ss19.sqlcoachservice.model.Group;
import de.hskl.swtp.ss19.sqlcoachservice.model.Scenario;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**

 * Diese Klasse verwendet eine Postgres Datenbank um Szenarien, Gruppen und Aufgaben aus der Datenbank zu lesen,
 * bzw. diese in die Datenbank zu schreiben.
 *
 * @author Bastian Beggel, Johannes Schmitt
 */
public class SqlCoachDBFacet {

    DataSource dataSource;

    private static final Logger log = Logger.getLogger(SqlCoachDBFacet.class);
    /**
     * Die Klasse wird in Verbindung mit einer Datenquelle instanziiert.
     * @param dataSource Die Datenquelle vom Typ DataSource (in der Regel eine Datenbank)
     */
    public SqlCoachDBFacet(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Methode zum Aufbau einer Datenbankverbindung.
     *
     * @return {@link Connection}
     * @throws SQLException Verbindungsfehler
     */
    private Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    /**
     * Methode zum Laden von Szenarien.
     * Ein SQL-Statement wird vorbereitet, erzeugt und ausgeführt.
     * @return Eine Liste von Szenarien
     */

    public List<Scenario> getScenarios() {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        sb.append(" scenarioid ");
        sb.append(", scenarioname ");
        sb.append(", scenarioowner ");
        sb.append(", datasetId ");
        sb.append(", rank ");
        sb.append(" from ");
        sb.append(" sc_scenario ");
        sb.append(" ORDER BY rank ");

        log.trace("SQL:" + sb.toString());
        List<Scenario> ret = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sb.toString());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("scenarioid");
                String scenarioName = rs.getString("scenarioname");
                String scenarioOwner = rs.getString("scenarioowner");
                int datasetId = rs.getInt("datasetId");
                int rank = rs.getInt("rank");

                ret.add(new Scenario(id, scenarioName, scenarioOwner,datasetId,rank));

            }
        } catch (SQLException exce) {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR loadData", exce);
        }

        return ret;
    }



    /**
     * Methode zum Laden von Gruppen.
     * Ein SQL-Statement wird vorbereitet, erzeugt und ausgeführt.
     * @return Eine Liste von Gruppen.
     */
    public List<Group> getGroups(int scenarioId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        sb.append(" groupId ");
        sb.append(", groupName ");
        sb.append(", rank ");
        sb.append(" from ");
        sb.append(" sc_group ");
        sb.append(" WHERE ");
        sb.append(" scenarioId = ?");
        sb.append(" ORDER BY rank ");


        List<Group> ret = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sb.toString());
            stmt.setInt(1, scenarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String groupName = rs.getString("groupName");
                int groupId= rs.getInt("groupId");

                ret.add(new Group(groupId, groupName, scenarioId));

            }
        } catch (SQLException exce) {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR loadData", exce);
        }

        return ret;
    }


    /**
     * Methode zum Laden von Aufgaben.
     * Ein SQL-Statement wird vorbereitet, erzeugt und ausgeführt.
     * @return Eine Liste von Aufgaben
     */
    public List<Exercise> getExercises(int scenarioId, int groupId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        sb.append(" exerciseId ");
        sb.append(", exerciseText ");
        sb.append(", exerciseSolution ");

        sb.append(" from ");
        sb.append(" sc_exercise ");
        sb.append(" WHERE ");
          sb.append(" groupId = ?");
        sb.append(" ORDER BY rank ");


        List<Exercise> ret = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sb.toString());
            stmt.setInt(1, groupId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String exerciseText = rs.getString("exerciseText");
                String exerciseSolution = rs.getString("exerciseSolution");
                int exerciseId= rs.getInt("exerciseId");

                ret.add(new Exercise(exerciseId,exerciseText,exerciseSolution,scenarioId,groupId));

            }
        } catch (SQLException exce) {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR loadData", exce);
        }

        return ret;
    }




    /**
     * Methode zum Speichern von Szenarien.
     * Ein SQL-Statement wird vorbereitet, erzeugt und ausgeführt.
     * @param scenario das zu speichernde Szenario.
     * @return scenario das gespeicherte Szenario
     */

    public Scenario addScenario(Scenario scenario) {

        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(" sc_scenario");
        sb.append("(");

        sb.append("scenarioname"); // 1
        sb.append(",scenarioowner");// 2
        sb.append(",datasetId"); // 1



        sb.append(" ) VALUES (");
        sb.append("?,?,?");
        sb.append(")");

        log.trace("SQL:" + sb.toString());
        try (Connection connection = getConnection())
        {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, scenario.getScenarioName());
            pstmt.setString(2, scenario.getScenarioOwner());
            pstmt.setInt(3, scenario.getDatasetId());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    scenario.setScenarioId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating scenario failed, no ID obtained.");
                }
            }

        }
        catch (SQLException exce)
        {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR loadData", exce);
        }
        return scenario;
    }

    /**
     * Methode zum Speichern von Gruppen.
     * @param group die zu Speichernde Gruppe
     * @return die gespeicherte Gruppe
     */
    public Group addGroup(Group group) {

    StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(" sc_group");
        sb.append("(");

        sb.append("groupName"); // 1
        sb.append(",scenarioId");// 2

        sb.append(" ) VALUES (");
        sb.append("?,?");
        sb.append(")");

        System.out.println("SQL:" + sb.toString());
        try (Connection connection = getConnection())
    {
        PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);


        pstmt.setString(1, group.getGroupName());
        pstmt.setInt(2, group.getScenarioId());
        int affectedRows = pstmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                group.setGroupId(generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("Creating group failed, no ID obtained.");
            }
        }

    }
        catch (SQLException exce)
    {
        exce.printStackTrace();
        throw new SqlCoachServiceException("ERROR addGroups", exce);
    }
        return group;
}

    /**
     * Methode zum Speichern von Aufgaben.
     * @param exercise die zu Speichernde Aufgabe
     * @return die gespeicherte Aufgabe.
     */
    public Exercise addExercise(Exercise exercise) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(" sc_exercise");
        sb.append("(");

        sb.append("groupId"); // 1
        sb.append(",exerciseText"); // 2
        sb.append(",exerciseSolution");// 3


        sb.append(" ) VALUES (");
        sb.append("?,?,?");
        sb.append(")");

        System.out.println("SQL:" + sb.toString());
        try (Connection connection = getConnection())
        {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, exercise.getGroupId());
            pstmt.setString(2, exercise.getExerciseText());
            pstmt.setString(3, exercise.getExerciseSolution());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    exercise.setExerciseId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        }
        catch (SQLException exce)
        {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR addExercise", exce);
        }
        return exercise;
    }


    public void updateScenario(Scenario sc) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append("\n sc_scenario ");
        sb.append("\n SET ");

        sb.append("\n scenarioName = ? "); // 1
        sb.append("\n ,scenarioOwner = ? "); // 2
        sb.append("\n ,datasetId = ? "); // 1



        sb.append("\n WHERE ");
        sb.append("\n scenarioId = ?");

        try (Connection connection = getConnection())
        {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sb.toString());

            pstmt.setString(1, sc.getScenarioName());
            pstmt.setString(2, sc.getScenarioOwner());
            pstmt.setInt(3, sc.getDatasetId());
            pstmt.setInt(4, sc.getScenarioId());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to Update Scenario.");
            }

        }
        catch (SQLException exce)
        {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR updateDATA", exce);
        }


    }



}
