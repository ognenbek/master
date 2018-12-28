package server.certificate;

import server.certificateAuthorityRequests.CAApi;

public class EstablishSignedCertificate {
	public static void establish(String alias, String port) {
		/*
		 * This class initializes the server application.properties and sends the certificate to CA for signing.
		 * The alias of the server is needed along with the port number which will be used to start the server.
		 */
		GenerateKeystore.create(alias);
		ImportCaCertificate.importCert();
		GenerateKeyAndCertificate.generate();
		CreateApplicationProperties.create(port);
	}	
}
