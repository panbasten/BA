package com.flywet.platform.bi.report.model;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.flywet.platform.bi.report.style.ISpreadSheetStyle;
import com.flywet.platform.bi.report.utils.DefaultSetting;
import com.flywet.platform.bi.report.utils.SpreedSheetUtils;

public class CellPosition implements java.io.Serializable, ISpreadSheetStyle {

	private static final long serialVersionUID = 1835618561898925687L;

	private static Map<String, SoftReference<CellPosition>> CACHE = new ConcurrentHashMap<String, SoftReference<CellPosition>>();

	private final int row;
	private final int col;

	private final String _uuid;

	private CellPosition(int row, int col) {
		this.row = row;
		this.col = col;

		this._uuid = createUUID(row, col);
	}

	public static CellPosition getDefaultInstance() {
		return getInstance(DefaultSetting.DEFAULT_ROW_POS,
				DefaultSetting.DEFAULT_COLUMN_POS);
	}

	public static CellPosition getInstance(int row, int col) {
		if (row < 0 || col < 0) {
			throw new IllegalArgumentException();
		}

		String key = createUUID(row, col);

		CellPosition pos = matchCache(key);
		if (pos == null) {
			pos = new CellPosition(row, col);
			putCache(key, pos);
		}
		return pos;
	}

	private static CellPosition matchCache(String key) {
		SoftReference<CellPosition> ref = CACHE.get(key);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	private static void putCache(String key, CellPosition cp) {
		CACHE.put(key, new SoftReference<CellPosition>(cp));
	}

	public static void clearCache() {
		CACHE.clear();
	}

	public static String createUUID(int row, int col) {
		String result = "";
		result = result + row + ",";
		result = result + col;
		return result;
	}

	public CellPosition move(int stepRow, int stepCol) {
		int row = this.getRow() + stepRow;
		int col = this.getCol() + stepCol;
		return getInstance(row, col);
	}

	@Override
	public String getUUID() {
		return _uuid;
	}

	@Override
	public boolean equals(Object cp) {
		return this.getUUID().equals(((CellPosition) cp).getUUID());
	}

	@Override
	public String toString() {
		return SpreedSheetUtils.getCellPositionString(row, col);
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

}
