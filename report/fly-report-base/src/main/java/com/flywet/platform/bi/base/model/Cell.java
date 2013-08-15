package com.flywet.platform.bi.base.model;

import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;

import com.flywet.platform.bi.base.style.ICellStyle;
import com.flywet.platform.bi.base.utils.Utils;

public class Cell implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = -4908563419218464581L;

	private static Class<?> PKG = Cell.class;
	private final Logger logger = Logger.getLogger(Cell.class);

	// 单元格样式
	private ICellStyle cellStyle;

	// 合并单元格信息
	private int rowspan = 1, colspan = 1;

	// 其他单元格格式信息
	private Map<String, Object> extFmtMap;

	// 单元格的值
	private Object val;

	/**
	 * 添加单元格附加格式信息。例如，一个单元格可以同时是一个公司和指标
	 * 
	 * @param extName
	 * @param extFmt
	 */
	public void addExtFmt(String extName, Object extFmt) {
		if (extFmtMap == null) {
			extFmtMap = new Hashtable<String, Object>();
		}
		if (extFmt == null) {
			extFmtMap.remove(extName);
		} else {
			extFmtMap.put(extName, extFmt);
		}
	}

	/**
	 * 获得单元格的附加格式信息。
	 * 
	 * @param extName
	 * @return
	 */
	public Object getExtFmt(String extName) {
		if (extFmtMap != null && extName != null) {
			return extFmtMap.get(extName);
		}
		return null;
	}

	public void removeExtFmt(String extName) {
		if (extFmtMap != null && extName != null) {
			extFmtMap.remove(extName);
		}
	}

	public void clearExtFmt() {
		if (extFmtMap != null) {
			extFmtMap.clear();
		}
	}

	public int getExtFmtSize() {
		return (extFmtMap == null) ? 0 : extFmtMap.size();
	}

	public ICellStyle getCellStyle() {
		return cellStyle;
	}

	public Cell setCellStyle(ICellStyle cellStyle) {
		this.cellStyle = cellStyle;
		return this;
	}

	public int getRowspan() {
		return rowspan;
	}

	public Cell setRowspan(int rowspan) {
		this.rowspan = rowspan;
		return this;
	}

	public int getColspan() {
		return colspan;
	}

	public Cell setColspan(int colspan) {
		this.colspan = colspan;
		return this;
	}

	public Object getVal() {
		return val;
	}

	public Cell setVal(Object val) {
		this.val = val;
		return this;
	}

	/**
	 * 重置Cell
	 */
	public void reset() {
		this.cellStyle = null;
		this.rowspan = 1;
		this.colspan = 1;
		this.val = null;
		this.clearExtFmt();
	}

	public void remove() {
		this.cellStyle = null;
		this.val = null;
		this.extFmtMap = null;
	}

	@Override
	public Cell clone() {
		try {
			Cell newcell = (Cell) super.clone();
			newcell.val = Utils.deepCopy(val);
			newcell.extFmtMap = Utils.deepCopy(extFmtMap);
			return newcell;
		} catch (Exception e) {
			logger.error("克隆一个Cell出现错误。");
		}
		return null;
	}
}
