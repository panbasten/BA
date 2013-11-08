package com.flywet.platform.bi.delegates.impl;

import java.util.List;

import org.pentaho.di.core.exception.KettleException;

import com.flywet.platform.bi.core.db.BIDatabaseRepositoryBase;
import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.delegates.anno.BIDelegate;
import com.flywet.platform.bi.delegates.intf.BIFilesysTypeAdaptor;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;

@BIDelegate(type = "db")
public class BIFilesysTypeAdaptorImpl extends BIAbstractDbAdaptor implements
		BIFilesysTypeAdaptor {

	@Override
	public List<Object[]> getAllFilesysType() throws BIKettleException {
		try {
			String sql = "SELECT "
					+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_TYPE_ID_FS_TYPE)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_TYPE_CODE)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_TYPE_DESCRIPTION)
					+ " FROM "
					+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_FILESYS_TYPE);
			return getRows(sql);
		} catch (KettleException e) {
			throw new BIKettleException(e);
		}
	}

}
