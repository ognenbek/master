package server.certificate;

import java.io.IOException;

public class ExportKeyToFile {
	public static String keyP12Location = GenerateKeystore.KEYSTOREPATH + "\\" + "keytemp.p12";
	public static void export() {
		String sslPath = "C:\\OpenSSL-Win64\\bin\\openssl.exe";
		String keyLocation = GenerateKeystore.KEYSTOREPATH + "\\"+ GenerateKeystore.ALIAS + "Key.key";
		String cmdKeystore = "keytool -importkeystore -srckeystore " + GenerateKeystore.KEYSTOREPATH + "\\" + GenerateKeystore.KEYSTORENAME + " -destkeystore " +
							 keyP12Location +" -deststoretype PKCS12 " + " -deststorepass " + GenerateKeystore.getPassword() + " -srcstorepass " + GenerateKeystore.getPassword();
		
		String cmdSSL = sslPath +" pkcs12 -passin pass:ogobe123 -in " + keyP12Location +"  -nodes -nocerts -out " + keyLocation;
		try {
			Process proc = Runtime.getRuntime().exec(cmdKeystore);
			while(proc.waitFor()!=0);
			Runtime.getRuntime().exec(cmdSSL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void export(String location) {
		String sslPath = "C:\\OpenSSL-Win64\\bin\\openssl.exe";
		String keyLocation = "\"" + location + "\\"+ GenerateKeystore.ALIAS + "Key.key" + "\"";
		
		String cmdKeystore = "keytool -importkeystore -srckeystore " + GenerateKeystore.KEYSTOREPATH + "\\" + GenerateKeystore.KEYSTORENAME + " -destkeystore " +
							 keyP12Location +" -deststoretype PKCS12 " + " -deststorepass " + GenerateKeystore.getPassword() + " -srcstorepass " + GenerateKeystore.getPassword();
		
		String cmdSSL = sslPath +" pkcs12 -passin pass:ogobe123 -in " + keyP12Location +"  -nodes -nocerts -out " + keyLocation;
		System.out.println(cmdKeystore);
		System.out.println(cmdSSL);
		try {
			Process proc = Runtime.getRuntime().exec(cmdKeystore);
			System.out.println("process1");
			while(proc.waitFor()!=0);
			System.out.println("process2");
			Runtime.getRuntime().exec(cmdSSL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
