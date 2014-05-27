package com.flywet.platform.bi.pivot.model.def;

import java.awt.GraphicsEnvironment;

/**
 * 系统默认的样式常量
 * 
 * @author PeterPan
 * 
 */
public class DefaultConst {

	// 定位分隔符
	public static final String CELL_POS_SEPARATOR = ":";

	// 未定义信息的值，由于白色的RGB值为-1，所有不能将此值定义为-1
	public static final int UNDEFINED_INT = -2;
	public static final float UNDEFINED_FLOAT = -2.0f;
	public static final String UNDEFINED_STR = "undefined";

	// 颜色
	// 颜色类型
	public static final int COLOR_TYPE_GENERAL = 1;
	public static final int COLOR_TYPE_WEB = 2;

	// 命名颜色
	public static final String COLOR_NULL = "null";
	public static final String COLOR_BROWN = "brown";
	public static final String COLOR_OLIVE_GREEN = "oliveGreen";
	public static final String COLOR_DARK_GREEN = "darkGreen";
	public static final String COLOR_DARK_TEAL = "darkTeal";
	public static final String COLOR_DARK_BLUE = "darkBlue";
	public static final String COLOR_INDIGO = "indigo";
	public static final String COLOR_GREY_80_PERCENT = "grey80";
	public static final String COLOR_ORANGE = "orange";
	public static final String COLOR_DARK_YELLOW = "darkYellow";
	public static final String COLOR_BLUE_GREY = "blueGrey";
	public static final String COLOR_GREY_50_PERCENT = "grey50";
	public static final String COLOR_LIGHT_ORANGE = "lightOrange";
	public static final String COLOR_SEA_GREEN = "seaGreen";
	public static final String COLOR_LIGHT_BLUE = "lightBlue";
	public static final String COLOR_VIOLET = "violet";
	public static final String COLOR_GREY_40_PERCENT = "grey40";
	public static final String COLOR_PINK = "pink";
	public static final String COLOR_GOLD = "gold";
	public static final String COLOR_BRIGHT_GREEN = "brightGreen";
	public static final String COLOR_TURQUOISE = "turquoise";
	public static final String COLOR_DARK_RED = "darkRed";
	public static final String COLOR_SKY_BLUE = "skyBlue";
	public static final String COLOR_PLUM = "plum";
	public static final String COLOR_GREY_25_PERCENT = "grey25";
	public static final String COLOR_ROSE = "rose";
	public static final String COLOR_LIGHT_YELLOW = "lightYellow";
	public static final String COLOR_LIGHT_GREEN = "lightGreen";
	public static final String COLOR_LIGHT_TURQUOISE = "lightTurquoise";
	public static final String COLOR_PALE_BLUE = "paleBlue";
	public static final String COLOR_LAVENDER = "lavender";
	public static final String COLOR_CORNFLOWER_BLUE = "cornflowerBlue";
	public static final String COLOR_LEMON_CHIFFON = "lemonChiffon";
	public static final String COLOR_ORCHID = "orchid";
	public static final String COLOR_CORAL = "coral";
	public static final String COLOR_ROYAL_BLUE = "royalBlue";
	public static final String COLOR_LIGHT_CORNFLOWER_BLUE = "lightCornflowerBlue";
	public static final String COLOR_TAN = "tan";

	public static final String COLOR_WEB_AQUA = "aqua";
	public static final String COLOR_WEB_BLACK = "black";
	public static final String COLOR_WEB_BLUE = "blue";
	public static final String COLOR_WEB_FUCHSIA = "fuchsia";
	public static final String COLOR_WEB_GRAY = "gray";
	public static final String COLOR_WEB_GREEN = "green";
	public static final String COLOR_WEB_LIME = "lime";
	public static final String COLOR_WEB_MAROON = "maroon";
	public static final String COLOR_WEB_NAVY = "navy";
	public static final String COLOR_WEB_OLIVE = "olive";
	public static final String COLOR_WEB_PURPLE = "purple";
	public static final String COLOR_WEB_RED = "red";
	public static final String COLOR_WEB_SILVER = "silver";
	public static final String COLOR_WEB_TEAL = "teal";
	public static final String COLOR_WEB_WHITE = "white";
	public static final String COLOR_WEB_YELLOW = "yellow";

	// 边线索引
	public static final int BORDER_TOP_INDEX = 0;
	public static final int BORDER_BOTTOM_INDEX = 1;
	public static final int BORDER_LEFT_INDEX = 2;
	public static final int BORDER_RIGHT_INDEX = 3;
	public static final int BORDER_DIAGONAL_INDEX = 4;
	public static final int BORDER_BACKSLASH_INDEX = 5;
	public static final int BORDER_HORIZONTAL_INDEX = 6;
	public static final int BORDER_VERTICAL_INDEX = 7;

	public static final String[] BORDER_TYPE_NAMES = new String[] { "top",
			"bottom", "left", "right", "diagonal", "backslash", "horizontal",
			"vertical" };

	public static String[] getFontFamilyNames() {
		GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();
		return null;
	}

}
