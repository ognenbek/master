package hadoop;
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class maxTempReducer extends Reducer<Text, Writable, Text, Writable>{
	@Override
	protected void reduce(Text key, Iterable<Writable> values,
			Reducer<Text, Writable, Text, Writable>.Context context) throws IOException, InterruptedException {
		float maxValue = Float.MIN_VALUE;
		System.out.println("Reduce: " + values.toString());
		for (Writable value : values) {
			System.out.println("key: " + key + "value: " + value);
		maxValue = Math.max(maxValue, ((FloatWritable) value).get());
		}
		context.write(key, new FloatWritable(maxValue));
		}
}
