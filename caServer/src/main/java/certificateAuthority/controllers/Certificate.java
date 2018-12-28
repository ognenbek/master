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
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;

import certificateAuthority.keystore.CreateKeystore;
import certificateAuthority.keystore.GenerateKeyAndCertificate;
import certificateAuthority.keystore.ImportCertificatefromString;

public class Certificate {

	private ArrayList<String> certificateList;
	public Certificate() {}
	public Certificate(String certificate, String alias) {
		certificateList = new ArrayList();
		this.certificateList.add(ImportCertificatefromString.importCertificate(certificate, alias));
		this.certificateList.add(loadMainCert());
	}
	public ArrayList<String> getCertificate() {
		return certificateList;
	}
	public void setCertificate(ArrayList<String> certificate) {
		this.certificateList = certificate;
	}
	private static String loadMainCert() {
		KeyStore ks;
		try {
			ks = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream readStream = new FileInputStream(new File(CreateKeystore.KEYSTOREPATH + "\\" + CreateKeystore.KEYSTORENAME));
			ks.load(readStream, CreateKeystore.getPassword().toCharArray());
			readStream.close();
				
		    X509Certificate certificate = (X509Certificate) ks
				.getCertificate(GenerateKeyAndCertificate.ALIAS);
		     byte[] derCert = certificate.getEncoded();
		     return new String(Base64.getEncoder().encode(derCert));
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
}
