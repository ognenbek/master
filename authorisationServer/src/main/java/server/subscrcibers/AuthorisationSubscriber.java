package server.subscrcibers;



import javax.net.ssl.SSLSocketFactory;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;


import server.mqttTools.MqttTools;

public class AuthorisationSubscriber {
	public static void subscribe(String caCertificatePath) {
		String serverUrl = "ssl://localhost:8883";
		MqttClient client;
		MqttTools mqttTools = MqttTools.getInstance();
		
		try {
			client = new MqttClient(serverUrl, "2");
			MqttConnectOptions options = new MqttConnectOptions();
			
			options.setConnectionTimeout(60);
			options.setKeepAliveInterval(60);
			options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);

			SSLSocketFactory socketFactory = mqttTools.getSocketFactory(caCertificatePath, "ogobe123");
			options.setSocketFactory(socketFactory);

			client.setCallback(new AuthorisationMessageCallback(caCertificatePath));
			client.connect(options);
			System.out.println("===================AUTHORISATION SUBSCRIBER STARTED===================");
			client.subscribe( "clientCredentials", 1);


		} catch (MqttException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}

