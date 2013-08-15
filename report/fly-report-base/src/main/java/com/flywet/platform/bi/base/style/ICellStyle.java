package com.flywet.platform.bi.base.style;

public interface ICellStyle extends ISpreadSheetStyle {

	public ICellFontStyle getFont();

	public ICellAlignStyle getAlign();

	public ICellLinesStyle getLines();

}
