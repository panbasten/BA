package com.flywet.platform.bi.pivot.model.chart;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.chart.attrs.Series;
import com.flywet.platform.bi.pivot.model.chart.attrs.Title;
import com.flywet.platform.bi.pivot.model.chart.attrs.Tooltip;
import com.flywet.platform.bi.pivot.model.chart.attrs.XAxis;
import com.flywet.platform.bi.pivot.model.chart.attrs.YAxis;
import com.tonbeller.wcf.controller.RequestContext;

public class BaseChart implements IChart {

	Title title;

	List<XAxis> xAxis;

	List<YAxis> yAxis;

	Tooltip tooltip;

	List<Series> series;

	public BaseChart(Node node) {
		Node titleNode = XMLHandler.getSubNode(node, "title");
		if (titleNode != null) {
			title = Title.instance(titleNode);
		}

		List<Node> xAxisNode = XMLHandler.getNodes(node, "xAxis");
		if (xAxisNode != null) {
			xAxis = new ArrayList<XAxis>();
			for (Node n : xAxisNode) {
				xAxis.add(XAxis.instance(n));
			}
		}

		List<Node> yAxisNode = XMLHandler.getNodes(node, "yAxis");
		if (yAxisNode != null) {
			yAxis = new ArrayList<YAxis>();
			for (Node n : yAxisNode) {
				yAxis.add(YAxis.instance(n));
			}
		}

		Node tooltipNode = XMLHandler.getSubNode(node, "tooltip");
		if (tooltipNode != null) {
			tooltip = Tooltip.instance(tooltipNode);
		}

		Node seriesNode = XMLHandler.getSubNode(node, "series");
		if (seriesNode != null) {
			List<Node> seriesItemList = XMLHandler.getNodes(seriesNode, "item");
			if (seriesItemList != null) {
				series = new ArrayList<Series>();
				for (Node n : seriesItemList) {
					series.add(Series.instance(n));
				}
			}
		}
	}

	public static BaseChart instance(Node node) {
		BaseChart c = new BaseChart(node);
		return c;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (title != null) {
			jo.put("title", title.renderJo(context));
		}

		if (xAxis != null) {
			if (xAxis.size() == 1) {
				jo.put("xAxis", xAxis.get(0).renderJo(context));
			} else if (xAxis.size() > 1) {
				JSONArray ja = new JSONArray();
				for (XAxis x : xAxis) {
					ja.add(x.renderJo(context));
				}
				jo.put("xAxis", ja);
			}

		}

		if (yAxis != null) {
			if (yAxis.size() == 1) {
				jo.put("yAxis", yAxis.get(0).renderJo(context));
			} else if (yAxis.size() > 1) {
				JSONArray ja = new JSONArray();
				for (YAxis y : yAxis) {
					ja.add(y.renderJo(context));
				}
				jo.put("yAxis", ja);
			}

		}

		if (tooltip != null) {
			jo.put("tooltip", tooltip.renderJo(context));
		}

		if (series != null) {
			JSONArray seriesJa = new JSONArray();
			for (Series s : series) {
				seriesJa.add(s.renderJo(context));
			}
			jo.put("series", seriesJa);
		}

		return jo;
	}

	@Override
	public void init(RequestContext context) throws BIException {
		if (title != null) {
			title.init(context);
		}

		if (xAxis != null) {
			for (XAxis x : xAxis) {
				x.init(context);
			}
		}

		if (yAxis != null) {
			for (YAxis y : yAxis) {
				y.init(context);
			}
		}

		if (tooltip != null) {
			tooltip.init(context);
		}

		if (series != null) {
			for (Series s : series) {
				s.init(context);
			}
		}
	}

	@Override
	public Object findByName(String name) throws BIException {
		Object rtn;

		if (title != null) {
			rtn = title.findByName(name);
			if (rtn != null)
				return rtn;
		}

		if (xAxis != null) {
			for (XAxis x : xAxis) {
				rtn = x.findByName(name);
				if (rtn != null)
					return rtn;
			}
		}

		if (yAxis != null) {
			for (YAxis y : yAxis) {
				rtn = y.findByName(name);
				if (rtn != null)
					return rtn;
			}
		}

		if (tooltip != null) {
			rtn = tooltip.findByName(name);
			if (rtn != null)
				return rtn;
		}

		if (series != null) {
			for (Series s : series) {
				rtn = s.findByName(name);
				if (rtn != null)
					return rtn;
			}
		}
		return null;
	}

}
