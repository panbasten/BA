package com.flywet.platform.bi.report.style;

import java.util.Map;
import java.util.WeakHashMap;

public class CellStyle implements ICellStyle {

	// 缓存
	private static Map<String, ICellStyle> CACHE = new WeakHashMap<String, ICellStyle>();

	// 字体样式
	private final ICellFontStyle font;

	// 对齐方式
	private final ICellAlignStyle align;

	// 线条样式
	private final ICellLinesStyle lines;

	// 数据格式化对象
	// private ICellStyle dataFormat;

	private final String _uuid;

	private CellStyle(ICellFontStyle font, ICellAlignStyle align,
			ICellLinesStyle lines) {
		this.font = font;
		this.align = align;
		this.lines = lines;

		this._uuid = createUUID(font, align, lines);
	}

	public static ICellStyle getDefaultInstance() {
		return getInstance(null, null, null);
	}

	public static ICellStyle getInstance(ICellFontStyle font,
			ICellAlignStyle align, ICellLinesStyle lines) {
		font = (font == null) ? CellFontStyle.getDefaultInstance() : font;
		align = (align == null) ? CellAlignStyle.getDefaultInstance() : align;
		lines = (lines == null) ? CellLinesStyle.getDefaultInstance() : lines;

		String key = createUUID(font, align, lines);

		ICellStyle cell = CACHE.get(key);
		if (cell == null) {
			cell = new CellStyle(font, align, lines);
			CACHE.put(key, cell);
		}

		return cell;
	}

	public static String createUUID(ICellFontStyle font, ICellAlignStyle align,
			ICellLinesStyle lines) {
		String result = "";
		result = result + font.getUUID() + ",";
		result = result + align.getUUID() + ",";
		result = result + lines.getUUID();
		return result;
	}

	@Override
	public String getUUID() {
		return _uuid;
	}

	@Override
	public ICellFontStyle getFont() {
		return font;
	}

	@Override
	public ICellAlignStyle getAlign() {
		return align;
	}

	@Override
	public ICellLinesStyle getLines() {
		return lines;
	}

}
