package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;

public class CreateDb {
	private static String jdbcDriver = "com.mysql.jdbc.Driver";
    private static String dbName = "Authentication";
    private static String userName = "ognen";
    private static String password = "admin";
    private static Statement statement = null;
    private static Connection connection = null;
    
    public static String CLIENT_TABLE_NAME = "client";
    public static String CLIENT_CLIENTID_COLUMN = "clientId";
    public static String CLIENT_PASSWORD_COLUMN = "password";
    public static String CLIENT_SALT_COLUMN = "salt";
    
    public static String ADMINISTRATOR_TABLE_NAME = "administrator";
    public static String ADMINISTRATOR_USERNAME_COLUMN = "username";
    public static String ADMINISTRATOR_PASSWORD_COLUMN = "password";
    public static String ADMINISTRATOR_SECTION_COLUMN = "section";
    public static String ADMINISTRATOR_SALT_COLUMN = "salt";
    
    public static String CLIENT_ROLE_TABLE_NAME = "clientRole";
    public static String CLIENT_ROLE_COLUMN = "clientRole";
    public static String CLIENT_ACTION_COLUMN = "clientAction";

	public static void create() {

		try {
			createDatabase();
			createClientsTable();
			createAdministratorstable();
			createClientRoleTable();
			CreateAdminUser.crate();
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
private static void createClientsTable() throws SQLException {
        String sql_stmt = "CREATE TABLE IF NOT EXISTS " + dbName + ".`" + CLIENT_TABLE_NAME + "` (\n"
                + "    `autoIndex` SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT,\n"
                + "    `" + CLIENT_CLIENTID_COLUMN + "` VARCHAR(45) NOT NULL,\n"
                + "    `" + CLIENT_PASSWORD_COLUMN + "` VARCHAR(45) NOT NULL,\n"
                + "    `" + CLIENT_SALT_COLUMN + "` VARBINARY(16) NOT NULL,\n"
                + "    PRIMARY KEY (`autoIndex`)\n"
                + ");";

        statement = connection.createStatement();

        statement.executeUpdate(sql_stmt);

        System.out.println("Customers table has successfully been created");
    }
private static void createAdministratorstable() throws SQLException {
    String sql_stmt = "CREATE TABLE IF NOT EXISTS " + dbName + ".`" + ADMINISTRATOR_TABLE_NAME + "` (\n"
            + "    `autoIndex` SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT,\n"
            + "    `" + ADMINISTRATOR_USERNAME_COLUMN + "` VARCHAR(45) NOT NULL,\n"
            + "    `" + ADMINISTRATOR_PASSWORD_COLUMN + "` VARCHAR(45) NOT NULL,\n"
            + "    `" + ADMINISTRATOR_SECTION_COLUMN + "` VARCHAR(45) NOT NULL,\n"
            + "    `" + ADMINISTRATOR_SALT_COLUMN + "` VARBINARY(16) NOT NULL,\n"
            + "    PRIMARY KEY (`autoIndex`)\n"
            + ");";

    System.out.println(sql_stmt);
    statement = connection.createStatement();

    statement.executeUpdate(sql_stmt);

    System.out.println("Administrators table has successfully been created");
}
private static void createClientRoleTable() throws SQLException {
	String sql_stmt = "CREATE TABLE IF NOT EXISTS " + dbName + ".`" + CLIENT_ROLE_TABLE_NAME + "` (\n"
            + "    `autoIndex` SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT,\n"
            + "    `" + CLIENT_CLIENTID_COLUMN + "` VARCHAR(45) NOT NULL,\n"
            + "    `" + CLIENT_ROLE_COLUMN + "` VARCHAR(45) NOT NULL,\n"
            + "    `" + CLIENT_ACTION_COLUMN + "` VARCHAR(45) NOT NULL,\n"
            + "    PRIMARY KEY (`autoIndex`)\n"
            + ");";
	System.out.println(sql_stmt);
    statement = connection.createStatement();

    statement.executeUpdate(sql_stmt);

    System.out.println("ClientRole table has successfully been created");
	
}

}
