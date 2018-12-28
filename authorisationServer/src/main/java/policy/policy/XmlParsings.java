package policy.policy;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlParsings {
	public static String setClientRole(String xacmlRequest, String role) {
		Document document = stringToDocument(xacmlRequest);
		NodeList nList = document.getElementsByTagName("Attributes");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			 Node node = nList.item(temp);
			 Element eElement = (Element) node;
			 String category = node.getAttributes().getNamedItem("Category").getNodeValue();
			 if(category.equals("urn:oasis:names:tc:xacml:1.0:subject-category:access-subject")) {
				 eElement.getElementsByTagName("AttributeValue").item(0).setTextContent(role);
				 return documentToString(document);
			 }
		}
		return null;
	} 
	
	public static String getClientAction(String xacmlRequest) {
		Document document = stringToDocument(xacmlRequest);
		NodeList nList = document.getElementsByTagName("Attributes");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			 Node node = nList.item(temp);
			 Element eElement = (Element) node;
			 String category = node.getAttributes().getNamedItem("Category").getNodeValue();
			 if(category.equals("urn:oasis:names:tc:xacml:3.0:attribute-category:action")) {
				 return eElement.getElementsByTagName("AttributeValue").item(0).getTextContent();
			 }
				 
		}
		return null;
	}
	
	private static Document stringToDocument(String xmlString) {
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));
			Document document = builder.parse(is);
			return document;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static String documentToString(Document doc){
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
		     Transformer transformer = tf.newTransformer();
		     transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			 StringWriter writer = new StringWriter();
			 transformer.transform(new DOMSource(doc), new StreamResult(writer));
		     String output = writer.getBuffer().toString();
		     return output;
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   return null;
   }
}
