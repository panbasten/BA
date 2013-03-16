package com.plywet.platform.bi.delegates.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.plywet.platform.bi.delegates.anno.BIDelegate;
import com.plywet.platform.bi.delegates.intf.BIFsFTPAdaptor;
import com.plywet.platform.bi.delegates.utils.BIFileSystemCategory;
import com.plywet.platform.bi.delegates.vo.FilesysDirectory;
import com.plywet.platform.bi.exceptions.BIKettleException;

@BIDelegate(type = "db")
public class BIFsFTPAdaptorImpl extends BIFsLocalAdaptorImpl implements
		BIFsFTPAdaptor {
	private final Logger logger = Logger.getLogger(BIFsFTPAdaptorImpl.class);

	@Override
	public List<FilesysDirectory> getRootDirectories() throws BIKettleException {
		return getRootDirectoriesByType(BIFileSystemCategory.FILESYS_TYPE_FTP
				.getId());
	}

}
