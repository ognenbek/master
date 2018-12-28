package policy.requestTemplate;

import java.util.regex.Pattern;

public class Attribute {
	private String category;
	private String id;
	private String value;
	private String type;
	
	public static final String DATATYPE_INTEGER = "integer";
	public static final String DATATYPE_STRING = "string";
	private static final String DATATYPE_DOUBLE = "double";

	public Attribute(String category, String id, String value) {
		// TODO Auto-generated constructor stub
		this.category = category;
		this.id = id;
		this.value = value;
		this.type = defineDataType(value);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	private static String defineDataType(String value) {
		if(Pattern.matches("[a-zA-Z]+", value))
			return DATATYPE_STRING;
		else if(value.contains("."))
			return DATATYPE_DOUBLE;
		else if(Pattern.matches("[0-9]+", value))
			return DATATYPE_INTEGER;		
		return "string";
			
	}
}
