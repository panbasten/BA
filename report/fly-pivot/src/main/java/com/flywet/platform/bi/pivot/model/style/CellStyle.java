package com.flywet.platform.bi.pivot.model.style;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.ICacheable;
import com.flywet.platform.bi.pivot.model.IJSONObjectable;
import com.tonbeller.wcf.controller.RequestContext;

public class CellStyle implements ICacheable, IJSONObjectable {

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
//	private final CellBackgroundStyle bg;

	// 数据格式化对象
	// private CellStyle dataFormat;

	private final String _uuid;

	private CellStyle(CellFontStyle font, CellAlignStyle align,
			CellBordersStyle borders) {
		this.font = font;
		this.align = align;
		this.borders = borders;

		this._uuid = createUUID(font, align, borders);
	}

	public static CellStyle instance(Node node) throws BIException {
		CellFontStyle font = CellFontStyle.instance(node);
		CellAlignStyle align = CellAlignStyle.instance(node);

		CellBordersStyle borders = CellBordersStyle.instance(node);

		return getInstance(font, align, borders);
	}

	public static CellStyle getDefaultInstance() {
		return getInstance(CellFontStyle.getDefaultInstance(),
				CellAlignStyle.getDefaultInstance(),
				CellBordersStyle.getDefaultInstance());
	}

	public static CellStyle getInstance(CellFontStyle font,
			CellAlignStyle align, CellBordersStyle lines) {

		if (font == null && align == null && lines == null) {
			return null;
		}

		String key = createUUID(font, align, lines);

		CellStyle cell = matchCache(key);
		if (cell == null) {
			cell = new CellStyle(font, align, lines);
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
			CellBordersStyle borders) {
		String result = "";
		result = result + ((font != null) ? font.getUUID() : "") + ",";
		result = result + ((align != null) ? align.getUUID() : "") + ",";
		result = result + ((borders != null) ? borders.getUUID() : "");
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

		return jo;
	}

}
