package com.flywet.platform.bi.report.model;

import org.apache.log4j.Logger;

import com.flywet.platform.bi.report.utils.DefaultConst;
import com.flywet.platform.bi.report.utils.SpreedSheetUtils;

public class CellArea implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = -4115801307205680822L;

	private static Class<?> PKG = Cell.class;
	private final Logger logger = Logger.getLogger(CellArea.class);

	private CellPosition startPos, endPos;

	private CellArea(CellPosition startPos) {
		this(startPos, startPos);
	}

	private CellArea(CellPosition startPos, CellPosition endPos) {
		resize(startPos, endPos);
	}

	public static CellArea getInstance(String area) {
		CellPosition[] areaPos = SpreedSheetUtils
				.parserCellAreaPositionString(area);
		return new CellArea(areaPos[0], areaPos[1]);
	}

	public static CellArea getInstance(int startRow, int startCol, int width,
			int height) {
		return new CellArea(CellPosition.getInstance(startRow, startCol),
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
		CellPosition[] pos = SpreedSheetUtils.sort(startPos, endPos);
		this.startPos = pos[0];
		this.endPos = pos[1];
	}

	/**
	 * 判断两个区域是否交叉
	 * 
	 * @return
	 */
	public boolean cross(CellArea ca) {
		return SpreedSheetUtils.cross(this, ca);
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
	public CellArea crossCellArea(CellArea ca) {
		if (!cross(ca)) {
			return null;
		}

		int row1 = Math.max(this.startPos.getRow(), ca.startPos.getRow());
		int column1 = Math.max(this.startPos.getCol(), ca.startPos.getCol());
		int row2 = Math.min(this.endPos.getRow(), ca.endPos.getRow());
		int column2 = Math.min(this.endPos.getCol(), ca.endPos.getCol());

		return new CellArea(CellPosition.getInstance(row1, column1),
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
}
