package server.AuthServer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import server.controllers.ClientCredentials;
import server.controllers.ImportClientCredentialsController;
import server.database.DatabaseStatements;

public class DuplicatePasswordsTest {
	@Test
	public void checkDuplicatePasswords() {
		ImportClientCredentialsController importClientcredentials = new ImportClientCredentialsController();
		importClientcredentials.ImportCredentials("device2", "passwordSame", "admin", "admin");
		importClientcredentials.ImportCredentials("device3", "passwordSame", "admin", "admin");
		
		String client1Password = DatabaseStatements.getClientCredentials("device2");
		String client2Password = DatabaseStatements.getClientCredentials("device3");
		boolean equal = client1Password.equals(client2Password);
		assertEquals(false, equal);
	}
}
