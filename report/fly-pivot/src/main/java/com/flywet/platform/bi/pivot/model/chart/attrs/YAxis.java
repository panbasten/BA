package com.flywet.platform.bi.pivot.model.chart.attrs;

import java.util.Map;

import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.pivot.model.IPivotReport;
import com.tonbeller.wcf.controller.RequestContext;

public class YAxis implements IPivotReport {
	Map<String, String> attrs;

	Boolean opposite;

	Title title;

	Label labels;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (attrs != null) {
			jo.putAll(attrs);
		}

		if (opposite != null) {
			jo.put("opposite", opposite);
		}

		if (title != null) {
			jo.put("title", title.renderJo(context));
		}

		if (labels != null) {
			jo.put("labels", labels.renderJo(context));
		}

		return jo;
	};

	public static YAxis instance(Node node) {
		YAxis y = new YAxis();

		Map<String, String> attrsMap = XMLHandler.getNodeAttributesMap(node);
		if (attrsMap != null) {
			y.attrs = attrsMap;
			y.opposite = Utils.toBoolean(attrsMap.get("opposite"), null);
		}

		Node titleNode = XMLHandler.getSubNode(node, "title");
		if (titleNode != null) {
			y.title = Title.instance(titleNode);
		}

		Node labelsNode = XMLHandler.getSubNode(node, "labels");
		if (labelsNode != null) {
			y.labels = Label.instance(labelsNode);
		}

		return y;
	}

	@Override
	public void init(RequestContext context) throws BIException {
		if (title != null) {
			title.init(context);
		}

		if (labels != null) {
			labels.init(context);
		}
	}

	@Override
	public Object findByName(String name) throws BIException {
		if (name.equals(attrs.get("name"))) {
			return this;
		}

		Object rtn;
		if (title != null) {
			rtn = title.findByName(name);
			if (rtn != null)
				return rtn;
		}

		if (labels != null) {
			rtn = labels.findByName(name);
			if (rtn != null)
				return rtn;
		}
		return null;
	}
}
