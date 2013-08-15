package com.flywet.platform.bi.base.style;

import com.flywet.platform.bi.base.enums.FontStyleEnum;
import com.flywet.platform.bi.base.model.Color;

public interface ICellFontStyle extends ISpreadSheetStyle {

	public String getFontName();

	public FontStyleEnum getFontStyle();

	public int getFontSize();

	public Color getFontColor();

	public Boolean getStrikethrough();

}