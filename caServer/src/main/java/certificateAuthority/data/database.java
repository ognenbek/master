package certificateAuthority.data;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class database {
	private static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3306/keystore";
	private static String USERNAME = "ognen";
	private static String PASSWORD = "admin";
	private static Connection conn;
	private static String INSERT_CLIENT_KEY = "INSERT INTO userkey (id, client_id, public_key) VALUES (NULL ,?,?)";
	private static String GET_CLIENT_KEY = "SELECT public_key FROM userkey WHERE client_id = ?";
	public static void establishConnection() throws Exception{
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);		
		} catch (Exception e) {
			System.out.println(e.toString());
		}		
	}
	
	public static void addClientKey(String clientId, String key) throws Exception {
		establishConnection();
		
		PreparedStatement insert = conn.prepareStatement(INSERT_CLIENT_KEY);
		insert.setString(1, clientId);
		insert.setString(2, key);
		
		insert.execute();
		
		
		conn.close();
	}
	
	public static String getClientKey(String clientId) throws Exception {
		establishConnection();
		String publicKey = "";
		
		PreparedStatement select = conn.prepareStatement(GET_CLIENT_KEY);
		select.setString(1, clientId);
		
		ResultSet rs = select.executeQuery();

		while (rs.next()) {

			publicKey = rs.getString("public_key");
		}
		
		conn.close();
		return publicKey;
		
	}
}
