package server.database;

import hashtools.HashedPasswordGenerator;
import hashtools.PasswordSaltKeeper;

public class CreateAdminUser {
	private static String username = "admin";
	private static String password = "admin";
	public static void crate() {
		PasswordSaltKeeper passSalt = HashedPasswordGenerator.getHashedPassword(password);
		DatabaseStatements.insertAdminCredentials(username, passSalt.getPassword(), "admin", passSalt.getSalt());
	}
}
