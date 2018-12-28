package server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hashtools.HashedPasswordGenerator;
import server.database.DatabaseStatements;

@RestController
public class CheckClientCredentialsController {
	@GetMapping("/checkClientCredentials")
	public ClientCredentialsExist checkCredentials(@RequestParam(value="clientId") String clientId, @RequestParam(value="password") String password) {
		try {
//			byte[]salt = DatabaseStatements.getClientSalt(clientId);
			String hashedPass = HashedPasswordGenerator.reproduceClientPassword(clientId, password);
			return new ClientCredentialsExist(clientId, hashedPass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
