package com.yonyou.bq8.di.delegates.utils;

import java.util.List;

import com.yonyou.bq8.di.delegates.intf.DIFsLocalAdaptor;
import com.yonyou.bq8.di.delegates.intf.DIHostAdaptor;
import com.yonyou.bq8.di.exceptions.DIKettleException;

public enum DIFileSystemCategory {
	FILESYS_TYPE_LOCAL(1, "local", "服务器文件系统") {
		@Override
		public List<Object[]> getFilesysRoots() throws DIKettleException {
			DIFsLocalAdaptor adaptor = DIAdaptorFactory
					.createAdaptor(DIFsLocalAdaptor.class);
			return adaptor.getLocalRoots();
		}
	},

	FILESYS_TYPE_FTP(2, "ftp", "FTP文件系统") {
		@Override
		public List<Object[]> getFilesysRoots() throws DIKettleException {
			DIHostAdaptor adaptor = DIAdaptorFactory
					.createAdaptor(DIHostAdaptor.class);
			return adaptor.getHostsByType(FILESYS_TYPE_FTP.id);
		}
	},
	FILESYS_TYPE_SFTP(3, "sftp", "SFTP文件系统") {
		@Override
		public List<Object[]> getFilesysRoots() throws DIKettleException {
			DIHostAdaptor adaptor = DIAdaptorFactory
					.createAdaptor(DIHostAdaptor.class);
			return adaptor.getHostsByType(FILESYS_TYPE_SFTP.id);
		}
	},
	FILESYS_TYPE_SVN(4, "svn", "SVN服务器") {
		@Override
		public List<Object[]> getFilesysRoots() throws DIKettleException {
			// TODO Auto-generated method stub
			return null;
		}
	},
	FILESYS_TYPE_GIT(5, "git", "GIT服务器") {
		@Override
		public List<Object[]> getFilesysRoots() throws DIKettleException {
			// TODO Auto-generated method stub
			return null;
		}
	};

	private int id;
	private String category;
	private String desc;

	private DIFileSystemCategory(int id, String category, String desc) {
		this.id = id;
		this.category = category;
		this.desc = desc;
	}

	public abstract List<Object[]> getFilesysRoots() throws DIKettleException;

	public String getCategory() {
		return category;
	}

	public String getDesc() {
		return desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
