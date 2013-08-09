package com.flywet.platform.bi.report.style;

import com.flywet.platform.bi.report.enums.FontStyleEnum;
import com.flywet.platform.bi.report.model.Color;

public interface ICellFontStyle extends ISpreadSheetStyle {

	public String getFontName();

	public FontStyleEnum getFontStyle();

	public int getFontSize();

	public Color getFontColor();

	public Boolean getStrikethrough();

}