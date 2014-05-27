package com.flywet.platform.bi.pivot.model.style;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.pivot.model.ICacheable;
import com.flywet.platform.bi.pivot.model.IJSONObjectable;
import com.flywet.platform.bi.pivot.model.def.DefaultConst;
import com.flywet.platform.bi.pivot.model.def.DefaultSetting;
import com.flywet.platform.bi.pivot.model.enums.FontStyleEnum;
import com.flywet.platform.bi.pivot.model.enums.FontWeightEnum;
import com.tonbeller.wcf.controller.RequestContext;

public class CellFontStyle implements ICacheable, IJSONObjectable {

	private static final long serialVersionUID = -1283476963173928723L;

	private static final String PROP_NAME_FONT_FAMILY = "fontFamily";
	private static final String PROP_NAME_FONT_STYLE = "fontStyle";
	private static final String PROP_NAME_FONT_WEIGHT = "fontWeight";
	private static final String PROP_NAME_FONT_SIZE = "fontSize";
	private static final String PROP_NAME_FONT_COLOR = "fontColor";
	private static final String PROP_NAME_STRIKETHROUGH = "strikethrough";

	// 字体名称
	private final String fontFamily;

	// 字体样式
	private final FontStyleEnum fontStyle;

	// 字体粗细
	private final FontWeightEnum fontWeight;

	// 字体大小
	private final Integer fontSize;

	// 字体颜色
	private final Color fontColor;

	// 删除线
	private final Boolean strikethrough;

	// 缓存
	private static Map<String, SoftReference<CellFontStyle>> CACHE = new ConcurrentHashMap<String, SoftReference<CellFontStyle>>();

	private final String _uuid;

	private CellFontStyle(String fontFamily, FontStyleEnum fontStyle,
			FontWeightEnum fontWeight, int fontSize, Color fontColor,
			Boolean strikethrough) {
		this.fontFamily = fontFamily;
		this.fontStyle = fontStyle;
		this.fontWeight = fontWeight;
		this.fontSize = fontSize;
		this.fontColor = fontColor;
		this.strikethrough = strikethrough;

		this._uuid = createUUID(fontFamily, fontStyle, fontWeight, fontSize,
				fontColor, strikethrough);
	}

	public static CellFontStyle instance(Node node) throws BIException {

		String fontFamily = Const.NVL(
				XMLHandler.getTagAttribute(node, PROP_NAME_FONT_FAMILY), null);

		Integer fontStyleInt = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_FONT_STYLE), null);
		FontStyleEnum fontStyle = null;
		if (fontStyleInt != null) {
			fontStyle = FontStyleEnum.get(fontStyleInt.shortValue());
		}

		Integer fontWeightInt = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_FONT_WEIGHT), null);
		FontWeightEnum fontWeight = null;
		if (fontWeightInt != null) {
			fontWeight = FontWeightEnum.get(fontWeightInt.shortValue());
		}

		Integer fontSize = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_FONT_SIZE), null);

		String fontColorStr = Const.NVL(
				XMLHandler.getTagAttribute(node, PROP_NAME_FONT_COLOR), null);
		Color fontColor = null;
		if (fontColorStr != null) {
			fontColor = Color.getInstance(fontColorStr);
		}

		Boolean strikethrough = Utils
				.toBoolean(XMLHandler.getTagAttribute(node,
						PROP_NAME_STRIKETHROUGH), null);

		return getInstance(fontFamily, fontStyle, fontWeight, fontSize,
				fontColor, strikethrough);

	}

	public static CellFontStyle getDefaultInstance() {
		return getInstance(DefaultConst.UNDEFINED_STR,
				DefaultSetting.DEFAULT_FONT_STYLE,
				DefaultSetting.DEFAULT_FONT_WEIGHT, DefaultConst.UNDEFINED_INT,
				DefaultSetting.DEFAULT_COLOR,
				DefaultSetting.DEFAULT_STRIKETHROUGH);
	}

	public static CellFontStyle getInstance(String fontFamily,
			FontStyleEnum fontStyle, FontWeightEnum fontWeight,
			Integer fontSize, Color fontColor, Boolean strikethrough) {
		if (fontFamily == null && fontStyle == null && fontWeight == null
				&& fontSize == null && fontColor == null
				&& strikethrough == null) {
			return null;
		}

		String key = createUUID(fontFamily, fontStyle, fontWeight, fontSize,
				fontColor, strikethrough);
		CellFontStyle font = matchCache(key);
		if (font == null) {
			font = new CellFontStyle(fontFamily, fontStyle, fontWeight,
					fontSize, fontColor, strikethrough);
			putCache(key, font);
		}
		return font;
	}

	private static CellFontStyle matchCache(String key) {
		SoftReference<CellFontStyle> ref = CACHE.get(key);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	private static void putCache(String key, CellFontStyle cfs) {
		CACHE.put(key, new SoftReference<CellFontStyle>(cfs));
	}

	public static void clearCache() {
		CACHE.clear();
	}

	/**
	 * 获得HashCode
	 * 
	 * @param fontFamily
	 * @param fontStyle
	 * @param fontSize
	 * @param fontColor
	 * @param background
	 * @return
	 */
	private static String createUUID(String fontFamily,
			FontStyleEnum fontStyle, FontWeightEnum fontWeight, int fontSize,
			Color fontColor, Boolean strikethrough) {
		String result = "";
		result = result + Const.NVL(fontFamily, "") + ",";
		result = result + ((fontStyle != null) ? fontStyle.getIndex() : "")
				+ ",";
		result = result + ((fontWeight != null) ? fontWeight.getIndex() : "")
				+ ",";
		result = result + Const.NVL(fontSize, "") + ",";
		result = result + ((fontColor != null) ? fontColor.getUUID() : "")
				+ ",";
		result = result + Const.NVL(strikethrough, "");
		return result;
	}

	@Override
	public String getUUID() {
		return _uuid;
	}

	@Override
	public String toString() {
		StringBuffer bf = new StringBuffer();
		bf.append("CellFontStyle(");
		bf.append(Const.NVL(fontFamily, "") + ",");
		bf.append(((fontStyle == null) ? "" : fontStyle.getText()) + ",");
		bf.append(((fontWeight == null) ? "" : fontWeight.getText()) + ",");
		bf.append(Const.NVL(fontSize, "") + ",");
		bf.append(((fontColor == null) ? "" : fontColor.toString()) + ",");
		bf.append(((strikethrough == null) ? "" : strikethrough.toString()));
		bf.append(")");
		return bf.toString();
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public FontStyleEnum getFontStyle() {
		return fontStyle;
	}

	public int getFontSize() {
		return fontSize;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public Boolean getStrikethrough() {
		return strikethrough;
	}

	public FontWeightEnum getFontWeight() {
		return fontWeight;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (fontFamily != null) {
			jo.put(PROP_NAME_FONT_FAMILY, fontFamily);
		}
		if (fontStyle != null) {
			jo.put(PROP_NAME_FONT_STYLE, fontStyle.getIndex());
		}
		if (fontWeight != null) {
			jo.put(PROP_NAME_FONT_WEIGHT, fontWeight.getIndex());
		}
		if (fontSize != null && fontSize > 0) {
			jo.put(PROP_NAME_FONT_SIZE, fontSize);
		}
		if (fontColor != null && !fontColor.isNull()) {
			jo.put(PROP_NAME_FONT_COLOR, "#" + fontColor.getRGBText());
		}
		if (strikethrough != null) {
			jo.put(PROP_NAME_STRIKETHROUGH, strikethrough);
		}

		return jo;
	}

}
