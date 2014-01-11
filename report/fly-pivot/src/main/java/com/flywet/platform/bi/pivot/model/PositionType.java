package com.flywet.platform.bi.pivot.model;

import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.utils.Utils;

public class PositionType {
	
	public static final String PROP_NAME_START_POSITION = "startPosition";
	
	private long x;
	private long y;

	public PositionType(long x, long y) {
		this.x = x;
		this.y = y;
	}

	public PositionType(Node node) {
		this.x = Utils.toLong(XMLHandler.getTagAttribute(node, "x"), 0);
		this.y = Utils.toLong(XMLHandler.getTagAttribute(node, "y"), 0);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject renderJo(){
		JSONObject jo = new JSONObject();
		jo.put("x", x);
		jo.put("y", y);
		return jo;
	}

	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

}
