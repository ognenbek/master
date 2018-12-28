package mqtt.subscribers;

import java.util.Base64;
import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import mqtt.policy.XmlParsings;
import mqtt.publishers.TokenValidityResponsePublisher;
import mqtt.resourceserver.TaskProperties;
import mqtt.resourceserver.TaskpropertiesHadoop;
import mqtt.resourceserver.TokensHolder;

public class TokenReceiverMessageCallback{
	/*
	 * On the first contact between the client and the resource server, the client needs to show its token, proove that it is authenticated,
	 * it needs to send the tool that it wants to be used(hadoop, spark..), furthermore it needs to provide the algorithms (.class files) that
	 * it wants to be used in processing the data. In the end if the clients is correctly validated and everything is ok with his request
	 * it should subscribe on a specific topic to receive whether the request is valid. 
	 * The structure of the whole cleint request
	 * {
	 * 	"token" : "tokenString"
	 * 	"algorithm" : " { // this is for hadoop purposes. for different processing tool it will be defined according to the tools needs.
	 * 		"map" : "algorithm",
	 * 		"combine" : "algorithm"
	 * 		"reduce" : "algorithm"
	 * 		}
	 * 	"tool" : "procesingTool"
	 * 	"responseTopic" : "topic"
	 * }
	 */
	private static final Object HADOOP_PROCESSING_TOOL = "hadoop";
	private static String TOKEN_JSON_FIELD="token";
	private static String TASK_ID_JSON_FIELD = "taskId";
	private static String PROCESSING_TOOL_JSON_FIELD = "tool";
	private static String ALGORITHM_JSON_FIELD = "algorithm";
	private static String RESPONSETOPICJSONAME="responseToken";//the name of the topic which the client will listen for the result of the validation. if the token is valid it will get ok message and continue streaming data
	private static HashMap<String, TaskProperties> taskMap = new HashMap<String, TaskProperties>();
	
	public static TaskProperties getTaskMap(String taskName) {
		return taskMap.get(taskName);
	}

	public static void setTaskMap(HashMap<String, TaskProperties> taskMap) {
		TokenReceiverMessageCallback.taskMap = taskMap;
	}

	public static void messageArrived(String caCertificatePath, MqttMessage message) {
		// TODO Auto-generated method stub
		//message from the server arrives here
		try {
		JSONObject json = new JSONObject(message.toString());
		//parse the json string in here
		String token = json.getString(TOKEN_JSON_FIELD);	
		String algorithm = json.getString(ALGORITHM_JSON_FIELD);
		String responseTopic = json.getString(RESPONSETOPICJSONAME);
		String processingTool = json.getString(PROCESSING_TOOL_JSON_FIELD);
		String taskId = json.getString(TASK_ID_JSON_FIELD);
		
		if(TokensHolder.checkTokenValidity(token)){
			//token is valid
			
			String payload = new String(Base64.getDecoder().decode(token.split("\\.")[1]));
			JSONObject payloadJson = new JSONObject(payload);
			TaskProperties task = null;
			String clientId = payloadJson.getString("clientId");
			String xacml = TokensHolder.decompressXacml(payloadJson.getString("xacml"));
			String action = XmlParsings.getClientAction(xacml);
			
			if(processingTool.equals(HADOOP_PROCESSING_TOOL) && getTask(clientId + taskId) == null)
				task = new TaskpropertiesHadoop(clientId + taskId, algorithm, processingTool, action);

			taskMap.put(task.getTaskId(), task);
			
			TokenValidityResponsePublisher.publish("OK", caCertificatePath, responseTopic);
		}
		

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public static TaskProperties getTask(String taskId) {
		return taskMap.get(taskId);
	}

}
