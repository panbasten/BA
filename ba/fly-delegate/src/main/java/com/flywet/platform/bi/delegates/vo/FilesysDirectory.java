package com.flywet.platform.bi.delegates.vo;

import org.pentaho.di.core.Const;

public class FilesysDirectory {
	private long id;
	private long fsType;
	private String path;
	private String desc;
	private String notes;

	public static FilesysDirectory instance() {
		return new FilesysDirectory();
	}

	public long getId() {
		return id;
	}

	public FilesysDirectory setId(long id) {
		this.id = id;
		return this;
	}

	public long getFsType() {
		return fsType;
	}

	public FilesysDirectory setFsType(long fsType) {
		this.fsType = fsType;
		return this;
	}

	public String getPath() {
		return path;
	}

	public FilesysDirectory setPath(String path) {
		this.path = Const.NVL(path, "");
		return this;
	}

	public String getDesc() {
		return desc;
	}

	public FilesysDirectory setDesc(String desc) {
		this.desc = Const.NVL(desc, "");
		return this;
	}

	public String getNotes() {
		return notes;
	}

	public FilesysDirectory setNotes(String notes) {
		this.notes = Const.NVL(notes, "");
		return this;
	}
}
