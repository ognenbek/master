package server.controllers;

import server.database.DatabaseStatements;

public class AdministratorCredentials {
	private String username;
	private String password;
	private String section;
	private byte[] salt;
	
	public AdministratorCredentials(String username, String password, String section, byte[] salt) {
		// TODO Auto-generated constructor stub
		DatabaseStatements.insertAdminCredentials(username, password, section, salt);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
}
