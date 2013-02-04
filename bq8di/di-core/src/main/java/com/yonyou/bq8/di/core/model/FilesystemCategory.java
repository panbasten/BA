package com.yonyou.bq8.di.core.model;

public enum FilesystemCategory {
	FILESYS_TYPE_LOCAL("local","服务器文件系统"),
	FILESYS_TYPE_FTP("ftp","FTP文件系统"),
	FILESYS_TYPE_SFTP("sftp","SFTP文件系统");
	
	private String category;
	private String desc;
	
	private FilesystemCategory(String category, String desc){
		this.category = category;
		this.desc = desc;
	}

	public String getCategory() {
		return category;
	}


	public String getDesc() {
		return desc;
	}
	
	public static FilesystemCategory getCategoryByCode(String category) {
		for (FilesystemCategory fsc : values()) {
			if (fsc.getCategory().equals(category)) {
				return fsc;
			}
		}
		return null;
	}
	
	public static String getDescByCategory(String category) {
		for (FilesystemCategory fsc : values()) {
			if (fsc.getCategory().equals(category)) {
				return fsc.getDesc();
			}
		}
		return null;
	}
}
