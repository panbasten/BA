package com.plywet.platform.dk.repjsp;

public class ReplaceRule {
	private String value;
	private String type;
	
	public ReplaceRule(String value,String type) {
		this.type = type;
		this.value = value;
	}
	
	public ReplaceRule(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
