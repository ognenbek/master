package server.certificateAuthorityRequests;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import server.certificate.GenerateKeystore;


public class CAApi {
	public static ArrayList<X509Certificate> signCertificate(X509Certificate certificate) {
		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
			    new javax.net.ssl.HostnameVerifier(){
			 
			        public boolean verify(String hostname,
			                javax.net.ssl.SSLSession sslSession) {
			            if (hostname.equals("localhost")) {
			                return true;
			            }
			            return false;
			        }
			    });
		
		String url = "https://localhost:8081/certificate?certificate=" + encodeCertificate(certificate) + "&alias=" + GenerateKeystore.ALIAS;
		
        
		try {
			 URL obj;
			 String certificateStr ="";
			 String certificateCaStr = "";
			 obj = new URL(url);
			 HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		     // optional default is GET
		     con.setRequestMethod("GET");
		     BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		     String inputLine;
		     StringBuffer response = new StringBuffer();
		     
		     while ((inputLine = in.readLine()) != null) {
		         response.append(inputLine);
		     }
		     
		     JSONObject jsonObj = new JSONObject(response.toString());


		     JSONArray arr = jsonObj.getJSONArray("certificate");
		     certificateStr = arr.getString(0);
		     certificateCaStr = arr.getString(1);
		         
		     in.close();
		     byte [] decoded = Base64.getDecoder().decode(certificateStr);
			 X509Certificate certificateSigned = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decoded));
			 byte [] decodedCa = Base64.getDecoder().decode(certificateCaStr);
			 X509Certificate certificateCa = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decodedCa));
			 
			 ArrayList<X509Certificate> certificatesList = new ArrayList();
			 certificatesList.add(certificateSigned);
			 certificatesList.add(certificateCa);
			 return certificatesList;
			 
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private static String encodeCertificate(X509Certificate certificate) {
		byte[] derCert;
		try {
			derCert = certificate.getEncoded();
			return new String(Base64.getEncoder().encode(derCert));
		} catch (CertificateEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
		
	}
}
