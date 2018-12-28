package policy.policy;

import java.util.HashMap;
import java.util.concurrent.CancellationException;
import java.util.regex.Pattern;

import policy.requestTemplate.Attribute;

public class AttributeFactory {
	private String value;
	private String designator;
	private String dataType;
	
	private String MATCH_END = "</Match>\r\n";
	
	private String CATEGORY_SUBJECT = "subject";
	private String CATEGORY_RESOURCE = "resource";
	private String CATEGORY_ENVIRONMENT = "environment";
	private String CATEGORY_ACTION = "action";
	
	private Attribute attribute;
	
	//value is the value of the attribute, whether the role or the action(sell, buy)..
	//attributeId is the id of the attribute
	//dataType is the way that the data is presented, it may be as integer or string...
	//Category is the category of the attribute, it can be resource, subject, environment...
	
	public AttributeFactory(String value, String attributeId, String category, HashMap<String, Attribute> requestTemplateAttributes) {
		// TODO Auto-generated constructor stub
		dataType = defineCategory(value);
		
		setValue(value, dataType);
		setDesignator(attributeId, dataType, category);
		this.dataType = dataType;
		
		if(dataType.equals("string"))
			value = "String";
		else if(dataType.equals("integer"))
			value = "0";
		else if(dataType.equals("double"))
			value = "0.0";
		else
			value = "String";
			
		requestTemplateAttributes.put(attributeId, new Attribute(category, attributeId, value));
	}
	
	public String createAttributeForTarget(String value, String attributeId, String category) {
		StringBuilder builder = new StringBuilder();
		dataType = defineCategory(value);
		
		builder.append(getMatchString());
		
		setValue(value, dataType);
		builder.append(getValue());

		setDesignator(attributeId, dataType.toLowerCase(), category);
		builder.append(getDesignator());
		
		builder.append(MATCH_END);
		
		return builder.toString();
	}
	
	public String createAttributeForTarget() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(getMatchString());
		builder.append(getValue());
		builder.append(getDesignator());
		builder.append(MATCH_END);
		
		return builder.toString();
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value, String dataType) {
		this.value = "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#" + dataType + "\">" + value + "</AttributeValue>\r\n";
	}

	public String getDesignator() {
		return designator;
	}

	public void setDesignator(String attributeId, String dataType, String category) {
		String categoryDesignatorStr= "";
		if(category.equals(CATEGORY_SUBJECT))
			categoryDesignatorStr = "Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\"";
		else if(category.equals(CATEGORY_RESOURCE))
			categoryDesignatorStr = "Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:resource\"";
		else if(category.equals(CATEGORY_ENVIRONMENT))
			categoryDesignatorStr = "Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:environment\"";
		else if(category.equals(CATEGORY_ACTION))
			categoryDesignatorStr = "Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\"";
		
		this.designator = "<AttributeDesignator " + categoryDesignatorStr + " AttributeId= \"" + attributeId + "\" DataType=\"http://www.w3.org/2001/XMLSchema#" + dataType + "\" MustBePresent=\"false\"/>\r\n";
	}
	
	public String getDatatype() {
		return dataType;
	}
	
	public static String defineCategory(String value) {
		if(Pattern.matches("[a-zA-Z]+", value))
			return "string";
		else if(value.contains("."))
			return "double";
		else if(Pattern.matches("[0-9]+", value))
			return "integer";
		
		return "string";
			
	}
	
	private String getMatchString() {
		return "<Match MatchId=\"urn:oasis:names:tc:xacml:1.0:function:" + getDatatype() + "-equal\">\r\n";
	}
}