package policy.requestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

public class AttributeCreator {
	private static final String ATTRIBUTE_XACML_SUBJECT = "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject";
	private static final String ATTRIBUTE_XACML_RESOURCE = "urn:oasis:names:tc:xacml:3.0:attribute-category:resource";
	private static final String ATTRIBUTE_XACML_ACTION = "urn:oasis:names:tc:xacml:3.0:attribute-category:action";
	private static final String ATTRIBUTE_XACML_ENVIROMENT = "urn:oasis:names:tc:xacml:3.0:attribute-category:environment";
	
	public static final String SUBJECT = "subject";
	public static final String RESOURCE = "resource";
	public static final String ACTION = "action";
	public static final String ENVIRONEMT = "environment";
	
	public static String createAttribute(Attribute attr) {
		String attribute =
				"<Attributes Category=\""+ getCategory(attr.getCategory())+ "\">\n" +
	                "<Attribute AttributeId=\"" + attr.getId() + "\" IncludeInResult=\"false\">\n" +
	                "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#" + attr.getType() +"\">" + attr.getValue() +"</AttributeValue>\n" +
	                "</Attribute>\n" +
	                "</Attributes>\n";
		return attribute;
	}
	public static String createXml(ArrayList<String> attributes) {
		StringBuilder strReturn = new StringBuilder();
		strReturn.append("<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n");
		for(String it : attributes)
			strReturn.append(it);
		strReturn.append("</Request>");
		
		return strReturn.toString();
	}
	public static String createXmlAttributes(ArrayList<Attribute> attributes) {
		StringBuilder strReturn = new StringBuilder();
		strReturn.append("<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n");
		for(Attribute it : attributes)
			strReturn.append(createAttribute(it));
		strReturn.append("</Request>");
		
		return strReturn.toString();
	}
	public static String createXmlAttributes(HashMap<String, Attribute> attributesMap) {
		StringBuilder strReturn = new StringBuilder();
		strReturn.append("<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n");
		
		for (String key: attributesMap.keySet()){
            strReturn.append(createAttribute(attributesMap.get(key)));
		} 
		strReturn.append("</Request>");
		
		return strReturn.toString();
	}
	private static String getCategory(String category) {
		if(category.equals(SUBJECT))
			return ATTRIBUTE_XACML_SUBJECT;
		else if(category.equals(RESOURCE))
			return ATTRIBUTE_XACML_RESOURCE;
		else if(category.equals(ACTION))
			return ATTRIBUTE_XACML_ACTION;
		else if(category.equals(ENVIRONEMT))
			return ATTRIBUTE_XACML_ENVIROMENT;
		return "";			
	}
}
