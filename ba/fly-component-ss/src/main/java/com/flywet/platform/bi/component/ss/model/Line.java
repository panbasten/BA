package com.flywet.platform.bi.component.ss.model;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;

import com.flywet.platform.bi.component.core.ComponentObjectInterface;
import com.flywet.platform.bi.component.ss.model.def.DefaultSetting;
import com.flywet.platform.bi.component.ss.model.enums.LineEnum;

/**
 * 线条
 * 
 * @author PeterPan
 * 
 */
public class Line implements ComponentObjectInterface, ISpreadSheetMeta {

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

	public static Line getInstance(LineEnum lineType, Color lineColor) {
		lineType = Const.NVL(lineType, DefaultSetting.DEFAULT_LINE_TYPE);
		lineColor = Const.NVL(lineColor, DefaultSetting.DEFAULT_COLOR);
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

	public static Line getDefaultInstance() {
		return getInstance(null, null);
	}

	private static String createUUID(LineEnum lineType, Color lineColor) {
		String result = "";
		result = result + lineType.getIndex() + ",";
		result = result + lineColor.getUUID();
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

	@Override
	public JSONObject toJSONObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
