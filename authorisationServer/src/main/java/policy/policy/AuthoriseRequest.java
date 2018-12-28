package policy.policy;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bouncycastle.asn1.dvcs.Data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.wso2.balana.Balana;
import org.wso2.balana.PDP;
import org.wso2.balana.ParsingException;
import org.wso2.balana.ctx.AbstractResult;
import org.wso2.balana.ctx.AttributeAssignment;
import org.wso2.balana.ctx.ResponseCtx;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;
import org.wso2.balana.xacml3.Advice;

import database.DatabaseStatements;
import server.subscrcibers.AuthorisationMessageCallback;
import server.subscrcibers.AuthorisatonReturnMessage;

public class AuthoriseRequest {
	private static Balana balana;
	
	public static AuthorisatonReturnMessage authorise(String index, String xaccmlRequest) {
		initBalana();
		
		PDP pdp = new PDP(balana.getPdpConfig());
		System.out.println("Request: " + xaccmlRequest);
		String response = pdp.evaluate(xaccmlRequest);
		AuthorisatonReturnMessage message = new AuthorisatonReturnMessage();
		try {
            ResponseCtx responseCtx = ResponseCtx.getInstance(getXacmlResponse(response));
            AbstractResult result  = responseCtx.getResults().iterator().next();
            if(AbstractResult.DECISION_PERMIT == result.getDecision()){
                System.out.println("\n user is authorized to perform this action\n\n");
                List<Advice> advices = result.getAdvices();
                for(Advice advice : advices){
                    List<AttributeAssignment> assignments = advice.getAssignments();
                    for(AttributeAssignment assignment : assignments){
                    	message.setMessage(assignment.getContent());
                        System.out.println("Advice :  " + assignment.getContent() +"\n\n");
                    }
                }
//                DatabaseStatements.insertXacml(index, xaccmlRequest);
                message.setIndex(index);
                message.setSuccess(true);
            } else {
                System.out.println("\n user is NOT authorized to perform this purchase\n");
                List<Advice> advices = result.getAdvices();
                System.out.println("Advices size : " + advices.size());
                for(Advice advice : advices){
                    List<AttributeAssignment> assignments = advice.getAssignments();
                    for(AttributeAssignment assignment : assignments){
                    	message.setMessage(assignment.getContent());
                        System.out.println("Advice :  " + assignment.getContent() +"\n\n");
                    }
                }
                message.setSuccess(false);
            }
        } catch (ParsingException e) {
            e.printStackTrace();
        }
		return message;
	}
	 private static void initBalana(){

	        try{
	            // using file based policy repository. so set the policy location as system property
	            String policyLocation = (new File(".")).getCanonicalPath() + File.separator + "resources" + File.separator + "policies";
	            System.setProperty(FileBasedPolicyFinderModule.POLICY_DIR_PROPERTY, policyLocation);
	        } catch (IOException e) {
	            System.err.println("Can not locate policy repository");
	        }
	        // create default instance of Balana
	        balana = Balana.getInstance();
	    }
	 
	 private static Element getXacmlResponse(String response) {

	        ByteArrayInputStream inputStream;
	        DocumentBuilderFactory dbf;
	        Document doc;

	        inputStream = new ByteArrayInputStream(response.getBytes());
	        dbf = DocumentBuilderFactory.newInstance();
	        dbf.setNamespaceAware(true);

	        try {
	            doc = dbf.newDocumentBuilder().parse(inputStream);
	        } catch (Exception e) {
	            System.err.println("DOM of request element can not be created from String");
	            return null;
	        } finally {
	            try {
	                inputStream.close();
	            } catch (IOException e) {
	               System.err.println("Error in closing input stream of XACML response");
	            }
	        }
	        return doc.getDocumentElement();
	    }  
}
