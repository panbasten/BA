package com.yonyou.bq8.di.core.model;

public enum DIFileSystemCategory {
	FILESYS_TYPE_LOCAL("local","服务器文件系统"),
	FILESYS_TYPE_FTP("ftp","FTP文件系统"),
	FILESYS_TYPE_SFTP("sftp","SFTP文件系统"),
	FILESYS_TYPE_SVN("svn", "SVN服务器"),
	FILESYS_TYPE_GIT("git", "GIT服务器");
	
	private String category;
	private String desc;
	
	private DIFileSystemCategory(String category, String desc){
		this.category = category;
		this.desc = desc;
	}

	public String getCategory() {
		return category;
	}


	public String getDesc() {
		return desc;
	}
	
	public static DIFileSystemCategory getCategoryByCode(String category) {
		for (DIFileSystemCategory fsc : values()) {
			if (fsc.getCategory().equals(category)) {
				return fsc;
			}
		}
		return null;
	}
	
	public static String getDescByCategory(String category) {
		for (DIFileSystemCategory fsc : values()) {
			if (fsc.getCategory().equals(category)) {
				return fsc.getDesc();
			}
		}
		return null;
	}
}
