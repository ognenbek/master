package server.certificate;

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
import java.util.Base64;

public class ExportCertificateFromKeystore {
	public static String export() {
		try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			
			InputStream readStream = new FileInputStream(new File(GenerateKeystore.KEYSTOREPATH + "//" + GenerateKeystore.KEYSTORENAME));
			ks.load(readStream, GenerateKeystore.getPassword().toCharArray());
			
	        X509Certificate cert = (X509Certificate) ks
					.getCertificate(GenerateKeystore.ALIAS);
	        
	        byte[] derCert = cert.getEncoded();
	        //CERTIFICATE IS IN PEM FORMAT
			String certPem = new String(Base64.getEncoder().encode(derCert));
	        return certPem;
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
		return null;
	}
}
