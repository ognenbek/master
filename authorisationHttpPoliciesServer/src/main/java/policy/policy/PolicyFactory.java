package policy.policy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import policy.requestTemplate.Attribute;
import policy.requestTemplate.AttributeCreator;

public class PolicyFactory {
	// if we have only one rule with one effect (Deny or permit) we need to add automatically the opposite rule without any condition, because the engine does not
	//automatically gives Permit if the request was not denied and vice versa. 
	private boolean permitFlag = false;
	private boolean denyflag = false;
	
	private boolean conditionLogicOperation = false;
	
	private String policyLocalPath;
	private String policyRequestTemplatePath;
	
	private String POLICY_START;
	private String POLICY_END;
	
	
	
	private String TARGET_START = "<Target>\r\n";
	private String TARGET_END = "</Target>\r\n";
	
	private String ANYOF_ALLOFF_START = "<AnyOf>\n<AllOf>\n";
	private String ANYOF_ALLOF_END = "</AllOf>\n</AnyOf>\n";
	
	private ArrayList<AttributeFactory> attribute;
	
	private PrintWriter printWriter;
	private FileWriter filewriter;
	
	private String ruleEffecr;
	
	private HashMap<String, Attribute> attrTemplatesMap = new HashMap();
	
	public PolicyFactory(String policyName, String combiningAlgorithm) {
		POLICY_START = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				" <Policy xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" PolicyId=\"" + policyName + "\"\r\n" + 
				"          RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:" + combiningAlgorithm + "\" Version=\"1.0\">";
		
		POLICY_END = "</Policy>";
		
		createPolicyTextFile(policyName);
		createPolicyRequestTextFile(policyName + "Template");
				
	}
	
	public void ruleStart(String effect, String ruleId) {
		printWriter.print("<Rule Effect=\"" + effect + "\" RuleId=\"" + ruleId + "\">\r\n");
		ruleEffecr = effect;
		if(effect.equals("Deny"))
			denyflag = true;
		else permitFlag = true;
	}
	
	public void ruleEnd() {
		printWriter.print("</Rule>\n");
	}
	
	public void conditionStart() {
		printWriter.print("<Condition>\n");
	}
	
	public void conditionStart(String logicalOperation) {
		printWriter.print("<Condition>\n");
		conditionLogicOperation = true;
		printWriter.print("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:"  + logicalOperation + "\">");
	}
	
	public void conditionEnd() {
		if(conditionLogicOperation == true) {
			printWriter.print("</Apply>\n");
			conditionLogicOperation = false;
		}
		printWriter.print("</Condition>\r\n");
	}
	
	public void addRuleConditionEntity(AttributeFactory attribute, String rule, String value) {
		String dataTypeRule = AttributeFactory.defineCategory(value);
		printWriter.print("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:" + dataTypeRule + "-" + rule + "\">\r\n");
		printWriter.print("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:" + dataTypeRule + "-one-and-only\">\r\n");
		printWriter.print(attribute.getDesignator());
		
		printWriter.print("</Apply>");
		printWriter.print("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#integer\">" + value + "</AttributeValue>\r\n");
		printWriter.print("</Apply>\r\n");
		
	}
	
	public void addRuleNotificationMessage(String notificationMessage) {
		printWriter.print("<AdviceExpressions><AdviceExpression AdviceId=\"urn:notifyUsers\" AppliesTo=\"" + ruleEffecr + "\">\r\n");
		printWriter.print("<AttributeAssignmentExpression AttributeId=\"urn:notification:message\" Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + notificationMessage + "</AttributeValue>\r\n");
		printWriter.print("</AttributeAssignmentExpression>\r\n");
		printWriter.print("</AdviceExpression>\r\n");
		printWriter.print("</AdviceExpressions>\r\n");
	}
	
	public void addRuleNotificationMessage(String notificationMessage, String effect) {
		printWriter.print("<AdviceExpressions><AdviceExpression AdviceId=\"urn:notifyUsers\" AppliesTo=\"" + effect + "\">\r\n");
		printWriter.print("<AttributeAssignmentExpression AttributeId=\"urn:notification:message\" Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + notificationMessage + "</AttributeValue>\r\n");
		printWriter.print("</AttributeAssignmentExpression>\r\n");
		printWriter.print("</AdviceExpression>\r\n");
		printWriter.print("</AdviceExpressions>\r\n");
	}
	
	public void policyAppliesToStart() {
		printWriter.print(TARGET_START);
	}
	
	public void policyAppliesToattributes(ArrayList<AttributeFactory> attributes) {
		for(AttributeFactory att : attributes) {
//			attrTemplatesMap.put(att.getValue(), new Attribute(att.get, id, value))
			printWriter.print(ANYOF_ALLOFF_START);
			printWriter.print(att.createAttributeForTarget());
			printWriter.print(ANYOF_ALLOF_END);
		}

	}
	
	public void policyappliesToEnd() {
		printWriter.print(TARGET_END);
	}
	public void start() {
		try {
			filewriter = new FileWriter(policyLocalPath);
			printWriter = new PrintWriter(filewriter);
			printWriter.print(POLICY_START);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void end(String defaultMessage) {
		String endRule = null;
		if(denyflag && !permitFlag)
			endRule = "Permit";
		else if(!denyflag && permitFlag)
			endRule = "Deny";
		
		if(endRule!=null) {
		printWriter.print("<Rule RuleId=\"end-rule\" Effect=\"" + endRule + "\">");
		addRuleNotificationMessage(defaultMessage, endRule);
		printWriter.print("</Rule>");
		}
		printWriter.print(POLICY_END);
		printWriter.close();

	}
	
	public void generateRequestTemplateForPolicy(HashMap<String, Attribute> map) {
		try {
			FileWriter templateFileWriter = new FileWriter(policyRequestTemplatePath);
			PrintWriter templateWriter = new PrintWriter(templateFileWriter);
			String xml = AttributeCreator.createXmlAttributes(map);
			templateWriter.print(xml);
			templateFileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void createPolicyTextFile(String policyName) {
		//First create the resource folder
		String resourcefolder = createFolder("c:\\Users\\ogo_b\\eclipse\\authorisationServer\\", "resources");
		String policyFolder = createFolder(resourcefolder, "policies");

		policyLocalPath = policyFolder+"\\"+policyName+".xml";
	}	
	
	private void createPolicyRequestTextFile(String fileName) {
		String resourceFolder = createFolder("c:\\Users\\ogo_b\\eclipse\\authorisationServer\\", "resources");
		String policyRequestsFolder = createFolder(resourceFolder, "policyRequesttemplates");
		
		policyRequestTemplatePath = policyRequestsFolder + "\\" + fileName + ".xml";
		
	}
	
	private String createFolder(String folder, String folderName) {
		String resourcePath = folder + "\\" + folderName;
		File resources = new File(resourcePath);
		if(!resources.exists())
			resources.mkdir();
		
		return resourcePath;
	}
}
