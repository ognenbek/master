package certificateAuthority.keystore;

import java.io.IOException;



public class ExportCertificateToFile {
	public static void export(String alias) {
		String sslPath = "C:\\OpenSSL-Win64\\bin\\openssl.exe";
		String slash = "\\";
		String keystoreLocation = CreateKeystore.KEYSTOREPATH + slash + CreateKeystore.KEYSTORENAME;
		String certificatePath = CreateKeystore.KEYSTOREPATH + slash + "ServerCaCer.crt";
		String certificateForAndroidPath = CreateKeystore.KEYSTOREPATH + slash + "ServerCaCerAndroid.cer";
		
		String cmdCertificate = sslPath + " pkcs12 -in " + ExportKeyToFile.keyP12Location +" -nokeys -out " + certificatePath + " -passin pass:ogobe123";
		
		String cmdCertificateAndroid = "keytool -exportcert -alias " + GenerateKeyAndCertificate.ALIAS + " -keystore " + keystoreLocation + " -file " + certificateForAndroidPath 
				+ " -storepass " + CreateKeystore.getPassword();
		try {
			Process proc = Runtime.getRuntime().exec(cmdCertificate);
			while(proc.waitFor()!=0);
			Runtime.getRuntime().exec(cmdCertificateAndroid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
