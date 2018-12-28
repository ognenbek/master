package jackson;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Policy {
	private String name;
	private String algorithm;
	private String message;

	private HashSet<BasicAttribute> basicAttributes;
	private HashSet<Rule> rules;
	@JsonProperty("policyName")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonProperty("policyAlgorithm")
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	@JsonProperty("basicAttributes")
	public HashSet<BasicAttribute> getBasicAttributes() {
		return basicAttributes;
	}
	public void setBasicAttributes(HashSet<BasicAttribute> basicAttributes) {
		this.basicAttributes = basicAttributes;
	}
	@JsonProperty("rule")
	public HashSet<Rule> getRules() {
		return rules;
	}
	public void setRules(HashSet<Rule> rules) {
		this.rules = rules;
	}	
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
