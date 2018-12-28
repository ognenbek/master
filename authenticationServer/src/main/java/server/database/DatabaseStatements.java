package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import server.controllers.ClientCredentials;

public class DatabaseStatements {
	private static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3306/authentication";
	private static String USERNAME = "ognen";
	private static String PASSWORD = "admin";
	private static Connection conn;
	
	private static String INSERT_CLIENT_KEY = "INSERT INTO " + CreateDb.CLIENT_TABLE_NAME + " (" + CreateDb.CLIENT_CLIENTID_COLUMN + ", "+ CreateDb.CLIENT_PASSWORD_COLUMN + " , " 
			+ CreateDb.CLIENT_SALT_COLUMN + ") VALUES (?,?,?)";
	private static String CHECK_CLIENT_CREDENTIALS = "SELECT " + CreateDb.CLIENT_CLIENTID_COLUMN + " FROM " + CreateDb.CLIENT_TABLE_NAME + " WHERE " + CreateDb.CLIENT_CLIENTID_COLUMN +
		" = ? AND " + CreateDb.CLIENT_PASSWORD_COLUMN + " = ?";
	private static String GET_CLIENT_SALT =  "SELECT " + CreateDb.CLIENT_SALT_COLUMN + " FROM " + CreateDb.CLIENT_TABLE_NAME + " WHERE " + CreateDb.CLIENT_CLIENTID_COLUMN
			+ " =?";
	
	private static String INSERT_ADMIN_CREDENTIAS = "INSERT INTO " + CreateDb.ADMINISTRATOR_TABLE_NAME + " (" + CreateDb.ADMINISTRATOR_USERNAME_COLUMN + ", " + CreateDb.ADMINISTRATOR_PASSWORD_COLUMN + ", " 
			+ CreateDb.ADMINISTRATOR_SECTION_COLUMN + ", " +CreateDb.ADMINISTRATOR_SALT_COLUMN + ") VALUES (?,?,?,?)";
	private static String CHECK_ADMIN_CREDENTIALS = "SELECT " + CreateDb.ADMINISTRATOR_USERNAME_COLUMN + " FROM " + CreateDb.ADMINISTRATOR_TABLE_NAME + " WHERE " + CreateDb.ADMINISTRATOR_USERNAME_COLUMN 
			+ " = ? AND " + CreateDb.ADMINISTRATOR_PASSWORD_COLUMN + " =?";
	private static String GET_ADMIN_SALT = "SELECT " + CreateDb.ADMINISTRATOR_SALT_COLUMN + " FROM " + CreateDb.ADMINISTRATOR_TABLE_NAME + " WHERE " + CreateDb.ADMINISTRATOR_USERNAME_COLUMN
			+ " =?";
	
	private static String INSERT_CLIENT_ROLE_CREDENTIALS = "INSERT INTO " + CreateDb.CLIENT_ROLE_TABLE_NAME + " (" + CreateDb.CLIENT_CLIENTID_COLUMN + "," + CreateDb.CLIENT_ROLE_COLUMN + "," 
	+ CreateDb.CLIENT_ACTION_COLUMN + ") VALUES (?,?,?)";
	private static String GET_CLIENT_ROLE = "SELECT " + CreateDb.CLIENT_ROLE_COLUMN + " FROM " + CreateDb.CLIENT_ROLE_TABLE_NAME + " WHERE " 
			+ CreateDb.CLIENT_CLIENTID_COLUMN + " =?" + " AND " + CreateDb.CLIENT_ACTION_COLUMN + " = ?" ;
	
	private static String CHECK_IF_CREDENTIALS_EXIST = "SELECT * FROM " + CreateDb.CLIENT_TABLE_NAME + " WHERE " + 
			CreateDb.CLIENT_CLIENTID_COLUMN + " = ?";
	
	private static String GET_CLIENT_CREDENTIALS = "SELECT * FROM " + CreateDb.CLIENT_TABLE_NAME + " WHERE " + 
			CreateDb.CLIENT_CLIENTID_COLUMN + " = ?";
	
	
	private static String ANSWER_EXIST = "Credentials OK !";
	private static String ANSWER_NOT_EXIST = "Device does not exist !";
	private static void establishConnection() throws Exception{
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);		
		} catch (Exception e) {
			System.out.println(e.toString());
		}		
	}
	
	public static String checkClientCredentials(String clientId, String password){
		String answer = ANSWER_NOT_EXIST; 
		try {
			establishConnection();
			PreparedStatement st = conn.prepareStatement(CHECK_CLIENT_CREDENTIALS);
			st.setString(1,clientId);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			
			
			if(rs.next())
		        answer = ANSWER_EXIST;
		     
			st.close();
			
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return answer;
	}
	public static void insertClientCredentials(String clientId, String password, byte[] salt) {
		try {
			establishConnection();
			PreparedStatement insert = conn.prepareStatement(INSERT_CLIENT_KEY);
			insert.setString(1,clientId);
			insert.setString(2, password);
			insert.setBytes(3, salt);
			
			insert.execute();
			System.out.println(" inserted client credentials" + clientId + " " + password);
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static byte[] getClientSalt(String clientId) {
		byte [] salt = null;
		try {
			establishConnection();
			PreparedStatement st = conn.prepareStatement(GET_CLIENT_SALT);
			st.setString(1, clientId);
			ResultSet rs = st.executeQuery();
				
			while(rs.next()) {
				salt = rs.getBytes(CreateDb.CLIENT_SALT_COLUMN);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return salt;
	}
	public static boolean checkAdminCredentials(String username, String password) {
		try {
			establishConnection();
			PreparedStatement st = conn.prepareStatement(CHECK_ADMIN_CREDENTIALS);
			st.setString(1,username);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			
			
			if(rs.next())
		        return true;
		     
			st.close();
			
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return false;
	}
	public static byte[] getAdminSalt(String username) {
		byte[] salt = null;
		try {
			establishConnection();
			PreparedStatement st = conn.prepareStatement(GET_ADMIN_SALT);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
				
			while(rs.next()) {
				salt = rs.getBytes(CreateDb.ADMINISTRATOR_SALT_COLUMN);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return salt;
	}

	public static void insertAdminCredentials(String username, String password, String section, byte[] salt) {
		try {
			establishConnection();
			PreparedStatement insert = conn.prepareStatement(INSERT_ADMIN_CREDENTIAS);
			insert.setString(1, username);
			insert.setString(2, password);
			insert.setString(3, section);
			insert.setBytes(4, salt);
			
			insert.execute();
			
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void insertClientRole(String clientId, String role, String action) {
		try {
			establishConnection();
			PreparedStatement insert = conn.prepareStatement(INSERT_CLIENT_ROLE_CREDENTIALS);
			insert.setString(1, clientId);
			insert.setString(2, role);
			insert.setString(3, action);
			
			insert.execute();
			
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public static String getClientRole(String clientId, String action) {
		try {
			establishConnection();
			PreparedStatement st = conn.prepareStatement(GET_CLIENT_ROLE);
			st.setString(1,clientId);
			st.setString(2, action);
			ResultSet rs = st.executeQuery();
			
			System.out.println(rs.toString());
			if(rs.next())
		        return rs.getString(CreateDb.CLIENT_ROLE_COLUMN);
		     
			st.close();
			
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println("NULLL");
		return null;
	}
	
	public static boolean CheckCredentialsExist(String clientId) {
		try {
			establishConnection();
			PreparedStatement st = conn.prepareStatement(CHECK_IF_CREDENTIALS_EXIST);
			st.setString(1,clientId);
			System.out.println(st.toString());
			ResultSet rs = st.executeQuery();
			 if(rs.next()) 
				 return true;
			 return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;	
	}
	
	public static String getClientCredentials(String clientId) {
		try {
			establishConnection();
			PreparedStatement st = conn.prepareStatement(CHECK_IF_CREDENTIALS_EXIST);
			st.setString(1,clientId);
			System.out.println(st.toString());
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				String password = rs.getString(CreateDb.CLIENT_PASSWORD_COLUMN);
				System.out.println(password);
				return  password;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
}
