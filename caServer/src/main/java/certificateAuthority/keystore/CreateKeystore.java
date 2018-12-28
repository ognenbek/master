package certificateAuthority.keystore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class CreateKeystore {
	public static String KEYSTOREPATH;
	public static String KEYSTORENAME = "keystore.jks";
	private static String keystorePassword = "ogobe123";
	public static void create() {

		KEYSTOREPATH = createKeystoreFilesHierarchy();
		
		String path = KEYSTOREPATH + "\\" + KEYSTORENAME;
		
		System.setProperty("javax.net.ssl.keyStorePassword","ogobe123");
		System.setProperty("javax.net.ssl.keyStore",path);
		
		KeyStore ks;
		try {
			ks = KeyStore.getInstance(KeyStore.getDefaultType());
			char[] password = keystorePassword.toCharArray();
			ks.load(null, password);
			
			// Store away the keystore.
			File file = new File(path);
			if(!file.exists()) {
				FileOutputStream fos = new FileOutputStream(path);
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
	
	public static String createKeystoreFilesHierarchy() {
		String path = System.getProperty("user.dir") + "\\target\\classes";		
		
		
		File resources = new File(path);
		
		if(!resources.exists()) {
			resources.mkdir();
		}
		
		return path;		
	}
	public static String getPassword() {
		return keystorePassword;
	}
}
