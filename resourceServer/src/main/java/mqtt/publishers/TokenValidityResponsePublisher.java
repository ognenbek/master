package mqtt.publishers;

import javax.net.ssl.SSLSocketFactory;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import mqtt.mqttTools.MqttTools;


public class TokenValidityResponsePublisher {
	public static void publish(String responseMessage, String caCertificatePath, String topic) {
		String serverUrl = "ssl://localhost:8883";
		MqttClient client;
		MqttTools mqttTools = MqttTools.getInstance();
		
		
		try {
			client = new MqttClient(serverUrl, "ResourceServerTokenValidityResponsePublisher");
			MqttConnectOptions options = new MqttConnectOptions();
			
			options.setConnectionTimeout(60);
			options.setKeepAliveInterval(60);
			options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
			
			SSLSocketFactory socketFactory = mqttTools.getSocketFactory(caCertificatePath, "ogobe123");
			options.setSocketFactory(socketFactory);

			client.connect(options);
			MqttMessage message;
			message = new MqttMessage(responseMessage.getBytes());
			message.setQos(0);

			client.publish(topic, message);
			client.disconnect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
