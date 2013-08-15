package com.flywet.platform.bi.base.style;

import com.flywet.platform.bi.base.enums.AlignEnum;
import com.flywet.platform.bi.base.enums.VerticalEnum;

public interface ICellAlignStyle extends ISpreadSheetStyle {

	public AlignEnum getAlign();

	public VerticalEnum getVertical();

	public float getIndentation();

	public boolean isWrap();

	public boolean isShrink();

}