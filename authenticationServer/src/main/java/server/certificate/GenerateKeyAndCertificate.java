package server.certificate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
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
import java.util.ArrayList;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import server.certificateAuthorityRequests.CAApi;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

public class GenerateKeyAndCertificate {
	private static X509Certificate certificate;
	private static KeyPair keyPair;
	public static void generate() {
		addBouncyCastleAsSecurityProvider();
		
        try {
        	CertAndKeyGen keyGen=new CertAndKeyGen("ECDSA","SHA256withECDSA",null);
            keyGen.generate(256);
            PrivateKey privateKey=keyGen.getPrivateKey();
            
            X509Certificate certificate = keyGen.getSelfCertificate(new X500Name("CN=" + GenerateKeystore.ALIAS), (long) 365 * 24 * 60 * 60);
            
            ArrayList<X509Certificate> certificateList = CAApi.signCertificate(certificate);
            ImportCertificateToKeystore.importCert(certificateList, privateKey);
            
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
	

	public static void addBouncyCastleAsSecurityProvider() {
	    Security.addProvider(new BouncyCastleProvider());
	}
}
