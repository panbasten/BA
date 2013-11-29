package com.flywet.platform.bi.component.ss.model;

import java.util.TreeMap;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.core.ComponentObjectInterface;
import com.flywet.platform.bi.component.ss.model.style.ICellStyle;

public class Sheet implements ComponentObjectInterface {

	private static final long serialVersionUID = -630795067754958151L;

	// 表格名称
	private String sheetName;

	private Workbook parent;

	private TreeMap<Integer, Row> rows;

	private TreeMap<Integer, Column> cols;

	// 总行数
	private Integer rowNum;

	// 总列数
	private Integer colNum;

	// 冻结列数
	private Integer colSplitNum;

	// 冻结行数
	private Integer rowSplitNum;

	// 左部显示列数
	private Integer leftColNum;

	// 上部显示行数
	private Integer topColNum;

	// 是否保护
	private boolean protect;

	// 是否显示网格线
	private boolean showGridlines;

	// 是否显示行列头
	private boolean showRowColHeadings;

	public Sheet(Workbook parent) {
		this.parent = parent;
		this.rows = new TreeMap<Integer, Row>();
		this.cols = new TreeMap<Integer, Column>();
	}

	@Override
	public JSONObject toJSONObject() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 创建一个冻结区域，将会覆盖现有的冻结区域
	 * 
	 * @param colSplit
	 *            冻结区域的水平位置
	 * @param rowSplit
	 *            冻结区域的垂直位置
	 */
	public void createFreezePane(int colSplit, int rowSplit) {
		createFreezePane(colSplit, rowSplit, colSplit, rowSplit);
	}

	/**
	 * 创建一个冻结区域，将会覆盖现有的冻结区域
	 * 
	 * @param colSplit
	 *            冻结区域的水平位置
	 * @param rowSplit
	 *            冻结区域的垂直位置
	 * @param leftCol
	 *            冻结区域左边显示列数
	 * @param topRow
	 *            冻结区域顶部显示行数
	 */
	public void createFreezePane(int colSplit, int rowSplit, int leftCol,
			int topRow) {
		this.colSplitNum = colSplit;
		this.rowSplitNum = rowSplit;
		this.leftColNum = leftCol;
		this.topColNum = topRow;
	}

	/**
	 * 创建一行
	 * 
	 * @param rowIdx
	 * @return
	 */
	public Row createRow(int rowIdx) {
		Row r = rows.get(rowIdx);
		if (r == null) {
			r = new Row(this);
			rows.put(rowIdx, r);
		}
		return r;
	}

	/**
	 * 插入一行
	 * 
	 * @param rowIdx
	 * @return
	 */
	public Row insertRow(int rowIdx) {
		if (rows.isEmpty() || rowIdx > rows.lastKey()) {
			return createRow(rowIdx);
		} else {
			// TODO
		}

		return null;
	}

	/**
	 * 删除一行
	 * 
	 * @param rowIdx
	 * @return
	 */
	public Row removeRow(int rowIdx) {
		if (rowIdx == rows.lastKey()) {
			return clearRow(rowIdx);
		} else {
			// TODO
		}
		return null;
	}

	/**
	 * 添加一行
	 * 
	 * @param rowIdx
	 * @param row
	 */
	public void addRow(int rowIdx, Row row) {
		rows.put(rowIdx, row);
	}

	/**
	 * 清除一行
	 * 
	 * @param rowIdx
	 * @return
	 */
	public Row clearRow(int rowIdx) {
		return rows.remove(rowIdx);
	}

	/**
	 * 创建一列
	 * 
	 * @param colIdx
	 * @return
	 */
	public Column createCol(int colIdx) {
		Column c = cols.get(colIdx);
		if (c == null) {
			c = new Column(this);
			cols.put(colIdx, c);
		}
		return c;
	}

	/**
	 * 插入一列
	 * 
	 * @param colIdx
	 * @return
	 */
	public Column insertCol(int colIdx) {
		if (cols.isEmpty() || colIdx > cols.lastKey()) {
			return createCol(colIdx);
		} else {
			// TODO
		}

		return null;
	}

	/**
	 * 删除一列
	 * 
	 * @param rowIdx
	 * @return
	 */
	public Column removeCol(int colIdx) {
		if (colIdx == cols.lastKey()) {
			return clearCol(colIdx);
		} else {
			// TODO
		}
		return null;
	}

	/**
	 * 添加一列
	 * 
	 * @param colIdx
	 * @param col
	 */
	public void addCol(int colIdx, Column col) {
		cols.put(colIdx, col);
	}

	/**
	 * 清除一列
	 * 
	 * @param colIdx
	 * @return
	 */
	public Column clearCol(int colIdx) {
		return cols.remove(colIdx);
	}

	/**
	 * 合并单元格
	 * 
	 * @param firstRow
	 * @param lastRow
	 * @param firstCol
	 * @param lastCol
	 */
	public void mergedCell(int firstRow, int lastRow, int firstCol, int lastCol) {
		// 检查创建行
		for (int i = firstRow; i <= lastRow; i++) {
			createRow(i);
		}

		// 合并单元格
		Row r = getRow(firstRow);
		Cell c = r.createCell(firstCol);
		c.setColspan(lastCol - firstCol + 1);
		c.setRowspan(lastRow - firstRow + 1);

		// 删除相关单元格
		for (int i = firstRow; i <= lastRow; i++) {
			for (int j = firstCol; j <= lastCol; j++) {
				getRow(i).removeCell(j);
			}
		}

		// 回填合并单元格
		r.addCell(firstCol, c);
	}

	/**
	 * 获得单元格上的注释
	 * 
	 * @param col
	 * @param row
	 * @return
	 */
	public CellComment getCellComment(int col, int row) {
		// TODO
		return null;
	}

	/**
	 * 获得单元格上的链接
	 * 
	 * @param col
	 * @param row
	 * @return
	 */
	public Hyperlink getCellHyperlink(int col, int row) {
		// TODO
		return null;
	}

	/**
	 * 通过ID获得列对象
	 * 
	 * @param colIdx
	 * @return
	 */
	public Column getCol(int colIdx) {
		return this.cols.get(colIdx);
	}

	/**
	 * 通过ID获得行对象
	 * 
	 * @param rowIdx
	 * @return
	 */
	public Row getRow(int rowIdx) {
		return this.rows.get(rowIdx);
	}

	/**
	 * 获得列宽
	 * 
	 * @param colIdx
	 * @return
	 */
	public int getColWidth(int colIdx) {
		Column c = getCol(colIdx);
		return (c != null && c.isSetWidth()) ? c.getWidth()
				: getDefaultColWidth();
	}

	/**
	 * 获得默认的列宽
	 * 
	 * @return
	 */
	public int getDefaultColWidth() {
		return this.parent.getDefaultColumnWidth();
	}

	/**
	 * 获得行高
	 * 
	 * @param rowIdx
	 * @return
	 */
	public int getRowHeight(int rowIdx) {
		Row r = getRow(rowIdx);
		return (r != null && r.isSetHeight()) ? r.getHeight()
				: getDefaultRowHeight();
	}

	/**
	 * 获得默认的行高
	 * 
	 * @return
	 */
	public int getDefaultRowHeight() {
		return this.parent.getDefaultRowHeight();
	}

	/**
	 * 获得列样式
	 * 
	 * @param colIdx
	 * @return
	 */
	public ICellStyle getColStyle(int colIdx) {
		Column c = getCol(colIdx);
		return (c != null && c.isSetStyle()) ? getStyle(c.getStyleIndex())
				: getDefaultColStyle();
	}

	/**
	 * 获得默认列样式
	 * 
	 * @return
	 */
	public ICellStyle getDefaultColStyle() {
		return this.parent.getDefaultColStyle();
	}

	/**
	 * 获得行样式
	 * 
	 * @param rowIdx
	 * @return
	 */
	public ICellStyle getRowStyle(int rowIdx) {
		Row r = getRow(rowIdx);
		return (r != null && r.isSetStyle()) ? getStyle(r.getStyleIndex())
				: getDefaultRowStyle();
	}

	/**
	 * 获得默认行样式
	 * 
	 * @return
	 */
	public ICellStyle getDefaultRowStyle() {
		return this.parent.getDefaultRowStyle();
	}

	/**
	 * 通过样式索引获得样式
	 * 
	 * @param styleIdx
	 * @return
	 */
	public ICellStyle getStyle(int styleIdx) {
		return this.parent.getStyle(styleIdx);
	}

	public int getRowNum() {
		return (rowNum == null) ? (rows.lastKey() + 1) : rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public int getColNum() {
		return (colNum == null) ? (cols.lastKey() + 1) : colNum;
	}

	public void setColNum(Integer colNum) {
		this.colNum = colNum;
	}

	public Integer getColSplitNum() {
		return colSplitNum;
	}

	public Integer getRowSplitNum() {
		return rowSplitNum;
	}

	public Integer getLeftColNum() {
		return leftColNum;
	}

	public Integer getTopColNum() {
		return topColNum;
	}

	public boolean isProtect() {
		return protect;
	}

	public void setProtect(boolean protect) {
		this.protect = protect;
	}

	public boolean isShowGridlines() {
		return showGridlines;
	}

	public void setShowGridlines(boolean showGridlines) {
		this.showGridlines = showGridlines;
	}

	public boolean isShowRowColHeadings() {
		return showRowColHeadings;
	}

	public void setShowRowColHeadings(boolean showRowColHeadings) {
		this.showRowColHeadings = showRowColHeadings;
	}

	/**
	 * 使用密码保护文档
	 * 
	 * @param password
	 */
	public void protectSheet(String password) {
		// TODO
	}

	/**
	 * 判断列是否隐藏
	 * 
	 * @param colIdx
	 * @return
	 */
	public boolean isColHidden(int colIdx) {
		Column c = getCol(colIdx);
		return (c != null) ? c.isHidden() : false;
	}

	/**
	 * 设置类是否隐藏
	 * 
	 * @param colIdx
	 * @param b
	 */
	public void setColHidden(int colIdx, boolean b) {
		Column c = createCol(colIdx);
		c.setHidden(b);
	}

	/**
	 * 判断列是否隐藏
	 * 
	 * @param rowIdx
	 * @return
	 */
	public boolean isRowHidden(int rowIdx) {
		Row r = getRow(rowIdx);
		return (r != null) ? r.isHidden() : false;
	}

	/**
	 * 设置类是否隐藏
	 * 
	 * @param rowIdx
	 * @param b
	 */
	public void setRowHidden(int rowIdx, boolean b) {
		Row r = createRow(rowIdx);
		r.setHidden(b);
	}

	/**
	 * 适应页面尺寸
	 */
	public void fitToPage() {
		// TODO
	}

	/**
	 * 按比例缩放 如果是75%，使用3/4
	 * 
	 * @param numerator
	 *            分子
	 * @param denominator
	 *            分母
	 */
	public void setZoom(int numerator, int denominator) {
		int zoom = 100 * numerator / denominator;
		// TODO
	}

	public void setZoom(int scale) {
		if (scale < 10 || scale > 400)
			throw new IllegalArgumentException("有效缩放值范围在10到400之间");
		// TODO
	}

}
