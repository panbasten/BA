package com.flywet.platform.bi.pivot.model.style;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.ICacheable;
import com.flywet.platform.bi.pivot.model.IPivotReport;
import com.tonbeller.wcf.controller.RequestContext;

public class CellValueFormat implements ICacheable, IPivotReport {

	private static final long serialVersionUID = 1283476963139528723L;

	private static final String PROP_NAME_VALUE_PREFIX = "valuePrefix";
	private static final String PROP_NAME_VALUE_SUBFIX = "valueSubfix";
	private static final String PROP_NAME_VALUE_FORMAT = "valueFormat";

	// 值前缀
	private final String valuePrefix;

	// 值后缀
	private final String valueSubfix;

	// 值格式
	private final String valueFormat;

	// 缓存
	private static Map<String, SoftReference<CellValueFormat>> CACHE = new ConcurrentHashMap<String, SoftReference<CellValueFormat>>();

	private final String _uuid;

	private CellValueFormat(String valuePrefix, String valueSubfix,
			String valueFormat) {
		this.valuePrefix = valuePrefix;
		this.valueSubfix = valueSubfix;
		this.valueFormat = valueFormat;

		this._uuid = createUUID(valuePrefix, valueSubfix, valueFormat);
	}

	public static CellValueFormat instance(Node node) throws BIException {
		String valuePrefix = Const.NVL(
				XMLHandler.getTagAttribute(node, PROP_NAME_VALUE_PREFIX), null);

		String valueSubfix = Const.NVL(
				XMLHandler.getTagAttribute(node, PROP_NAME_VALUE_SUBFIX), null);

		String valueFormat = Const.NVL(
				XMLHandler.getTagAttribute(node, PROP_NAME_VALUE_FORMAT), null);

		return getInstance(valuePrefix, valueSubfix, valueFormat);
	}

	public static CellValueFormat getDefaultInstance() {
		return getInstance(null, null, null);
	}

	public static CellValueFormat getInstance(String valuePrefix,
			String valueSubfix, String valueFormat) {
		if (valuePrefix == null && valueSubfix == null && valueFormat == null) {
			return null;
		}

		String key = createUUID(valuePrefix, valueSubfix, valueFormat);
		CellValueFormat cvf = matchCache(key);
		if (cvf == null) {
			cvf = new CellValueFormat(valuePrefix, valueSubfix, valueFormat);
			putCache(key, cvf);
		}
		return cvf;
	}

	private static CellValueFormat matchCache(String key) {
		SoftReference<CellValueFormat> ref = CACHE.get(key);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	private static void putCache(String key, CellValueFormat cfs) {
		CACHE.put(key, new SoftReference<CellValueFormat>(cfs));
	}

	public static void clearCache() {
		CACHE.clear();
	}

	private static String createUUID(String valuePrefix, String valueSubfix,
			String valueFormat) {
		String result = "";
		result = result + Const.NVL(valuePrefix, "") + ",";
		result = result + Const.NVL(valueSubfix, "") + ",";
		result = result + Const.NVL(valueFormat, "");
		return result;
	}

	@Override
	public String getUUID() {
		return _uuid;
	}

	@Override
	public String toString() {
		StringBuffer bf = new StringBuffer();
		bf.append("CellValueFormat(");
		bf.append(Const.NVL(valuePrefix, "") + ",");
		bf.append(Const.NVL(valueSubfix, "") + ",");
		bf.append(Const.NVL(valueFormat, ""));
		bf.append(")");
		return bf.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (valuePrefix != null) {
			jo.put(PROP_NAME_VALUE_PREFIX, valuePrefix);
		}

		if (valueSubfix != null) {
			jo.put(PROP_NAME_VALUE_SUBFIX, valueSubfix);
		}

		if (valueFormat != null) {
			jo.put(PROP_NAME_VALUE_FORMAT, valueFormat);
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

	public String getValuePrefix() {
		return valuePrefix;
	}

	public String getValueSubfix() {
		return valueSubfix;
	}

	public String getValueFormat() {
		return valueFormat;
	}

}
