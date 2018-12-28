package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseStatements {
	private static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3306/authentication";
	private static String USERNAME = "ognen";
	private static String PASSWORD = "admin";
	private static Connection conn;
	
	private static String INSERT_REQUEST = "INSERT INTO " + CreateDb.XACML_REQUESTS_TABLE_NAME + " (" + CreateDb.XACML_INDEX_COLUMN  + ", " + CreateDb.XACML_REQUEST_COLUMN + ") VALUES ( ?, ? )";
	private static String CHECK_REQUEST = "SELECT " + CreateDb.XACML_REQUEST_COLUMN + " WHERE autoIndex = ?";
	
	private static void establishConnection() throws Exception{
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);		
		} catch (Exception e) {
			System.out.println(e.toString());
		}		
	}
	
	public static String getXacml(String index){
		String answer = null;
		try {
			establishConnection();
			PreparedStatement st = conn.prepareStatement(CHECK_REQUEST);
			st.setString(1, index);
			ResultSet rs = st.executeQuery();
			
			
			if(rs.next())
		        answer = rs.getString(CreateDb.XACML_REQUEST_COLUMN);
		     
			st.close();
			
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return answer;
	}
	
	public static void insertXacml(String id, String xacml) {
		try {
			System.out.println("Inserting xacml " + id + " " + xacml);
			establishConnection();
			PreparedStatement insert = conn.prepareStatement(INSERT_REQUEST);
			insert.setString(1, id);
			insert.setString(2, xacml);	
			System.out.println(insert.toString());
			insert.execute();
			
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
