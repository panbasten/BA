package com.flywet.platform.bi.pivot.model.def;

import com.flywet.platform.bi.pivot.model.enums.AlignEnum;
import com.flywet.platform.bi.pivot.model.enums.FontStyleEnum;
import com.flywet.platform.bi.pivot.model.enums.FontWeightEnum;
import com.flywet.platform.bi.pivot.model.enums.LineEnum;
import com.flywet.platform.bi.pivot.model.enums.VerticalEnum;
import com.flywet.platform.bi.pivot.model.style.Color;
import com.flywet.platform.bi.pivot.model.style.Line;

/**
 * 默认设置
 * 
 * @author PeterPan
 * 
 */
public class DefaultSetting {

	// 对齐
	public static final AlignEnum DEFAULT_ALIGN = AlignEnum.ALIGN_LEFT;
	public static final VerticalEnum DEFAULT_VERTICAL = VerticalEnum.VERTICAL_CENTER;
	public static final float DEFAULT_INDENTATION = 0L;
	public static final boolean DEFAULT_WRAP = false;
	public static final boolean DEFAULT_SHRINK = false;

	// 字体
	public static final String DEFAULT_FONT_NAME = "宋体"; // TODO 来自系统参数
	public static final int DEFAULT_FONT_SIZE = 12; // TODO 来自系统参数
	public static final FontStyleEnum DEFAULT_FONT_STYLE = FontStyleEnum.FONT_STYLE_NORMAL;
	public static final FontWeightEnum DEFAULT_FONT_WEIGHT = FontWeightEnum.FONT_WEIGHT_NORMAL;
	public static final Boolean DEFAULT_STRIKETHROUGH = false;

	// 颜色
	public static final Color DEFAULT_COLOR = Color.NULL_COLOR;

	// 线条
	public static final LineEnum DEFAULT_LINE_TYPE = LineEnum.BORDER_NONE;
	public static final Line DEFAULT_LINE = Line.getDefaultInstance();

	// 定位
	public static final int DEFAULT_ROW_POS = 0;
	public static final int DEFAULT_COLUMN_POS = 0;

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
