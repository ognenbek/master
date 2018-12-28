package server.controllers;

import server.database.DatabaseStatements;

public class ClientRole {
	private String clientId;
	private String role;
	private String action;
	
	public ClientRole(String clientId, String role, String action) {
		DatabaseStatements.insertClientRole(clientId, role, action);
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}	
	public static String getRole(String clientId, String action) {
		return DatabaseStatements.getClientRole(clientId, action);
	}
}
