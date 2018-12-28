package server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hashtools.HashedPasswordGenerator;
import hashtools.PasswordSaltKeeper;
import server.database.DatabaseStatements;

@RestController
public class importClientRoleControler {

	/*
	 * path: https://localhost:8086/sendClientRole?clientId=clientid&role=role&action=action&adminId=admin&adminPass=adminpass
	 */
	@GetMapping("/sendClientRole")
	public String clientRole(@RequestParam(value="clientId") String clientId, @RequestParam(value="role") String role, 
			@RequestParam(value="action") String action, @RequestParam(value="adminId") String adminId, 
			@RequestParam(value="adminPass") String adminPass) {
		//get the hashed value of the password so it can be compared with the hashed value in the database
		adminPass = HashedPasswordGenerator.reproduceAdminPassword(adminId, adminPass);
		
		if( DatabaseStatements.checkAdminCredentials(adminId, adminPass)) {
			new ClientRole(clientId, role, action);
			return "Client role added";
		} else {
			return "WRONG CREDENTIALS";
		}
	}
}
