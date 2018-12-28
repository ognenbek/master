package server.controllers;

import server.database.DatabaseStatements;

public class ClientCredentials {
	private String clientId;
	private String password;
	private byte[] salt;
	private String message;
	public ClientCredentials(String clientId, String password, byte[] salt) {
		DatabaseStatements.insertClientCredentials(clientId, password, salt);
		setClientId(clientId);
		setPassword(password);
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
//	public String getPassword() {
//		return password;
//	}
	public void setPassword(String password) {
		this.password = password;
	}
//	public byte[] getSalt() {
//		return salt;
//	}
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	public String getMessage() {
		return "Credentials Ok!";
	}
}
