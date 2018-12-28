package hadoop;
import org.apache.hadoop.io.FloatWritable;

public final class itemsFactory {
	private static itemsFactory factory;
	private itemsFactory() {}
	public FloatWritable getFloatWritable(Float number)
	{
		return new FloatWritable(number);
	}
	public static itemsFactory getInstance()
	{
		if(factory == null)
			factory = new itemsFactory();
		return factory;
	}
}
