package server.AuthServer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import server.controllers.ClientCredentials;
import server.controllers.ImportClientCredentialsController;

public class ImportClientCredentialsTest {
	@Test
	public void ImportClientCredentials() {
		ImportClientCredentialsController importClientcredentials = new ImportClientCredentialsController();
		ClientCredentials client = importClientcredentials.ImportCredentials("device1", "password", "admin", "admin");
		assertEquals("device1", client.getClientId());
	}
	@Test
	public void ImportClientCredentialsWrongAdministrator() {
		ImportClientCredentialsController importClientcredentials = new ImportClientCredentialsController();
		ClientCredentials client = importClientcredentials.ImportCredentials("device1", "password", "admin1", "admin");
		assertEquals(null, client);
	}
}
