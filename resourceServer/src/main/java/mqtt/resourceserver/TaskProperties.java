package mqtt.resourceserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.json.JSONObject;

public class TaskProperties {

	/*
	 * Each client sends a different task for processing,storing or both. Additionally the task needs to have the algorithm that needs to be used
	 * in processing the data, the task name which will also be used in tracking the task and gathering the processed data and the location of the
	 * input and the output data of each task. For that purpose the following class was created in storing that data.
	 * As different processing tools can be used as, hadoop, spark... we need one general (adapter) class which will adapt later the
	 * custom made classes for holding the information about the processing, storing.
	 * 
	 * The algorithm that is received can be defined differently for a different processing tool(hadoop, spark)... This requires defining a different
	 * structure of the algorithm field, sent by the client, for a different tool. As only hadoop will be implemented for now the structure for hadoop
	 * will be following
	 * {
	 * 	"map" : "algorithm",
	 * 	"combine" : "algorithm"
	 * 	"reduce" : "algorithm"
	 * }
	 */
	private static String taskId;
	private String action;
	private static String algorithm;
	private static String directoryLocation;
	private String inputLocation;
	private String outputLocation;
	private String TOKEN_FIELD_CLIENTID = "clientId";
	private String TOKEN_FIELD_DATE = "date";
	private String processingTool;
	
	public TaskProperties(String taskId, String processingTool, String action) {
		this.taskId = taskId;
		this.action = action;
		this.inputLocation = createInputFile(createfileLocation("input"));
		this.outputLocation = "c:\\Users\\ogo_b\\eclipse\\HadoopTest\\files\\" + taskId+"\\output";
		this.processingTool = processingTool;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getProcessingTool() {
		return processingTool;
	}

	public void setProcessingTool(String processingTool) {
		this.processingTool = processingTool;
	}
	public static  String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public String getInputLocation() {
		return inputLocation;
	}
	public void setInputLocation(String inputLocation) {
		this.inputLocation = inputLocation;
	}
	public String getOutputLocation() {
		return outputLocation;
	}
	public void setOutputLocation(String outputLocation) {
		this.outputLocation = outputLocation;
	}
	private static String createInputFile(String fileLocation) {
		File file = new File(fileLocation);
		try {
			file.createNewFile();
			return fileLocation;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private static String createfileLocation(String type) {
		String directory = "c:\\Users\\ogo_b\\eclipse\\HadoopTest\\files\\" + taskId;
		setDirectoryLocation(directory);
		System.out.println("Directory: " + directory);
		File file = new File(directory);
		
		if(file.exists())
			return null;
		
		file.mkdir();
		String fileStr = directory + File.separator + type + ".txt";
		System.out.println(fileStr);
		return fileStr;
	}
	public boolean processData() {
		return false;

	}
	public static String getDirectoryLocation() {
		return directoryLocation;
	}

	public static void setDirectoryLocation(String directoryLocation) {
		TaskProperties.directoryLocation = directoryLocation;
	}
}
