package mqtt.resourceserver;

import java.io.File;
import java.util.Scanner;

import mqtt.subscribers.GeneralSubscriber;
import server.certificate.GenerateKeyAndCertificate;
import server.certificate.GenerateKeystore;
import server.certificate.ImportCaCertificate;

/**
 * Hello world!
 *
 */
public class InitializeServer 
{
	private static String ALIAS = "resourcereceiver";
	private static String caCertificatePath;
    public static void main( String[] args )
    {
    	System.setProperty("java.home", "c:\\Program Files\\Java\\jdk-9");
    	GenerateKeystore.create(ALIAS);
		caCertificatePath = GenerateKeystore.KEYSTOREPATH + "\\ServerCaCer.crt";
		System.out.println("For initialization and launching of the server press [1], for just starting the server press any other number");
    	Scanner keyboard = new Scanner(System.in);
    	int choice = keyboard.nextInt();
    	if(choice==1)
    		initialize();
		GeneralSubscriber.subscribe(caCertificatePath);

		
	}
	
	public static void initialize() {
		/*
		 * initialize server set his own key and certificate and import the certificate of the Certificate Authority(CA) in order to communicate with the other services.
		 */
		
		ImportCaCertificate.importCert();
		GenerateKeyAndCertificate.generate();
		//create the file directory, which holds the data to be processed, and the algorithms how it to be processed
		File file = new File("c:\\Users\\ogo_b\\eclipse\\HadoopTest\\files");
		file.mkdir();
	}
}
