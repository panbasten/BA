package com.flywet.platform.bi.component.ss.model.style;

import com.flywet.platform.bi.component.ss.model.ISpreadSheetMeta;
import com.flywet.platform.bi.component.ss.model.Line;

public interface ICellLinesStyle extends ISpreadSheetMeta {

	public Line getUp();

	public Line getDown();

	public Line getLeft();

	public Line getRight();

	public Line getDiagonal();

	public Line getBackslash();

	public Line getHorizontal();

	public Line getVertical();

}