package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDb {
	private static String jdbcDriver = "com.mysql.jdbc.Driver";
    private static String dbName = "Authorisation";
    private static String userName = "ognen";
    private static String password = "admin";
    private static Statement statement = null;
    private static Connection connection = null;
    
    public static String XACML_REQUESTS_TABLE_NAME = "xacmlRequests";
    public static String XACML_REQUEST_COLUMN = "xacmlReuest";
    public static String XACML_INDEX_COLUMN = "index";
    
    public static void create() {

		try {
			createDatabase();
			createXacmlRequestTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    private static void createDatabase() throws ClassNotFoundException, SQLException{
		Class.forName(jdbcDriver);
		connection = DriverManager.getConnection("jdbc:mysql://localhost/?user=" + userName +"&password=" + password);
		statement = connection.createStatement();
		statement.executeUpdate("CREATE DATABASE IF NOT EXISTS "+dbName);
	}
    
    private static void createXacmlRequestTable() throws SQLException {
        String sql_stmt = "CREATE TABLE IF NOT EXISTS " + dbName + ".`" + XACML_REQUESTS_TABLE_NAME + "` (\n"
                + "    `autoIndex` SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT,\n"
                + "    `" + XACML_INDEX_COLUMN + "` VARCHAR(45) NOT NULL,\n"
                + "    `" + XACML_REQUEST_COLUMN + "` TEXT NOT NULL,\n"
                + "    PRIMARY KEY (`autoIndex`)\n"
                + ");";

        statement = connection.createStatement();

        statement.executeUpdate(sql_stmt);

        System.out.println("Customers table has successfully been created");
    }
    
}
