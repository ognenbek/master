package server.controllers;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hashtools.HashedPasswordGenerator;
import hashtools.PasswordSaltKeeper;

@RestController
public class ImportAdministratorCredentialsController {
	@GetMapping("/sendAdminCredentials")
	public String adminCredentials (@RequestParam(value="username") String username, @RequestParam(value="password") String password, @RequestParam(value="section") String section) {
		PasswordSaltKeeper passwordSaltHasmap = HashedPasswordGenerator.getHashedPassword(password);
		new AdministratorCredentials(username, passwordSaltHasmap.getPassword(), section, passwordSaltHasmap.getSalt());
		return "ok";
	}
	
}
