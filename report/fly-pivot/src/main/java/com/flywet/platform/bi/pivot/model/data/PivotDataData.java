package com.flywet.platform.bi.pivot.model.data;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.IPivotReport;
import com.flywet.platform.bi.pivot.model.style.CellStyle;
import com.tonbeller.wcf.controller.RequestContext;

public class PivotDataData implements IPivotReport {

	public static final String PROP_NAME_HEAD = "head";
	public static final String PROP_NAME_BODY = "body";
	public static final String PROP_NAME_SLICER = "slicer";

	PivotDataDataBody head;
	PivotDataDataBody body;
	PivotDataDataSlicer slicer;

	public static PivotDataData instance(Node node) throws BIException {
		PivotDataData data = new PivotDataData();

		Node headNode = XMLHandler.getSubNode(node, PROP_NAME_HEAD);
		if (headNode != null) {
			data.head = PivotDataDataBody.instance(headNode);
		}

		Node bodyNode = XMLHandler.getSubNode(node, PROP_NAME_BODY);
		if (headNode != null) {
			data.body = PivotDataDataBody.instance(bodyNode);
		}

		Node slicerNode = XMLHandler.getSubNode(node, PROP_NAME_SLICER);
		if (slicerNode != null) {
			data.slicer = PivotDataDataSlicer.instance(slicerNode);
		}

		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (head != null) {
			jo.put(PROP_NAME_HEAD, head.renderJo(context));
		}

		if (body != null) {
			jo.put(PROP_NAME_BODY, body.renderJo(context));
		}

		if (slicer != null) {
			jo.put(PROP_NAME_SLICER, slicer.renderJo(context));
		}

		return jo;
	}

	@Override
	public void init(RequestContext context) throws BIException {
		if (head != null) {
			head.init(context);
		}

		if (body != null) {
			body.init(context);
		}

		if (slicer != null) {
			slicer.init(context);
		}
	}

	@Override
	public Object findByName(String name) throws BIException {
		Object rtn;
		if (head != null) {
			rtn = head.findByName(name);
			if (rtn != null)
				return rtn;
		}

		if (body != null) {
			rtn = body.findByName(name);
			if (rtn != null)
				return rtn;
		}

		if (slicer != null) {
			rtn = slicer.findByName(name);
			if (rtn != null)
				return rtn;
		}

		return null;
	}

}

class PivotDataDataSlicer implements IPivotReport {

	public static final String PROP_NAME_MEMBERS = "members";
	public static final String PROP_NAME_MEMBER = "member";

	List<PivotDataDataMember> members;

	public static PivotDataDataSlicer instance(Node node) {
		PivotDataDataSlicer s = new PivotDataDataSlicer();

		s.members = new ArrayList<PivotDataDataMember>();

		List<Node> memberNodes = XMLHandler.getNodes(node, PROP_NAME_MEMBER);
		for (Node memberNode : memberNodes) {
			s.members.add(PivotDataDataMember.instance(memberNode));
		}

		return s;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		JSONArray membersJa = new JSONArray();
		for (PivotDataDataMember member : members) {
			membersJa.add(member.renderJo(context));
		}

		return jo;
	}

	@Override
	public void init(RequestContext context) throws BIException {
		for (PivotDataDataMember member : members) {
			member.init(context);
		}
	}

	@Override
	public Object findByName(String name) throws BIException {
		Object rtn;
		for (PivotDataDataMember member : members) {
			rtn = member.findByName(name);
			if (rtn != null)
				return rtn;
		}

		return null;
	}

}

class PivotDataDataMember implements IPivotReport {

	public static final String PROP_NAME_LEVEL = "level";
	public static final String PROP_NAME_CAPTION = "caption";
	public static final String PROP_NAME_DEPTH = "depth";

	String level;
	String caption;
	Integer depth;

	public static PivotDataDataMember instance(Node node) {
		PivotDataDataMember m = new PivotDataDataMember();

		m.level = Const.trim(XMLHandler.getTagAttribute(node, PROP_NAME_LEVEL));
		m.caption = Const.trim(XMLHandler.getTagAttribute(node,
				PROP_NAME_CAPTION));

		String depth = XMLHandler.getTagAttribute(node, PROP_NAME_DEPTH);
		if (depth != null) {
			m.depth = Integer.valueOf(depth);
		}

		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (level != null) {
			jo.put(PROP_NAME_LEVEL, level);
		}

		if (caption != null) {
			jo.put(PROP_NAME_CAPTION, caption);
		}

		if (depth != null) {
			jo.put(PROP_NAME_DEPTH, depth);
		}

		return jo;
	}

	@Override
	public void init(RequestContext context) throws BIException {

	}

	@Override
	public Object findByName(String name) throws BIException {
		return null;
	}
}

class PivotDataDataBody implements IPivotReport {

	public static final String PROP_NAME_ROW = "row";
	public static final String PROP_NAME_CELL = "cell";

	List<List<PivotDataDataCell>> cells;

	public static PivotDataDataBody instance(Node node) throws BIException {
		PivotDataDataBody b = new PivotDataDataBody();

		b.cells = new ArrayList<List<PivotDataDataCell>>();

		List<Node> rowsNode = XMLHandler.getNodes(node, PROP_NAME_ROW);

		for (Node rowNode : rowsNode) {
			List<PivotDataDataCell> row = new ArrayList<PivotDataDataCell>();
			List<Node> cellsNode = XMLHandler.getNodes(rowNode, PROP_NAME_CELL);
			for (Node cellNode : cellsNode) {
				row.add(PivotDataDataCell.instance(cellNode));
			}
			b.cells.add(row);
		}

		return b;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		JSONArray rowsJa = new JSONArray();
		for (List<PivotDataDataCell> row : cells) {
			JSONArray rowJa = new JSONArray();
			for (PivotDataDataCell cell : row) {
				rowJa.add(cell.renderJo(context));
			}
			rowsJa.add(rowJa);
		}

		jo.put(PROP_NAME_ROW, rowsJa);

		return jo;
	}

	@Override
	public void init(RequestContext context) throws BIException {
		for (List<PivotDataDataCell> row : cells) {
			for (PivotDataDataCell cell : row) {
				cell.init(context);
			}
		}
	}

	@Override
	public Object findByName(String name) throws BIException {
		Object rtn;
		for (List<PivotDataDataCell> row : cells) {
			for (PivotDataDataCell cell : row) {
				rtn = cell.findByName(name);
				if (rtn != null)
					return rtn;
			}
		}
		return null;
	}

}

class PivotDataDataCell implements IPivotReport {

	public static final String PROP_NAME_COLSPAN = "colspan";
	public static final String PROP_NAME_ROWSPAN = "rowspan";
	public static final String PROP_NAME_TAG = "_TAG";
	public static final String PROP_NAME_STYLE = "style";
	public static final String PROP_NAME_DRILL_OTHER = "drillOther";
	public static final String PROP_NAME_CAPTION = "caption";
	public static final String PROP_NAME_DRILL_EXPAND = "drillExpand";
	public static final String PROP_NAME_ID = "id";
	public static final String PROP_NAME_IMG = "img";
	public static final String PROP_NAME_VALUE = "value";

	Integer colspan;
	Integer rowspan;
	String tag;
	String style;

	CellStyle cellStyle;

	boolean drillOther = false;
	String drillOther_img;

	boolean caption = false;
	String caption_caption;

	boolean drillExpand = false;
	String drillExpand_id;
	String drillExpand_img;

	String val;

	public static PivotDataDataCell instance(Node node) throws BIException {
		PivotDataDataCell cell = new PivotDataDataCell();

		String colspan = XMLHandler.getTagAttribute(node, PROP_NAME_COLSPAN);
		if (colspan != null) {
			cell.colspan = Integer.valueOf(colspan);
		}

		String rowspan = XMLHandler.getTagAttribute(node, PROP_NAME_ROWSPAN);
		if (rowspan != null) {
			cell.rowspan = Integer.valueOf(rowspan);
		}

		cell.tag = Const.trim(XMLHandler.getTagAttribute(node, PROP_NAME_TAG));
		cell.style = Const.trim(XMLHandler.getTagAttribute(node,
				PROP_NAME_STYLE));
		cell.val = Const
				.trim(XMLHandler.getTagAttribute(node, PROP_NAME_VALUE));

		cell.cellStyle = CellStyle.instance(node);

		Node drillOther = XMLHandler.getSubNode(node, PROP_NAME_DRILL_OTHER);
		if (drillOther != null) {
			cell.drillOther = true;
			cell.drillOther_img = Const.trim(XMLHandler.getTagAttribute(
					drillOther, PROP_NAME_IMG));
		}

		Node caption = XMLHandler.getSubNode(node, PROP_NAME_CAPTION);
		if (caption != null) {
			cell.caption = true;
			cell.caption_caption = Const.trim(XMLHandler.getTagAttribute(
					caption, PROP_NAME_CAPTION));
		}

		Node drillExpand = XMLHandler.getSubNode(node, PROP_NAME_DRILL_EXPAND);
		if (drillExpand != null) {
			cell.drillExpand = true;
			cell.drillExpand_id = Const.trim(XMLHandler.getTagAttribute(
					drillExpand, PROP_NAME_ID));
			cell.drillExpand_img = Const.trim(XMLHandler.getTagAttribute(
					drillExpand, PROP_NAME_IMG));
		}

		return cell;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (colspan != null) {
			jo.put(PROP_NAME_COLSPAN, colspan);
		}
		if (rowspan != null) {
			jo.put(PROP_NAME_ROWSPAN, rowspan);
		}
		if (tag != null) {
			jo.put(PROP_NAME_TAG, tag);
		}
		if (style != null) {
			jo.put(PROP_NAME_STYLE, style);
		}
		if (val != null) {
			jo.put(PROP_NAME_VALUE, val);
		}
		if (cellStyle != null) {
			jo.putAll(cellStyle.renderJo(context));
		}
		if (drillOther) {
			JSONObject drillOtherJo = new JSONObject();
			if (drillOther_img != null) {
				drillOtherJo.put(PROP_NAME_IMG, drillOther_img);
			}
			jo.put(PROP_NAME_DRILL_OTHER, drillOtherJo);
		}
		if (caption) {
			JSONObject captionJo = new JSONObject();
			if (caption_caption != null) {
				captionJo.put(PROP_NAME_CAPTION, caption_caption);
			}
			jo.put(PROP_NAME_CAPTION, captionJo);
		}
		if (drillExpand) {
			JSONObject drillExpandJo = new JSONObject();
			if (drillExpand_id != null) {
				drillExpandJo.put(PROP_NAME_ID, drillExpand_id);
			}
			if (drillExpand_img != null) {
				drillExpandJo.put(PROP_NAME_IMG, drillExpand_img);
			}
			jo.put(PROP_NAME_DRILL_EXPAND, drillExpandJo);
		}

		return jo;
	}

	@Override
	public void init(RequestContext context) throws BIException {
		if (cellStyle != null) {
			cellStyle.init(context);
		}
	}

	@Override
	public Object findByName(String name) throws BIException {
		Object rtn;
		if (cellStyle != null) {
			rtn = cellStyle.findByName(name);
			if (rtn != null)
				return rtn;
		}
		return null;
	}
}
