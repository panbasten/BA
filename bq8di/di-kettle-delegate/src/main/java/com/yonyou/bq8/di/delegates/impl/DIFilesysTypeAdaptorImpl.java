package com.yonyou.bq8.di.delegates.impl;

import java.util.List;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryBase;

import com.yonyou.bq8.di.delegates.anno.DIDelegate;
import com.yonyou.bq8.di.delegates.intf.DIFilesysTypeAdaptor;
import com.yonyou.bq8.di.delegates.model.AbstractDbAdaptor;
import com.yonyou.bq8.di.exceptions.DIKettleException;

@DIDelegate(type="db")
public class DIFilesysTypeAdaptorImpl extends AbstractDbAdaptor implements DIFilesysTypeAdaptor {

	@Override
	public List<Object[]> getAllFilesysType() throws DIKettleException {
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
			throw new DIKettleException(e);
		}
	}

}
