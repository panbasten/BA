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
 * 透视报表对象
 * 
 * @author PeterPan
 * 
 */
public class PivotReport {

	public static final String PROP_NAME_INFINITE_TABLE = "infiniteTable";
	public static final String PROP_NAME_ANNOTATION = "annotation";
	public static final String PROP_NAME_START_POSITION = "StartPosition";
	public static final String PROP_NAME_REGION = "Region";

	// 是否是无限扩展表
	private boolean infiniteTable;

	// 注释
	private String annotation;

	// 开始位置
	private PositionType startPosition;

	// 区域集
	private List<Region> regions;

	private PivotReport() {

	}

	public static PivotReport instance(Node node) {
		PivotReport pr = new PivotReport();
		pr.infiniteTable = Utils.toBoolean(XMLHandler.getTagAttribute(node,
				PROP_NAME_INFINITE_TABLE), true);
		pr.annotation = XMLHandler.getTagAttribute(node, PROP_NAME_ANNOTATION);

		Node startPosition = XMLHandler.getSubNode(node,
				PROP_NAME_START_POSITION);
		if (startPosition != null) {
			pr.startPosition = new PositionType(startPosition);
		}

		List<Node> regionNodes = XMLHandler.getNodes(node, PROP_NAME_REGION);
		if (regionNodes != null && regionNodes.size() > 0) {
			for (Node n : regionNodes) {
				pr.addRegion(Region.instance(n));
			}
		}

		return pr;
	}

	public void init() throws BIException {
		if (regions != null && regions.size() > 0) {
			for (Region r : regions) {
				r.init();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();
		jo.put(PROP_NAME_INFINITE_TABLE, infiniteTable);

		if (annotation != null) {
			jo.put(PROP_NAME_ANNOTATION, annotation);
		}

		if (startPosition != null) {
			jo.put(PROP_NAME_START_POSITION, startPosition.renderJo());
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

	public boolean isInfiniteTable() {
		return infiniteTable;
	}

	public void setInfiniteTable(boolean infiniteTable) {
		this.infiniteTable = infiniteTable;
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

}
