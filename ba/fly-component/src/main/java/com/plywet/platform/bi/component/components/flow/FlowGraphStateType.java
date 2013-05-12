package com.plywet.platform.bi.component.components.flow;

public enum FlowGraphStateType {

	// 显示 (默认)
	SHOW("show"),
	// 不显示
	HIDE("hide"),
	// 选择
	SELECTED("selected");

	private String name;

	FlowGraphStateType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static FlowGraphStateType get(String name) {
		for (FlowGraphStateType state : FlowGraphStateType.values()) {
			if (state.getName().equalsIgnoreCase(name)) {
				return state;
			}
		}
		return SHOW;
	}

}
