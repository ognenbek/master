package hadoop;
	import java.io.File;
import java.net.URL;
	import java.net.URLClassLoader;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	
	import org.apache.hadoop.conf.Configuration;
	import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MaxTemperature {	
	private static TaskProperties taskProoperties;
	public static void main(String[] args) throws Exception  {
//			if(run("MaxTemp1", "c:/Users/ogo_b/eclipse/HadoopTest/bin/", "hadoop.MaxTempMapper", "hadoop.maxTempReducer", null))
//			System.out.println("=============success================");
		run("task1", "C:\\Users\\ogo_b\\Desktop\\input_file.txt", "C:\\Users\\ogo_b\\Desktop\\output_file", 
				"C:\\Users\\ogo_b\\eclipse\\HadoopTest\\temp\\algorithms\\MaxTempMapper", "", "C:\\Users\\ogo_b\\eclipse\\HadoopTest\\temp\\algorithms\\maxTempReducer");
	}
	
	public static boolean run(String taskId, String inputFileLocation, String outputFileLocaiton, String mapClassLocation, String combineClassLcoation,
			String reduceClassLocation) throws Exception{
		taskProoperties = new TaskProperties(taskId, inputFileLocation, outputFileLocaiton, mapClassLocation, combineClassLcoation, reduceClassLocation);

		Configuration conf = new Configuration();
		
		Job job = Job.getInstance(conf);
		job.setJarByClass(MaxTemperature.class);
		job.setJobName(taskProoperties.getTaskId());
		
		FileInputFormat.addInputPath(job, new org.apache.hadoop.fs.Path(taskProoperties.getInputFileLocation()));
		FileOutputFormat.setOutputPath(job, new org.apache.hadoop.fs.Path(taskProoperties.getOutputFileLocaiton()));
		
		String mapperLocation = taskProoperties.getMapClassLocation();
		String combinerLocation = taskProoperties.getCombineClassLcoation();
		String reducerLocation = taskProoperties.getReduceClassLocation();
		
		if(!mapperLocation.equals("")) 
			job.setMapperClass(getClassFromFile(mapperLocation));

		if(!combinerLocation.equals(""))
			job.setCombinerClass(getClassFromFile(combinerLocation));
		
		if(!reducerLocation.equals(""))
			job.setReducerClass(getClassFromFile(reducerLocation));
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FloatWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);
		return job.waitForCompletion(true) ?  true :  false;
		
	}
	
	private static Class getClassFromFile(String fullClassLocation) throws Exception {
		Path p =  Paths.get(fullClassLocation);
		String classFolder = p.getParent().toString();
		String className = p.getFileName().toString();
		System.out.println(classFolder);
		System.out.println(className);
//	    URLClassLoader loader = new URLClassLoader(new URL[] {
//	            new URL("file://" + classFolder)	
//	    });
//	    Class cl = loader.loadClass(className);
//	    loader.close();
//	    return cl;
		
//		Path p =  Paths.get(fullClassLocation);
//		String classFolder = p.getParent().toString();
//		String className = p.getFileName().toString();
//		System.out.println(classFolder);
//		System.out.println(className);
//		File file = new File(classFolder);W
//		URL url = file.toURL();
//		URL[] urls = new URL[]{url};
//		
//		 ClassLoader cl = new URLClassLoader(urls);
//		 Class cls = cl.loadClass(className);
//		 return cls;
		
		File file = new File(classFolder); 
		
		URL url = file.toURI().toURL(); 
	    URL[] urls = new URL[]{url}; 
	    
	    ClassLoader cl = new URLClassLoader(urls); 
	    Class  cls = cl.loadClass("hadoop." + className);
	    System.out.println("cls.getName() = " + cls.getName());
	    return cls;
	}
}
