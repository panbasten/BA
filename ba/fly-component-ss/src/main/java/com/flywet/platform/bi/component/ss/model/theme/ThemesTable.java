package com.flywet.platform.bi.component.ss.model.theme;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.core.ComponentObjectInterface;
import com.flywet.platform.bi.component.ss.model.style.ICellStyle;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.PropertyUtils;

/**
 * 主题表
 * 
 * @author PeterPan
 * 
 */
public class ThemesTable implements ComponentObjectInterface {

	private static final long serialVersionUID = -4411726807141236767L;

	private static Class<?> PKG = ThemesTable.class;
	private static final String THEMES_TABLE_PREFIX = "SpreadSheet.ThemesTable.";
	private static final String THEMES_TABLE_SUBFIX = ".tt";

	private static final String DEFAUTL_THEMES_TABLE_NAME = "default";

	private static Map<String, SoftReference<ThemesTable>> CACHE = new ConcurrentHashMap<String, SoftReference<ThemesTable>>();

	private ThemesTable(String themesTableName) throws BIException {

		String fileName = THEMES_TABLE_PREFIX + themesTableName
				+ THEMES_TABLE_SUBFIX;

		Properties prop = PropertyUtils.getProperties(fileName, PKG);
		// TODO

	}

	public static ThemesTable getInstance() throws BIException {
		return getInstance(DEFAUTL_THEMES_TABLE_NAME);
	}

	public static ThemesTable getInstance(String themesTableName)
			throws BIException {
		ThemesTable tt = matchCache(themesTableName);
		if (tt != null) {
			return tt;
		}
		tt = new ThemesTable(themesTableName);
		putCache(themesTableName, tt);
		return tt;
	}

	private static ThemesTable matchCache(String v) {
		ThemesTable tt = null;
		SoftReference<ThemesTable> ref = CACHE.get(v);
		if (ref != null) {
			tt = ref.get();
		}

		return tt;
	}

	private static void putCache(String v, ThemesTable tt) {
		CACHE.put(v, new SoftReference<ThemesTable>(tt));
	}

	public static void clearCache() {
		CACHE.clear();
	}

	// 列宽
	private int colWidth;

	// 行高
	private int rowHeight;

	// 列样式
	private ICellStyle colStyle;

	// 行样式
	private ICellStyle rowStyle;

	@Override
	public JSONObject toJSONObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getColWidth() {
		return colWidth;
	}

	public int getRowHeight() {
		return rowHeight;
	}

	public ICellStyle getColStyle() {
		return colStyle;
	}

	public ICellStyle getRowStyle() {
		return rowStyle;
	}

}
