package certificateAuthority.keystore;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CreateApplicaitonProperties {
	private static String PROPERIESFILENAME = "application.properties";
	public static void create() {
		String path = CreateKeystore.KEYSTOREPATH + "\\" + PROPERIESFILENAME;
	    BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(path));
			writer.write(content());
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	private static String content() {
		StringBuffer buff = new StringBuffer();
		String newLine = "\n";
		
		buff.append("server.port = 8081" + newLine);
		buff.append("security.require-ssl=true" + newLine);
		buff.append("server.ssl.key-store-type=PKCS12" + newLine);
		buff.append("server.ssl.key-store = classpath:" + CreateKeystore.KEYSTORENAME + newLine);
		buff.append("server.ssl.key-store-password = " + CreateKeystore.getPassword() + newLine);
		buff.append("server.ssl.key-alias= " + GenerateKeyAndCertificate.ALIAS + newLine);
		
		return buff.toString();
	}
}
