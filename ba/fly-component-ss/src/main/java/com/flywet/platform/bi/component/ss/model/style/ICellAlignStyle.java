package com.flywet.platform.bi.component.ss.model.style;

import com.flywet.platform.bi.component.ss.model.ISpreadSheetMeta;
import com.flywet.platform.bi.component.ss.model.enums.AlignEnum;
import com.flywet.platform.bi.component.ss.model.enums.VerticalEnum;


public interface ICellAlignStyle extends ISpreadSheetMeta {

	public AlignEnum getAlign();

	public VerticalEnum getVertical();

	public float getIndentation();

	public boolean isWrap();

	public boolean isShrink();

}