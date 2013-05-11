package com.plywet.platform.bi.delegates.impl;

import java.util.List;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryBase;

import com.plywet.platform.bi.delegates.anno.BIDelegate;
import com.plywet.platform.bi.delegates.exceptions.BIKettleException;
import com.plywet.platform.bi.delegates.intf.BIFilesysTypeAdaptor;
import com.plywet.platform.bi.delegates.model.BIAbstractDbAdaptor;

@BIDelegate(type = "db")
public class BIFilesysTypeAdaptorImpl extends BIAbstractDbAdaptor implements
		BIFilesysTypeAdaptor {

	@Override
	public List<Object[]> getAllFilesysType() throws BIKettleException {
		try {
			String sql = "SELECT "
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_TYPE_ID_FS_TYPE
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_TYPE_CODE
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_TYPE_DESCRIPTION
					+ " FROM "
					+ KettleDatabaseRepositoryBase.TABLE_R_FILESYS_TYPE;
			return getRows(sql);
		} catch (KettleException e) {
			throw new BIKettleException(e);
		}
	}

}
