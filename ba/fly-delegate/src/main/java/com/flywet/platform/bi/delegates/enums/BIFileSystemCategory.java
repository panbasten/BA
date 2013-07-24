package com.flywet.platform.bi.delegates.enums;

import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.core.utils.FileUtils;
import com.flywet.platform.bi.delegates.intf.BIFsAdaptor;
import com.flywet.platform.bi.delegates.intf.BIFsFTPAdaptor;
import com.flywet.platform.bi.delegates.intf.BIFsGITAdaptor;
import com.flywet.platform.bi.delegates.intf.BIFsLocalAdaptor;
import com.flywet.platform.bi.delegates.intf.BIFsSFTPAdaptor;
import com.flywet.platform.bi.delegates.intf.BIFsSVNAdaptor;
import com.flywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.flywet.platform.bi.delegates.vo.FilesysDirectory;

public enum BIFileSystemCategory {
	FILESYS_TYPE_LOCAL(1, "local", "FileSystem.Category.Local.Description") {
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

	FILESYS_TYPE_FTP(2, "ftp", "FileSystem.Category.FTP.Description") {
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
	FILESYS_TYPE_SFTP(3, "sftp", "FileSystem.Category.SFTP.Description") {
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
	FILESYS_TYPE_SVN(4, "svn", "FileSystem.Category.SVN.Description") {
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
	FILESYS_TYPE_GIT(5, "git", "FileSystem.Category.GIT.Description") {
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

	private static Class<?> PKG = BIFileSystemCategory.class;

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
		return BaseMessages.getString(PKG, desc);
	}

	public int getId() {
		return id;
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
