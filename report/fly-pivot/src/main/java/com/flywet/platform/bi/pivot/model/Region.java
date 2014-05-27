package com.flywet.platform.bi.pivot.model;

import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.factory.PivotRegionFactory;
import com.tonbeller.wcf.controller.RequestContext;

public class Region implements IJSONObjectable {

	public static final String PROP_NAME_NAME = "name";
	public static final String PROP_NAME_ANNOTATION = "annotation";
	public static final String PROP_NAME_REGION_OBJECT = "regionObject";

	// 名称
	private String name;

	// 注释
	private String annotation;

	// 开始位置
	private PositionType startPosition;

	// 区域
	private IRegionObject regionObject;

	private Region() {

	}

	public static Region instance(Node node) throws BIException {
		Region r = new Region();
		r.name = XMLHandler.getTagAttribute(node, PROP_NAME_NAME);
		r.annotation = XMLHandler.getTagAttribute(node, PROP_NAME_ANNOTATION);

		Node startPosition = XMLHandler.getSubNode(node,
				PositionType.NODE_NAME_START_POSITION);
		if (startPosition != null) {
			r.startPosition = new PositionType(startPosition);
		}

		r.regionObject = PivotRegionFactory.resolver(node);

		return r;
	}

	public void init(RequestContext context) throws BIException {
		if (regionObject != null) {
			regionObject.init(context);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (name != null) {
			jo.put(PROP_NAME_NAME, name);
		}

		if (startPosition != null) {
			jo.put(PositionType.PROP_NAME_START_POSITION,
					startPosition.renderJo(context));
		}

		if (regionObject != null) {
			jo.put(PROP_NAME_REGION_OBJECT, regionObject.renderJo(context));
		}

		return jo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public IRegionObject getRegionObject() {
		return regionObject;
	}

	public void setRegionObject(IRegionObject regionObject) {
		this.regionObject = regionObject;
	}

}
