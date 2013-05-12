package com.plywet.platform.bi.delegates.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.plywet.platform.bi.delegates.anno.BIDelegate;
import com.plywet.platform.bi.delegates.enums.BIFileSystemCategory;
import com.plywet.platform.bi.delegates.exceptions.BIKettleException;
import com.plywet.platform.bi.delegates.intf.BIFsSFTPAdaptor;
import com.plywet.platform.bi.delegates.vo.FilesysDirectory;

@BIDelegate(type = "db")
public class BIFsSFTPAdaptorImpl extends BIFsLocalAdaptorImpl implements
		BIFsSFTPAdaptor {
	private final Logger logger = Logger.getLogger(BIFsSFTPAdaptorImpl.class);

	@Override
	public List<FilesysDirectory> getRootDirectories() throws BIKettleException {
		return getRootDirectoriesByType(BIFileSystemCategory.FILESYS_TYPE_SFTP
				.getId());
	}

}
