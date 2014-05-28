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
	List<ChartDataDataCategories> categories;
	List<ChartDataDataDataSet> dataSet;

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

		if (dataSet != null && dataSet.size() > 0) {
			JSONArray dataSetJa = new JSONArray();
			for (ChartDataDataDataSet c : dataSet) {
				dataSetJa.add(c.renderJo(context));
			}
			jo.put("dataSet", dataSetJa);
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

		List<Node> dataSet = XMLHandler.getNodes(node, "dataSet");
		if (dataSet != null && dataSet.size() > 0) {
			d.dataSet = new ArrayList<ChartDataDataDataSet>();
			for (Node n : dataSet) {
				d.dataSet.add(ChartDataDataDataSet.instance(n));
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
		Map<String, String> map = XMLHandler.getNodeAttributesMap(node);
		ChartDataDataChart chart = new ChartDataDataChart();
		chart.attrs = map;
		return chart;
	}

}

class ChartDataDataCategories implements IJSONObjectable {
	List<ChartDataDataCategory> catagory;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (catagory != null && catagory.size() > 0) {
			JSONArray ja = new JSONArray();

			for (ChartDataDataCategory c : catagory) {
				ja.add(c.renderJo(context));
			}

			jo.put("catagory", catagory);
		}

		return jo;
	}

	public static ChartDataDataCategories instance(Node node)
			throws BIException {
		ChartDataDataCategories c = new ChartDataDataCategories();

		List<Node> catagory = XMLHandler.getNodes(node, "catagory");
		if (catagory != null && catagory.size() > 0) {
			c.catagory = new ArrayList<ChartDataDataCategory>();
			for (Node n : catagory) {
				c.catagory.add(ChartDataDataCategory.instance(n));
			}
		}

		return c;
	}
}

class ChartDataDataCategory implements IJSONObjectable {
	String label;
	String value;
	Color color;

	List<ChartDataDataCategory> catagory;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (label != null) {
			jo.put("label", jo);
		}

		if (value != null) {
			jo.put("value", jo);
		}

		if (color != null) {
			jo.put("color", color.getRGBText());
		}

		if (catagory != null && catagory.size() > 0) {
			JSONArray ja = new JSONArray();

			for (ChartDataDataCategory c : catagory) {
				ja.add(c.renderJo(context));
			}

			jo.put("catagory", catagory);
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

		List<Node> catagory = XMLHandler.getNodes(node, "catagory");
		if (catagory != null && catagory.size() > 0) {
			c.catagory = new ArrayList<ChartDataDataCategory>();
			for (Node n : catagory) {
				c.catagory.add(ChartDataDataCategory.instance(n));
			}
		}

		return c;
	}

}

class ChartDataDataDataSet implements IJSONObjectable {

	String seriesName;

	List<ChartDataDataDataSetData> data;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (seriesName != null) {
			jo.put("seriesName", seriesName);
		}

		if (data != null && data.size() > 0) {
			JSONArray dataJa = new JSONArray();
			for (ChartDataDataDataSetData d : data) {
				dataJa.add(d.renderJo(context));
			}
			jo.put("data", dataJa);
		}

		return jo;
	}

	public static ChartDataDataDataSet instance(Node node) throws BIException {
		ChartDataDataDataSet ds = new ChartDataDataDataSet();
		ds.seriesName = Const.NVL(
				XMLHandler.getTagAttribute(node, "seriesName"), null);

		List<Node> set = XMLHandler.getNodes(node, "set");
		if (set != null && set.size() > 0) {
			ds.data = new ArrayList<ChartDataDataDataSetData>();
			for (Node n : set) {
				ds.data.add(ChartDataDataDataSetData.instance(n));
			}
		}

		return ds;
	}

}

class ChartDataDataDataSetData implements IJSONObjectable {
	String value;
	String link;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (value != null) {
			jo.put("value", value);
		}

		if (link != null) {
			jo.put("link", link);
		}

		return jo;
	}

	public static ChartDataDataDataSetData instance(Node node)
			throws BIException {
		ChartDataDataDataSetData d = new ChartDataDataDataSetData();
		d.value = Const.NVL(XMLHandler.getTagAttribute(node, "value"), null);
		d.link = Const.NVL(XMLHandler.getTagAttribute(node, "link"), null);
		return d;
	}
}
