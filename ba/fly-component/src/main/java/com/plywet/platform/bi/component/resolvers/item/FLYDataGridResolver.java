package com.plywet.platform.bi.component.resolvers.item;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.pms.util.Const;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.HTMLWriter;
import com.plywet.platform.bi.core.exception.BIJSONException;
import com.plywet.platform.bi.core.exception.BIPageException;
import com.plywet.platform.bi.core.utils.JSONUtils;

public class FLYDataGridResolver extends BaseComponentResolver implements
		ComponentResolverInterface {
	public static final String ATTR_COLUMNS = "columns";
	public static final String ATTR_ROW = "row";
	public static final String ATTR_COLUMN = "column";
	public static final String ATTR_TOOLBAR = "toolbar";
	public static final String ATTR_DATA = "data";

	@SuppressWarnings("unchecked")
	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
			String weightVar = HTML.getTagAttribute(node, HTML.TAG_WEIGHT_VAR,
					attrs);
			String id = HTML.getId(node, attrs);
			html.writeAttribute(HTML.ATTR_ID, id);

			HTML.writeStyleAttribute(node, html, attrs);
			HTML.writeStyleClassAttribute(node, html, attrs, "");

			html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);

			Map<String, Object> map = HTML.getAttributesMap(node
					.getAttributes(), new String[] { ATTR_COLUMNS,
					ATTR_TOOLBAR, ATTR_DATA }, attrs);
			JSONObject jo = JSONUtils.convertToJSONObject(map);
			// id
			jo.put(HTML.ATTR_ID, id);
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

			script.add("Plywet.cw('Grid','" + Const.NVL(weightVar, "") + "',"
					+ jo.toJSONString() + ");");

		} catch (BIPageException e) {
			throw e;
		} catch (Exception e) {
			throw new BIPageException("解析数据表格出现错误", e);
		}
	}

	@SuppressWarnings("unchecked")
	private void parserSubNode(JSONObject jo, Node node,
			FLYVariableResolver attrs) throws BIPageException, BIJSONException {
		Node columns = this.getFirstSubNode(node,
				HTML.COMPONENT_TYPE_FLY_PREFIX + ATTR_COLUMNS);
		if (columns != null) {
			jo.put(ATTR_COLUMNS, parserColumns(columns, attrs));
		}
	}

	@SuppressWarnings("unchecked")
	private JSONArray parserColumns(Node node, FLYVariableResolver attrs)
			throws BIPageException, BIJSONException {
		JSONArray ja = new JSONArray();
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node subNode = nodeList.item(i);
				String subNodeName = subNode.getNodeName();
				if (subNodeName.equalsIgnoreCase(HTML.COMPONENT_TYPE_FLY_PREFIX
						+ ATTR_ROW)) {
					ja.add(parserColumnsRow(subNode, attrs));
				}
			}
		}
		return ja;
	}

	@SuppressWarnings("unchecked")
	private JSONArray parserColumnsRow(Node node, FLYVariableResolver attrs)
			throws BIPageException, BIJSONException {
		JSONArray ja = new JSONArray();
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node subNode = nodeList.item(i);
				String subNodeName = subNode.getNodeName();
				if (subNodeName.equalsIgnoreCase(HTML.COMPONENT_TYPE_FLY_PREFIX
						+ ATTR_COLUMN)) {
					ja.add(parserColumnsRowColumn(subNode, attrs));
				}
			}
		}
		return ja;
	}

	private JSONObject parserColumnsRowColumn(Node node,
			FLYVariableResolver attrs) throws BIPageException, BIJSONException {
		Map<String, Object> map = HTML.getAttributesMap(node.getAttributes(),
				null, attrs);
		return JSONUtils.convertToJSONObject(map);
	}

}
