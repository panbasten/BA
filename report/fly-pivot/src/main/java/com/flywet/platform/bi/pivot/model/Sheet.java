package com.flywet.platform.bi.pivot.model;

import java.util.ArrayList;
import java.util.List;

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
public class Sheet {
	public static final String PROP_NAME_SHEET_NAME = "sheetName";
	public static final String PROP_NAME_ANNOTATION = "annotation";
	public static final String PROP_NAME_DEFAULT_COL_WIDTH = "defaultColWidth";
	public static final String PROP_NAME_DEFAULT_ROW_HEIGHT = "defaultRowHeight";
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

	// 开始位置
	private PositionType startPosition;

	// 区域集
	private List<Region> regions;

	private Sheet() {

	}

	public static Sheet instance(Node node) {
		Sheet s = new Sheet();
		s.sheetName = XMLHandler.getTagAttribute(node, PROP_NAME_SHEET_NAME);
		s.annotation = XMLHandler.getTagAttribute(node, PROP_NAME_ANNOTATION);

		s.defaultColWidth = Utils.toInt(XMLHandler.getTagAttribute(node,
				PROP_NAME_DEFAULT_COL_WIDTH), null);
		s.defaultRowHeight = Utils.toInt(XMLHandler.getTagAttribute(node,
				PROP_NAME_DEFAULT_ROW_HEIGHT), null);

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

	public void init(RequestContext context) throws BIException {
		if (regions != null && regions.size() > 0) {
			for (Region r : regions) {
				r.init(context);
			}
		}
	}

	@SuppressWarnings("unchecked")
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

		if (startPosition != null) {
			jo.put(PositionType.PROP_NAME_START_POSITION, startPosition
					.renderJo());
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
}
