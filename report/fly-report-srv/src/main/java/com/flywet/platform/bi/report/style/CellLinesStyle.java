package com.flywet.platform.bi.report.style;

import java.util.Map;
import java.util.WeakHashMap;

import com.flywet.platform.bi.report.model.Line;
import com.flywet.platform.bi.report.utils.DefaultConst;
import com.flywet.platform.bi.report.utils.DefaultSetting;

public class CellLinesStyle implements ICellLinesStyle {

	// 顺序按照：上、下、左、右、对角线、反斜线、水平、垂直
	private Line[] lines = new Line[8];

	// 缓存
	private static Map<String, ICellLinesStyle> CACHE = new WeakHashMap<String, ICellLinesStyle>();

	private CellLinesStyle() {

	}

	private CellLinesStyle(Line[] lines) {
		this.lines = lines;
	}

	private static String createUUID(Line[] lines) {
		String result = lines[0].getUUID();
		for (int i = 1; i < lines.length; i++) {
			result = result + "," + lines[i].getUUID();
		}
		return result;
	}

	@Override
	public String getUUID() {
		return createUUID(lines);
	}

	public static ICellLinesStyle getInstance(Line[] lines) {
		int i = 0;
		Line[] rtn = new Line[8];
		if (lines != null) {
			for (; i < lines.length && i < 8; i++) {
				rtn[i] = ((lines[i] == null) ? DefaultSetting.DEFAULT_LINE
						: lines[i]);
			}
		}
		for (; i < 8; i++) {
			rtn[i] = DefaultSetting.DEFAULT_LINE;
		}

		String key = createUUID(rtn);

		ICellLinesStyle linesStyle = CACHE.get(key);
		if (linesStyle == null) {
			linesStyle = new CellLinesStyle(rtn);
			CACHE.put(key, linesStyle);
		}

		return linesStyle;
	}

	@Override
	public Line getUp() {
		return lines[DefaultConst.LINE_UP_INDEX];
	}

	@Override
	public Line getDown() {
		return lines[DefaultConst.LINE_DOWN_INDEX];
	}

	@Override
	public Line getLeft() {
		return lines[DefaultConst.LINE_LEFT_INDEX];
	}

	@Override
	public Line getRight() {
		return lines[DefaultConst.LINE_RIGHT_INDEX];
	}

	@Override
	public Line getDiagonal() {
		return lines[DefaultConst.LINE_DIAGONAL_INDEX];
	}

	@Override
	public Line getBackslash() {
		return lines[DefaultConst.LINE_BACKSLASH_INDEX];
	}

	@Override
	public Line getHorizontal() {
		return lines[DefaultConst.LINE_HORIZONTAL_INDEX];
	}

	@Override
	public Line getVertical() {
		return lines[DefaultConst.LINE_VERTICAL_INDEX];
	}

}
