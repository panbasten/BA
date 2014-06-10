package com.flywet.platform.bi.pivot.model.enums;

import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.pivot.model.IStyleEnum;

public enum PicturePositionEnum implements IStyleEnum {

	// 填充
	POSITION_FILLING((short) 0),
	// 拉伸
	POSITION_STRETCHING((short) 1),
	// 居中
	POSITION_CENTER((short) 2),
	// 平铺
	POSITION_TILE((short) 3);

	public static final String ENUM_NAME = "PictruePosition";

	private static Class<?> PKG = PicturePositionEnum.class;

	PicturePositionEnum(short index) {
		this.index = index;
	}

	private short index;

	public static PicturePositionEnum get(short index) {
		for (PicturePositionEnum e : PicturePositionEnum.values()) {
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
