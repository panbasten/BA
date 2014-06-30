package com.flywet.platform.bi.pivot.model.chart.attrs;

import java.util.Map;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.pivot.model.IPivotReport;
import com.tonbeller.wcf.controller.RequestContext;

public class XAxis implements IPivotReport {
	Map<String, String> attrs;

	Title title;

	Label labels;

	String categories;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (attrs != null) {
			jo.putAll(attrs);
		}

		if (title != null) {
			jo.put("title", title.renderJo(context));
		}

		if (labels != null) {
			jo.put("labels", labels.renderJo(context));
		}

		if (categories != null) {
			try {
				jo.put("categories",
						JSONUtils.convertStringToJSONArray(categories));
			} catch (Exception e) {
				throw new BIException("解析xAxis的categories属性出现问题。", e);
			}
		}

		return jo;
	};

	public static XAxis instance(Node node) {
		XAxis x = new XAxis();

		Map<String, String> attrsMap = XMLHandler.getNodeAttributesMap(node);
		if (attrsMap != null) {
			x.attrs = attrsMap;
		}

		Node titleNode = XMLHandler.getSubNode(node, "title");
		if (titleNode != null) {
			x.title = Title.instance(titleNode);
		}

		Node labelsNode = XMLHandler.getSubNode(node, "labels");
		if (labelsNode != null) {
			x.labels = Label.instance(labelsNode);
		}

		Node categoriesNode = XMLHandler.getSubNode(node, "categories");
		if (categoriesNode != null) {
			x.categories = Const.removeTAB(Const.removeCRLF(XMLHandler
					.getNodeValue(categoriesNode)));
		}

		return x;
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
