package certificateAuthority.controllers;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import certificateAuthority.keystore.CreateApplicaitonProperties;
import certificateAuthority.keystore.CreateKeystore;
import certificateAuthority.keystore.ExportCertificateToBSKeystore;
import certificateAuthority.keystore.ExportCertificateToFile;
import certificateAuthority.keystore.ExportKeyToFile;
import certificateAuthority.keystore.GenerateKeyAndCertificate;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    	CreateKeystore.create();
    	
    	System.out.println("For initialization and launching of the server press [1], for just starting the server press any other number");
    	Scanner keyboard = new Scanner(System.in);
    	int choice = keyboard.nextInt();
    	if(choice==1)
    		initialize();
    	
        SpringApplication.run(Application.class, args);
    }
    
    private static void initialize() {
    	GenerateKeyAndCertificate.generate();
    	CreateApplicaitonProperties.create();
    	ExportCertificateToBSKeystore.export();
    }
}
	