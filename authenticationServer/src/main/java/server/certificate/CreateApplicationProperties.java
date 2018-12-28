package server.certificate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CreateApplicationProperties {
	private static String PROPERIESFILENAME = "application.properties";
	public static void create(String port) {
		String path = GenerateKeystore.KEYSTOREPATH + "\\" + PROPERIESFILENAME;
	    BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(path));
			writer.write(content(port));
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	private static String content(String port) {
		StringBuffer buff = new StringBuffer();
		String newLine = "\n";
		
		buff.append("server.port = " + port + newLine);
		buff.append("security.require-ssl = true" + newLine);
		buff.append("server.ssl.key-store-type = PKCS12" + newLine);
		buff.append("server.ssl.key-store = classpath:" + GenerateKeystore.KEYSTORENAME + newLine);
		buff.append("server.ssl.key-store-password = " + GenerateKeystore.getPassword() + newLine);
		buff.append("server.ssl.key-alias = " + GenerateKeystore.ALIAS + newLine);
		
		return buff.toString();
	}
}
