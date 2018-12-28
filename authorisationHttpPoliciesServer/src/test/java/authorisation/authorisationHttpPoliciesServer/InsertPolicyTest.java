package authorisation.authorisationHttpPoliciesServer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class InsertPolicyTest {
	@Test
	public void insertPolicy() {
		String policy = "{\r\n" + 
    			"\"policyName\" : \"StorePolicy\",\r\n" + 
    			"\"policyAlgorithm\" : \"permit-overrides\",\r\n" + 
    			"\"basicAttributes\" : [\r\n" + 
    			"	{ \r\n" + 
    			"	 \"value\" : \"store\", \"id\" : \"actionId\", \"category\" : \"action\" 	\r\n" + 
    			"	}	\r\n" + 
    			"],\r\n" + 
    			"\"rule\" : [\r\n" + 
    			"	{\r\n" + 
    			"		\"action\" : \"Permit\",\r\n" + 
    			"		\"id\" : \"B-can-Store\",\r\n" + 
    			"		\"basicAttributes\" : [\r\n" + 
    			"			{ \r\n" + 
    			"				 \"value\" : \"B\", \"id\" : \"roleId\", \"category\" : \"subject\" \r\n" + 
    			"			}	\r\n" + 
    			"		],\r\n" + 
    			"		\"condition\" : [\r\n" + 
    			"			{\r\n" + 
    			"					\"action\" : \"and\",\r\n" + 
    			"					\"basicAttributes\" : [\r\n" + 
    			"					{ \r\n" + 
    			"						\"value\" : \"4\", \"id\" : \"environmentId\", \"category\" : \"environment\", \"rule\": \"greater-than\" \r\n" + 
    			"					},	\r\n" + 
    			"					{ \r\n" + 
    			"						\"value\" : \"11\", \"id\" : \"environmentId\", \"category\" : \"environment\" , \"rule\": \"less-than\"\r\n" + 
    			"					}\r\n" + 
    			"				]\r\n" + 
    			"			}\r\n" + 
    			"		],\r\n" + 
    			"		\"message\" : \"You can store\"\r\n" + 
    			"	}\r\n" + 
    			"],\r\n" + 
    			"\"message\" : \"you can not store\"\r\n" + 
    			"}";
		InsertPolicyController insertPolicy = new InsertPolicyController();
		assertEquals("Policy generated", insertPolicy.insertPolicy(policy));
	}
	
	@Test
	public void insertWrongPolicy() {
		String policy =
    			"\"policyName\" : \"StorePolicy\",\r\n" + 
    			"\"policyAlgorithm\" : \"permit-overrides\",\r\n" + 
    			"\"basicAttributes\" : [\r\n" + 
    			"	{ \r\n" + 
    			"	 \"value\" : \"store\", \"id\" : \"actionId\", \"category\" : \"action\" 	\r\n" + 
    			"	}	\r\n" + 
    			"],\r\n" + 
    			"\"rule\" : [\r\n" + 
    			"	{\r\n" + 
    			"		\"action\" : \"Permit\",\r\n" + 
    			"		\"id\" : \"B-can-Store\",\r\n" + 
    			"		\"basicAttributes\" : [\r\n" + 
    			"			{ \r\n" + 
    			"				 \"value\" : \"B\", \"id\" : \"roleId\", \"category\" : \"subject\" \r\n" + 
    			"			}	\r\n" + 
    			"		],\r\n" + 
    			"		\"condition\" : [\r\n" + 
    			"			{\r\n" + 
    			"					\"action\" : \"and\",\r\n" + 
    			"					\"basicAttributes\" : [\r\n" + 
    			"					{ \r\n" + 
    			"						\"value\" : \"4\", \"id\" : \"environmentId\", \"category\" : \"environment\", \"rule\": \"greater-than\" \r\n" + 
    			"					},	\r\n" + 
    			"					{ \r\n" + 
    			"						\"value\" : \"11\", \"id\" : \"environmentId\", \"category\" : \"environment\" , \"rule\": \"less-than\"\r\n" + 
    			"					}\r\n" + 
    			"				]\r\n" + 
    			"			}\r\n" + 
    			"		],\r\n" + 
    			"		\"message\" : \"You can store\"\r\n" + 
    			"	}\r\n" + 
    			"],\r\n" + 
    			"\"message\" : \"you can not store\"\r\n" + 
    			"}";
		InsertPolicyController insertPolicy = new InsertPolicyController();
		assertEquals("Wrong input", insertPolicy.insertPolicy(policy));
	}
}
