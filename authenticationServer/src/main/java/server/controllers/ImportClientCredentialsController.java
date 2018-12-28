package server.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hashtools.HashedPasswordGenerator;
import hashtools.PasswordSaltKeeper;
import server.database.DatabaseStatements;

@RestController
public class ImportClientCredentialsController {
	/*
	 * path: https://localhost:8086/sendClientCredentials?clientId=clientid&password=password&adminId=admin&adminPass=admin
	 */
	@GetMapping("/sendClientCredentials")
	public ClientCredentials ImportCredentials(@RequestParam(value="clientId") String clientId, @RequestParam(value="password") String password, 
			@RequestParam(value="adminId") String adminId, @RequestParam(value="adminPass") String adminPass) {
		try {
			//get the hashed value of the password so it can be compared with the hashed value in the database
			adminPass = HashedPasswordGenerator.reproduceAdminPassword(adminId, adminPass);
			
			if( DatabaseStatements.checkAdminCredentials(adminId, adminPass) && !DatabaseStatements.CheckCredentialsExist(clientId)) {
				System.out.println("GOOD CREDENTIALS");
				PasswordSaltKeeper passwordSaltMap = HashedPasswordGenerator.getHashedPassword(password);
				return new ClientCredentials(clientId, passwordSaltMap.getPassword(), passwordSaltMap.getSalt());
			}
			else
				return null;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
