package server.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class GetRole {
	/*
	 * If we want to check clients intentions and authorise him we need to take his role first. The role was previously added on the
	 * authentication server. So using the authentication api we will retrieve the role. The authentication api requires the clients id
	 * and the clients action
	 */
	public static String getRole(String clientId, String action) {
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
		
		String url = "https://localhost:8086/getClientRole?clientId=" + clientId + "&action=" + action;
		
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
		     return response.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
}
