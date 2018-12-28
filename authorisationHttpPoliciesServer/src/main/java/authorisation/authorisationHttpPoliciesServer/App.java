package authorisation.authorisationHttpPoliciesServer;

import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import jackson.JacskonToPolicyParser;
import server.certificate.CreateApplicationProperties;
import server.certificate.GenerateKeyAndCertificate;
import server.certificate.ImportCaCertificate;

@SpringBootApplication
public class App 
{

    public static void main( String[] args )
    {
//    	System.out.println("For initialization and launching of the server press [1], for just starting the server press any other number");
//    	Scanner keyboard = new Scanner(System.in);
//    	int choice = keyboard.nextInt();
//    	if(choice==1)
//    		initialize();
   
    	
    	
    	JacskonToPolicyParser parser = new JacskonToPolicyParser();
    	parser.parse(returnProcessingPolicy());
    }
    
    public static void initialize() {
		/*
		 * initialize server set his own key and certificate and import the certificate of the Certificate Authority(CA) in order to communicate with the other services.
		 */		
		ImportCaCertificate.importCert();
		GenerateKeyAndCertificate.generate();
		CreateApplicationProperties.create("8084");
	
	}
    public static String returnAcquirePolicy() {
    	return "{\r\n" + 
    			"\"policyName\" : \"AcquirePolicy\",\r\n" + 
    			"\"policyAlgorithm\" : \"permit-overrides\",\r\n" + 
    			"\"basicAttributes\" : [\r\n" + 
    			"	{ \r\n" + 
    			"	 \"value\" : \"acquire\", \"id\" : \"actionId\", \"category\" : \"action\" 	\r\n" + 
    			"	}	\r\n" + 
    			"],\r\n" + 
    			"\"rule\" : [\r\n" + 
    			"	{\r\n" + 
    			"		\"action\" : \"Permit\",\r\n" + 
    			"		\"id\" : \"A-can-Acquire\",\r\n" + 
    			"		\"basicAttributes\" : [\r\n" + 
    			"			{ \r\n" + 
    			"				 \"value\" : \"A\", \"id\" : \"roleId\", \"category\" : \"subject\" \r\n" + 
    			"			}	\r\n" + 
    			"		],\r\n" + 
    			"		\"condition\" : [\r\n" + 
    			"			{\r\n" + 
    			"					\"action\" : \"and\",\r\n" + 
    			"					\"basicAttributes\" : [\r\n" + 
    			"					{ \r\n" + 
    			"						\"value\" : \"5\", \"id\" : \"environmentId\", \"category\" : \"environment\", \"rule\": \"greater-than\" \r\n" + 
    			"					},	\r\n" + 
    			"					{ \r\n" + 
    			"						\"value\" : \"10\", \"id\" : \"environmentId\", \"category\" : \"environment\" , \"rule\": \"less-than\"\r\n" + 
    			"					}\r\n" + 
    			"				]\r\n" + 
    			"			}\r\n" + 
    			"		],\r\n" + 
    			"		\"message\" : \"You can acquire\"\r\n" + 
    			"	}\r\n" + 
    			"],\r\n" + 
    			"\"message\" : \"you can not acquire\"\r\n" + 
    			"}";
    }
    public static String returnProcessingPolicy() {
    	return  "{\r\n" + 
    			"\"policyName\" : \"ProcessingPolicy\",\r\n" + 
    			"\"policyAlgorithm\" : \"permit-overrides\",\r\n" + 
    			"\"basicAttributes\" : [\r\n" + 
    			"	{ \r\n" + 
    			"	 \"value\" : \"process\", \"id\" : \"actionId\", \"category\" : \"action\" 	\r\n" + 
    			"	}	\r\n" + 
    			"],\r\n" + 
    			"\"rule\" : [\r\n" + 
    			"	{\r\n" + 
    			"		\"action\" : \"Permit\",\r\n" + 
    			"		\"id\" : \"A-can-Process\",\r\n" + 
    			"		\"basicAttributes\" : [\r\n" + 
    			"			{ \r\n" + 
    			"				 \"value\" : \"A\", \"id\" : \"roleId\", \"category\" : \"subject\" \r\n" + 
    			"			}	\r\n" + 
    			"		],\r\n" + 
    			"		\"condition\" : [\r\n" + 
    			"			{\r\n" + 
    			"					\"action\" : \"and\",\r\n" + 
    			"					\"basicAttributes\" : [\r\n" + 
    			"					{ \r\n" + 
    			"						\"value\" : \"5\", \"id\" : \"environmentId\", \"category\" : \"environment\", \"rule\": \"greater-than\" \r\n" + 
    			"					},	\r\n" + 
    			"					{ \r\n" + 
    			"						\"value\" : \"10\", \"id\" : \"environmentId\", \"category\" : \"environment\" , \"rule\": \"less-than\"\r\n" + 
    			"					}\r\n" + 
    			"				]\r\n" + 
    			"			}\r\n" + 
    			"		],\r\n" + 
    			"		\"message\" : \"You can acquire\"\r\n" + 
    			"	}\r\n" + 
    			"],\r\n" + 
    			"\"message\" : \"you can not acquire\"\r\n" + 
    			"}";
    }
    public static String returnStorePolicy() {
    	return "{\r\n" + 
    			"\"policyName\" : \"StorePolicy\",\r\n" + 
    			"\"policyAlgorithm\" : \"permit-overrides\",\r\n" + 
    			"\"basicAttributes\" : [\r\n" + 
    			"	{ \r\n" + 
    			"	 \"value\" : \"store\", \"id\" : \"actionId\", \"category\" : \"action\" 	\r\n" + 
    			"	}	\r\n" + 
    			"],\r\n" + 
    			"\"rule\" : [\r\n" + 
    			"	{\r\n" + 
    			"		\"action\" : \"Permit\",\r\n" + 
    			"		\"id\" : \"A-can-Store\",\r\n" + 
    			"		\"basicAttributes\" : [\r\n" + 
    			"			{ \r\n" + 
    			"				 \"value\" : \"A\", \"id\" : \"roleId\", \"category\" : \"subject\" \r\n" + 
    			"			}	\r\n" + 
    			"		],\r\n" + 
    			"		\"condition\" : [\r\n" + 
    			"			{\r\n" + 
    			"					\"action\" : \"and\",\r\n" + 
    			"					\"basicAttributes\" : [\r\n" + 
    			"					{ \r\n" + 
    			"						\"value\" : \"5\", \"id\" : \"environmentId\", \"category\" : \"environment\", \"rule\": \"greater-than\" \r\n" + 
    			"					},	\r\n" + 
    			"					{ \r\n" + 
    			"						\"value\" : \"10\", \"id\" : \"environmentId\", \"category\" : \"environment\" , \"rule\": \"less-than\"\r\n" + 
    			"					}\r\n" + 
    			"				]\r\n" + 
    			"			}\r\n" + 
    			"		],\r\n" + 
    			"		\"message\" : \"You can store\"\r\n" + 
    			"	}\r\n" + 
    			"],\r\n" + 
    			"\"message\" : \"you can not store\"\r\n" + 
    			"}";
    }
}
