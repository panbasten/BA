package com.plywet.platform.bi.delegates.enums;

import org.pentaho.di.i18n.BaseMessages;

public enum BIDirectoryCategory {
	DI(0L, "di"), REPORT(1L, "report"), DOMAIN(2L, "domain");

	private static Class<?> PKG = BIDirectoryCategory.class;

	private long rootId;
	private String id;

	private BIDirectoryCategory(long rootId, String id) {
		this.rootId = rootId;
		this.id = id;
	}

	public long getRootId() {
		return rootId;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return BaseMessages.getString(PKG, "Directory.Category."
				+ this.name());
	}

}
