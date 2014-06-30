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

public class CellStyle implements ICacheable, IPivotReport {

	private static final long serialVersionUID = -4830092425990536943L;

	private static final String PROP_NAME_BORDER = "border";

	private static final String PROP_NAME_DATA_FORMAT_REFS = "dataFormatRefs";

	// 缓存
	private static Map<String, SoftReference<CellStyle>> CACHE = new ConcurrentHashMap<String, SoftReference<CellStyle>>();

	// 字体样式
	private final CellFontStyle font;

	// 对齐方式
	private final CellAlignStyle align;

	// 线条样式
	private final CellBordersStyle borders;

	// 背景样式
	private final CellBackgroundStyle bg;

	// 数据格式引用(多值 ,分割)
	private final String dataFormatRefs;

	// 值格式
	private final CellValueFormat valueFormat;

	private final String _uuid;

	private CellStyle(CellFontStyle font, CellAlignStyle align,
			CellBordersStyle borders, CellBackgroundStyle bg,
			String dataFormatRefs, CellValueFormat valueFormat) {
		this.font = font;
		this.align = align;
		this.borders = borders;
		this.bg = bg;
		this.dataFormatRefs = dataFormatRefs;
		this.valueFormat = valueFormat;

		this._uuid = createUUID(font, align, borders, bg, dataFormatRefs,
				valueFormat);
	}

	public static CellStyle instance(Node node) throws BIException {
		CellFontStyle font = CellFontStyle.instance(node);

		CellAlignStyle align = CellAlignStyle.instance(node);

		CellBordersStyle borders = CellBordersStyle.instance(node);

		CellBackgroundStyle bg = CellBackgroundStyle.instance(node);

		String dataFormatRefs = Const.NVL(
				XMLHandler.getTagAttribute(node, PROP_NAME_DATA_FORMAT_REFS),
				null);

		CellValueFormat valueFormat = CellValueFormat.instance(node);

		return getInstance(font, align, borders, bg, dataFormatRefs,
				valueFormat);
	}

	public static CellStyle getDefaultInstance() {
		return getInstance(CellFontStyle.getDefaultInstance(),
				CellAlignStyle.getDefaultInstance(),
				CellBordersStyle.getDefaultInstance(),
				CellBackgroundStyle.getDefaultInstance(), null,
				CellValueFormat.getDefaultInstance());
	}

	public static CellStyle getInstance(CellFontStyle font,
			CellAlignStyle align, CellBordersStyle lines,
			CellBackgroundStyle bg, String dataFormatRefs,
			CellValueFormat valueFormat) {

		if (font == null && align == null && lines == null && bg == null
				&& dataFormatRefs == null && valueFormat == null) {
			return null;
		}

		String key = createUUID(font, align, lines, bg, dataFormatRefs,
				valueFormat);

		CellStyle cell = matchCache(key);
		if (cell == null) {
			cell = new CellStyle(font, align, lines, bg, dataFormatRefs,
					valueFormat);
			putCache(key, cell);
		}

		return cell;
	}

	private static CellStyle matchCache(String key) {
		SoftReference<CellStyle> ref = CACHE.get(key);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	private static void putCache(String key, CellStyle cs) {
		CACHE.put(key, new SoftReference<CellStyle>(cs));
	}

	public static void clearCache() {
		CACHE.clear();
	}

	public static String createUUID(CellFontStyle font, CellAlignStyle align,
			CellBordersStyle borders, CellBackgroundStyle bg,
			String dataFormatRefs, CellValueFormat valueFormat) {
		String result = "";
		result = result + ((font != null) ? font.getUUID() : "") + ",";
		result = result + ((align != null) ? align.getUUID() : "") + ",";
		result = result + ((borders != null) ? borders.getUUID() : "") + ",";
		result = result + ((bg != null) ? bg.getUUID() : "") + ",";
		result = result + ((dataFormatRefs != null) ? dataFormatRefs : "")
				+ ",";
		result = result + ((valueFormat != null) ? valueFormat.getUUID() : "");
		return result;
	}

	public String getUUID() {
		return _uuid;
	}

	public CellFontStyle getFont() {
		return font;
	}

	public CellAlignStyle getAlign() {
		return align;
	}

	public CellBordersStyle getBorders() {
		return borders;
	}

	public CellBackgroundStyle getBg() {
		return bg;
	}

	public String getDataFormatRefs() {
		return dataFormatRefs;
	}

	public CellValueFormat getValueFormat() {
		return valueFormat;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (font != null) {
			jo.putAll(font.renderJo(context));
		}

		if (align != null) {
			jo.putAll(align.renderJo(context));
		}

		if (borders != null) {
			jo.put(PROP_NAME_BORDER, borders.renderJo(context));
		}

		if (bg != null) {
			jo.putAll(bg.renderJo(context));
		}

		if (dataFormatRefs != null) {
			jo.put(PROP_NAME_DATA_FORMAT_REFS, dataFormatRefs);
		}

		if (valueFormat != null) {
			jo.putAll(valueFormat.renderJo(context));
		}

		return jo;
	}

	@Override
	public void init(RequestContext context) throws BIException {
		if (font != null) {
			font.init(context);
		}

		if (align != null) {
			align.init(context);
		}

		if (borders != null) {
			borders.init(context);
		}

		if (bg != null) {
			bg.init(context);
		}

		if (valueFormat != null) {
			valueFormat.init(context);
		}
	}

	@Override
	public Object findByName(String name) throws BIException {
		Object rtn;
		if (font != null) {
			rtn = font.findByName(name);
			if (rtn != null)
				return rtn;
		}

		if (align != null) {
			rtn = align.findByName(name);
			if (rtn != null)
				return rtn;
		}

		if (borders != null) {
			rtn = borders.findByName(name);
			if (rtn != null)
				return rtn;
		}

		if (bg != null) {
			rtn = bg.findByName(name);
			if (rtn != null)
				return rtn;
		}

		if (valueFormat != null) {
			rtn = valueFormat.findByName(name);
			if (rtn != null)
				return rtn;
		}
		return null;
	}

}
