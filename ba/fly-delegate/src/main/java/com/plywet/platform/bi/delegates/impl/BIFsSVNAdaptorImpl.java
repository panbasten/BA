package com.plywet.platform.bi.delegates.impl;

import java.util.List;

import com.plywet.platform.bi.delegates.anno.BIDelegate;
import com.plywet.platform.bi.delegates.enums.BIFileSystemCategory;
import com.plywet.platform.bi.delegates.exceptions.BIKettleException;
import com.plywet.platform.bi.delegates.intf.BIFsSVNAdaptor;
import com.plywet.platform.bi.delegates.vo.FilesysDirectory;

@BIDelegate(type = "db")
public class BIFsSVNAdaptorImpl extends BIAbstractFsAdaptor implements
		BIFsSVNAdaptor {

	@Override
	public List<FilesysDirectory> getRootDirectories() throws BIKettleException {
		return getRootDirectoriesByType(BIFileSystemCategory.FILESYS_TYPE_SVN
				.getId());
	}

}
