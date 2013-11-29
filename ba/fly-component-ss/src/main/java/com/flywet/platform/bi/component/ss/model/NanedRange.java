package com.flywet.platform.bi.component.ss.model;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.core.ComponentObjectInterface;
import com.flywet.platform.bi.component.ss.model.def.DefaultConst;
import com.flywet.platform.bi.component.ss.utils.SpreadSheetUtils;

public class NanedRange implements ComponentObjectInterface {

	private static final long serialVersionUID = -4115801307205680822L;

	private static Class<?> PKG = Cell.class;
	private final Logger logger = Logger.getLogger(NanedRange.class);

	private CellPosition startPos, endPos;

	private NanedRange(CellPosition startPos) {
		this(startPos, startPos);
	}

	private NanedRange(CellPosition startPos, CellPosition endPos) {
		resize(startPos, endPos);
	}

	public static NanedRange getInstance(String area) {
		CellPosition[] areaPos = SpreadSheetUtils
				.parserCellAreaPositionString(area);
		return new NanedRange(areaPos[0], areaPos[1]);
	}

	public static NanedRange getInstance(int startRow, int startCol, int width,
			int height) {
		return new NanedRange(CellPosition.getInstance(startRow, startCol),
				CellPosition.getInstance(startRow + height - 1, startCol
						+ width - 1));
	}

	/**
	 * 改变区域尺寸
	 * 
	 * @param startRow
	 * @param startColumn
	 * @param endRow
	 * @param endColumn
	 */
	public void resize(CellPosition startPos, CellPosition endPos) {
		CellPosition[] pos = SpreadSheetUtils.sort(startPos, endPos);
		this.startPos = pos[0];
		this.endPos = pos[1];
	}

	/**
	 * 判断两个区域是否交叉
	 * 
	 * @return
	 */
	public boolean cross(NanedRange ca) {
		return SpreadSheetUtils.cross(this, ca);
	}

	/**
	 * 判断是否是一个Cell
	 * 
	 * @return
	 */
	public boolean isCell() {
		if (this.startPos.equals(this.endPos)) {
			return true;
		}
		return false;
	}

	/**
	 * 移动区域
	 */
	public void move(int stepRow, int stepCol) {
		resize(this.startPos.move(stepRow, stepCol), this.endPos.move(stepRow,
				stepCol));
	}

	/**
	 * 起始值:结束值，例如：A1:B2
	 */
	@Override
	public String toString() {
		if (isCell()) {
			return this.startPos.toString();
		}

		return this.startPos.toString() + DefaultConst.CELL_POS_SEPARATOR
				+ this.endPos.toString();
	}

	/**
	 * 创建交集区域
	 * 
	 * @param ca
	 * @return
	 */
	public NanedRange crossCellArea(NanedRange ca) {
		if (!cross(ca)) {
			return null;
		}

		int row1 = Math.max(this.startPos.getRow(), ca.startPos.getRow());
		int column1 = Math.max(this.startPos.getCol(), ca.startPos.getCol());
		int row2 = Math.min(this.endPos.getRow(), ca.endPos.getRow());
		int column2 = Math.min(this.endPos.getCol(), ca.endPos.getCol());

		return new NanedRange(CellPosition.getInstance(row1, column1),
				CellPosition.getInstance(row2, column2));
	}

	public CellPosition getStartPos() {
		return startPos;
	}

	public CellPosition getEndPos() {
		return endPos;
	}

	public int getRowNum() {
		return endPos.getRow() - startPos.getRow() + 1;
	}

	public int getColumnNum() {
		return endPos.getCol() - startPos.getCol() + 1;
	}

	@Override
	public JSONObject toJSONObject() {
		// TODO Auto-generated method stub
		return null;
	}
}
