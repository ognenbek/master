package server.AuthServer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import server.controllers.ClientCredentials;
import server.controllers.ImportClientCredentialsController;

public class ImportDuplicateClientCredentialsTest {	
	@Test
	public void ImportDuplicateClientCredentials() {
		ImportClientCredentialsController importClientcredentials = new ImportClientCredentialsController();
		assertEquals(null, importClientcredentials.ImportCredentials("device1", "password", "admin", "admin"));
	}
}
