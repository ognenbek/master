package hadoop;

public class TaskProperties {
	private String taskId;
	private String inputFileLocation;
	private String outputFileLocaiton;
	private String mapClassLocation;
	private String combineClassLcoation;
	private String reduceClassLocation;
	
	
	public TaskProperties(String taskId, String inputFileLocation, String outputFileLocaiton, String mapClassLocation,
			String combineClassLcoation, String reduceClassLocation) {
		this.taskId = taskId;
		this.inputFileLocation = inputFileLocation;
		this.outputFileLocaiton = outputFileLocaiton;
		this.mapClassLocation = mapClassLocation;
		this.combineClassLcoation = combineClassLcoation;
		this.reduceClassLocation = reduceClassLocation;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getInputFileLocation() {
		return inputFileLocation;
	}
	public void setInputFileLocation(String inputFileLocation) {
		this.inputFileLocation = inputFileLocation;
	}
	public String getOutputFileLocaiton() {
		return outputFileLocaiton;
	}
	public void setOutputFileLocaiton(String outputFileLocaiton) {
		this.outputFileLocaiton = outputFileLocaiton;
	}
	public String getMapClassLocation() {
		return mapClassLocation;
	}
	public void setMapClassLocation(String mapClassLocation) {
		this.mapClassLocation = mapClassLocation;
	}
	public String getCombineClassLcoation() {
		return combineClassLcoation;
	}
	public void setCombineClassLcoation(String combineClassLcoation) {
		this.combineClassLcoation = combineClassLcoation;
	}
	public String getReduceClassLocation() {
		return reduceClassLocation;
	}
	public void setReduceClassLocation(String reduceClassLocation) {
		this.reduceClassLocation = reduceClassLocation;
	}
}
