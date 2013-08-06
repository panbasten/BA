package com.flywet.platform.bi.report.style;

import com.flywet.platform.bi.report.enums.AlignEnum;
import com.flywet.platform.bi.report.enums.FontStyleEnum;
import com.flywet.platform.bi.report.enums.VerticalEnum;

/**
 * 系统默认的样式常量
 * 
 * @author PeterPan
 * 
 */
public class DefaultStyleConst {

	// 对齐
	public static final AlignEnum DEFAULT_ALIGN = AlignEnum.ALIGN_GENERAL;
	public static final VerticalEnum DEFAULT_VERTICAL = VerticalEnum.VERTICAL_CENTER;
	public static final float DEFAULT_INDENTATION = 0L;
	public static final boolean DEFAULT_WRAP = false;
	public static final boolean DEFAULT_SHRINK = false;

	// 字体
	public static final FontStyleEnum DEFAULT_FONT_STYLE = FontStyleEnum.FONT_STYLE_GENERAL;
}
