package com.yonyou.bq8.di.component.resolvers.item;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.pms.util.Const;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yonyou.bq8.di.component.core.ComponentResolverInterface;
import com.yonyou.bq8.di.component.resolvers.BaseComponentResolver;
import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.component.utils.HTMLWriter;
import com.yonyou.bq8.di.core.exception.DIJSONException;
import com.yonyou.bq8.di.core.exception.DIPageException;
import com.yonyou.bq8.di.core.utils.JSONUtils;

public class BQDataGridResolver extends BaseComponentResolver implements
		ComponentResolverInterface {
	public static final String ATTR_WIDTH = "width";
	public static final String ATTR_HEIGHT = "height";
	public static final String ATTR_COLUMNS = "columns";
	public static final String ATTR_ROW = "row";
	public static final String ATTR_COLUMN = "column";
	public static final String ATTR_TOOLBAR = "toolbar";
	public static final String ATTR_DATA = "data";

	@SuppressWarnings("unchecked")
	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
		try {
			html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
			String weightVar = HTML.getTagAttribute(node, HTML.TAG_WEIGHT_VAR,
					attrs);
			String id = HTML.getTagAttribute(node, HTML.ATTR_ID, attrs);
			String width = HTML.getTagAttribute(node, ATTR_WIDTH, attrs);
			String height = HTML.getTagAttribute(node, ATTR_HEIGHT, attrs);
			String style = HTML.getTagAttribute(node, HTML.ATTR_STYLE, attrs);
			style = Const.NVL(style, "");
			if (width != null) {
				style += ATTR_WIDTH
						+ ":"
						+ ((StringUtils.isNumeric(width)) ? width + "px"
								: width) + ";";
			}
			if (height != null) {
				style += ATTR_HEIGHT
						+ ":"
						+ ((StringUtils.isNumeric(height)) ? height + "px"
								: height) + ";";
			}
			html.writeAttribute(HTML.ATTR_ID, id);
			html.writeAttribute(HTML.ATTR_STYLE, style);

			html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);

			Map<String, Object> map = HTML.getAttributesMap(node
					.getAttributes(), new String[] { ATTR_WIDTH, ATTR_HEIGHT,
					HTML.ATTR_STYLE, ATTR_COLUMNS, ATTR_TOOLBAR, ATTR_DATA },
					attrs);
			JSONObject jo = JSONUtils.convertToJSONObject(map);
			// column,toolbar
			JSONArray columns = (JSONArray) HTML.getTagAttributeObject(node,
					ATTR_COLUMNS, attrs);
			JSONArray toolbar = (JSONArray) HTML.getTagAttributeObject(node,
					ATTR_TOOLBAR, attrs);
			Object data = HTML.getTagAttributeObject(node, ATTR_DATA, attrs);

			if (columns != null)
				jo.put(ATTR_COLUMNS, columns);
			if (toolbar != null)
				jo.put(ATTR_TOOLBAR, toolbar);
			if (data != null) {
				jo.put(ATTR_DATA, getJSONAttribute(data));
			}

			// 解析子元素
			parserSubNode(jo, node, attrs);

			script.add("YonYou.cw('Grid','" + Const.NVL(weightVar, "") + "',"
					+ jo.toJSONString() + ");");

		} catch (DIPageException e) {
			throw e;
		} catch (Exception e) {
			throw new DIPageException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void parserSubNode(JSONObject jo, Node node,
			BQVariableResolver attrs) throws DIPageException, DIJSONException {
		Node columns = this.getFirstSubNode(node, HTML.COMPONENT_TYPE_BQ_PREFIX
				+ ATTR_COLUMNS);
		if (columns != null) {
			jo.put(ATTR_COLUMNS, parserColumns(columns, attrs));
		}
	}

	@SuppressWarnings("unchecked")
	private JSONArray parserColumns(Node node, BQVariableResolver attrs)
			throws DIPageException, DIJSONException {
		JSONArray ja = new JSONArray();
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node subNode = nodeList.item(i);
				String subNodeName = subNode.getNodeName();
				if (subNodeName.equalsIgnoreCase(HTML.COMPONENT_TYPE_BQ_PREFIX
						+ ATTR_ROW)) {
					ja.add(parserColumnsRow(subNode, attrs));
				}
			}
		}
		return ja;
	}

	@SuppressWarnings("unchecked")
	private JSONArray parserColumnsRow(Node node, BQVariableResolver attrs)
			throws DIPageException, DIJSONException {
		JSONArray ja = new JSONArray();
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node subNode = nodeList.item(i);
				String subNodeName = subNode.getNodeName();
				if (subNodeName.equalsIgnoreCase(HTML.COMPONENT_TYPE_BQ_PREFIX
						+ ATTR_COLUMN)) {
					ja.add(parserColumnsRowColumn(subNode, attrs));
				}
			}
		}
		return ja;
	}

	private JSONObject parserColumnsRowColumn(Node node,
			BQVariableResolver attrs) throws DIPageException, DIJSONException {
		Map<String, Object> map = HTML.getAttributesMap(node.getAttributes(),
				null, attrs);
		return JSONUtils.convertToJSONObject(map);
	}

}
