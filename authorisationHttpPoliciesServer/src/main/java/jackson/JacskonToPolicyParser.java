package jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import com.fasterxml.jackson.databind.ObjectMapper;

import policy.policy.*;
import policy.requestTemplate.*;;

	public class JacskonToPolicyParser {
		
		public boolean parse(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			Policy policyJson = mapper.readValue(json, Policy.class);
			HashMap<String, Attribute> requestTemplateAttributes = new HashMap();
			
			PolicyFactory policy = new PolicyFactory(policyJson.getName(), policyJson.getAlgorithm());
			policy.start();
			
			//parsing the basic attributes
			policy.policyAppliesToStart();
			policy.policyAppliesToattributes(AttributeJsonToArrayParser.parse(policyJson.getBasicAttributes(), requestTemplateAttributes));
			policy.policyappliesToEnd();
			
			//Parsing the rules
			for(Rule rule : policyJson.getRules()) {
				policy.ruleStart(rule.getAction(),rule.getId());
				
				policy.policyAppliesToStart();
				policy.policyAppliesToattributes(AttributeJsonToArrayParser.parse(rule.getBasicAttributes(), requestTemplateAttributes));
				policy.policyappliesToEnd();
				
				if(!rule.getConditions().isEmpty()) {
					for(Condition it : rule.getConditions()) {
						policy.conditionStart(it.getAction());
						
						for(ConditionAttributes attr : it.getCondAttributes())
						policy.addRuleConditionEntity(new AttributeFactory(attr.getValue(), attr.getId(), attr.getCategory(), requestTemplateAttributes), attr.getRule(), attr.getValue());
						
						policy.conditionEnd();
					}
				}
				if(!rule.getMessage().isEmpty())
					policy.addRuleNotificationMessage(rule.getMessage());
			}
			policy.ruleEnd();
			policy.end(policyJson.getMessage());
			policy.generateRequestTemplateForPolicy(requestTemplateAttributes);
			return true;
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;		
	}
}
