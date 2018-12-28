package server.certificate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;

public class ImportCertificateToKeystore {
	public static void importCert(ArrayList<X509Certificate> certificateList, PrivateKey privateKey) {
		try {			
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		    
		    //load keystore files
		    File keystoreFile = new File(GenerateKeystore.KEYSTOREPATH + "\\" + GenerateKeystore.KEYSTORENAME);
		    FileInputStream in = new FileInputStream(keystoreFile);
		    keyStore.load(in, GenerateKeystore.getPassword().toCharArray());
		    in.close();
		    
		     
		    FileOutputStream fos = new FileOutputStream(GenerateKeystore.KEYSTOREPATH + "\\" + GenerateKeystore.KEYSTORENAME);
	        java.security.cert.Certificate[] chain = new java.security.cert.Certificate[certificateList.size()];
	        for(int i=0; i<certificateList.size();i++)
	        	chain[i] = (java.security.cert.Certificate) certificateList.get(i);

	        keyStore.setKeyEntry(GenerateKeystore.ALIAS, privateKey, GenerateKeystore.getPassword().toCharArray(), chain);
	        keyStore.store(fos, GenerateKeystore.getPassword().toCharArray());
	        fos.close();
		    
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
