package mqtt.resourceserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.tools.ToolProvider;
import org.json.JSONObject;

import hadoop.MaxTemperature;

public class TaskpropertiesHadoop extends TaskProperties{
	private String JSON_FIELD_MAP = "map";
	private String JSON_FIELD_COMBINE = "combine";
	private String JSON_FIELD_REDUCE = "reduce";
	private String mapAlgorithm;
	private String combineAlgorithm;
	private static String algorithmsFolder;

	private String reduceAlgorithm;

	/*
	 * The algorithm that is received can be defined differently for a different processing tool(hadoop, spark)... This requires defining a different
	 * structure of the algorithm field, sent by the client, for a different tool. As only hadoop will be implemented for now the structure for hadoop
	 * will be following
	 * {
	 * 	"map" : "algorithm",
	 * 	"combine" : "algorithm"
	 * 	"reduce" : "algorithm"
	 * }
	 * In the algorithm field we have to store the algorithms in a specific files, which will later be used in order the algorithms to be read.
	 * As a return getAlgorithm will return the file locations of those files.
	 */
	public TaskpropertiesHadoop(String taskId, String algorithm, String processingTool, String action) {
		super(taskId, processingTool, action);
		this.setAlgorithm(algorithm);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getAlgorithm() {
		// TODO Auto-generated method stub
		return super.getAlgorithm();
	}
	
	@Override
	public void setAlgorithm(String algorithm) {
		// TODO Auto-generated method stub
		JSONObject jsonAlg = new JSONObject(algorithm);
		String mapAlgorithm = jsonAlg.getString(JSON_FIELD_MAP);
		String combineAlgorithm = jsonAlg.getString(JSON_FIELD_COMBINE);
		String reduceAlgorithm = jsonAlg.getString(JSON_FIELD_REDUCE);
		
		algorithmsFolder = getDirectoryLocation() + File.separator + "algorithms";
		createAlgorithmsDirectory(algorithmsFolder);
		
		if(!mapAlgorithm.equals("")) { 
			mapAlgorithm = this.crateAlgorithmFiles(mapAlgorithm, "Map");
		}
		this.setMapAlgorithm(mapAlgorithm);
		
		if(!combineAlgorithm.equals("")) {
			combineAlgorithm = this.crateAlgorithmFiles(combineAlgorithm, "Combine");
		}
		this.setCombineAlgorithm(combineAlgorithm);
		
		if(!reduceAlgorithm.equals("")) {
			reduceAlgorithm = this.crateAlgorithmFiles(reduceAlgorithm, "Reduce");
		}
		this.setReduceAlgorithm(reduceAlgorithm);
		
		super.setAlgorithm(mapAlgorithm + ";" + combineAlgorithm + ";" + reduceAlgorithm);	
		
	}
	
	private static String crateAlgorithmFiles(String algorithmTxt, String processType) {
		String classPath = algorithmsFolder + File.separator + getCLassNameFromAlgorithmTxt(algorithmTxt);
		String classPathJava = classPath + ".java";
		File classJava = new File(classPathJava);
		try {
			classJava.createNewFile();
			PrintStream out = new PrintStream(new FileOutputStream(classJava,false));
			out.println(algorithmTxt);
			out.close();
			
			
			//After creating the .java file we need to generate .class file in order the hadoop to load the classes and continue processing
			Process process = Runtime.getRuntime().exec("javac -classpath " + System.getenv("HADOOP_CLASSPATH")  + " " + classPathJava);
			
			process.waitFor();
			System.out.println(classPath + ".class FILE CREATED");
			return classPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	private void createAlgorithmsDirectory(String path) {
		File file = new File(path);
		file.mkdir();
	}
	
	private static String getCLassNameFromAlgorithmTxt(String algorithm) {
		String[] temp = algorithm.split("extends Mapper");
		if(temp.length == 1)
			temp = algorithm.split("extends Reducer");
		temp = temp[0].split(" ");
		String className = temp[temp.length-1];
		return className;
	}
	@Override
	public boolean processData() {
		// TODO Auto-generated method stub
		try {
//			if(getAction().contains("process")) {
//			}
			System.out.println("PROCESS DATA!");
					return MaxTemperature.run(this.getTaskId(), this.getInputLocation(), this.getOutputLocation(),
							this.getMapAlgorithm(), this.getCombineAlgorithm(), this.getReduceAlgorithm());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	
	public String getMapAlgorithm() {
		return mapAlgorithm;
	}

	public void setMapAlgorithm(String mapAlgorithm) {
		this.mapAlgorithm = mapAlgorithm;
	}

	public String getCombineAlgorithm() {
		return combineAlgorithm;
	}

	public void setCombineAlgorithm(String combineAlgorithm) {
		this.combineAlgorithm = combineAlgorithm;
	}

	public String getReduceAlgorithm() {
		return reduceAlgorithm;
	}

	public void setReduceAlgorithm(String reduceAlgorithm) {
		this.reduceAlgorithm = reduceAlgorithm;
	}
}
