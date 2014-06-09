package com.flywet.platform.bi.pivot.model;

import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.tonbeller.wcf.controller.RequestContext;

public class PositionType implements IPivotReport {

	public static final String PROP_NAME_START_POSITION = "startPosition";
	public static final String NODE_NAME_START_POSITION = "StartPosition";

	public static final String PROP_NAME_END_POSITION = "endPosition";
	public static final String NODE_NAME_END_POSITION = "EndPosition";

	private long cidx;
	private long ridx;

	public PositionType(long cidx, long y) {
		this.cidx = cidx;
		this.ridx = y;
	}

	public PositionType(Node node) {
		this.cidx = Utils.toLong(XMLHandler.getTagAttribute(node, "cidx"), 0L);
		this.ridx = Utils.toLong(XMLHandler.getTagAttribute(node, "ridx"), 0L);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();
		jo.put("cidx", cidx);
		jo.put("ridx", ridx);
		return jo;
	}

	public long getCidx() {
		return cidx;
	}

	public void setCidx(long cidx) {
		this.cidx = cidx;
	}

	public long getRidx() {
		return ridx;
	}

	public void setRidx(long ridx) {
		this.ridx = ridx;
	}

	@Override
	public void init(RequestContext context) throws BIException {

	}

	@Override
	public Object findByName(String name) throws BIException {
		return null;
	}

}
