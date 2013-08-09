package com.flywet.platform.bi.report.utils;

import com.flywet.platform.bi.report.enums.AlignEnum;
import com.flywet.platform.bi.report.enums.FontStyleEnum;
import com.flywet.platform.bi.report.enums.LineEnum;
import com.flywet.platform.bi.report.enums.VerticalEnum;
import com.flywet.platform.bi.report.model.Color;
import com.flywet.platform.bi.report.model.Line;

public class DefaultSetting {

	// 对齐
	public static final AlignEnum DEFAULT_ALIGN = AlignEnum.ALIGN_GENERAL;
	public static final VerticalEnum DEFAULT_VERTICAL = VerticalEnum.VERTICAL_CENTER;
	public static final float DEFAULT_INDENTATION = 0L;
	public static final boolean DEFAULT_WRAP = false;
	public static final boolean DEFAULT_SHRINK = false;

	// 字体
	public static final String DEFAULT_FONT_NAME = "宋体"; // TODO 来自系统参数
	public static final int DEFAULT_FONT_Size = 12; // TODO 来自系统参数
	public static final FontStyleEnum DEFAULT_FONT_STYLE = FontStyleEnum.FONT_STYLE_GENERAL;
	public static final Boolean DEFAULT_STRIKETHROUGH = false;

	// 颜色
	public static final Color DEFAULT_COLOR = Color.NULL_COLOR;

	// 线条
	public static final LineEnum DEFAULT_LINE_TYPE = LineEnum.BORDER_NONE;
	public static final Line DEFAULT_LINE = Line.getDefaultInstance();
}
