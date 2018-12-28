package server.controllers;

import server.database.DatabaseStatements;

public class ClientCredentialsExist {
	private String response;
	public ClientCredentialsExist(String clientId, String password) {
		
		response = DatabaseStatements.checkClientCredentials(clientId, password);
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
}
