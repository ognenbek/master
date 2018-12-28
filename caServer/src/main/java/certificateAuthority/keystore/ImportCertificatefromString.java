package certificateAuthority.keystore;

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
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class ImportCertificatefromString {
	public static String importCertificate(String certificateStr, String alias) {
		try {
	    	byte [] decoded = Base64.getDecoder().decode(certificateStr);
			X509Certificate certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decoded));
			X509Certificate certSigned = SignCertificate.sign(certificate);
			
			
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		    
		    //load keystore files
		    File keystoreFile = new File(CreateKeystore.KEYSTOREPATH + "\\" + CreateKeystore.KEYSTORENAME);
		    FileInputStream in = new FileInputStream(keystoreFile);
		    keyStore.load(in, CreateKeystore.getPassword().toCharArray());
		    in.close();
		    
		  
		  
			//First create a chain of certificates
			X509Certificate[] chain = new X509Certificate[2];
	        chain[0] = certSigned;
	        chain[1] = loadMainCert();
	        //store it in the keystore
	        FileOutputStream fos;
			
	        keyStore.setKeyEntry(alias, loadPrivateKey(), CreateKeystore.getPassword().toCharArray(), chain);
	        
	        fos = new FileOutputStream(CreateKeystore.KEYSTOREPATH + "\\" + CreateKeystore.KEYSTORENAME);
		    keyStore.store(fos, CreateKeystore.getPassword().toCharArray());
		    fos.close();		   		    
		    
		    //in the end load the chain from keystore encode it and return it back
//		    certSigned = loadSignedCert(alias);
		    byte[] derCert = certSigned.getEncoded();
			return new String(Base64.getEncoder().encode(derCert));
			
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private static X509Certificate loadSignedCert(String alias) {
		KeyStore ks;
		try {
			ks = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream readStream = new FileInputStream(new File(CreateKeystore.KEYSTOREPATH + "\\" + CreateKeystore.KEYSTORENAME));
			ks.load(readStream, CreateKeystore.getPassword().toCharArray());
			readStream.close();
				
		    X509Certificate certificate = (X509Certificate) ks
				.getCertificate(alias);
		    return certificate;
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
		return null;
	}
	private static X509Certificate loadMainCert() {
		KeyStore ks;
		try {
			ks = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream readStream = new FileInputStream(new File(CreateKeystore.KEYSTOREPATH + "\\" + CreateKeystore.KEYSTORENAME));
			ks.load(readStream, CreateKeystore.getPassword().toCharArray());
			readStream.close();
				
		    X509Certificate rootCertificate = (X509Certificate) ks
				.getCertificate(GenerateKeyAndCertificate.ALIAS);
		    return rootCertificate;
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
		return null;
		 
	}
	private static PrivateKey loadPrivateKey() {
			KeyStore ks;
			try {
				ks = KeyStore.getInstance(KeyStore.getDefaultType());
				InputStream readStream = new FileInputStream(new File(CreateKeystore.KEYSTOREPATH + "\\" + CreateKeystore.KEYSTORENAME));
				ks.load(readStream, CreateKeystore.getPassword().toCharArray());
				readStream.close();
				
				return (PrivateKey) ks.getKey(GenerateKeyAndCertificate.ALIAS, CreateKeystore.getPassword().toCharArray());
				
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
			} catch (UnrecoverableKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
	}
}
