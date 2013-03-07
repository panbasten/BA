package com.yonyou.bq8.di.delegates.utils;

import com.yonyou.bq8.di.core.utils.BQFileUtils;
import com.yonyou.bq8.di.delegates.intf.DIFsAdaptor;
import com.yonyou.bq8.di.delegates.intf.DIFsFTPAdaptor;
import com.yonyou.bq8.di.delegates.intf.DIFsGITAdaptor;
import com.yonyou.bq8.di.delegates.intf.DIFsLocalAdaptor;
import com.yonyou.bq8.di.delegates.intf.DIFsSFTPAdaptor;
import com.yonyou.bq8.di.delegates.intf.DIFsSVNAdaptor;
import com.yonyou.bq8.di.delegates.vo.FilesysDirectory;

public enum DIFileSystemCategory {
	FILESYS_TYPE_LOCAL(1, "local", "服务器文件系统") {
		@Override
		public DIFsAdaptor getFsAdaptor() {
			DIFsLocalAdaptor adaptor = DIAdaptorFactory
					.createAdaptor(DIFsLocalAdaptor.class);
			return adaptor;
		}

		@Override
		public String getVfsPath(String workDir, FilesysDirectory fd) {
			String rootDir = fd.getPath();
			return BQFileUtils.dirAppend(rootDir, workDir);
		}
	},

	FILESYS_TYPE_FTP(2, "ftp", "FTP文件系统") {
		@Override
		public DIFsAdaptor getFsAdaptor() {
			DIFsFTPAdaptor adaptor = DIAdaptorFactory
					.createAdaptor(DIFsFTPAdaptor.class);
			return adaptor;
		}

		@Override
		public String getVfsPath(String workDir, FilesysDirectory fd) {
			// vfsPath = "ftp://" + host.getUsername() + ":"
			// + host.getPassword() + "@" + host.getIp() + ":"
			// + host.getPort() + workDir;
			return workDir;
		}
	},
	FILESYS_TYPE_SFTP(3, "sftp", "SFTP文件系统") {
		@Override
		public DIFsAdaptor getFsAdaptor() {
			DIFsSFTPAdaptor adaptor = DIAdaptorFactory
					.createAdaptor(DIFsSFTPAdaptor.class);
			return adaptor;
		}

		@Override
		public String getVfsPath(String workDir, FilesysDirectory fd) {
//			vfsPath = "sftp://" + host.getUsername() + ":" + host.getPassword()
//					+ "@" + host.getIp() + ":" + host.getPort() + workDir;
			return workDir;
		}
	},
	FILESYS_TYPE_SVN(4, "svn", "SVN服务器") {
		@Override
		public DIFsAdaptor getFsAdaptor() {
			DIFsSVNAdaptor adaptor = DIAdaptorFactory
					.createAdaptor(DIFsSVNAdaptor.class);
			return adaptor;
		}

		@Override
		public String getVfsPath(String workDir, FilesysDirectory fd) {
			// TODO Auto-generated method stub
			return workDir;
		}
	},
	FILESYS_TYPE_GIT(5, "git", "GIT服务器") {
		@Override
		public DIFsAdaptor getFsAdaptor() {
			DIFsGITAdaptor adaptor = DIAdaptorFactory
					.createAdaptor(DIFsGITAdaptor.class);
			return adaptor;
		}

		@Override
		public String getVfsPath(String workDir, FilesysDirectory fd) {
			// TODO Auto-generated method stub
			return workDir;
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

	public abstract DIFsAdaptor getFsAdaptor();

	public abstract String getVfsPath(String workDir, FilesysDirectory fd);

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
