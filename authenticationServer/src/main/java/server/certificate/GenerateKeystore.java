package server.certificate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class GenerateKeystore {
	public static String KEYSTOREPATH;
	private static String keystorePassword = "ogobe123";
	public static String TRUSTSTORENAME = "trustStore.ts";
	public static String KEYSTORENAME = "keystore.jks";
	public static String ALIAS = "";
	public static void create(String alias) {
		
		ALIAS = alias;
		KEYSTOREPATH = createKeystoreFilesHierarchy();
		String keyStorePath = KEYSTOREPATH + "\\" + KEYSTORENAME;
		String trustStrorePath = KEYSTOREPATH + "\\" + TRUSTSTORENAME;
		System.setProperty("javax.net.ssl.keyStore", keyStorePath);
		System.setProperty("javax.net.ssl.keyStorePassword", getPassword());
		System.setProperty("javax.net.ssl.trustStore", trustStrorePath);
		System.setProperty("javax.net.ssl.trustStorePassword", getPassword());
		
		KeyStore ks;
		try {
			ks = KeyStore.getInstance(KeyStore.getDefaultType());
			char[] password = keystorePassword.toCharArray();
			ks.load(null, password);
			
			//Store away the keystore.
			File file = new File(keyStorePath);
			if(!file.exists()) {
				FileOutputStream fos = new FileOutputStream(keyStorePath);
				ks.store(fos, password);
				fos.close();
				
			}
			//Create truststore
			file = new File(trustStrorePath);
			if(!file.exists()) {
				FileOutputStream fos = new FileOutputStream(trustStrorePath);
				ks.store(fos, password);
				fos.close();
				
			}
			
			
			
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static String createKeystoreFilesHierarchy() {
		String path = System.getProperty("user.dir") + "\\target\\classes";		
		
		File resources = new File(path);
		
		
		if(!resources.exists())
			resources.mkdir();
		
		return path;		
	}
	public static String getPassword() {
		return keystorePassword;
	}
}
