package server.authorisationServer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import policy.policy.AuthoriseRequest;
import policy.policy.XmlParsings;
import server.subscrcibers.AuthorisatonReturnMessage;

public class AuthorisationTest {
	@Test
	public void authorisationTestPermitted() {
		String requestXACML = "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n" +
	            "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:environment\">\n" +
	            "<Attribute AttributeId=\"environmentId\" IncludeInResult=\"false\">\n" +
	            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#integer\">9</AttributeValue>\n" +
	            "</Attribute>\n" +
	            "</Attributes>\n" +
	            "<Attributes Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n" +
	            "<Attribute AttributeId=\"roleId\" IncludeInResult=\"false\">\n" +
	            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">B</AttributeValue>\n" +
	            "</Attribute>\n" +
	            "</Attributes>\n" +
	            "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n" +
	            "<Attribute AttributeId=\"actionId\" IncludeInResult=\"false\">\n" +
	            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">store</AttributeValue>\n" +
	            "</Attribute>\n" +
	            "</Attributes>\n" +
	            "</Request>";
		
		String clientAction = XmlParsings.getClientAction(requestXACML);
		AuthorisatonReturnMessage result = AuthoriseRequest.authorise("B" + clientAction, requestXACML);
		assertEquals(true, result.isSuccess());
		
	}
	
	@Test
	public void authorisationTestNotPermitted() {
		String requestXACML = "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n" +
	            "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:environment\">\n" +
	            "<Attribute AttributeId=\"environmentId\" IncludeInResult=\"false\">\n" +
	            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#integer\">9</AttributeValue>\n" +
	            "</Attribute>\n" +
	            "</Attributes>\n" +
	            "<Attributes Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n" +
	            "<Attribute AttributeId=\"roleId\" IncludeInResult=\"false\">\n" +
	            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">A</AttributeValue>\n" +
	            "</Attribute>\n" +
	            "</Attributes>\n" +
	            "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n" +
	            "<Attribute AttributeId=\"actionId\" IncludeInResult=\"false\">\n" +
	            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">store</AttributeValue>\n" +
	            "</Attribute>\n" +
	            "</Attributes>\n" +
	            "</Request>";
		
		String clientAction = XmlParsings.getClientAction(requestXACML);
		AuthorisatonReturnMessage result = AuthoriseRequest.authorise("A" + clientAction, requestXACML);
		assertEquals(false, result.isSuccess());
		
	}
}
