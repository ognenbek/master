package mqtt.subscribers;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class GeneralCallback implements MqttCallback{
	private String caCertificatePath;
	private MqttClient client;
	
	public GeneralCallback(String caCertificatePath, MqttClient client) {
		this.caCertificatePath = caCertificatePath;
		this.client = client;
	}
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		if(topic.equals(GeneralSubscriber.topic1))
			TokenReceiverMessageCallback.messageArrived(caCertificatePath, message);
		else if(topic.equals(GeneralSubscriber.topic2))
			DataReceiverMessageCallback.messageArrived(message, client);
		
	}

}
