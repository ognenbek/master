package certificateAuthority.keystore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.InvalidKeyException;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

public class GenerateKeyAndCertificate {
	public static String ALIAS = "caservercertificate1";
	private static String CERTFICATELOCALFILENAME = "serverCaCer.cer";
	public static void generate() {
		Security.addProvider(new BouncyCastleProvider());
		
        try {
        	CertAndKeyGen keyGen=new CertAndKeyGen("ECDSA","SHA256withECDSA",null);
            keyGen.generate(256);
            PrivateKey privateKey=keyGen.getPrivateKey();
            
            X509Certificate certificate = keyGen.getSelfCertificate(new X500Name("CN=CA"), (long) 365 * 24 * 60 * 60);

			importCertificate(certificate, privateKey, ALIAS);
			
		} catch (CertificateEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
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
	
	private static void importCertificate(X509Certificate certificate, PrivateKey privateKey, String alias) {
		try {
	        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        //load keystore files
	        X509Certificate[] chain = null;
		    	 keyStore.load(null, null);
		    	 
		    	 certificate = SignCertificate.sign(certificate, privateKey);
		    	 chain = new X509Certificate[1];
		    	 chain[0] = certificate;
		    	 
		    	 keyStore.setKeyEntry(alias, privateKey, CreateKeystore.getPassword().toCharArray(), chain);
			        
			        FileOutputStream fos = new FileOutputStream(CreateKeystore.KEYSTOREPATH + "\\" + CreateKeystore.KEYSTORENAME);
			        keyStore.store(fos, CreateKeystore.getPassword().toCharArray());
			        fos.close();          	
			          
			        ExportKeyToFile.export();
			        ExportCertificateToFile.export(alias);
	         	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public static void addBouncyCastleAsSecurityProvider() {
	    Security.addProvider(new BouncyCastleProvider());
	}
	
}
