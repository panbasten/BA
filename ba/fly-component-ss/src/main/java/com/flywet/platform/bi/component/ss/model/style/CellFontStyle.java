package com.flywet.platform.bi.component.ss.model.style;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.pentaho.di.core.Const;

import com.flywet.platform.bi.component.ss.model.Color;
import com.flywet.platform.bi.component.ss.model.def.DefaultConst;
import com.flywet.platform.bi.component.ss.model.def.DefaultSetting;
import com.flywet.platform.bi.component.ss.model.enums.FontStyleEnum;

public class CellFontStyle implements ICellFontStyle, java.io.Serializable {

	private static final long serialVersionUID = -1283476963173928723L;

	// 字体名称
	private final String fontName;

	// 字体样式
	private final FontStyleEnum fontStyle;

	// 字体大小
	private final int fontSize;

	// 字体颜色
	private final Color fontColor;

	// 删除线
	private final Boolean strikethrough;

	// 缓存
	private static Map<String, SoftReference<ICellFontStyle>> CACHE = new ConcurrentHashMap<String, SoftReference<ICellFontStyle>>();

	private final String _uuid;

	private CellFontStyle(String fontName, FontStyleEnum fontStyle,
			int fontSize, Color fontColor, Boolean strikethrough) {
		this.fontName = fontName;
		this.fontStyle = fontStyle;
		this.fontSize = fontSize;
		this.fontColor = fontColor;
		this.strikethrough = strikethrough;

		this._uuid = createUUID(fontName, fontStyle, fontSize, fontColor,
				strikethrough);
	}

	public static ICellFontStyle getInstance(String fontName,
			FontStyleEnum fontStyle, int fontSize, Color fontColor,
			Boolean strikethrough) {
		fontName = Const.NVL(fontName, DefaultConst.UNDEFINED_STR);
		fontStyle = Const.NVL(fontStyle, DefaultSetting.DEFAULT_FONT_STYLE);
		fontSize = (fontSize < 1) ? DefaultConst.UNDEFINED_INT : fontSize;
		fontColor = Const.NVL(fontColor, DefaultSetting.DEFAULT_COLOR);
		strikethrough = Const.NVL(strikethrough,
				DefaultSetting.DEFAULT_STRIKETHROUGH);

		String key = createUUID(fontName, fontStyle, fontSize, fontColor,
				strikethrough);
		ICellFontStyle font = matchCache(key);
		if (font == null) {
			font = new CellFontStyle(fontName, fontStyle, fontSize, fontColor,
					strikethrough);
			putCache(key, font);
		}
		return font;
	}

	private static ICellFontStyle matchCache(String key) {
		SoftReference<ICellFontStyle> ref = CACHE.get(key);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	private static void putCache(String key, ICellFontStyle cfs) {
		CACHE.put(key, new SoftReference<ICellFontStyle>(cfs));
	}

	public static void clearCache() {
		CACHE.clear();
	}

	public static ICellFontStyle getDefaultInstance() {
		return getInstance(null, null, 0, null, null);
	}

	/**
	 * 获得HashCode
	 * 
	 * @param fontName
	 * @param fontStyle
	 * @param fontSize
	 * @param fontColor
	 * @param background
	 * @return
	 */
	private static String createUUID(String fontName, FontStyleEnum fontStyle,
			int fontSize, Color fontColor, Boolean strikethrough) {
		String result = "";
		result = result + fontName + ",";
		result = result + fontStyle.getIndex() + ",";
		result = result + fontSize + ",";
		result = result + fontColor.getUUID() + ",";
		result = result + strikethrough;
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
		bf.append(Const.NVL(fontName, "") + ",");
		bf.append(((fontStyle == null) ? "" : fontStyle.getText()) + ",");
		bf.append(fontSize + ",");
		bf.append(((fontColor == null) ? "" : fontColor.toString()) + ",");
		bf.append(((strikethrough == null) ? "" : strikethrough.toString()));
		bf.append(")");
		return bf.toString();
	}

	@Override
	public String getFontName() {
		return fontName;
	}

	@Override
	public FontStyleEnum getFontStyle() {
		return fontStyle;
	}

	@Override
	public int getFontSize() {
		return fontSize;
	}

	@Override
	public Color getFontColor() {
		return fontColor;
	}

	@Override
	public Boolean getStrikethrough() {
		return strikethrough;
	}

}
