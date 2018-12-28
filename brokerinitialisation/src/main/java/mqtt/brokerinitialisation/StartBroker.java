package mqtt.brokerinitialisation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class StartBroker {
	public static void start(String brokerLocation, String certificatesLocation, String alias) {
		String slash = "\\";
		String caCertificate = certificatesLocation + slash + "ServerCaCer.crt";
		String brokerCertificate = certificatesLocation + slash + alias+"Cer.crt";
		String brokerKey = certificatesLocation + slash + alias + "Key.key";
		String configurationFileLocation = certificatesLocation + slash + "customConfiguration.conf";
		generateConfigurationFile(configurationFileLocation, caCertificate, brokerCertificate, brokerKey);
		
		startBroker(brokerLocation, configurationFileLocation);
		
	}
	private static void startBroker(String brokerLocation, String configurationFileLocation) {
		String mosquittoExe = "\""+brokerLocation+"\\mosquitto.exe\"";
		String cmd = mosquittoExe + " -c " + configurationFileLocation + " -v";
		System.out.println(cmd);
		try {
			Runtime.getRuntime().exec(cmd);
			System.out.println("======================BROKER STARTED====================");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void generateConfigurationFile(String configurationfileLocation, String caCertificate, String brokerCertificate, String brokerKey){
		PrintWriter writer;
		String port = "port 8883";
		String caFile = "cafile " + caCertificate;
		String keyFile = "keyfile " + brokerKey;
		String certFile = "certfile " + brokerCertificate;
		String tlsVersion = "tls_version tlsv1";
		String requireCertificate = "require_certificate false";
		try {
			writer = new PrintWriter(configurationfileLocation, "UTF-8");
			writer.println(port);
			writer.println(caFile);
			writer.println(keyFile);
			writer.println(certFile);
			writer.println(tlsVersion);
			writer.println(requireCertificate);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
