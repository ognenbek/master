package jackson;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rule {
	private String action;
	private String id;
	private HashSet<BasicAttribute> basicAttributes;
	private HashSet<Condition> conditions;
	private String message;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JsonProperty("basicAttributes")
	public HashSet<BasicAttribute> getBasicAttributes() {
		return basicAttributes;
	}
	public void setBasicAttributes(HashSet<BasicAttribute> basicAttributes) {
		this.basicAttributes = basicAttributes;
	}
	@JsonProperty("condition")
	public HashSet<Condition> getConditions() {
		return conditions;
	}
	public void setConditions(HashSet<Condition> conditions) {
		this.conditions = conditions;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
