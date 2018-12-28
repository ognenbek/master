package mqtt.resourceserver;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import mqtt.publishers.TokenValidityResponsePublisher;
import server.certificate.GenerateKeystore;
import javafx.util.*;
import java.util.Base64;
import java.util.Date;

public class TokensHolder {
	private static HashSet<String> tokenSet = new HashSet();
	//servers which have signed the tokens, in the map we have the serverId and the public key of that server
	private static HashMap<String, PublicKey> authorisationServersSet = new HashMap();
	private static String RESULTOK = "OK";
	private static String RESULTWRONGTOKEN = "wrong token";
	private static String tokenPayload;
	
	public static boolean checkTokenValidity(String token) throws Exception{
		//First check if the token have not expired
		setTokenPayload(token);		
		JSONObject json = new JSONObject(getTokenPayload());
		
		Long date = json.getLong("exp");
		
		if(date < System.currentTimeMillis()/1000) {
			System.out.println("Token is expired");
			if(tokenSet.contains(token))
				tokenSet.remove(token);
			return false;
		}
		//Check if we have the token in cache, which means we have already checked its validity and we can continue immediately
		if(tokenSet.contains(token)) {
			System.out.println("Credentials are ok, token is in cache, it was alredy checked");
			return true;
		}
		
		//First split the token to get the payload(splitting on '.' as the header the payload and the signature are splitted with that chatacter
		//After that decode it. the decoded payload is in json format so we will parse the json iss field to get the issuer.

		
		String issuer = json.getString("iss");

		PublicKey pubKey;
		
		if(!authorisationServersSet.containsKey(issuer)) {
			String pubKeyStr = getAuthorityPublicKey(issuer);
			pubKey = convertStringToKey(pubKeyStr);
			//We dont have the key of the authorisation server that signed the token, so we have to get it from the CA authority
			authorisationServersSet.put(issuer, pubKey);
			System.out.println("Authorisation unit credentials acquired from the CA, and inserted in memory!");
		}
		else {
			pubKey = authorisationServersSet.get(issuer);
			System.out.println("Authorisation unit credentials already in memory !");
		}
		
		try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream readStream = new FileInputStream(new File(GenerateKeystore.KEYSTOREPATH + "\\" + GenerateKeystore.KEYSTORENAME));
			ks.load(readStream, GenerateKeystore.getPassword().toCharArray());
			readStream.close();
			
			Jwts.parser().setSigningKey(pubKey).parseClaimsJws(token);
			System.out.println("Credentials are ok, token inserted in memory");
			tokenSet.add(token);
			return true;
		} catch (JwtException ex) {
			System.out.println(ex);
			System.out.println("Credentials are wrong");
			return false;
		}
	}
	
	private static String getAuthorityPublicKey(String issuer){
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
		
		String url = "https://localhost:8081/getPublicKey?clientId=" + issuer;
		
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
		     String authorityKey = jsonObj.getString("key");
		     //Here we check if the credentials are valid or not so later on we generate the token
		     if(authorityKey==null)
		    	 return null;

		     return authorityKey;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static PublicKey convertStringToKey(String key) {
		try {
			byte[] decoded = Base64.getDecoder().decode(key);
			KeyFactory kf = KeyFactory.getInstance("ECDH", "BC");
			return kf.generatePublic(new X509EncodedKeySpec(decoded));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public static String decompressXacml(String xacml) {
		StringBuffer xacmlDecompressed = new StringBuffer();
		try {
			GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(xacml)));
			BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
			String line;
			while ((line=bf.readLine())!=null) {
			  	xacmlDecompressed.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xacmlDecompressed.toString();
	}
	
	public static String getTokenPayload() {
		return tokenPayload;
	}

	public static void setTokenPayload(String token) {
		TokensHolder.tokenPayload = new String(Base64.getDecoder().decode(token.split("\\.")[1]));
	}
}
