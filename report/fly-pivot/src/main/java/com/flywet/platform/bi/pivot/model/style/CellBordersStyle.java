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
import com.flywet.platform.bi.pivot.model.enums.LineEnum;
import com.tonbeller.wcf.controller.RequestContext;

public class CellBordersStyle implements ICacheable, IJSONObjectable {

	private static final long serialVersionUID = -7414000347865876669L;

	// 顺序按照：上、下、左、右、对角线、反斜线、水平、垂直
	private final Line[] lines;

	// 缓存
	private static Map<String, SoftReference<CellBordersStyle>> CACHE = new ConcurrentHashMap<String, SoftReference<CellBordersStyle>>();

	private final String _uuid;

	private CellBordersStyle(Line[] lines) {
		this.lines = lines;
		this._uuid = createUUID(lines);
	}

	private static String createUUID(Line[] lines) {
		if (lines == null || lines.length == 0) {
			return "";
		}
		String result = lines[0].getUUID();
		for (int i = 1; i < lines.length; i++) {
			result = result + ","
					+ ((lines[i] != null) ? lines[i].getUUID() : "");
		}
		return result;
	}

	@Override
	public String getUUID() {
		return _uuid;
	}

	public static CellBordersStyle instance(Node node) throws BIException {
		Line[] rtn = new Line[8];
		Integer styleInt;
		String colorStr;
		LineEnum style = null;
		Color color = null;
		for (int i = 0; i < 8; i++) {
			styleInt = Utils.toInt(XMLHandler.getTagAttribute(node,
					DefaultConst.BORDER_TYPE_NAMES[i] + "-style"), null);
			if (styleInt != null) {
				style = LineEnum.get(styleInt.shortValue());
			} else {
				style = null;
			}

			colorStr = Const.NVL(XMLHandler.getTagAttribute(node,
					DefaultConst.BORDER_TYPE_NAMES[i] + "-color"), null);
			if (colorStr != null) {
				color = Color.getInstance(colorStr);
			} else {
				color = null;
			}

			rtn[i] = Line.getInstance(style, color);
		}

		return getInstance(rtn);
	}

	public static CellBordersStyle getDefaultInstance() {
		Line[] rtn = new Line[8];
		for (int i = 0; i < 8; i++) {
			rtn[i] = DefaultSetting.DEFAULT_LINE;
		}

		return getInstance(rtn);
	}

	public static CellBordersStyle getInstance(Line[] lines) {

		if (lines == null) {
			return null;
		}

		boolean hasLine = false;
		for (Line line : lines) {
			if (line != null) {
				hasLine = true;
				break;
			}
		}
		if (!hasLine) {
			return null;
		}

		String key = createUUID(lines);

		CellBordersStyle linesStyle = matchCache(key);
		if (linesStyle == null) {
			linesStyle = new CellBordersStyle(lines);
			putCache(key, linesStyle);
		}

		return linesStyle;
	}

	private static CellBordersStyle matchCache(String key) {
		SoftReference<CellBordersStyle> ref = CACHE.get(key);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	private static void putCache(String key, CellBordersStyle cls) {
		CACHE.put(key, new SoftReference<CellBordersStyle>(cls));
	}

	public static void clearCache() {
		CACHE.clear();
	}

	public Line getTop() {
		return lines[DefaultConst.BORDER_TOP_INDEX];
	}

	public Line getBottom() {
		return lines[DefaultConst.BORDER_BOTTOM_INDEX];
	}

	public Line getLeft() {
		return lines[DefaultConst.BORDER_LEFT_INDEX];
	}

	public Line getRight() {
		return lines[DefaultConst.BORDER_RIGHT_INDEX];
	}

	public Line getDiagonal() {
		return lines[DefaultConst.BORDER_DIAGONAL_INDEX];
	}

	public Line getBackslash() {
		return lines[DefaultConst.BORDER_BACKSLASH_INDEX];
	}

	public Line getHorizontal() {
		return lines[DefaultConst.BORDER_HORIZONTAL_INDEX];
	}

	public Line getVertical() {
		return lines[DefaultConst.BORDER_VERTICAL_INDEX];
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		if (lines == null) {
			return null;
		}
		JSONObject jo = new JSONObject();

		Line line;
		for (int i = 0; i < 8; i++) {
			line = lines[i];
			if (line != null) {
				if (line.getLineType() != null) {
					jo.put(DefaultConst.BORDER_TYPE_NAMES[i] + "-style", line
							.getLineType().getIndex());
				}
				if (line.getLineColor() != null
						&& !line.getLineColor().isNull()) {
					jo.put(DefaultConst.BORDER_TYPE_NAMES[i] + "-color", "#"
							+ line.getLineColor().getRGBText());
				}
			}
		}

		return jo;
	}

}
