package com.plywet.platform.bi.delegates.utils;

import com.plywet.platform.bi.core.utils.FileUtils;
import com.plywet.platform.bi.delegates.intf.BIFsAdaptor;
import com.plywet.platform.bi.delegates.intf.BIFsFTPAdaptor;
import com.plywet.platform.bi.delegates.intf.BIFsGITAdaptor;
import com.plywet.platform.bi.delegates.intf.BIFsLocalAdaptor;
import com.plywet.platform.bi.delegates.intf.BIFsSFTPAdaptor;
import com.plywet.platform.bi.delegates.intf.BIFsSVNAdaptor;
import com.plywet.platform.bi.delegates.vo.FilesysDirectory;

public enum BIFileSystemCategory {
	FILESYS_TYPE_LOCAL(1, "local", "服务器文件系统") {
		@Override
		public BIFsAdaptor getFsAdaptor() {
			BIFsLocalAdaptor adaptor = BIAdaptorFactory
					.createAdaptor(BIFsLocalAdaptor.class);
			return adaptor;
		}

		@Override
		public String getVfsPath(String workDir, FilesysDirectory fd) {
			String rootDir = fd.getPath();
			return FileUtils.dirAppend(rootDir, workDir);
		}
	},

	FILESYS_TYPE_FTP(2, "ftp", "FTP文件系统") {
		@Override
		public BIFsAdaptor getFsAdaptor() {
			BIFsFTPAdaptor adaptor = BIAdaptorFactory
					.createAdaptor(BIFsFTPAdaptor.class);
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
		public BIFsAdaptor getFsAdaptor() {
			BIFsSFTPAdaptor adaptor = BIAdaptorFactory
					.createAdaptor(BIFsSFTPAdaptor.class);
			return adaptor;
		}

		@Override
		public String getVfsPath(String workDir, FilesysDirectory fd) {
			// vfsPath = "sftp://" + host.getUsername() + ":" +
			// host.getPassword()
			// + "@" + host.getIp() + ":" + host.getPort() + workDir;
			return workDir;
		}
	},
	FILESYS_TYPE_SVN(4, "svn", "SVN服务器") {
		@Override
		public BIFsAdaptor getFsAdaptor() {
			BIFsSVNAdaptor adaptor = BIAdaptorFactory
					.createAdaptor(BIFsSVNAdaptor.class);
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
		public BIFsAdaptor getFsAdaptor() {
			BIFsGITAdaptor adaptor = BIAdaptorFactory
					.createAdaptor(BIFsGITAdaptor.class);
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

	private BIFileSystemCategory(int id, String category, String desc) {
		this.id = id;
		this.category = category;
		this.desc = desc;
	}

	public abstract BIFsAdaptor getFsAdaptor();

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

	public static BIFileSystemCategory getCategoryById(int id) {
		for (BIFileSystemCategory fsc : values()) {
			if (fsc.getId() == id) {
				return fsc;
			}
		}
		return null;
	}

	public static BIFileSystemCategory getCategoryByCode(String category) {
		for (BIFileSystemCategory fsc : values()) {
			if (fsc.getCategory().equals(category)) {
				return fsc;
			}
		}
		return null;
	}

	public static String getDescByCategory(String category) {
		for (BIFileSystemCategory fsc : values()) {
			if (fsc.getCategory().equals(category)) {
				return fsc.getDesc();
			}
		}
		return null;
	}
}
