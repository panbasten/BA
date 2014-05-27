package com.flywet.platform.bi.pivot.model.style;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.ICacheable;
import com.flywet.platform.bi.pivot.model.def.DefaultConst;

public class Color implements ICacheable {

	private static final long serialVersionUID = -7207575989364961615L;

	// 颜色值
	private final int value;

	private static Class<?> PKG = Color.class;

	private static final String COLOR_LONG_DESC_PREFIX = "SpreadSheet.ColorLongDesc";

	// 缓存颜色
	private static Map<Integer, SoftReference<Color>> CACHE = new ConcurrentHashMap<Integer, SoftReference<Color>>();
	// 命名缓存颜色
	private static Map<String, Color> NAMED_CACHE = new ConcurrentHashMap<String, Color>();
	private static Map<Integer, Color> NAMED_CACHE2 = new ConcurrentHashMap<Integer, Color>();
	private static INamedColor[] NAMED_COLORS, WEB_NAMED_COLORS;

	public static Color NULL_COLOR = new NULL();
	private static AtomicBoolean initNamedCache = new AtomicBoolean(false);

	static {
		if (!initNamedCache.get()) {
			NAMED_COLORS = new INamedColor[] { new BROWN(), new OLIVE_GREEN(),
					new DARK_GREEN(), new DARK_TEAL(), new DARK_BLUE(),
					new INDIGO(), new GREY_80_PERCENT(), new ORANGE(),
					new DARK_YELLOW(), new BLUE_GREY(), new GREY_50_PERCENT(),
					new LIGHT_ORANGE(), new SEA_GREEN(), new LIGHT_BLUE(),
					new VIOLET(), new GREY_40_PERCENT(), new PINK(),
					new GOLD(), new BRIGHT_GREEN(), new TURQUOISE(),
					new DARK_RED(), new SKY_BLUE(), new PLUM(),
					new GREY_25_PERCENT(), new ROSE(), new LIGHT_YELLOW(),
					new LIGHT_GREEN(), new LIGHT_TURQUOISE(), new PALE_BLUE(),
					new LAVENDER(), new CORNFLOWER_BLUE(), new LEMON_CHIFFON(),
					new ORCHID(), new CORAL(), new ROYAL_BLUE(),
					new LIGHT_CORNFLOWER_BLUE(), new TAN() };
			WEB_NAMED_COLORS = new INamedColor[] { new WEB_AQUA(),
					new WEB_BLACK(), new WEB_BLUE(), new WEB_FUCHSIA(),
					new WEB_GRAY(), new WEB_GREEN(), new WEB_LIME(),
					new WEB_MAROON(), new WEB_NAVY(), new WEB_OLIVE(),
					new WEB_PURPLE(), new WEB_RED(), new WEB_SILVER(),
					new WEB_TEAL(), new WEB_WHITE(), new WEB_YELLOW() };

			for (INamedColor nc : NAMED_COLORS) {
				NAMED_CACHE.put(nc.getName(), (Color) nc);
				NAMED_CACHE2.put(nc.getRGBA(), (Color) nc);
			}

			for (INamedColor nc : WEB_NAMED_COLORS) {
				NAMED_CACHE.put(nc.getName(), (Color) nc);
				NAMED_CACHE2.put(nc.getRGBA(), (Color) nc);
			}

			initNamedCache.set(true);
		}
	}

	private static final double FACTOR = 0.7;

	private Color(int rgba, boolean hasalpha) {
		if (hasalpha) {
			value = rgba;
		} else {
			value = 0xFF000000 | rgba;
		}
	}

	/**
	 * 实例化一个颜色
	 * 
	 * @param r
	 *            红色
	 * @param g
	 *            绿色
	 * @param b
	 *            蓝色
	 * @param a
	 *            透明度
	 * @return
	 * @throws BIException
	 */
	public static Color getInstance(int r, int g, int b, int a)
			throws BIException {
		testColorValueRange(r, g, b, a);
		Integer v = convertColorValue(r, g, b, a);
		Color c = matchCache(v);
		if (c != null) {
			return c;
		}
		c = new Color(v, true);
		putCache(v, c);
		return c;
	}

	public static Color getInstance(int r, int g, int b) throws BIException {
		return getInstance(r, g, b, 255);
	}

	/**
	 * 支持的颜色字符串类型：<br>
	 * 1)fff or #fff or ffffff or #ffffff<br>
	 * 2)命名颜色
	 * 
	 * @param color
	 * @return
	 * @throws BIException
	 */
	public static Color getInstance(String color) throws BIException {
		// 1.十六进制颜色字符串
		if (isColorHexString(color)) {
			if (color.startsWith("#")) {
				color = color.substring(1);
			}

			int r, g, b;

			if (color.length() == 3) {
				r = Integer.decode("0x" + color.substring(0, 1)
						+ color.substring(0, 1));
				g = Integer.decode("0x" + color.substring(1, 2)
						+ color.substring(1, 2));
				b = Integer.decode("0x" + color.substring(2, 3)
						+ color.substring(2, 3));
			} else {
				r = Integer.decode("0x" + color.substring(0, 2));
				g = Integer.decode("0x" + color.substring(2, 4));
				b = Integer.decode("0x" + color.substring(4, 6));
			}

			return getInstance(r, g, b);
		}

		// 2.命名的颜色
		Color c = matchNamedCache(color);
		if (c != null)
			return c;

		throw new BIException("通过颜色字符串实例化颜色对象出现错误：" + color);
	}

	public boolean isNull() {
		return value == DefaultConst.UNDEFINED_INT;
	}

	/**
	 * 获得RGBA值
	 * 
	 * @return
	 */
	public int getRGBA() {
		if (isNull()) {
			return DefaultConst.UNDEFINED_INT;
		}
		return value;
	}

	/**
	 * 获得RGB值
	 * 
	 * @return
	 */
	public int getRGB() {
		if (isNull()) {
			return DefaultConst.UNDEFINED_INT;
		}
		return 0xFFFFFF & value;
	}

	/**
	 * 活动
	 * 
	 * @return
	 */
	public String getRGBText() {
		if (isNull()) {
			return null;
		}

		String RGB = Integer.toHexString(getRGB());

		for (int i = RGB.length(); i < 6; i++) {
			RGB = "0" + RGB;
		}

		return RGB;

	}

	/**
	 * 获得透明度值
	 * 
	 * @return
	 */
	public int getAlpha() {
		if (isNull()) {
			return DefaultConst.UNDEFINED_INT;
		}
		return (getRGBA() >> 24) & 0xFF;
	}

	/**
	 * 获得红色值
	 * 
	 * @return
	 */
	public int getRed() {
		if (isNull()) {
			return DefaultConst.UNDEFINED_INT;
		}
		return (getRGBA() >> 16) & 0xFF;
	}

	/**
	 * 获得绿色值
	 * 
	 * @return
	 */
	public int getGreen() {
		if (isNull()) {
			return DefaultConst.UNDEFINED_INT;
		}
		return (getRGBA() >> 8) & 0xFF;
	}

	/**
	 * 获得蓝色值
	 * 
	 * @return
	 */
	public int getBlue() {
		if (isNull()) {
			return DefaultConst.UNDEFINED_INT;
		}
		return (getRGBA() >> 0) & 0xFF;
	}

	/**
	 * 变亮
	 * 
	 * @return
	 * @throws BIException
	 */
	public Color brighter() throws BIException {
		if (isNull()) {
			return (Color) NULL_COLOR;
		}
		int r = getRed(), g = getGreen(), b = getBlue(), a = getAlpha();

		int i = (int) (1.0 / (1.0 - FACTOR));
		if (r == 0 && g == 0 && b == 0) {
			return getInstance(i, i, i, a);
		}
		if (r > 0 && r < i)
			r = i;
		if (g > 0 && g < i)
			g = i;
		if (b > 0 && b < i)
			b = i;

		return getInstance(Math.min((int) (r / FACTOR), 255),
				Math.min((int) (g / FACTOR), 255),
				Math.min((int) (b / FACTOR), 255), a);
	}

	public Color darker() throws BIException {
		if (isNull()) {
			return (Color) NULL_COLOR;
		}
		return getInstance(Math.max((int) (getRed() * FACTOR), 0),
				Math.max((int) (getGreen() * FACTOR), 0),
				Math.max((int) (getBlue() * FACTOR), 0), getAlpha());
	}

	private static Color matchCache(Integer v) {
		Color color = NAMED_CACHE2.get(v);
		if (color == null) {
			SoftReference<Color> ref = CACHE.get(v);
			if (ref != null) {
				color = ref.get();
			}
		}
		return color;
	}

	private static void putCache(Integer v, Color c) {
		CACHE.put(v, new SoftReference<Color>(c));
	}

	public static void clearCache() {
		CACHE.clear();
	}

	private static Color matchNamedCache(String colorName) {
		if (colorName == null) {
			return null;
		}
		return NAMED_CACHE.get(colorName.toLowerCase());
	}

	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public String getUUID() {
		return String.valueOf(value);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Color
				&& ((Color) obj).getRGBA() == this.getRGBA();
	}

	@Override
	public String toString() {
		String str = "颜色(";
		if (isNull()) {
			str = str + "未定义";
		} else {
			str = str + "透明度: " + getAlpha() + ",";
			str = str + "红: " + getRed() + ",";
			str = str + "绿: " + getGreen() + ",";
			str = str + "蓝: " + getBlue();
		}
		str = str + ")";
		return str;
	}

	/**
	 * 判断是否十六进制颜色字符串<br>
	 * 可接受的字符串如：#fff, #ffffff, fff, ffffff
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isColorHexString(String str) {
		Pattern pattern = Pattern.compile("^#?[0-9a-fA-F]{6}$"), pattern2 = Pattern
				.compile("^#?[0-9a-fA-F]{3}$");
		return pattern.matcher(str).matches()
				|| pattern2.matcher(str).matches();
	}

	/**
	 * 检查颜色值范围
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 * @throws BIException
	 */
	private static void testColorValueRange(int r, int g, int b, int a)
			throws BIException {
		String badString = "";
		boolean rangeError = false;

		if (a < 0 || a > 255) {
			rangeError = true;
			badString = badString + "透明度: " + a;
		}

		if (r < 0 || r > 255) {
			rangeError = true;
			badString = badString + "红: " + r;
		}

		if (g < 0 || g > 255) {
			rangeError = true;
			badString = badString + "绿: " + g;
		}

		if (b < 0 || b > 255) {
			rangeError = true;
			badString = badString + "蓝: " + b;
		}

		if (rangeError) {
			throw new BIException("颜色参数超出预期值，" + badString);
		}
	}

	/**
	 * 转换rgb值为颜色值
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	private static int convertColorValue(int r, int g, int b, int a) {
		return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8)
				| ((b & 0xFF) << 0);
	}

	private static int convertColorValue(int r, int g, int b) {
		return convertColorValue(r, g, b, 255);
	}

	/**
	 * 命名颜色接口类
	 * 
	 * @author PeterPan
	 * 
	 */
	public interface INamedColor {
		public int getType();

		public String getName();

		public String getText();

		public int getRGBA();

		public int getRGB();

		public int getRed();

		public int getGreen();

		public int getBlue();

		public int getAlpha();
	}

	public final static class NULL extends Color implements INamedColor {
		private static final long serialVersionUID = 1L;

		public NULL() {
			super(DefaultConst.UNDEFINED_INT, true);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_NULL;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class BROWN extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public BROWN() {
			super(convertColorValue(153, 51, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_BROWN;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class OLIVE_GREEN extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public OLIVE_GREEN() {
			super(convertColorValue(51, 51, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_OLIVE_GREEN;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class DARK_GREEN extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public DARK_GREEN() {
			super(convertColorValue(0, 51, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_DARK_GREEN;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class DARK_TEAL extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public DARK_TEAL() {
			super(convertColorValue(0, 51, 102), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_DARK_TEAL;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class DARK_BLUE extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public DARK_BLUE() {
			super(convertColorValue(0, 0, 128), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_DARK_BLUE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class INDIGO extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public INDIGO() {
			super(convertColorValue(51, 51, 153), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_INDIGO;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class GREY_80_PERCENT extends Color implements
			INamedColor {

		private static final long serialVersionUID = 1L;

		public GREY_80_PERCENT() {
			super(convertColorValue(51, 51, 51), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_GREY_80_PERCENT;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class ORANGE extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public ORANGE() {
			super(convertColorValue(255, 102, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_ORANGE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class DARK_YELLOW extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public DARK_YELLOW() {
			super(convertColorValue(128, 128, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_DARK_YELLOW;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class BLUE_GREY extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public BLUE_GREY() {
			super(convertColorValue(102, 102, 153), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_BLUE_GREY;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class GREY_50_PERCENT extends Color implements
			INamedColor {

		private static final long serialVersionUID = 1L;

		public GREY_50_PERCENT() {
			super(convertColorValue(128, 128, 128), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_GREY_50_PERCENT;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class LIGHT_ORANGE extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public LIGHT_ORANGE() {
			super(convertColorValue(255, 153, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_LIGHT_ORANGE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class SEA_GREEN extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public SEA_GREEN() {
			super(convertColorValue(51, 153, 102), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_SEA_GREEN;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class LIGHT_BLUE extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public LIGHT_BLUE() {
			super(convertColorValue(51, 102, 255), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_LIGHT_BLUE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class VIOLET extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public VIOLET() {
			super(convertColorValue(128, 0, 128), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_VIOLET;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class GREY_40_PERCENT extends Color implements
			INamedColor {

		private static final long serialVersionUID = 1L;

		public GREY_40_PERCENT() {
			super(convertColorValue(150, 150, 150), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_GREY_40_PERCENT;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class PINK extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public PINK() {
			super(convertColorValue(255, 0, 255), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_PINK;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class GOLD extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public GOLD() {
			super(convertColorValue(255, 204, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_GOLD;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class BRIGHT_GREEN extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public BRIGHT_GREEN() {
			super(convertColorValue(0, 255, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_BRIGHT_GREEN;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class TURQUOISE extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public TURQUOISE() {
			super(convertColorValue(0, 255, 255), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_TURQUOISE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class DARK_RED extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public DARK_RED() {
			super(convertColorValue(128, 0, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_DARK_RED;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class SKY_BLUE extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public SKY_BLUE() {
			super(convertColorValue(0, 204, 255), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_SKY_BLUE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class PLUM extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public PLUM() {
			super(convertColorValue(153, 51, 102), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_PLUM;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class GREY_25_PERCENT extends Color implements
			INamedColor {

		private static final long serialVersionUID = 1L;

		public GREY_25_PERCENT() {
			super(convertColorValue(192, 192, 192), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_GREY_25_PERCENT;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class ROSE extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public ROSE() {
			super(convertColorValue(255, 153, 204), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_ROSE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class LIGHT_YELLOW extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public LIGHT_YELLOW() {
			super(convertColorValue(255, 255, 153), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_LIGHT_YELLOW;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class LIGHT_GREEN extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public LIGHT_GREEN() {
			super(convertColorValue(204, 255, 204), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_LIGHT_GREEN;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class LIGHT_TURQUOISE extends Color implements
			INamedColor {

		private static final long serialVersionUID = 1L;

		public LIGHT_TURQUOISE() {
			super(convertColorValue(204, 255, 255), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_LIGHT_TURQUOISE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class PALE_BLUE extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public PALE_BLUE() {
			super(convertColorValue(153, 204, 255), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_PALE_BLUE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class LAVENDER extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public LAVENDER() {
			super(convertColorValue(204, 153, 255), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_LAVENDER;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class CORNFLOWER_BLUE extends Color implements
			INamedColor {

		private static final long serialVersionUID = 1L;

		public CORNFLOWER_BLUE() {
			super(convertColorValue(153, 153, 255), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_CORNFLOWER_BLUE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class LEMON_CHIFFON extends Color implements
			INamedColor {

		private static final long serialVersionUID = 1L;

		public LEMON_CHIFFON() {
			super(convertColorValue(255, 255, 204), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_LEMON_CHIFFON;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class ORCHID extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public ORCHID() {
			super(convertColorValue(102, 0, 102), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_ORCHID;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class CORAL extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public CORAL() {
			super(convertColorValue(255, 128, 128), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_CORAL;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class ROYAL_BLUE extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public ROYAL_BLUE() {
			super(convertColorValue(0, 102, 204), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_ROYAL_BLUE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class LIGHT_CORNFLOWER_BLUE extends Color implements
			INamedColor {

		private static final long serialVersionUID = 1L;

		public LIGHT_CORNFLOWER_BLUE() {
			super(convertColorValue(204, 204, 255), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_LIGHT_CORNFLOWER_BLUE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class TAN extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public TAN() {
			super(convertColorValue(255, 204, 153), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_GENERAL;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_TAN;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_AQUA extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_AQUA() {
			super(convertColorValue(0, 255, 255), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_AQUA;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_BLACK extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_BLACK() {
			super(convertColorValue(0, 0, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_BLACK;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_BLUE extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_BLUE() {
			super(convertColorValue(0, 0, 255), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_BLUE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_FUCHSIA extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_FUCHSIA() {
			super(convertColorValue(255, 0, 255), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_FUCHSIA;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_GRAY extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_GRAY() {
			super(convertColorValue(128, 128, 128), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_GRAY;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_GREEN extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_GREEN() {
			super(convertColorValue(0, 128, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_GREEN;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_LIME extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_LIME() {
			super(convertColorValue(0, 255, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_LIME;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_MAROON extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_MAROON() {
			super(convertColorValue(128, 0, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_MAROON;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_NAVY extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_NAVY() {
			super(convertColorValue(0, 0, 128), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_NAVY;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_OLIVE extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_OLIVE() {
			super(convertColorValue(128, 128, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_OLIVE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_PURPLE extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_PURPLE() {
			super(convertColorValue(128, 0, 128), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_PURPLE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_RED extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_RED() {
			super(convertColorValue(255, 0, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_RED;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_SILVER extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_SILVER() {
			super(convertColorValue(192, 192, 192), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_SILVER;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_TEAL extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_TEAL() {
			super(convertColorValue(0, 128, 128), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_TEAL;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_WHITE extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_WHITE() {
			super(convertColorValue(255, 255, 255), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_WHITE;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

	public final static class WEB_YELLOW extends Color implements INamedColor {

		private static final long serialVersionUID = 1L;

		public WEB_YELLOW() {
			super(convertColorValue(255, 255, 0), false);
		}

		@Override
		public int getType() {
			return DefaultConst.COLOR_TYPE_WEB;
		}

		@Override
		public String getName() {
			return DefaultConst.COLOR_WEB_YELLOW;
		}

		@Override
		public String getText() {
			return BaseMessages.getString(PKG, COLOR_LONG_DESC_PREFIX + "."
					+ getName());
		}
	}

}
