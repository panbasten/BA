package com.flywet.platform.bi.base.enums;

/**
 * 电子表格枚举类接口
 * 
 * @author PeterPan
 * 
 */
public interface ISpreadSheetEnum {
	public static final String ENUM_LONG_DESC_PREFIX = "SpreadSheet.EnumLongDesc";

	public short getIndex();

	public String getText();
}
