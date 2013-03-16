package com.plywet.platform.bi.delegates.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.plywet.platform.bi.delegates.anno.BIDelegate;
import com.plywet.platform.bi.delegates.intf.BIFsGITAdaptor;
import com.plywet.platform.bi.delegates.utils.BIFileSystemCategory;
import com.plywet.platform.bi.delegates.vo.FilesysDirectory;
import com.plywet.platform.bi.exceptions.BIKettleException;

@BIDelegate(type = "db")
public class BIFsGITAdaptorImpl extends BIFsLocalAdaptorImpl implements
		BIFsGITAdaptor {
	private final Logger logger = Logger.getLogger(BIFsGITAdaptorImpl.class);

	@Override
	public List<FilesysDirectory> getRootDirectories() throws BIKettleException {
		return getRootDirectoriesByType(BIFileSystemCategory.FILESYS_TYPE_GIT
				.getId());
	}

}
