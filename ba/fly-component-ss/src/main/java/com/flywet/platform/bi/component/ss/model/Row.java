package com.flywet.platform.bi.component.ss.model;

import java.util.TreeMap;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.core.ComponentObjectInterface;

public class Row implements ComponentObjectInterface {

	private static final long serialVersionUID = -600528507398123576L;

	private Sheet parent;

	// 高度
	private Integer height;

	// 样式索引
	private Integer styleIdx;

	// 是否隐藏
	private boolean hidden = false;

	// Cell
	private TreeMap<Integer, Cell> cells;

	public Row(Sheet parent) {
		this.parent = parent;

		this.cells = new TreeMap<Integer, Cell>();
	}

	/**
	 * 创建一个Cell
	 * 
	 * @param colIdx
	 * @return
	 */
	public Cell createCell(int colIdx) {
		Cell c = getCell(colIdx);
		if (c == null) {
			c = new Cell(this);
			this.cells.put(colIdx, c);
		}
		return c;
	}

	/**
	 * 添加一个Cell
	 * 
	 * @param colIdx
	 * @param c
	 */
	public void addCell(int colIdx, Cell c) {
		this.cells.put(colIdx, c);
	}

	/**
	 * 获得一个Cell
	 * 
	 * @param colIdx
	 * @return
	 */
	public Cell getCell(int colIdx) {
		return this.cells.get(colIdx);
	}

	/**
	 * 删除一个Cell
	 * 
	 * @param colIdx
	 * @return
	 */
	public Cell removeCell(int colIdx) {
		return this.cells.remove(colIdx);
	}

	public boolean isSetHeight() {
		return (height != null);
	}

	public Integer getHeight() {
		return height;
	}

	public boolean isSetStyle() {
		return (styleIdx != null);
	}

	public Integer getStyleIndex() {
		return styleIdx;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	@Override
	public JSONObject toJSONObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
