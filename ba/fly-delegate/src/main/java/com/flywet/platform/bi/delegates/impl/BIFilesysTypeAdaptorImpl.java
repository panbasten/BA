package com.flywet.platform.bi.delegates.impl;

import java.util.List;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryBase;

import com.flywet.platform.bi.delegates.anno.BIDelegate;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.intf.BIFilesysTypeAdaptor;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;

@BIDelegate(type = "db")
public class BIFilesysTypeAdaptorImpl extends BIAbstractDbAdaptor implements
		BIFilesysTypeAdaptor {

	@Override
	public List<Object[]> getAllFilesysType() throws BIKettleException {
		try {
			String sql = "SELECT "
					+ quote(KettleDatabaseRepositoryBase.FIELD_FILESYS_TYPE_ID_FS_TYPE)
					+ ","
					+ quote(KettleDatabaseRepositoryBase.FIELD_FILESYS_TYPE_CODE)
					+ ","
					+ quote(KettleDatabaseRepositoryBase.FIELD_FILESYS_TYPE_DESCRIPTION)
					+ " FROM "
					+ quoteTable(KettleDatabaseRepositoryBase.TABLE_R_FILESYS_TYPE);
			return getRows(sql);
		} catch (KettleException e) {
			throw new BIKettleException(e);
		}
	}

}
