package jackson;

public class ConditionAttributes extends BasicAttribute{
	private String rule;
	private String ruleValue;
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getRuleValue() {
		return ruleValue;
	}
	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}
	
}
