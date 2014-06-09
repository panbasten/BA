package com.flywet.platform.bi.pivot.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.pivot.model.context.IContext;
import com.flywet.platform.bi.pivot.model.factory.PivotContextFactory;
import com.flywet.platform.bi.pivot.model.style.CellStyle;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * 透视报表对象
 * 
 * @author PeterPan
 * 
 */
public class PivotReport implements IPivotReport {

	public static final String PROP_NAME_WIDTH = "width";
	public static final String PROP_NAME_HEIGHT = "height";
	public static final String PROP_NAME_SHOW_H_SCROLL = "showHScroll";
	public static final String PROP_NAME_SHOW_V_SCROLL = "showVScroll";
	public static final String PROP_NAME_OFFSET_CELL_NUMBER = "offsetCellNumber";
	public static final String PROP_NAME_CURRENT_SHEET_INDEX = "currentSheetIndex";
	public static final String PROP_NAME_SHEET = "sheet";
	public static final String NODE_NAME_SHEET = "Sheet";
	public static final String PROP_NAME_STYLE = "style";
	public static final String NODE_NAME_STYLE = "Style";
	public static final String PROP_NAME_NAME = "name";
	public static final String NODE_NAME_CONTEXT = "Context";

	public static final String PROP_NAME_ATTRS = "attrs";

	// 扩展属性，来自程序
	private Map<String, Object> attrs;

	// 报表区域宽度
	private Integer width;

	// 报表区域高度
	private Integer height;

	// 显示横向滚动条
	private Boolean showHScroll;

	// 显示纵向滚动条
	private Boolean showVScroll;

	// 溢出单元格数量
	private Integer offsetCellNumber;

	// 当前单元格索引
	private Integer currentSheetIndex;

	// 电子表格对象集
	private List<Sheet> sheets;

	// 命名样式表
	private Map<String, CellStyle> styles;

	// 上下文
	private Map<String, IContext> contexts;

	private PivotReport() {

	}

	public static PivotReport instance(Node node) throws BIException {
		PivotReport pr = new PivotReport();
		pr.width = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_WIDTH), null);
		pr.height = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_HEIGHT), null);

		pr.showHScroll = Utils
				.toBoolean(XMLHandler.getTagAttribute(node,
						PROP_NAME_SHOW_H_SCROLL), null);
		pr.showVScroll = Utils
				.toBoolean(XMLHandler.getTagAttribute(node,
						PROP_NAME_SHOW_V_SCROLL), null);

		pr.offsetCellNumber = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_OFFSET_CELL_NUMBER),
				null);
		pr.currentSheetIndex = Utils
				.toInt(XMLHandler.getTagAttribute(node,
						PROP_NAME_CURRENT_SHEET_INDEX), null);

		// Sheet
		List<Node> sheetNodes = XMLHandler.getNodes(node, NODE_NAME_SHEET);
		if (sheetNodes != null && sheetNodes.size() > 0) {
			for (Node n : sheetNodes) {
				pr.addSheet(Sheet.instance(n));
			}
		}

		// Style
		List<Node> styleNodes = XMLHandler.getNodes(node, NODE_NAME_STYLE);
		if (styleNodes != null && styleNodes.size() > 0) {
			for (Node n : styleNodes) {
				String name = XMLHandler.getTagAttribute(n, PROP_NAME_NAME);
				pr.addStyle(name, CellStyle.instance(n));
			}
		}

		// Context
		List<Node> contextNodes = XMLHandler.getNodes(node, NODE_NAME_CONTEXT);
		if (contextNodes != null && contextNodes.size() > 0) {
			for (Node n : contextNodes) {
				IContext c = PivotContextFactory.resolver(n);
				pr.addContext(c.getName(), c);
			}
		}

		return pr;
	}

	@Override
	public void init(RequestContext context) throws BIException {
		if (sheets != null && sheets.size() > 0) {
			for (Sheet s : sheets) {
				s.init(context);
			}
		}

		if (styles != null && styles.size() > 0) {
			for (String sk : styles.keySet()) {
				styles.get(sk).init(context);
			}
		}

		if (contexts != null && contexts.size() > 0) {
			for (String ck : contexts.keySet()) {
				contexts.get(ck).init(context);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (width != null) {
			jo.put(PROP_NAME_WIDTH, width);
		}

		if (height != null) {
			jo.put(PROP_NAME_HEIGHT, height);
		}

		if (showHScroll != null) {
			jo.put(PROP_NAME_SHOW_H_SCROLL, showHScroll);
		}

		if (showVScroll != null) {
			jo.put(PROP_NAME_SHOW_V_SCROLL, showVScroll);
		}

		if (offsetCellNumber != null) {
			jo.put(PROP_NAME_OFFSET_CELL_NUMBER, offsetCellNumber);
		}

		if (currentSheetIndex != null) {
			jo.put(PROP_NAME_CURRENT_SHEET_INDEX, currentSheetIndex);
		}

		// Sheet
		if (sheets != null && sheets.size() > 0) {
			JSONArray sheetJa = new JSONArray();
			for (Sheet r : sheets) {
				sheetJa.add(r.renderJo(context));
			}
			jo.put(PROP_NAME_SHEET, sheetJa);
		}

		// Style
		if (styles != null && styles.size() > 0) {
			JSONObject stylesJo = new JSONObject();
			for (String key : styles.keySet()) {
				stylesJo.put(key, styles.get(key).renderJo(context));
			}
			jo.put(PROP_NAME_STYLE, stylesJo);
		}

		if (attrs != null) {
			jo.put(PROP_NAME_ATTRS, JSONUtils.convertToJSONObject(attrs));
		}

		return jo;
	}

	public void addSheet(Sheet s) {
		if (sheets == null) {
			sheets = new ArrayList<Sheet>();
		}
		sheets.add(s);
	}

	public void addStyle(String name, CellStyle s) {
		if (styles == null) {
			styles = new HashMap<String, CellStyle>();
		}
		styles.put(name, s);
	}

	public void addAttr(String name, Object attr) {
		if (attrs == null) {
			attrs = new HashMap<String, Object>();
		}
		attrs.put(name, attr);
	}

	public void addContext(String name, IContext c) {
		if (contexts == null) {
			contexts = new HashMap<String, IContext>();
		}
		attrs.put(name, c);
	}

	@Override
	public Object findByName(String name) throws BIException {
		Object rtn;
		// Sheet
		if (sheets != null && sheets.size() > 0) {
			for (Sheet r : sheets) {
				rtn = r.findByName(name);
				if (rtn != null)
					return rtn;
			}
		}

		// Style
		if (styles != null && styles.size() > 0) {
			rtn = styles.get(name);
			if (rtn != null)
				return rtn;
		}

		// Context
		if (contexts != null && contexts.size() > 0) {
			rtn = contexts.get(name);
			if (rtn != null)
				return rtn;
		}

		// Attribute
		if (attrs != null) {
			rtn = attrs.get(name);
			if (rtn != null)
				return rtn;
		}

		return null;
	}

}
