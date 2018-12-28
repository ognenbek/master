package mqtt.subscribers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Base64;

import javax.swing.plaf.FileChooserUI;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import mqtt.resourceserver.TaskProperties;
import mqtt.resourceserver.TaskpropertiesHadoop;
import mqtt.resourceserver.TokensHolder;
import hadoop.MaxTemperature;

public class DataReceiverMessageCallback {
	private static String TOKEN_JSON_FIELD = "token";
	private static String DATA_JSON_FIELD = "data";
	private static String TASK_JSON_FIELD = "taskId";
	/*
	 * Here we get the data to be processed or stored from the clients. In order the client to be able to transfer some data to the server it has to be authenticated and his token needs to be verified.
	 * for that purpose the client sends his token along with the data to be transferred, in order the server to be sure it is the verified client who sends the data.
	 * the structure of the message is the following
	 * {
	 * 	token : token
	 * 	taskId : taskId
	 * 	data : someData
	 * }
	 * (non-Javadoc)
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
	 */
	public static void messageArrived(MqttMessage message, MqttClient client) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Data received!");
		JSONObject json = new JSONObject(message.toString());
		String token = json.getString(TOKEN_JSON_FIELD);
		if(TokensHolder.checkTokenValidity(token)) {
			String payload = new String(Base64.getDecoder().decode(token.split("\\.")[1]));
			JSONObject payloadJson = new JSONObject(payload);
			TaskProperties task = TokenReceiverMessageCallback.getTask(payloadJson.getString("clientId") + json.getString(TASK_JSON_FIELD));
			if(task!=null) {
				String data = json.getString(DATA_JSON_FIELD);
				if(data.equals("stopStreaming")){
					System.out.println("Stop streaming");
					if(task.processData()) {
						System.out.println("Data was succesfully processed!!!");
						
					}
				} else {
					System.out.println("data: " + data);
					File yourFile = new File(task.getInputLocation());
					yourFile.createNewFile(); 
					PrintStream out = new PrintStream(new FileOutputStream(yourFile, true));
					out.println(data);
					out.close();
				}
			}
		}
		
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
}
