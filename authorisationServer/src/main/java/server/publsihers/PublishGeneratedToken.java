package server.publsihers;

import javax.net.ssl.SSLSocketFactory;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import server.mqttTools.MqttTools;

public class PublishGeneratedToken {
	public static void publish(String token, String topic, String caCertificatePath) {
		/*
		 * After authorising the client we have to send back his access token, if the authorisation was succesful or deny if it was not.
		 * the token will consist of the issuer the client and the xacml request zipped and encoded using BASE64 encoding.
		 */
		String serverUrl = "ssl://localhost:8883";
		MqttClient client;
		MqttTools mqttTools = MqttTools.getInstance();
		
		
		try {
			client = new MqttClient(serverUrl, "AuthorisationTokenPublisher");
			MqttConnectOptions options = new MqttConnectOptions();
			
			options.setConnectionTimeout(60);
			options.setKeepAliveInterval(60);
			options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
			
			SSLSocketFactory socketFactory = mqttTools.getSocketFactory(caCertificatePath, "ogobe123");
			options.setSocketFactory(socketFactory);

			client.connect(options);
			MqttMessage message;
			if(token!=null) {
				System.out.println("Token was published");
				message = new MqttMessage(token.getBytes());
				message.setQos(0);
			}
			else
				message = new MqttMessage("invalidCredentials".getBytes());

			client.publish(topic, message);
			client.disconnect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
