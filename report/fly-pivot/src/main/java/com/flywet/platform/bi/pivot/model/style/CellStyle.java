package com.flywet.platform.bi.pivot.model.style;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.ICacheable;
import com.flywet.platform.bi.pivot.model.IPivotReport;
import com.tonbeller.wcf.controller.RequestContext;

public class CellStyle implements ICacheable, IPivotReport {

	private static final long serialVersionUID = -4830092425990536943L;

	private static final String PROP_NAME_BORDER = "border";

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

	// 数据格式化对象
	// private CellStyle dataFormat;

	private final String _uuid;

	private CellStyle(CellFontStyle font, CellAlignStyle align,
			CellBordersStyle borders, CellBackgroundStyle bg) {
		this.font = font;
		this.align = align;
		this.borders = borders;
		this.bg = bg;

		this._uuid = createUUID(font, align, borders, bg);
	}

	public static CellStyle instance(Node node) throws BIException {
		CellFontStyle font = CellFontStyle.instance(node);
		
		CellAlignStyle align = CellAlignStyle.instance(node);

		CellBordersStyle borders = CellBordersStyle.instance(node);

		CellBackgroundStyle bg = CellBackgroundStyle.instance(node);

		return getInstance(font, align, borders, bg);
	}

	public static CellStyle getDefaultInstance() {
		return getInstance(CellFontStyle.getDefaultInstance(),
				CellAlignStyle.getDefaultInstance(),
				CellBordersStyle.getDefaultInstance(),
				CellBackgroundStyle.getDefaultInstance());
	}

	public static CellStyle getInstance(CellFontStyle font,
			CellAlignStyle align, CellBordersStyle lines, CellBackgroundStyle bg) {

		if (font == null && align == null && lines == null && bg == null) {
			return null;
		}

		String key = createUUID(font, align, lines, bg);

		CellStyle cell = matchCache(key);
		if (cell == null) {
			cell = new CellStyle(font, align, lines, bg);
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
			CellBordersStyle borders, CellBackgroundStyle bg) {
		String result = "";
		result = result + ((font != null) ? font.getUUID() : "") + ",";
		result = result + ((align != null) ? align.getUUID() : "") + ",";
		result = result + ((borders != null) ? borders.getUUID() : "") + ",";
		result = result + ((bg != null) ? bg.getUUID() : "");
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
		return null;
	}

}
