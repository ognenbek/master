package server.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import server.certificate.GenerateKeystore;
import server.token.GenerateToken;

public class CheckCredentials {
	/*
	 * As soon as we get the credentials from the client we have to check them  if they are valid at the authentication server
	 * connection with the server is established and message Ok i received if the credentials are valid, otherwise not exist if the credentials are invalid.
	 * If the message was ok, meaning that the credentials are valid token i generated for the specific client(iot) and is sent back to the client.
	 * The method returns a token if the credentials are ok otherwise it returns null.
	 */
	public static boolean check(String clientId, String password) {
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
		
		String url = "https://localhost:8086/checkClientCredentials?clientId=" + clientId + "&password=" + password;
		
		URL obj;
		 try {
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
		     String responseMessage = jsonObj.getString("response");
		     System.out.println(responseMessage);
		     //Here we check if the credentials are valid or not so later on we generate the token
		     if(responseMessage.equals("Credentials OK !"))
		    	 return true;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	
	}
}
