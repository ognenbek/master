package mqtt.subscribers;

import javax.net.ssl.SSLSocketFactory;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import mqtt.mqttTools.MqttTools;


public class GeneralSubscriber {
	public static String topic1 = "tokenreceivetopic";
	public static String topic2 = "datareceivetopic";
	public static void subscribe(String caCertificatePath) {
		String serverUrl = "ssl://localhost:8883";
		MqttClient client;
		MqttTools mqttTools = MqttTools.getInstance();
		
		try {
			client = new MqttClient(serverUrl, "3");
			MqttConnectOptions options = new MqttConnectOptions();
			
			options.setCleanSession(true);
			options.setConnectionTimeout(60);
			options.setKeepAliveInterval(60);
			options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);

			SSLSocketFactory socketFactory = mqttTools.getSocketFactory(caCertificatePath, "ogobe123");
			options.setSocketFactory(socketFactory);
			
			client.setCallback(new GeneralCallback(caCertificatePath, client));
			client.connect(options);
			System.out.println("===================Resource Server SUBSCRIBER STARTED===================");
			client.subscribe(topic1, 2);
			client.subscribe(topic2, 2);


		} catch (MqttException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
