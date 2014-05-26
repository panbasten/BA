package com.flywet.platform.bi.pivot.model;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * 透视报表对象
 * 
 * @author PeterPan
 * 
 */
public class PivotReport implements IJSONObjectable {

	public static final String PROP_NAME_WIDTH = "width";
	public static final String PROP_NAME_HEIGHT = "height";
	public static final String PROP_NAME_OFFSET_CELL_NUMBER = "offsetCellNumber";
	public static final String PROP_NAME_CURRENT_SHEET_INDEX = "currentSheetIndex";
	public static final String PROP_NAME_SHEET = "sheet";
	public static final String NODE_NAME_SHEET = "Sheet";

	// 报表区域宽度
	private Integer width;

	// 报表区域高度
	private Integer height;

	// 溢出单元格数量
	private Integer offsetCellNumber;

	// 当前单元格索引
	private Integer currentSheetIndex;

	// 电子表格对象集
	private List<Sheet> sheets;

	private PivotReport() {

	}

	public static PivotReport instance(Node node) {
		PivotReport pr = new PivotReport();
		pr.width = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_WIDTH), null);
		pr.height = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_HEIGHT), null);

		pr.offsetCellNumber = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_OFFSET_CELL_NUMBER),
				null);
		pr.currentSheetIndex = Utils
				.toInt(XMLHandler.getTagAttribute(node,
						PROP_NAME_CURRENT_SHEET_INDEX), null);

		List<Node> sheetNodes = XMLHandler.getNodes(node, NODE_NAME_SHEET);
		if (sheetNodes != null && sheetNodes.size() > 0) {
			for (Node n : sheetNodes) {
				pr.addSheet(Sheet.instance(n));
			}
		}

		return pr;
	}

	public void init(RequestContext context) throws BIException {
		if (sheets != null && sheets.size() > 0) {
			for (Sheet s : sheets) {
				s.init(context);
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

		if (offsetCellNumber != null) {
			jo.put(PROP_NAME_OFFSET_CELL_NUMBER, offsetCellNumber);
		}

		if (currentSheetIndex != null) {
			jo.put(PROP_NAME_CURRENT_SHEET_INDEX, currentSheetIndex);
		}

		if (sheets != null && sheets.size() > 0) {
			JSONArray regionJa = new JSONArray();
			for (Sheet r : sheets) {
				regionJa.add(r.renderJo(context));
			}
			jo.put(PROP_NAME_SHEET, regionJa);
		}

		return jo;
	}

	public void addSheet(Sheet s) {
		if (sheets == null) {
			sheets = new ArrayList<Sheet>();
		}
		sheets.add(s);
	}

}
