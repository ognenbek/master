package server.token;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import server.certificate.GenerateKeystore;

public class GenerateToken {
	public static String generate(String clientId, String xacmlRequest) {
		/*
		 * The token consist of the clientId, the issuer of the token and the xacml request zipped and encdoed with BASE64( so that it can be transfered on the network)
		 */
		System.out.println("Index: " + xacmlRequest);
		PrivatePublicKeyHolder holder = getKeys();
		 Calendar calendar = Calendar.getInstance();

		  calendar.add(Calendar.DATE, 3);
		  
		  Date date = calendar.getTime();
	String issuer =  GenerateKeystore.ALIAS;
   	Algorithm algorithm = Algorithm.ECDSA256((ECPublicKey)holder.getPublicKey(), (ECPrivateKey)holder.getPrivateKey());
     	String token = JWT.create()
     			.withIssuer(issuer)
     			.withClaim("clientId", clientId)
     			.withClaim("xacml", xacmlRequest)
     			.withExpiresAt(date)
     			.sign(algorithm);
     	
     	
     	//validation off the token, just for an example     

//		try {
//			Jwts.parser().setSigningKey(holder.getPublicKey()).parseClaimsJws(token);
//			System.out.println("Credentials are ok");
//		} catch (JwtException ex) {
//			System.err.println(ex);
//			System.out.println("Credentials are wrong");
//		}
     	
		return token;
	}	
	
	private static PrivatePublicKeyHolder getKeys() {
		PrivatePublicKeyHolder keyholder = new PrivatePublicKeyHolder();
		KeyStore ks;
		try {
			ks = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream readStream = new FileInputStream(new File(GenerateKeystore.KEYSTOREPATH + "\\" + GenerateKeystore.KEYSTORENAME));
			ks.load(readStream, GenerateKeystore.getPassword().toCharArray());
			readStream.close();
			

			
			try {
				keyholder.setPrivateKey((PrivateKey) ks.getKey(GenerateKeystore.ALIAS, GenerateKeystore.getPassword().toCharArray()));
				keyholder.setPublicKey(ks.getCertificate(GenerateKeystore.ALIAS).getPublicKey());
				keyholder.setCertificate(ks.getCertificate(GenerateKeystore.ALIAS));
			} catch (UnrecoverableKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		
		return keyholder;

	}
}
