package com.flywet.platform.bi.pivot.model.enums;

import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.pivot.model.IStyleEnum;

public enum ChartTypeEnum implements IStyleEnum {

	// 2D面积图
	AREA2D((short) 0, "Area2D"),
	// 2D柱形图
	COLUMN2D((short) 0, "Column2D"),
	// 3D柱形图
	COLUMN3D((short) 0, "Column3D"),
	// 2D饼图
	PIE2D((short) 0, "Pie2D"),
	// 3D饼图
	PIE3D((short) 0, "Pie3D"),
	//
	LINE((short) 0, "Line"),
	//
	MSCOLUMN2D((short) 0, "MSColumn2D"),
	//
	MSCOLUMN3D((short) 0, "MSColumn3D"),
	//
	MSLINE((short) 0, "MSLine"),
	//
	MSCOMBI2D((short) 0, "MSCombi2D"),
	//
	MSCOMBI3D((short) 0, "MSCombi3D"),
	//
	MSCOLUMNLINE3D((short) 0, "MSColumnLine3D"),
	//
	STACKEDCOLUMN3D((short) 0, "StackedColumn3D"),
	//
	MSCOLUMN3DLINEDY((short) 0, "MSColumn3DLineDY");

	public static final String ENUM_NAME = "ChartType";

	private static Class<?> PKG = ChartTypeEnum.class;

	ChartTypeEnum(short index, String chartName) {
		this.index = index;
		this.chartName = chartName;
	}

	private short index;

	private String chartName;

	public static ChartTypeEnum get(short index) {
		for (ChartTypeEnum e : ChartTypeEnum.values()) {
			if (e.index == index) {
				return e;
			}
		}
		return null;
	}

	public static ChartTypeEnum getByName(String chartName) {
		for (ChartTypeEnum e : ChartTypeEnum.values()) {
			if (e.chartName.equals(chartName)) {
				return e;
			}
		}
		return null;
	}

	public String getChartName() {
		return chartName;
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
