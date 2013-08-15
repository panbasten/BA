package com.flywet.platform.bi.base.style;

import com.flywet.platform.bi.base.model.Line;

public interface ICellLinesStyle extends ISpreadSheetStyle {

	public Line getUp();

	public Line getDown();

	public Line getLeft();

	public Line getRight();

	public Line getDiagonal();

	public Line getBackslash();

	public Line getHorizontal();

	public Line getVertical();

}