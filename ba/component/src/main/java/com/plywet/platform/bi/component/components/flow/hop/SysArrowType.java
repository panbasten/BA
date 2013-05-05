package com.plywet.platform.bi.component.components.flow.hop;

public enum SysArrowType {
	// 空
	EMPTY(""),
	// 默认
	DEFAULT("default"),
	// 圆形
	CIRCULARITY("circularity"),
	// 简单
	SIMPLE("simple");

	private String name;

	SysArrowType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static SysArrowType get(String name) {
		for (SysArrowType arrow : SysArrowType.values()) {
			if (arrow.getName().equalsIgnoreCase(name)) {
				return arrow;
			}
		}
		return EMPTY;
	}

}
