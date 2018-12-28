package mqtt.brokerinitialisation;

import java.io.File;
import java.util.Scanner;

import server.certificate.ExportCertificateToFile;
import server.certificate.ExportKeyToFile;
import server.certificate.GenerateKeyAndCertificate;
import server.certificate.GenerateKeystore;
import server.certificate.ImportCaCertificate;

/**
 * Hello world!
 *
 */
public class App 
{
	 public static void main( String[] args )
	    {
	    	String alias = "mqttBroker";
	    	String brokerLocation = "c:\\Program Files (x86)\\mosquitto";
	    	String certificatesLocation = generateCertificatesFile();
	    	
	        GenerateKeystore.create(alias);
			System.out.println("For initialization and launching of the server press [1], for just starting the server press any other number");
	    	Scanner keyboard = new Scanner(System.in);
	    	int choice = keyboard.nextInt();
	    	keyboard.close();
	    	if(choice == 1)
	    		initialize(alias, certificatesLocation);
	    	
	        StartBroker.start(brokerLocation, certificatesLocation, alias);
	        
	    }
	 private static void initialize(String alias, String certificatesLocation) {
		    ImportCaCertificate.importCert();
	        GenerateKeyAndCertificate.generate();
	        ExportKeyToFile.export(certificatesLocation);
	        ExportCertificateToFile.export(certificatesLocation);
	        CopyCACertificateToMosquitto.copy(certificatesLocation);
	 }
	    private static String generateCertificatesFile() {
	    	String path = "c:\\mosquittoCertificates";
	    	File resources = new File(path);
			
			
			if(!resources.exists())
				resources.mkdir();
			
			return path;	
	    }
}
