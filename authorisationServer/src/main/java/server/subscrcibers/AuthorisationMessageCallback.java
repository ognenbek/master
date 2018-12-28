package server.subscrcibers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import policy.policy.AuthoriseRequest;
import policy.policy.XmlParsings;
import server.authentication.CheckCredentials;
import server.authentication.GetRole;
import server.publsihers.PublishGeneratedToken;
import server.token.GenerateToken;


public class AuthorisationMessageCallback implements MqttCallback{
	private String CLIENTID = "clientId";
	private String PASSWORD = "password";
	private String REQUEST_XACML = "requestXacml";
	
	private String TOPIC = "topic";
	private String caCertificatePath;
	public AuthorisationMessageCallback(String caCertificatePath) {
		// TODO Auto-generated constructor stub
		this.caCertificatePath = caCertificatePath;
	}

	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
	}

	public void messageArrived(String topicsubscriber, MqttMessage message){
		System.out.println("message arrived");
		// TODO Auto-generated method stub
		/*
		 * Receive the clientId, password of the device and the request that he wants to make( request to store, access... data)
		 * After that send it to the authentication unit to check if the credentials are valid
		 * the format for the clientId and password are the following:
		 * {
		 * 	clientId : clientid1
		 * 	password : pass1
		 *  requestXacml : request
		 *  topic : returnTopic
		 * }
		 */
		String response;
		JSONObject json = new JSONObject(message.toString());
		String clientId = json.getString(CLIENTID);
		String password = json.getString(PASSWORD);
		String requestXacml = json.getString(REQUEST_XACML);
				
		String topicPublishtoken = json.getString(TOPIC);
		//the credentials of the client are checked, if they are correct we can continue with authorising the action that the
		// client wants to perform.
		if(CheckCredentials.check(clientId, password)) {
			System.out.println("Credentials are ok");
			
			String clientAction = XmlParsings.getClientAction(requestXacml);
			
			String role = GetRole.getRole(clientId, clientAction);
			if(role == null)
				response = "invalid role";

			requestXacml = XmlParsings.setClientRole(requestXacml, role);
			
			AuthorisatonReturnMessage result = AuthoriseRequest.authorise(clientId + clientAction, requestXacml);
			
			System.out.println("result: " + result.getMessage());
			if(result.isSuccess()) {
				response = GenerateToken.generate(clientId, compressRequest(requestXacml));
				System.out.println("Token was generated");
				PublishGeneratedToken.publish(response, topicPublishtoken + clientId, caCertificatePath);	
			}
			else
				response = result.getMessage();
			
			
				
		}	
		
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}
	
	private String compressRequest(String xacml) {
		String zippedString = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(out);
			gzip.write(xacml.getBytes("UTF-8"));
		    gzip.close();
		    zippedString = Base64.getEncoder().encodeToString(out.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return zippedString;
	}
}