package com.flywet.platform.bi.report.style;

import com.flywet.platform.bi.report.enums.AlignEnum;
import com.flywet.platform.bi.report.enums.VerticalEnum;

public interface ICellAlignStyle extends ISpreadSheetStyle {

	public AlignEnum getAlign();

	public VerticalEnum getVertical();

	public float getIndentation();

	public boolean isWrap();

	public boolean isShrink();

}