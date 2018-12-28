package jackson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import policy.policy.AttributeFactory;
import policy.requestTemplate.Attribute;

public class AttributeJsonToArrayParser {
	
	public static ArrayList<AttributeFactory> parse(HashSet<BasicAttribute> set, HashMap<String, Attribute> requestTemplateAttributes){
		ArrayList<AttributeFactory> list = new ArrayList();
		for(BasicAttribute it : set) {
			list.add(new AttributeFactory(it.getValue(), it.getId(), it.getCategory(), requestTemplateAttributes));
		}
		
		return list;
	}
}
