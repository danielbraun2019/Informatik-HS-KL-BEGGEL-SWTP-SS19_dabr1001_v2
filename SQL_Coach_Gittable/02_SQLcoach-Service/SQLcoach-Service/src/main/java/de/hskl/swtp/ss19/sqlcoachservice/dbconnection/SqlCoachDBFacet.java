package de.hskl.swtp.ss19.sqlcoachservice.dbconnection;

import de.hskl.swtp.ss19.sqlcoachservice.exception.SqlCoachServiceException;
import de.hskl.swtp.ss19.sqlcoachservice.model.Exercise;
import de.hskl.swtp.ss19.sqlcoachservice.model.Group;
import de.hskl.swtp.ss19.sqlcoachservice.model.Scenario;
import de.hskl.swtp.ss19.sqlcoachservice.model.Table_shemas;
import de.hskl.swtp.ss19.sqlcoachservice.model.QueryReturn;
import org.apache.log4j.Logger;
import java.io.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**

 * Diese Klasse verwendet eine Postgres Datenbank um Szenarien, Gruppen und Aufgaben aus der Datenbank zu lesen,
 * bzw. diese in die Datenbank zu schreiben.
 *
 * @author Bastian Beggel, Johannes Schmitt, Braun Daniel
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

    public void updateGroup(Group gp) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append("\n sc_group ");
        sb.append("\n SET ");

        sb.append("\n groupName = ? "); // 1
        sb.append("\n ,scenarioId = ? "); // 2


        sb.append("\n WHERE ");
        sb.append("\n groupId = ?");

        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sb.toString());

            pstmt.setString(1, gp.getGroupName());
            pstmt.setInt(2, gp.getScenarioId());
            pstmt.setInt(3, gp.getGroupId());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to Update Scenario.");
            }

        } catch (SQLException exce) {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR updateDATA", exce);
        }


    }
    public void updateExercise(Exercise e) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append("\n sc_exercise ");
        sb.append("\n SET ");

        sb.append("\n exerciseText = ? "); // 1
        sb.append("\n ,exerciseSolution = ? "); // 2
        sb.append("\n ,groupId= ? "); // 3

        sb.append("\n WHERE ");
        sb.append("\n exerciseId = ?");//5

        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sb.toString());

            pstmt.setString(1, e.getExerciseText());
            pstmt.setString(2, e.getExerciseSolution());
            pstmt.setInt(3, e.getGroupId());
            pstmt.setInt(4, e.getExerciseId());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to Update Scenario.");
            }

        } catch (SQLException exce) {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR updateDATA", exce);
        }
    }

    public void deleteScenario(int scenarioId) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM");
        sb.append("\n sc_scenario ");

        sb.append("\n WHERE ");
        sb.append("\n scenarioId = ?");//1

        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sb.toString());

            pstmt.setInt(1, scenarioId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("\"Failed to Delete Scenario, there might not exist a Scenario with matching scenarioId.\"");
            }

        } catch (SQLException exce) {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR deleteDATA", exce);
        }
    }

    public void deleteGroup(int scenarioId, int groupId) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM");
        sb.append("\n sc_group ");

        sb.append("\n WHERE ");
        sb.append("\n scenarioId = ?");//1
        sb.append("\n AND groupId = ?");//2


        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sb.toString());

            pstmt.setInt(1, scenarioId);
            pstmt.setInt(2, groupId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to Delete Group, there might not exist a Group with matching groupId.");
            }

        } catch (SQLException exce) {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR deleteDATA", exce);
        }
    }
    public void deleteExercise(int groupId, int exerciseId) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM");
        sb.append("\n sc_exercise ");

        sb.append("\n WHERE ");
        sb.append("\n groupId = ?");//1
        sb.append("\n AND exerciseId = ?");//2


        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sb.toString());
            pstmt.setInt(1, groupId);
            pstmt.setInt(2, exerciseId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to Delete Exercise, there might not exist a Exercise with matching exerciseId.");
            }

        } catch (SQLException exce) {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR deleteDATA", exce);
        }


    }

    /**
     *
     * @return Die Tabellenshemata in Form von Jsons, welche auf dem Objekt Table_shemas beruhen
     * Ein Table_shema enthält
     *     Den Namen der Tabelle            private String tableName;
     *     Eine Liste der Spaltennamen       private List<String> column_names;
     *     Eine Liste der Primary Keys       private List<String> primary_key;
     *     Eine Liste der Foreign Keys       private List<String> foreign_key;
     */
    public List<Table_shemas> getTableShemas() {
        List<Table_shemas> table_shemas = new ArrayList<>();
        List<String> tablenames = getTableNames();
        for(String tablename:tablenames) {
            try (Connection connection = getConnection()) {
                table_shemas.add(new Table_shemas(tablename,getColumnNames(tablename),getPrimaryKey(tablename),getForeignKeys(tablename)));
            } catch (SQLException exce) {
                exce.printStackTrace();
                throw new SqlCoachServiceException("ERROR loadData", exce);
            }
        }
        return (table_shemas);
    }
    private  List<String> getTableNames(){
        List<String> table_names = new ArrayList<>();
        try (Connection connection = getConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            String[] ArrayofDoom = new String[]{"TABLE"};
            ResultSet rs = metadata.getTables(connection.getCatalog(), connection.getSchema(), null, ArrayofDoom);
            while (rs.next()) {
                table_names.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException exce) {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR loadData", exce);
        }
        return (table_names);
    }
    private List<String> getColumnNames(String table_name) {
        List<String> ListOfColumnNames = new ArrayList<>();
        try (Connection connection = getConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet rs = metadata.getColumns(connection.getCatalog(), connection.getSchema(),table_name, null);
            while (rs.next()) {
                ListOfColumnNames.add(rs.getString("Column_NAME"));
            }
        } catch (SQLException exce) {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR loadData", exce);
        }
        return (ListOfColumnNames);
    }
    private List<String> getPrimaryKey(String tablename) {
        List<String> primary_keys=new ArrayList<>();
        try (Connection connection = getConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet rs = metadata.getPrimaryKeys(connection.getCatalog(), connection.getSchema(),tablename);
            while (rs.next()) {
                primary_keys.add(rs.getString("COLUMN_NAME"));
            }
        } catch (SQLException exce) {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR loadData", exce);
        }
        return (primary_keys);
    }
    private List<String> getForeignKeys(String tablename) {
        List<String> ListOfForeignKeys = new ArrayList<>();
        try (Connection connection = getConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet rs = metadata.getImportedKeys(connection.getCatalog(), connection.getSchema(),tablename);
            while (rs.next()) {
                ListOfForeignKeys.add(rs.getString("FK_NAME"));
            }
        } catch (SQLException exce) {
            exce.printStackTrace();
            throw new SqlCoachServiceException("ERROR loadData", exce);
        }
        return (ListOfForeignKeys);
    }



/////////////////////////////////////////////////////////

    /**
     *
     * @param Query Übergebene Query
     * @return Liste von QueryReturn(s)
     * Ein QueryReturn enthält Spaltennamen und Werte  der Selektierten Daten
     */
    public List<QueryReturn> selectQuery(String Query) {
        if (Query.startsWith("select")||Query.startsWith("Select")||Query.startsWith("SELECT")) {
            return(executeQuery(Query));
        }
        List<QueryReturn> query_return = new ArrayList<>();
        return (query_return);
    }



    /* Methode die das Ausführen von SQL insert queries auf dem Datensatz personaldatensatz ermöglicht
    Mit Postman prüfbar:
    http://localhost:8001/sqlcoachservice/api/v1/dataset/1/execute?query=insert into personal  (PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
    VALUES (666, 'Donald',	'TRUMP',	5, 1201, 1000)
    */

    public List<QueryReturn> insertQuery(String Query) {
        if (Query.startsWith("insert")||Query.startsWith("Insert")||Query.startsWith("INSERT")) {
            executeQueryforInsert_Delete_and_Update(Query);
            return(returnColumn(Query));
        }
        List<QueryReturn> query_return = new ArrayList<>();
        return (query_return);
    }

    /* Methode die das Ausführen von SQL delete queries auf dem Datensatz personaldatensatz Ermöglicht
    Mit Postman prüfbar:
    http://localhost:8001/sqlcoachservice/api/v1/dataset/1/execute?query=delete from personal where  persnr= 666
    */


    public List<QueryReturn> deleteQuery(String Query) {
        if (Query.startsWith("delete")||Query.startsWith("Delete")||Query.startsWith("DELETE")) {
            executeQueryforInsert_Delete_and_Update(Query);
           return(returnColumn(Query));
        }
        List<QueryReturn> query_return = new ArrayList<>();
        return (query_return);
    }


    /*
    Die Methode returnColumn gibt die Werte einer Spalte auf der Insert oder delete queries ausgeführt werden aus.
    Mit
    String[] s =Query.split(" ");
    StringBuilder sb = new StringBuilder();
    Wird die Query in ein Array umgewandelt wobei der Name der durch die Query zu veränderten Spalte an Position 2 des Array steht
    Zurückgegeben werden Alle Werte der Spalte als QueryReturn
     */
    private List<QueryReturn> returnColumn(String Query) {
        String[] s =Query.split(" ");
        StringBuilder sb = new StringBuilder();
        sb.append("select*from ");
        sb.append(s[2]);
        return(executeQuery(sb.toString()));
    }

    /**
     Executes Queries  via executeQuery()
     */
    private List<QueryReturn> executeQuery(String Query) {
        List<QueryReturn> query_return = new ArrayList<>();
        try (Connection connection = getConnection()) {
                PreparedStatement stmt = connection.prepareStatement(Query);
                ResultSet rs = stmt.executeQuery();
                ResultSetMetaData resultsetmetadata = rs.getMetaData();
                int columnCount = resultsetmetadata.getColumnCount();

                while (rs.next()) {
                    List<String> columnnameandvaluesList = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++) {
                        StringBuilder columnname_plusvalue = new StringBuilder();
                        columnname_plusvalue.append(resultsetmetadata.getColumnName(i));
                        columnname_plusvalue.append(": ");
                        columnname_plusvalue.append(rs.getString(i));
                        columnnameandvaluesList.add(columnname_plusvalue.toString());
                    }
                    query_return.add(new QueryReturn(columnnameandvaluesList));

                }
            } catch (SQLException exce) {
                exce.printStackTrace();
                throw new SqlCoachServiceException("ERROR loadData", exce);
            }
        return (query_return);
    }

    /**
    Executes Queries  via executeUpdate()
     */
    private void executeQueryforInsert_Delete_and_Update(String Query) {
        List<QueryReturn> query_return = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Query Execution Failed 0 Rows affected");
            }
        }
        catch (SQLException exce)
        {
            exce.printStackTrace();
            throw new SqlCoachServiceException("", exce);
        }
    }
}
