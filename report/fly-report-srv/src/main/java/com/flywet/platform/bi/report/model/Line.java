package com.flywet.platform.bi.report.model;

import java.util.Map;
import java.util.WeakHashMap;

import org.pentaho.di.core.Const;

import com.flywet.platform.bi.report.enums.LineEnum;
import com.flywet.platform.bi.report.style.ISpreadSheetStyle;
import com.flywet.platform.bi.report.utils.DefaultSetting;

/**
 * 线条
 * 
 * @author PeterPan
 * 
 */
public class Line implements java.io.Serializable, ISpreadSheetStyle {

	private static final long serialVersionUID = 3394336996488187718L;

	private static Class<?> PKG = Line.class;

	// 缓存线条
	private static Map<String, Line> CACHE = new WeakHashMap<String, Line>();

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
		Line line = CACHE.get(key);
		if (line == null) {
			line = new Line(lineType, lineColor);
			CACHE.put(key, line);
		}
		return line;
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

}
