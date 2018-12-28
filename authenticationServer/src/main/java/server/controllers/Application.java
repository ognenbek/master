package server.controllers;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import server.certificate.EstablishSignedCertificate;
import server.certificate.GenerateKeystore;
import server.database.CreateAdminUser;
import server.database.CreateDb;
import test.ValidatePassword;

@SpringBootApplication
public class Application {
	private static String ALIAS = "authserver";
	public static void main(String[] args) {
		System.out.println("For initialization and launching of the server press [1], for just starting the server press any other number");
    	Scanner keyboard = new Scanner(System.in);
    	int choice = keyboard.nextInt();
    	if(choice==1) {
    		EstablishSignedCertificate.establish(ALIAS,"8086");
			CreateDb.create(); 
	}
		SpringApplication.run(Application.class, args);
	}
}
