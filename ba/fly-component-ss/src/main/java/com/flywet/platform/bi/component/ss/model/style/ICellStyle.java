package com.flywet.platform.bi.component.ss.model.style;

import com.flywet.platform.bi.component.ss.model.ISpreadSheetMeta;

public interface ICellStyle extends ISpreadSheetMeta {

	public ICellFontStyle getFont();

	public ICellAlignStyle getAlign();

	public ICellLinesStyle getLines();

}
