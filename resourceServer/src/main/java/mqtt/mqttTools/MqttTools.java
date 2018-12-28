package mqtt.mqttTools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class MqttTools {
	private static MqttTools instance;
	private MqttTools() {
		
	}
	public static MqttTools getInstance() {
		if(instance == null)
			instance = new MqttTools();
		return instance;
	}
	public SSLSocketFactory  getSocketFactory(final String caCrtFile, final String password) {
		Security.addProvider(new BouncyCastleProvider());

		// load CA certificate
		X509Certificate caCert = null;

		FileInputStream fis;
		try {
			fis = new FileInputStream(caCrtFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");

			while (bis.available() > 0) {
				caCert = (X509Certificate) cf.generateCertificate(bis);
			}

			

			// CA certificate is used to authenticate server
			KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
			caKs.load(null, null);
			caKs.setCertificateEntry("ca-certificate", caCert);
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
			tmf.init(caKs);
			
			SSLContext context = SSLContext.getInstance("TLSv1.2");
			context.init(null, tmf.getTrustManagers(), null);
			
			return context.getSocketFactory();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
