package server.certificate;

import java.io.IOException;

public class ImportCaCertificate {
	public static void importCert() {
		String slash ="\\";
		String caCertificateLocation = GenerateKeystore.KEYSTOREPATH + slash + "serverCaCer.crt";
		String trustStoreLocation = GenerateKeystore.KEYSTOREPATH + slash + GenerateKeystore.TRUSTSTORENAME;
		String cmdStr = "keytool -import -alias caservercertificate -file " + caCertificateLocation + " -keystore " + trustStoreLocation + " -storepass " + GenerateKeystore.getPassword() + " -noprompt";
		try {
			Runtime.getRuntime().exec(cmdStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
