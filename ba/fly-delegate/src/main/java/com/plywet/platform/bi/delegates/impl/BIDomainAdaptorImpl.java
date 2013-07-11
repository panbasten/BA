package com.plywet.platform.bi.delegates.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryBase;

import com.plywet.platform.bi.delegates.anno.BIDelegate;
import com.plywet.platform.bi.delegates.exceptions.BIKettleException;
import com.plywet.platform.bi.delegates.intf.BIDomainAdaptor;
import com.plywet.platform.bi.delegates.model.BIAbstractDbAdaptor;

@BIDelegate(type = "db")
public class BIDomainAdaptorImpl extends BIAbstractDbAdaptor implements
		BIDomainAdaptor {
	private final Logger logger = Logger.getLogger(BIDomainAdaptorImpl.class);

	@Override
	public Object[] getDomainObject(String id) throws BIKettleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> getSubDirectoryObjects(String dirId)
			throws BIKettleException {
		try {
			String sql = "SELECT "
					+ quote(KettleDatabaseRepositoryBase.FIELD_DOMAIN_ID_DOMAIN)
					+ ","
					+ quote(KettleDatabaseRepositoryBase.FIELD_DOMAIN_DOMAIN_TYPE)
					+ ","
					+ quote(KettleDatabaseRepositoryBase.FIELD_DOMAIN_DESCRIPTION)
					+ ","
					+ quote(KettleDatabaseRepositoryBase.FIELD_DOMAIN_DOMAIN_STATUS)
					+ " FROM "
					+ quoteTable(KettleDatabaseRepositoryBase.TABLE_R_DOMAIN)
					+ " WHERE "
					+ quote(KettleDatabaseRepositoryBase.FIELD_DOMAIN_ID_DOMAIN_DIRECTORY)
					+ " = " + dirId;
			return getRows(sql);
		} catch (KettleException e) {
			logger.error("获得目录下面的模型出现错误。");
			throw new BIKettleException(e);
		}
	}

}
