package server.authorisationServer;

import java.util.Scanner;

import database.CreateDb;
import server.certificate.CreateApplicationProperties;
import server.certificate.GenerateKeyAndCertificate;
import server.certificate.GenerateKeystore;
import server.certificate.ImportCaCertificate;
import server.subscrcibers.AuthorisationSubscriber;

public class InitializeServer {
	private static String ALIAS = "authorisationserver";
	private static String caCertificatePath;
	public static void main(String[] args) {	
		GenerateKeystore.create(ALIAS);
		caCertificatePath = GenerateKeystore.KEYSTOREPATH + "\\ServerCaCer.crt";		
		
		System.out.println("For initialization and launching of the server press [1], for just starting the server press any other number");
    	Scanner keyboard = new Scanner(System.in);
    	int choice = keyboard.nextInt();
    	if(choice==1)
    		initialize();
		AuthorisationSubscriber.subscribe(caCertificatePath);
		
	}
	
	public static void initialize() {
		/*
		 * initialize server set his own key and certificate and import the certificate of the Certificate Authority(CA) in order to communicate with the other services.
		 */		
		CreateDb.create();
		ImportCaCertificate.importCert();
		GenerateKeyAndCertificate.generate();
	
	}
}
