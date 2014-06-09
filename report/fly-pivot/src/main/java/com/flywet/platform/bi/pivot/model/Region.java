package com.flywet.platform.bi.pivot.model;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.pivot.model.factory.PivotRegionFactory;
import com.tonbeller.wcf.controller.RequestContext;

public class Region implements IPivotReport {

	public static final String PROP_NAME_NAME = "name";
	public static final String PROP_NAME_ANNOTATION = "annotation";
	public static final String PROP_NAME_MARGIN = "margin";
	public static final String PROP_NAME_REGION_OBJECT = "regionObject";

	// 名称
	private String name;

	// 注释
	private String annotation;

	// 间距
	private Integer margin;

	// 开始位置
	private PositionType startPosition;

	// 结束位置
	private PositionType endPosition;

	// 区域
	private IRegionObject regionObject;

	private Region() {

	}

	public static Region instance(Node node) throws BIException {
		Region r = new Region();
		r.name = Const.NVL(XMLHandler.getTagAttribute(node, PROP_NAME_NAME),
				null);
		r.annotation = Const.NVL(
				XMLHandler.getTagAttribute(node, PROP_NAME_ANNOTATION), null);
		r.margin = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_MARGIN), null);

		Node startPosition = XMLHandler.getSubNode(node,
				PositionType.NODE_NAME_START_POSITION);
		if (startPosition != null) {
			r.startPosition = new PositionType(startPosition);
		}

		Node endPosition = XMLHandler.getSubNode(node,
				PositionType.NODE_NAME_END_POSITION);
		if (endPosition != null) {
			r.endPosition = new PositionType(endPosition);
		}

		r.regionObject = PivotRegionFactory.resolver(node);

		return r;
	}

	@Override
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

		if (margin != null) {
			jo.put(PROP_NAME_MARGIN, margin);
		}

		if (startPosition != null) {
			jo.put(PositionType.PROP_NAME_START_POSITION,
					startPosition.renderJo(context));
		}

		if (endPosition != null) {
			jo.put(PositionType.PROP_NAME_END_POSITION,
					endPosition.renderJo(context));
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

	public PositionType getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(PositionType endPosition) {
		this.endPosition = endPosition;
	}

	public IRegionObject getRegionObject() {
		return regionObject;
	}

	public void setRegionObject(IRegionObject regionObject) {
		this.regionObject = regionObject;
	}

	public Integer getMargin() {
		return margin;
	}

	public void setMargin(Integer margin) {
		this.margin = margin;
	}

	@Override
	public Object findByName(String name) throws BIException {
		if (name.equals(this.name)) {
			return this;
		}
		return null;
	}

}
