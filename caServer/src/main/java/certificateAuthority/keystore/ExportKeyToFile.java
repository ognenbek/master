package certificateAuthority.keystore;

import java.io.IOException;

public class ExportKeyToFile {
	public static String keyP12Location = CreateKeystore.KEYSTOREPATH + "\\" + "keytemp.p12";
	public static void export() {
		String sslPath = "C:\\OpenSSL-Win64\\bin\\openssl.exe";
		String keyLocation = CreateKeystore.KEYSTOREPATH + "\\"+ "serverCa.key";
		String cmdKeystore = "keytool -importkeystore -srckeystore " + CreateKeystore.KEYSTOREPATH + "\\" + CreateKeystore.KEYSTORENAME + " -destkeystore " +
							 keyP12Location +" -deststoretype PKCS12 " + " -deststorepass " + CreateKeystore.getPassword() + " -srcstorepass " + CreateKeystore.getPassword();
		
		String cmdSSL = sslPath +" pkcs12 -passin pass:ogobe123 -in " + keyP12Location +"  -nodes -nocerts -out " + keyLocation;
		System.out.println("before start");
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
}
