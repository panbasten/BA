package com.flywet.platform.bi.report.style;

public class SpreadSheetStyle implements ISpreadSheetStyle {

	// 字体样式
	private ICellFontStyle font;

	// 对齐方式
	private ICellAlignStyle align;

	// 线条样式
	private ICellLinesStyle lines;

	// 数据格式化对象
	private ICellDataFormat dataFormat;

	private SpreadSheetStyle() {

	}

	public static SpreadSheetStyle getDefaultInstance() {
		SpreadSheetStyle s3 = new SpreadSheetStyle();

		return s3;
	}

	@Override
	public String getUUID() {
		return null;
	}
}
