package com.flywet.platform.bi.delegates.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;

import com.flywet.platform.bi.core.db.BIDatabaseRepositoryBase;
import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.delegates.anno.BIDelegate;
import com.flywet.platform.bi.delegates.intf.BIDomainAdaptor;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;

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
					+ quote(BIDatabaseRepositoryBase.FIELD_DOMAIN_ID_DOMAIN)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_DOMAIN_DOMAIN_TYPE)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_DOMAIN_DESCRIPTION)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_DOMAIN_DOMAIN_STATUS)
					+ " FROM "
					+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_DOMAIN)
					+ " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_DOMAIN_ID_DOMAIN_DIRECTORY)
					+ " = " + dirId;
			return getRows(sql);
		} catch (KettleException e) {
			logger.error("获得目录下面的模型出现错误。");
			throw new BIKettleException(e);
		}
	}

}
