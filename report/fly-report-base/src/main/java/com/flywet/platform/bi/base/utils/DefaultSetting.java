package com.flywet.platform.bi.base.utils;

import com.flywet.platform.bi.base.enums.AlignEnum;
import com.flywet.platform.bi.base.enums.FontStyleEnum;
import com.flywet.platform.bi.base.enums.LineEnum;
import com.flywet.platform.bi.base.enums.VerticalEnum;
import com.flywet.platform.bi.base.model.Color;
import com.flywet.platform.bi.base.model.Line;

/**
 * 默认设置
 * 
 * @author PeterPan
 * 
 */
public class DefaultSetting {

	// 对齐
	public static final AlignEnum DEFAULT_ALIGN = AlignEnum.ALIGN_GENERAL;
	public static final VerticalEnum DEFAULT_VERTICAL = VerticalEnum.VERTICAL_CENTER;
	public static final float DEFAULT_INDENTATION = 0L;
	public static final boolean DEFAULT_WRAP = false;
	public static final boolean DEFAULT_SHRINK = false;

	// 字体
	public static final String DEFAULT_FONT_NAME = "宋体"; // TODO 来自系统参数
	public static final int DEFAULT_FONT_SIZE = 12; // TODO 来自系统参数
	public static final FontStyleEnum DEFAULT_FONT_STYLE = FontStyleEnum.FONT_STYLE_GENERAL;
	public static final Boolean DEFAULT_STRIKETHROUGH = false;

	// 颜色
	public static final Color DEFAULT_COLOR = Color.NULL_COLOR;

	// 线条
	public static final LineEnum DEFAULT_LINE_TYPE = LineEnum.BORDER_NONE;
	public static final Line DEFAULT_LINE = Line.getDefaultInstance();

	// 定位
	public static final int DEFAULT_ROW_POS = 0;
	public static final int DEFAULT_COLUMN_POS = 0;

	// 标头
	// 1毫米等于4个像素
	public static final int MILLIMETER_PIXEL = 4;
	// 行高
	public static final int DEFAULT_ROW_SIZE = 8 * MILLIMETER_PIXEL;
	public static final int DEFAULT_MIN_ROW_SIZE = 0;
	public static final int DEFAULT_MAX_ROW_SIZE = 160 * MILLIMETER_PIXEL;
	// 列宽
	public static final int DEFAULT_COLUMN_SIZE = 24 * MILLIMETER_PIXEL;
	public static final int DEFAULT_MIN_COLUMN_SIZE = 0;
	public static final int DEFAULT_MAX_COLUMN_SIZE = 240 * MILLIMETER_PIXEL;

	// 模型最大行数和列数
	public static final int DEFAULT_MODEL_MAX_ROW_NUM = Short.MAX_VALUE * 2;
	public static final int DEFAULT_MODEL_MAX_COLUMN_NUM = 256;
	
	// Excel 2003 之前版本最大行数
	public static final int EXCEL03_MODEL_MAX_ROW_NUM = 65536;
	public static final int EXCEL03_MODEL_MAX_COLUMN_NUM = 256;
	
	// Excel 2007 之后版本最大行数
	public static final int EXCEL07_MODEL_MAX_ROW_NUM = 1048576;
	public static final int EXCEL07_MODEL_MAX_COLUMN_NUM = 16384;
}
