package certificateAuthority.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Base64;

import certificateAuthority.data.database;
import certificateAuthority.keystore.CreateKeystore;

public class PublicKey {
	
	private String key = null;
	
	public PublicKey(String clientId) {
		try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream readStream = new FileInputStream(new File(CreateKeystore.KEYSTOREPATH + "\\" + CreateKeystore.KEYSTORENAME));
			ks.load(readStream, CreateKeystore.getPassword().toCharArray());
			readStream.close();
			
			key = Base64.getEncoder().encodeToString(ks.getCertificate(clientId).getPublicKey().getEncoded());
			
			System.out.println(key);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
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
	public String getKey() {
		return key;
	}
}
