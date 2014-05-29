package com.flywet.platform.bi.pivot.model.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.pivot.model.IJSONObjectable;
import com.flywet.platform.bi.pivot.model.style.Color;
import com.tonbeller.wcf.controller.RequestContext;

public class ChartDataData implements IJSONObjectable {

	ChartDataDataChart chart;

	// 正常分类
	List<ChartDataDataCategories> categories;

	// 嵌套结构分类
	List<ChartDataDataCategory> category;

	// 数据集
	List<ChartDataDataDataSet> dataSet;

	// 趋势线
	List<ChartDataDataTrendlines> trendlines;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (chart != null) {
			jo.put("chart", chart.renderJo(context));
		}

		if (categories != null && categories.size() > 0) {
			JSONArray categoriesJa = new JSONArray();
			for (ChartDataDataCategories c : categories) {
				categoriesJa.add(c.renderJo(context));
			}
			jo.put("categories", categoriesJa);
		}

		if (category != null && category.size() > 0) {
			JSONArray categoryJa = new JSONArray();
			for (ChartDataDataCategory c : category) {
				categoryJa.add(c.renderJo(context));
			}
			jo.put("category", categoryJa);
		}

		if (dataSet != null && dataSet.size() > 0) {
			JSONArray dataSetJa = new JSONArray();
			for (ChartDataDataDataSet c : dataSet) {
				dataSetJa.add(c.renderJo(context));
			}
			jo.put("dataSet", dataSetJa);
		}

		if (trendlines != null && trendlines.size() > 0) {
			JSONArray trendlinesJa = new JSONArray();
			for (ChartDataDataTrendlines tl : trendlines) {
				trendlinesJa.add(tl.renderJo(context));
			}
			jo.put("trendlines", trendlinesJa);
		}

		return jo;
	}

	public static ChartDataData instance(Node node) throws BIException {
		ChartDataData d = new ChartDataData();
		d.chart = ChartDataDataChart.instance(node);

		List<Node> categories = XMLHandler.getNodes(node, "categories");
		if (categories != null && categories.size() > 0) {
			d.categories = new ArrayList<ChartDataDataCategories>();
			for (Node n : categories) {
				d.categories.add(ChartDataDataCategories.instance(n));
			}
		}

		List<Node> category = XMLHandler.getNodes(node, "category");
		if (category != null && category.size() > 0) {
			d.category = new ArrayList<ChartDataDataCategory>();
			for (Node n : categories) {
				d.category.add(ChartDataDataCategory.instance(n));
			}
		}

		List<Node> dataSet = XMLHandler.getNodes(node, "dataSet");
		if (dataSet != null && dataSet.size() > 0) {
			d.dataSet = new ArrayList<ChartDataDataDataSet>();
			for (Node n : dataSet) {
				d.dataSet.add(ChartDataDataDataSet.instance(n));
			}
		}

		List<Node> trendlines = XMLHandler.getNodes(node, "trendlines");
		if (trendlines != null && trendlines.size() > 0) {
			d.trendlines = new ArrayList<ChartDataDataTrendlines>();
			for (Node n : trendlines) {
				d.trendlines.add(ChartDataDataTrendlines.instance(n));
			}
		}

		return d;

	}

}

class ChartDataDataChart implements IJSONObjectable {

	Map<String, String> attrs;

	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		if (attrs != null) {
			return JSONUtils.convertToJSONObject(attrs);
		}
		return null;
	}

	public static ChartDataDataChart instance(Node node) {
		ChartDataDataChart chart = new ChartDataDataChart();
		Map<String, String> map = XMLHandler.getNodeAttributesMap(node);
		chart.attrs = map;
		return chart;
	}

}

class ChartDataDataCategories implements IJSONObjectable {
	List<ChartDataDataCategory> category;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (category != null && category.size() > 0) {
			JSONArray ja = new JSONArray();

			for (ChartDataDataCategory c : category) {
				ja.add(c.renderJo(context));
			}

			jo.put("category", ja);
		}

		return jo;
	}

	public static ChartDataDataCategories instance(Node node)
			throws BIException {
		ChartDataDataCategories c = new ChartDataDataCategories();

		List<Node> catagory = XMLHandler.getNodes(node, "category");
		if (catagory != null && catagory.size() > 0) {
			c.category = new ArrayList<ChartDataDataCategory>();
			for (Node n : catagory) {
				c.category.add(ChartDataDataCategory.instance(n));
			}
		}

		return c;
	}
}

class ChartDataDataCategory implements IJSONObjectable {
	String label;
	String value;
	Color color;

	List<ChartDataDataCategory> category;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (label != null) {
			jo.put("label", label);
		}

		if (value != null) {
			jo.put("value", value);
		}

		if (color != null) {
			jo.put("color", color.getRGBText());
		}

		if (category != null && category.size() > 0) {
			JSONArray ja = new JSONArray();

			for (ChartDataDataCategory c : category) {
				ja.add(c.renderJo(context));
			}

			jo.put("category", ja);
		}

		return jo;
	}

	public static ChartDataDataCategory instance(Node node) throws BIException {
		ChartDataDataCategory c = new ChartDataDataCategory();
		c.label = Const.NVL(XMLHandler.getTagAttribute(node, "label"), null);
		c.value = Const.NVL(XMLHandler.getTagAttribute(node, "value"), null);

		String colorStr = Const.NVL(XMLHandler.getTagAttribute(node, "color"),
				null);
		if (colorStr != null) {
			c.color = Color.getInstance(colorStr);
		}

		List<Node> catagory = XMLHandler.getNodes(node, "category");
		if (catagory != null && catagory.size() > 0) {
			c.category = new ArrayList<ChartDataDataCategory>();
			for (Node n : catagory) {
				c.category.add(ChartDataDataCategory.instance(n));
			}
		}

		return c;
	}

}

class ChartDataDataDataSet implements IJSONObjectable {

	String seriesName;
	Map<String, String> attrs;

	// set节点
	List<ChartDataDataDataSetData> data;

	// 嵌套dataSet
	List<ChartDataDataDataSet> dataSet;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (seriesName != null) {
			jo.put("seriesName", seriesName);
		}

		if (attrs != null) {
			jo.putAll(attrs);
		}

		if (data != null && data.size() > 0) {
			JSONArray dataJa = new JSONArray();
			for (ChartDataDataDataSetData d : data) {
				dataJa.add(d.renderJo(context));
			}
			jo.put("data", dataJa);
		}

		if (dataSet != null && dataSet.size() > 0) {
			JSONArray dataSetJa = new JSONArray();
			for (ChartDataDataDataSet ds : dataSet) {
				dataSetJa.add(ds.renderJo(context));
			}
			jo.put("dataSet", dataSetJa);
		}

		return jo;
	}

	public static ChartDataDataDataSet instance(Node node) throws BIException {
		ChartDataDataDataSet ds = new ChartDataDataDataSet();
		ds.seriesName = Const.NVL(
				XMLHandler.getTagAttribute(node, "seriesName"), null);

		Map<String, String> map = XMLHandler.getNodeAttributesMap(node);
		ds.attrs = map;

		List<Node> set = XMLHandler.getNodes(node, "set");
		if (set != null && set.size() > 0) {
			ds.data = new ArrayList<ChartDataDataDataSetData>();
			for (Node n : set) {
				ds.data.add(ChartDataDataDataSetData.instance(n));
			}
		}

		List<Node> dataSet = XMLHandler.getNodes(node, "dataSet");
		if (dataSet != null && dataSet.size() > 0) {
			ds.dataSet = new ArrayList<ChartDataDataDataSet>();
			for (Node n : set) {
				ds.dataSet.add(ChartDataDataDataSet.instance(n));
			}
		}

		return ds;
	}

}

class ChartDataDataDataSetData implements IJSONObjectable {
	Map<String, String> attrs;

	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		if (attrs != null) {
			return JSONUtils.convertToJSONObject(attrs);
		}
		return null;
	}

	public static ChartDataDataDataSetData instance(Node node) {
		ChartDataDataDataSetData d = new ChartDataDataDataSetData();
		Map<String, String> map = XMLHandler.getNodeAttributesMap(node);
		d.attrs = map;
		return d;
	}
}

class ChartDataDataTrendlines implements IJSONObjectable {

	// set节点
	List<ChartDataDataTrendlinesLine> line;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (line != null && line.size() > 0) {
			JSONArray lineJa = new JSONArray();
			for (ChartDataDataTrendlinesLine d : line) {
				lineJa.add(d.renderJo(context));
			}
			jo.put("line", lineJa);
		}

		return jo;
	}

	public static ChartDataDataTrendlines instance(Node node) {
		ChartDataDataTrendlines tl = new ChartDataDataTrendlines();

		List<Node> line = XMLHandler.getNodes(node, "line");
		if (line != null && line.size() > 0) {
			tl.line = new ArrayList<ChartDataDataTrendlinesLine>();
			for (Node n : line) {
				tl.line.add(ChartDataDataTrendlinesLine.instance(n));
			}
		}

		return tl;
	}

}

class ChartDataDataTrendlinesLine implements IJSONObjectable {
	Map<String, String> attrs;

	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		if (attrs != null) {
			return JSONUtils.convertToJSONObject(attrs);
		}
		return null;
	}

	public static ChartDataDataTrendlinesLine instance(Node node) {
		ChartDataDataTrendlinesLine l = new ChartDataDataTrendlinesLine();
		Map<String, String> map = XMLHandler.getNodeAttributesMap(node);
		l.attrs = map;
		return l;
	}
}
