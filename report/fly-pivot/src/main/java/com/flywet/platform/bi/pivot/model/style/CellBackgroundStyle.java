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
import com.flywet.platform.bi.pivot.model.IJSONObjectable;
import com.flywet.platform.bi.pivot.model.def.DefaultSetting;
import com.flywet.platform.bi.pivot.model.enums.AlignEnum;
import com.flywet.platform.bi.pivot.model.enums.VerticalEnum;
import com.tonbeller.wcf.controller.RequestContext;

public class CellBackgroundStyle implements ICacheable, IJSONObjectable {

	private static final long serialVersionUID = 3877054599975202852L;

	private static final String PROP_NAME_BACKGROUND_COLOR = "backgroundColor";

	private final Color bgColor;

	// 缓存
	private static Map<String, SoftReference<CellBackgroundStyle>> CACHE = new ConcurrentHashMap<String, SoftReference<CellBackgroundStyle>>();

	private final String _uuid;

	private CellBackgroundStyle(Color bgColor) {
		this.bgColor = bgColor;

		this._uuid = createUUID(bgColor);
	}

	public static CellBackgroundStyle instance(Node node) throws BIException {

		String bgColorStr = Const.NVL(
				XMLHandler.getTagAttribute(node, PROP_NAME_BACKGROUND_COLOR),
				null);
		Color bgColor = null;
		if (bgColorStr != null) {
			bgColor = Color.getInstance(bgColorStr);
		}

		return getInstance(bgColor);

	}

	public static CellBackgroundStyle getDefaultInstance() {
		return getInstance(DefaultSetting.DEFAULT_COLOR);
	}

	public static CellBackgroundStyle getInstance(Color bgColor) {
		if (bgColor == null) {
			return null;
		}

		String key = createUUID(bgColor);
		CellBackgroundStyle bgStyle = matchCache(key);
		if (bgStyle == null) {
			bgStyle = new CellBackgroundStyle(bgColor);
			putCache(key, bgStyle);
		}
		return bgStyle;
	}

	private static CellBackgroundStyle matchCache(String key) {
		SoftReference<CellBackgroundStyle> ref = CACHE.get(key);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	private static void putCache(String key, CellBackgroundStyle cbs) {
		CACHE.put(key, new SoftReference<CellBackgroundStyle>(cbs));
	}

	public static void clearCache() {
		CACHE.clear();
	}

	private static String createUUID(Color bgColor) {
		String result = "";
		result = result + ((bgColor != null) ? bgColor.getUUID() : "");
		return result;
	}

	@Override
	public String getUUID() {
		return _uuid;
	}

	public Color getBgColor() {
		return bgColor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (bgColor != null) {
			jo.put(PROP_NAME_BACKGROUND_COLOR, "#" + bgColor.getRGBText());
		}

		return jo;
	}

}
