package hadoop;
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxTempMapper extends Mapper<LongWritable, Text, Text, Writable> {
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Writable>.Context context)
			throws IOException, InterruptedException {
		String []line = value.toString().split(";");
		String interval = line[0];
		String temperature = line[1];
		System.out.println("temperature: " + temperature);
		if(interval!=null && !temperature.isEmpty())
			context.write(new Text(interval), new FloatWritable(Float.parseFloat(temperature)));
	}
}
