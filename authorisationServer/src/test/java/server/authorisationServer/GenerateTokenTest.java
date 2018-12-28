package server.authorisationServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

import org.junit.Test;

import server.certificate.GenerateKeystore;
import server.token.GenerateToken;

public class GenerateTokenTest {
	@Test
	public void generateToken() {
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
		GenerateKeystore.create("authorisationserver");
		
		String response = GenerateToken.generate("android", compressRequest(requestXACML));
		System.out.println(response);
	}
	private String compressRequest(String xacml) {
		String zippedString = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(out);
			gzip.write(xacml.getBytes("UTF-8"));
		    gzip.close();
		    zippedString = Base64.getEncoder().encodeToString(out.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return zippedString;
	}
}
