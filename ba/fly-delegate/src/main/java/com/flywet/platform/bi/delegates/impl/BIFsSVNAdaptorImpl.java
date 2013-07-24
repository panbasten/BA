package com.flywet.platform.bi.delegates.impl;

import java.util.List;

import com.flywet.platform.bi.delegates.anno.BIDelegate;
import com.flywet.platform.bi.delegates.enums.BIFileSystemCategory;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.intf.BIFsSVNAdaptor;
import com.flywet.platform.bi.delegates.vo.FilesysDirectory;

@BIDelegate(type = "db")
public class BIFsSVNAdaptorImpl extends BIAbstractFsAdaptor implements
		BIFsSVNAdaptor {

	@Override
	public List<FilesysDirectory> getRootDirectories() throws BIKettleException {
		return getRootDirectoriesByType(BIFileSystemCategory.FILESYS_TYPE_SVN
				.getId());
	}

}
