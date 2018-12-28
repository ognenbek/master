package jackson;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Condition {
	private String action;
	private HashSet<ConditionAttributes> condAttributes;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	@JsonProperty("basicAttributes")
	public HashSet<ConditionAttributes> getCondAttributes() {
		return condAttributes;
	}
	public void setCondAttributes(HashSet<ConditionAttributes> condAttributes) {
		this.condAttributes = condAttributes;
	}
	
	
}
