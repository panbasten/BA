package com.flywet.platform.bi.pivot.model.enums;

import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.pivot.model.IStyleEnum;

/**
 * 边框的线条
 * 
 * @author PeterPan
 * 
 */
public enum LineEnum implements IStyleEnum {
	// 无表格线
	BORDER_NONE((short) 0),
	// 细线
	BORDER_THIN((short) 1),
	// 中等粗线
	BORDER_MEDIUM((short) 2),
	// 虚线
	BORDER_DASHED((short) 3),
	// 点线
	BORDER_HAIR((short) 4),
	// 加粗线
	BORDER_THICK((short) 5),
	// 双线
	BORDER_DOUBLE((short) 6),
	// 点虚线
	BORDER_DOTTED((short) 7),
	// 中等点虚线
	BORDER_MEDIUM_DASHED((short) 8),
	// 破折点线
	BORDER_DASH_DOT((short) 9),
	// 中等破折点线
	BORDER_MEDIUM_DASH_DOT((short) 10),
	// 破折点点线
	BORDER_DASH_DOT_DOT((short) 11),
	// 中等破折点点线
	BORDER_MEDIUM_DASH_DOT_DOT((short) 12),
	// 倾斜破折点线
	BORDER_SLANTED_DASH_DOT((short) 13);

	public static final String ENUM_NAME = "Border";

	private static Class<?> PKG = LineEnum.class;

	LineEnum(short index) {
		this.index = index;
	}

	private short index;

	public static LineEnum get(short index) {
		for (LineEnum e : LineEnum.values()) {
			if (e.index == index) {
				return e;
			}
		}
		return null;
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
