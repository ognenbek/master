package certificateAuthority.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
  
import sun.security.x509.BasicConstraintsExtension;
import sun.security.x509.CertificateExtensions;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;


public class SignCertificate {
	private static X509Certificate signInternal(X509Certificate certificate, X509Certificate issuerCertificate, PrivateKey issuerPrivateKey) {
		try {
			
			  Principal issuer = issuerCertificate.getSubjectDN();
		      String issuerSigAlg = issuerCertificate.getSigAlgName();
		           
		      byte[] inCertBytes = certificate.getTBSCertificate();
		      X509CertInfo info = new X509CertInfo(inCertBytes);
		      info.set(X509CertInfo.ISSUER, (X500Name) issuer);
		        
//		        make it able to sign others
		      if(certificate.getSubjectDN().getName().equals("CN=CA")){
		    	  CertificateExtensions exts=new CertificateExtensions();
			      BasicConstraintsExtension bce = new BasicConstraintsExtension(true, -1);
			      exts.set(BasicConstraintsExtension.NAME,new BasicConstraintsExtension(false, bce.getExtensionValue()));
			      info.set(X509CertInfo.EXTENSIONS, exts);
		      }
		            	              
		      X509CertImpl outCert = new X509CertImpl(info);
		      outCert.sign(issuerPrivateKey, issuerSigAlg);
		      return outCert;
		      
		} catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
	}
	public static X509Certificate sign(X509Certificate certificate, PrivateKey privateKey) {
		return signInternal(certificate, certificate, privateKey);
	}
	public static X509Certificate sign(X509Certificate certificate) {
		  KeyStore ks;
		try {
			  ks = KeyStore.getInstance(KeyStore.getDefaultType());
			  InputStream readStream = new FileInputStream(new File(CreateKeystore.KEYSTOREPATH + "\\" + CreateKeystore.KEYSTORENAME));
			  ks.load(readStream, CreateKeystore.getPassword().toCharArray());
			  readStream.close();
				
		      X509Certificate issuerCertificate = (X509Certificate) ks
						.getCertificate(GenerateKeyAndCertificate.ALIAS);
		        
		      PrivateKey issuerPrivateKey = (PrivateKey) ks.getKey(GenerateKeyAndCertificate.ALIAS, CreateKeystore.getPassword().toCharArray());
		      return signInternal(certificate, issuerCertificate, issuerPrivateKey);
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
