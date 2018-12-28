package test;

import java.util.HashMap;

import hashtools.HashedPasswordGenerator;
import hashtools.PasswordSaltKeeper;
import server.database.DatabaseStatements;

public class ValidatePassword {
	public static void get() {
		String password = "ogobe123";
		String username = "android0";
		PasswordSaltKeeper passSalt = HashedPasswordGenerator.getHashedPassword(password);
		DatabaseStatements.insertAdminCredentials(username, passSalt.getPassword(), "sectiona", passSalt.getSalt());
		
		
		byte[] salt = DatabaseStatements.getAdminSalt(username);
		String hashedPass = HashedPasswordGenerator.reproduceAdminPassword(username, password);
		System.out.println(hashedPass);
		System.out.println(DatabaseStatements.checkAdminCredentials(username, hashedPass));
		
		
	}
}
