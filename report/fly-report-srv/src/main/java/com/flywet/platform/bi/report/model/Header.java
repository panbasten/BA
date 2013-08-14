package com.flywet.platform.bi.report.model;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.flywet.platform.bi.report.enums.HeaderEnum;
import com.flywet.platform.bi.report.style.ISpreadSheetStyle;
import com.flywet.platform.bi.report.utils.DefaultSetting;

public class Header implements java.io.Serializable, ISpreadSheetStyle {

	private static final long serialVersionUID = 8859550129361435462L;

	private static Class<?> PKG = Header.class;

	private static Map<String, SoftReference<Header>> CACHE = new ConcurrentHashMap<String, SoftReference<Header>>();

	// 默认尺寸
	private int size;

	// 最大尺寸
	private int maxSize;

	// 最小尺寸
	private int minSize;

	// 类型
	private HeaderEnum type;

	private Header(int size, int maxSize, int minSize, HeaderEnum type) {
		this.size = size;
		this.maxSize = maxSize;
		this.minSize = minSize;
		this.type = type;
	}

	public static Header getDefaultInstance(HeaderEnum type) {
		if (type == HeaderEnum.HEADER_ROW) {
			return new Header(DefaultSetting.DEFAULT_ROW_SIZE,
					DefaultSetting.DEFAULT_MAX_ROW_SIZE,
					DefaultSetting.DEFAULT_MIN_ROW_SIZE, type);
		} else if (type == HeaderEnum.HEADER_COLUMN) {
			return new Header(DefaultSetting.DEFAULT_COLUMN_SIZE,
					DefaultSetting.DEFAULT_MAX_COLUMN_SIZE,
					DefaultSetting.DEFAULT_MIN_COLUMN_SIZE, type);
		}
		return null;
	}

	@Override
	public String getUUID() {
		// TODO Auto-generated method stub
		return null;
	}
}
