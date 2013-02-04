package com.yonyou.bq8.di.component.components.flow.hop;

public enum SysLineType {

	// 实线
	SOLID("solid"),

	// 虚线
	DOTTED("dotted");

	SysLineType(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

	public static SysLineType get(String name) {
		for (SysLineType line : SysLineType.values()) {
			if (line.getName().equalsIgnoreCase(name)) {
				return line;
			}
		}
		return SOLID;
	}

}
