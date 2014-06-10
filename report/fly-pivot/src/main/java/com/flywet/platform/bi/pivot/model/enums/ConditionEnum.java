package com.flywet.platform.bi.pivot.model.enums;

import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.pivot.model.IStyleEnum;

public enum ConditionEnum implements IStyleEnum {
	// 等于
	EQUAL((short) 0, "=="),
	// 不等于
	NOT_EQUAL((short) 1, "!="),
	// 小于
	SMALLER((short) 2, "<"),
	// 小于等于
	SMALLER_EQUAL((short) 3, "<="),
	// 大于
	LARGER((short) 4, ">"),
	// 大于等于
	LARGER_EQUAL((short) 5, ">="),
	// 为空
	NULL((short) 6, "") {
		public String getCondition(String left, String right) {
			return left;
		}
	},
	// 非空
	NOT_NULL((short) 7, "!") {
		public String getCondition(String left, String right) {
			return this.getName() + left;
		}
	};

	public static final String ENUM_NAME = "Condition.Operator";

	private static Class<?> PKG = ConditionEnum.class;

	ConditionEnum(short index, String name) {
		this.index = index;
		this.name = name;
	}

	private final short index;

	private final String name;

	public static ConditionEnum get(short index) {
		for (ConditionEnum e : ConditionEnum.values()) {
			if (e.index == index) {
				return e;
			}
		}
		return null;
	}

	public static ConditionEnum getByName(String name) {
		for (ConditionEnum e : ConditionEnum.values()) {
			if (e.name.equals(name)) {
				return e;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public String getCondition(String left, String right) {
		return left + name + right;
	}

	@Override
	public short getIndex() {
		return index;
	}

	@Override
	public String getText() {
		return BaseMessages.getString(PKG, ENUM_LONG_DESC_PREFIX + "."
				+ ENUM_NAME + "." + this.name());
	}

}
