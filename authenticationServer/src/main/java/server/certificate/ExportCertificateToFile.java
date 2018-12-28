package server.certificate;

import java.io.IOException;


public class ExportCertificateToFile {
	public static void export() {
		String sslPath = "C:\\OpenSSL-Win64\\bin\\openssl.exe";
		String slash = "\\";
		String certificatePath = GenerateKeystore.KEYSTOREPATH + slash + GenerateKeystore.ALIAS + "Cer.crt";
		String keystoreName = GenerateKeystore.KEYSTOREPATH + slash + GenerateKeystore.KEYSTORENAME; 
		String password = GenerateKeystore.getPassword();
		String keystoreP12Name = GenerateKeystore.KEYSTOREPATH + slash + "keytemp.p12";
		/*
		 * If key was not generated previously
		 */
//		String cmdKeytoolString = "keytool -importkeystore -srckeystore " + keystoreName + " -destkeystore " + keystoreP12Name + 
//				" -deststoretype PKCS12 -deststorepass " + password + " -srcstorepass " + password;

		String cmdSSlString = sslPath + " pkcs12 -in " + ExportKeyToFile.keyP12Location +" -nokeys -out " + certificatePath + " -passin pass:ogobe123";
		try {
//			Process proc = Runtime.getRuntime().exec(cmdKeytoolString);
//			while(proc.waitFor()!=0);
			Runtime.getRuntime().exec(cmdSSlString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void export(String location) {
		String sslPath = "C:\\OpenSSL-Win64\\bin\\openssl.exe";
		String slash = "\\";
		String certificatePath = location + slash + GenerateKeystore.ALIAS + "Cer.crt";
		String keystoreP12Name = GenerateKeystore.KEYSTOREPATH  + slash + "keytemp.p12";
		
		String cmdSSlString = sslPath + " pkcs12 -in " + keystoreP12Name+" -nokeys -out " + certificatePath + " -passin pass:ogobe123";
		System.out.println(cmdSSlString);
		try {
			Runtime.getRuntime().exec(cmdSSlString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
