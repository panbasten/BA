package com.flywet.platform.bi.component.ss.model.style;

import com.flywet.platform.bi.component.ss.model.Color;
import com.flywet.platform.bi.component.ss.model.ISpreadSheetMeta;
import com.flywet.platform.bi.component.ss.model.enums.FontStyleEnum;

public interface ICellFontStyle extends ISpreadSheetMeta {

	public String getFontName();

	public FontStyleEnum getFontStyle();

	public int getFontSize();

	public Color getFontColor();

	public Boolean getStrikethrough();

}