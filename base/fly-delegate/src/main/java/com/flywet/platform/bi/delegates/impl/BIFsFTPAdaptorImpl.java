package com.flywet.platform.bi.delegates.impl;

import java.util.List;

import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.delegates.anno.BIDelegate;
import com.flywet.platform.bi.delegates.enums.BIFileSystemCategory;
import com.flywet.platform.bi.delegates.intf.BIFsFTPAdaptor;
import com.flywet.platform.bi.delegates.vo.FilesysDirectory;

@BIDelegate(type = "db")
public class BIFsFTPAdaptorImpl extends BIAbstractFsAdaptor implements
		BIFsFTPAdaptor {

	@Override
	public List<FilesysDirectory> getRootDirectories() throws BIKettleException {
		return getRootDirectoriesByType(BIFileSystemCategory.FILESYS_TYPE_FTP
				.getId());
	}

}
