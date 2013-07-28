package com.flywet.platform.bi.component.resolvers.item;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLUtils;
import org.pentaho.pms.util.Const;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.component.utils.PageTemplateResolverType;
import com.flywet.platform.bi.component.vo.ComponentFunction;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.exception.BIPageException;
import com.flywet.platform.bi.core.utils.JSONUtils;

public class FLYDataGridResolver extends BaseComponentResolver implements
		ComponentResolverInterface {
	public static final String ATTR_COLUMNS = "columns";
	public static final String ATTR_ROW = "row";
	public static final String ATTR_COLUMN = "column";
	public static final String ATTR_TOOLBAR = "toolbar";
	public static final String ATTR_DATA = "data";
	public static final String ATTR_ROW_STYLERS = "rowStylers";
	public static final String ATTR_ROW_STYLER = "rowStyler";

	public static final String ATTR_DATA_FILTERS = "dataFilters";
	public static final String ATTR_DATA_FILTER = "dataFilter";

	public static final String ATTR_FIELD = "field";
	public static final String ATTR_OPERTION = "opertion";
	public static final String ATTR_CONDITION = "condition";

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

			// column
			JSONArray columns = (JSONArray) HTML.getTagAttributeObject(node,
					ATTR_COLUMNS, attrs);
			if (columns != null)
				jo.put(ATTR_COLUMNS, columns);

			// rowStyler
			JSONArray rowStyler = (JSONArray) HTML.getTagAttributeObject(node,
					ATTR_ROW_STYLER, attrs);
			if (rowStyler != null)
				jo.put(ATTR_ROW_STYLER, rowStyler);

			// dataFilters
			JSONArray dataFilter = (JSONArray) HTML.getTagAttributeObject(node,
					ATTR_DATA_FILTER, attrs);
			if (dataFilter != null)
				jo.put(ATTR_DATA_FILTER, dataFilter);

			// toolbar
			JSONArray toolbar = (JSONArray) HTML.getTagAttributeObject(node,
					ATTR_TOOLBAR, attrs);
			if (toolbar != null)
				jo.put(ATTR_TOOLBAR, toolbar);

			// data
			Object data = HTML.getTagAttributeObject(node, ATTR_DATA, attrs);
			if (data != null) {
				jo.put(ATTR_DATA, getJSONAttribute(data));
			}

			// 解析子元素
			parserSubNode(jo, node, attrs);

			script.add("Flywet.cw('Grid','" + Const.NVL(weightVar, "") + "',"
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
		// columns
		Node columns = XMLUtils.selectSingleNode(node,
				XMLUtils.getSubTagExpress(HTML.COMPONENT_TYPE_FLY_PREFIX
						+ ATTR_COLUMNS));
		if (columns != null) {
			jo.put(ATTR_COLUMNS, parserColumns(columns, attrs));
		}

		// toolbar
		Node toolbar = XMLUtils.selectSingleNode(node,
				XMLUtils.getSubTagExpress(HTML.COMPONENT_TYPE_FLY_PREFIX
						+ ATTR_TOOLBAR));
		if (toolbar != null) {
			jo.put(ATTR_TOOLBAR, parserToolbar(toolbar, attrs));
		}

		// rowStylers
		Node rowStylers = XMLUtils.selectSingleNode(node, XMLUtils
				.getSubTagExpress(HTML.COMPONENT_TYPE_FLY_PREFIX
						+ ATTR_ROW_STYLERS));
		if (rowStylers != null) {
			jo.put(ATTR_ROW_STYLER, parserRowStylers(rowStylers, attrs));
		}

		// dataFilters
		Node dataFilters = XMLUtils.selectSingleNode(node, XMLUtils
				.getSubTagExpress(HTML.COMPONENT_TYPE_FLY_PREFIX
						+ ATTR_DATA_FILTERS));
		if (rowStylers != null) {
			jo.put(ATTR_DATA_FILTER, parserDataFilters(dataFilters, attrs));
		}
	}

	private ComponentFunction parserDataFilters(Node node,
			FLYVariableResolver attrs) {
		ComponentFunction func = ComponentFunction.instance();
		func.addParameter("index").addParameter("row");

		NodeList nodeList = XMLUtils.selectNodes(node, XMLUtils
				.getSubTagExpress(HTML.COMPONENT_TYPE_FLY_PREFIX
						+ ATTR_DATA_FILTER));

		if (nodeList != null) {
			func.addStatement("if(");
			boolean first = true;
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node subNode = nodeList.item(i);
				if (first) {
					first = false;
				} else {
					func.addStatement("||");
				}

				String condition = HTML.getTagAttribute(subNode,
						ATTR_CONDITION, attrs);

				if (!Const.isEmpty(condition)) {
					func.addStatement("(" + condition + ")");
				} else {
					String field = HTML.getTagAttribute(subNode, ATTR_FIELD,
							attrs);
					String oper = HTML.getTagAttribute(subNode, ATTR_OPERTION,
							attrs);
					String value = HTML.getTagAttribute(subNode,
							HTML.ATTR_VALUE, attrs);
					func.addStatement("(row." + field + oper + "'" + value
							+ "')");
				}
			}
			func.addStatement("){return true;}return false;");
		}else{
			func.addStatement("return true;");
		}

		return func;
	}

	private ComponentFunction parserRowStylers(Node node,
			FLYVariableResolver attrs) {
		ComponentFunction func = ComponentFunction.instance();
		func.addParameter("index").addParameter("row");

		NodeList nodeList = XMLUtils.selectNodes(node, XMLUtils
				.getSubTagExpress(HTML.COMPONENT_TYPE_FLY_PREFIX
						+ ATTR_ROW_STYLER));
		if (nodeList != null) {
			func.addStatement("var style = '';");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node subNode = nodeList.item(i);
				String condition = HTML.getTagAttribute(subNode,
						ATTR_CONDITION, attrs);
				String style = HTML.getTagAttribute(subNode, HTML.ATTR_STYLE,
						attrs);

				if (!Const.isEmpty(condition)) {
					func.addStatement("if(" + condition + "){style+='" + style
							+ "';}");
				} else {
					String field = HTML.getTagAttribute(subNode, ATTR_FIELD,
							attrs);
					String oper = HTML.getTagAttribute(subNode, ATTR_OPERTION,
							attrs);
					String value = HTML.getTagAttribute(subNode,
							HTML.ATTR_VALUE, attrs);
					func.addStatement("if(row." + field + oper + "'" + value
							+ "'){style+='" + style + "';}");
				}

			}
			func.addStatement("return style;");
		}

		return func;
	}

	@SuppressWarnings("unchecked")
	private JSONArray parserToolbar(Node node, FLYVariableResolver attrs)
			throws BIPageException, BIJSONException {
		JSONArray ja = new JSONArray();
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node subNode = nodeList.item(i);
				if (!XMLUtils.isTextNode(subNode)) {
					ja.add(parserToolbarElement(subNode, attrs));
				}
			}
		}
		return ja;
	}

	private JSONObject parserToolbarElement(Node node, FLYVariableResolver attrs)
			throws BIPageException, BIJSONException {
		Map<String, Object> map = HTML.getAttributesMap(node.getAttributes(),
				null, attrs);
		map.put("componentType", PageTemplateResolverType
				.convertComponentPluginName(node.getNodeName()));
		return JSONUtils.convertToJSONObject(map);
	}

	@SuppressWarnings("unchecked")
	private JSONArray parserColumns(Node node, FLYVariableResolver attrs)
			throws BIPageException, BIJSONException {
		JSONArray ja = new JSONArray();
		// 获得所有行
		NodeList nodeList = XMLUtils.selectNodes(node, XMLUtils
				.getSubTagExpress(HTML.COMPONENT_TYPE_FLY_PREFIX + ATTR_ROW));
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				ja.add(parserColumnsRow(nodeList.item(i), attrs));
			}
		}
		return ja;
	}

	@SuppressWarnings("unchecked")
	private JSONArray parserColumnsRow(Node node, FLYVariableResolver attrs)
			throws BIPageException, BIJSONException {
		JSONArray ja = new JSONArray();
		NodeList nodeList = XMLUtils
				.selectNodes(node, XMLUtils
						.getSubTagExpress(HTML.COMPONENT_TYPE_FLY_PREFIX
								+ ATTR_COLUMN));
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				ja.add(parserColumnsRowColumn(nodeList.item(i), attrs));
			}
		}
		return ja;
	}

	private JSONObject parserColumnsRowColumn(Node node,
			FLYVariableResolver attrs) throws BIPageException, BIJSONException {
		Map<String, Object> map = HTML.getAttributesMap(node.getAttributes(),
				null, attrs);
		map.put(HTML.ATTR_WIDTH, HTML.getTagAttribute(node, HTML.ATTR_WIDTH,
				attrs));
		return JSONUtils.convertToJSONObject(map);
	}

}
