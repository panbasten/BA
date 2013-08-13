package com.flywet.platform.bi.report.model;

import org.apache.log4j.Logger;

import com.flywet.platform.bi.report.utils.SpreedSheetUtils;

public class CellArea implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = -4115801307205680822L;

	private static Class<?> PKG = Cell.class;
	private final Logger logger = Logger.getLogger(CellArea.class);

	private int startRow, startColumn;
	private int endRow, endColumn;

	private CellArea(int startRow, int startColumn) {
		this(startRow, startColumn, startRow, startColumn);
	}

	private CellArea(int startRow, int startColumn, int endRow, int endColumn) {
		resize(startRow, startColumn, startRow, startColumn);
	}

	public static CellArea getInstance(String area) {
		int[] areaPos = SpreedSheetUtils.parserAreaPositionString(area);
		return new CellArea(areaPos[0], areaPos[1], areaPos[2], areaPos[3]);
	}

	public static CellArea getInstance(int startRow, int startCol, int width,
			int height) {
		int endRow = startRow + height - 1;
		int endCol = startCol + width - 1;

		return new CellArea(startRow, startCol, endRow, endCol);
	}

	/**
	 * 改变区域尺寸
	 * 
	 * @param startRow
	 * @param startColumn
	 * @param endRow
	 * @param endColumn
	 */
	public void resize(int startRow, int startColumn, int endRow, int endColumn) {
		if (startRow < 0 || startColumn < 0 || endRow < 0 || endColumn < 0) {
			throw new IllegalArgumentException();
		}
		if (startRow <= endRow && startColumn <= endColumn) {
			this.startRow = startRow;
			this.endRow = endRow;
			this.startColumn = startColumn;
			this.endColumn = endColumn;
		} else {
			this.startRow = Math.min(startRow, endRow);
			this.endRow = Math.max(startRow, endRow);
			this.startColumn = Math.min(startColumn, endColumn);
			this.endColumn = Math.max(startColumn, endColumn);
		}
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
		if (this.startRow == this.endRow && this.startColumn == this.endColumn) {
			return true;
		}
		return false;
	}

	/**
	 * 移动区域
	 */
	public void move(int stepRow, int stepCol) {
		resize(this.startRow + stepRow, this.endRow + stepRow, this.startColumn
				+ stepCol, this.endColumn + stepCol);
	}

	/**
	 * 起始值:结束值，例如：A1:B2
	 */
	@Override
	public String toString() {
		if (isCell()) {
			return SpreedSheetUtils
					.getCellPositionString(startRow, startColumn);
		}

		return SpreedSheetUtils.getCellPositionString(startRow, startColumn)
				+ ":"
				+ SpreedSheetUtils.getCellPositionString(endRow, endColumn);
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

		int row1 = Math.max(this.startRow, ca.startRow);
		int column1 = Math.max(this.startColumn, ca.startColumn);
		int row2 = Math.min(this.endRow, ca.endRow);
		int column2 = Math.min(this.endColumn, ca.endColumn);

		return new CellArea(row1, column1, row2, column2);
	}

	public int getStartRow() {
		return startRow;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getEndColumn() {
		return endColumn;
	}

	public int getRowNum() {
		return endRow - startRow + 1;
	}

	public int getColumnNum() {
		return endColumn - startColumn + 1;
	}
}
