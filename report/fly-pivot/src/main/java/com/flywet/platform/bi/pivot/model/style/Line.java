package com.flywet.platform.bi.pivot.model.style;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.flywet.platform.bi.pivot.model.ICacheable;
import com.flywet.platform.bi.pivot.model.def.DefaultSetting;
import com.flywet.platform.bi.pivot.model.enums.LineEnum;

/**
 * 线条
 * 
 * @author PeterPan
 * 
 */
public class Line implements ICacheable {

	private static final long serialVersionUID = 3394336996488187718L;

	private static Class<?> PKG = Line.class;

	// 缓存线条
	private static Map<String, SoftReference<Line>> CACHE = new ConcurrentHashMap<String, SoftReference<Line>>();

	private final LineEnum lineType;

	private final Color lineColor;

	private final String _uuid;

	private Line(LineEnum lineType, Color lineColor) {
		this.lineType = lineType;
		this.lineColor = lineColor;

		this._uuid = createUUID(lineType, lineColor);
	}

	public static Line getDefaultInstance() {
		return getInstance(DefaultSetting.DEFAULT_LINE_TYPE,
				DefaultSetting.DEFAULT_COLOR);
	}

	public static Line getInstance(LineEnum lineType, Color lineColor) {
		if (lineType == null && lineColor == null) {
			return null;
		}

		String key = createUUID(lineType, lineColor);
		Line line = matchCache(key);
		if (line == null) {
			line = new Line(lineType, lineColor);
			putCache(key, line);
		}
		return line;
	}

	private static Line matchCache(String key) {
		SoftReference<Line> ref = CACHE.get(key);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	private static void putCache(String key, Line line) {
		CACHE.put(key, new SoftReference<Line>(line));
	}

	public static void clearCache() {
		CACHE.clear();
	}

	private static String createUUID(LineEnum lineType, Color lineColor) {
		String result = "";
		result = result + ((lineType != null) ? lineType.getIndex() : "") + ",";
		result = result + ((lineColor != null) ? lineColor.getUUID() : "");
		return result;
	}

	@Override
	public String getUUID() {
		return _uuid;
	}

	@Override
	public String toString() {
		StringBuffer bf = new StringBuffer();
		bf.append("Line(");
		bf.append(((lineType == null) ? "" : lineType.getText()) + ",");
		bf.append(((lineColor == null) ? "" : lineColor.toString()));
		bf.append(")");
		return bf.toString();
	}

	public LineEnum getLineType() {
		return lineType;
	}

	public Color getLineColor() {
		return lineColor;
	}

}
