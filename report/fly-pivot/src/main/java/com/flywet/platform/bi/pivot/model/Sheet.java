package com.flywet.platform.bi.pivot.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * 电子表格对象
 * 
 * @author PeterPan
 * 
 */
public class Sheet implements IJSONObjectable {
	public static final String PROP_NAME_SHEET_NAME = "sheetName";
	public static final String PROP_NAME_ANNOTATION = "annotation";
	public static final String PROP_NAME_DEFAULT_COL_WIDTH = "defaultColWidth";
	public static final String PROP_NAME_DEFAULT_ROW_HEIGHT = "defaultRowHeight";

	public static final String PROP_NAME_SHOW_COL_HEAD = "showColHead";
	public static final String PROP_NAME_SHOW_ROW_HEAD = "showRowHead";
	public static final String PROP_NAME_SHOW_GRID = "showGrid";

	public static final String PROP_NAME_COL_NUM = "colNum";
	public static final String PROP_NAME_ROW_NUM = "rowNum";

	public static final String PROP_NAME_ROWS_HEIGHT = "rowsHeight";
	public static final String NODE_NAME_ROWS_HEIGHT = "RowsHeight";
	public static final String PROP_NAME_COLS_WIDTH = "colsWidth";
	public static final String NODE_NAME_COLS_WIDTH = "ColsWidth";

	public static final String PROP_NAME_ELEMENT = "elememt";
	public static final String PROP_NAME_ELEMENT_IDX = "idx";
	public static final String PROP_NAME_ELEMENT_SIZE = "size";

	public static final String PROP_NAME_REGION = "region";
	public static final String NODE_NAME_REGION = "Region";

	// 电子表格名称
	private String sheetName;

	// 注释
	private String annotation;

	// 默认列宽
	private Integer defaultColWidth;

	// 默认行高
	private Integer defaultRowHeight;

	// 显示列头
	private Boolean showColHead;

	// 显示行头
	private Boolean showRowHead;

	// 显示网格线
	private Boolean showGrid;

	// 列数
	private Integer colNum;

	// 行数
	private Integer rowNum;

	// 开始位置
	private PositionType startPosition;

	// 行高集合
	private Map<Integer, Integer> rowsHeight;

	// 列宽集合
	private Map<Integer, Integer> colsWidth;

	// 区域集
	private List<Region> regions;

	private Sheet() {

	}

	public static Sheet instance(Node node) throws BIException {
		Sheet s = new Sheet();
		s.sheetName = XMLHandler.getTagAttribute(node, PROP_NAME_SHEET_NAME);
		s.annotation = XMLHandler.getTagAttribute(node, PROP_NAME_ANNOTATION);

		s.defaultColWidth = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_DEFAULT_COL_WIDTH),
				null);
		s.defaultRowHeight = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_DEFAULT_ROW_HEIGHT),
				null);

		s.showColHead = Utils
				.toBoolean(XMLHandler.getTagAttribute(node,
						PROP_NAME_SHOW_COL_HEAD), null);
		s.showRowHead = Utils
				.toBoolean(XMLHandler.getTagAttribute(node,
						PROP_NAME_SHOW_ROW_HEAD), null);
		s.showGrid = Utils.toBoolean(
				XMLHandler.getTagAttribute(node, PROP_NAME_SHOW_GRID), null);

		// 显示的行列数
		s.colNum = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_COL_NUM), null);
		s.rowNum = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_ROW_NUM), null);

		// 行高列宽
		s.rowsHeight = instanceRowColSize(node, NODE_NAME_ROWS_HEIGHT);
		s.colsWidth = instanceRowColSize(node, NODE_NAME_COLS_WIDTH);

		Node startPosition = XMLHandler.getSubNode(node,
				PositionType.NODE_NAME_START_POSITION);
		if (startPosition != null) {
			s.startPosition = new PositionType(startPosition);
		}

		List<Node> regionNodes = XMLHandler.getNodes(node, NODE_NAME_REGION);
		if (regionNodes != null && regionNodes.size() > 0) {
			for (Node n : regionNodes) {
				s.addRegion(Region.instance(n));
			}
		}

		return s;
	}

	private static Map<Integer, Integer> instanceRowColSize(Node node,
			String tag) {
		Node sizes = XMLHandler.getSubNode(node, tag);
		if (sizes != null) {
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			List<Node> eles = XMLHandler.getNodes(sizes, PROP_NAME_ELEMENT);
			Integer idx, size;
			for (Node ele : eles) {
				idx = Utils.toInt(
						XMLHandler.getTagAttribute(ele, PROP_NAME_ELEMENT_IDX),
						null);
				size = Utils
						.toInt(XMLHandler.getTagAttribute(ele,
								PROP_NAME_ELEMENT_SIZE), null);

				if (idx != null && size != null) {
					map.put(idx, size);
				}
			}

			return map;

		}
		return null;
	}

	public void init(RequestContext context) throws BIException {
		if (regions != null && regions.size() > 0) {
			for (Region r : regions) {
				r.init(context);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (sheetName != null) {
			jo.put(PROP_NAME_SHEET_NAME, sheetName);
		}

		if (annotation != null) {
			jo.put(PROP_NAME_ANNOTATION, annotation);
		}

		if (defaultColWidth != null) {
			jo.put(PROP_NAME_DEFAULT_COL_WIDTH, defaultColWidth);
		}

		if (defaultRowHeight != null) {
			jo.put(PROP_NAME_DEFAULT_ROW_HEIGHT, defaultRowHeight);
		}

		if (showColHead != null) {
			jo.put(PROP_NAME_SHOW_COL_HEAD, showColHead);
		}

		if (showRowHead != null) {
			jo.put(PROP_NAME_SHOW_ROW_HEAD, showRowHead);
		}

		if (showGrid != null) {
			jo.put(PROP_NAME_SHOW_GRID, showGrid);
		}

		if (colNum != null) {
			jo.put(PROP_NAME_COL_NUM, colNum);
		}

		if (rowNum != null) {
			jo.put(PROP_NAME_ROW_NUM, rowNum);
		}

		if (startPosition != null) {
			jo.put(PositionType.PROP_NAME_START_POSITION,
					startPosition.renderJo(context));
		}

		if (rowsHeight != null) {
			jo.put(PROP_NAME_ROWS_HEIGHT, renderRowColSize(rowsHeight, "r_"));
		}

		if (colsWidth != null) {
			jo.put(PROP_NAME_COLS_WIDTH, renderRowColSize(colsWidth, "c_"));
		}

		if (regions != null && regions.size() > 0) {
			JSONArray regionJa = new JSONArray();
			for (Region r : regions) {
				regionJa.add(r.renderJo(context));
			}
			jo.put(PROP_NAME_REGION, regionJa);
		}

		return jo;
	}

	@SuppressWarnings("unchecked")
	private JSONObject renderRowColSize(Map<Integer, Integer> size,
			String prefix) {
		JSONObject jo = new JSONObject();
		for (Integer idx : size.keySet()) {
			jo.put(prefix + idx, size.get(idx));
		}
		return jo;
	}

	public void addRegion(Region r) {
		if (regions == null) {
			regions = new ArrayList<Region>();
		}
		regions.add(r);
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public PositionType getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(PositionType startPosition) {
		this.startPosition = startPosition;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public Integer getDefaultColWidth() {
		return defaultColWidth;
	}

	public void setDefaultColWidth(Integer defaultColWidth) {
		this.defaultColWidth = defaultColWidth;
	}

	public Integer getDefaultRowHeight() {
		return defaultRowHeight;
	}

	public void setDefaultRowHeight(Integer defaultRowHeight) {
		this.defaultRowHeight = defaultRowHeight;
	}

	public Boolean getShowColHead() {
		return showColHead;
	}

	public void setShowColHead(Boolean showColHead) {
		this.showColHead = showColHead;
	}

	public Boolean getShowRowHead() {
		return showRowHead;
	}

	public void setShowRowHead(Boolean showRowHead) {
		this.showRowHead = showRowHead;
	}

	public Boolean getShowGrid() {
		return showGrid;
	}

	public void setShowGrid(Boolean showGrid) {
		this.showGrid = showGrid;
	}

	public Map<Integer, Integer> getRowsHeight() {
		return rowsHeight;
	}

	public void setRowsHeight(Map<Integer, Integer> rowsHeight) {
		this.rowsHeight = rowsHeight;
	}

	public Map<Integer, Integer> getColsWidth() {
		return colsWidth;
	}

	public void setColsWidth(Map<Integer, Integer> colsWidth) {
		this.colsWidth = colsWidth;
	}

	public Integer getColNum() {
		return colNum;
	}

	public void setColNum(Integer colNum) {
		this.colNum = colNum;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

}
