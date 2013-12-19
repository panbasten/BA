package com.flywet.platform.bi.smart.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;

import com.flywet.platform.bi.core.db.BIDatabaseRepositoryBase;
import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.delegates.anno.BIDelegate;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;
import com.flywet.platform.bi.smart.dao.intf.BISmartAdaptor;

@BIDelegate(type = "db")
public class BISmartAdaptorImpl extends BIAbstractDbAdaptor implements
		BISmartAdaptor {
	private final Logger logger = Logger.getLogger(BISmartAdaptorImpl.class);

	@Override
	public Object[] getSmartObject(String id) throws BIKettleException {
		try {
			String sql = "SELECT "
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_ID_SMART)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_ID_SMART_DIRECTORY)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_ID_DATABASE)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_SMART_OBJECT)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_SMART_TYPE)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_DESCRIPTION)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_SMART_VERSION)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_SMART_STATUS)
					+ " FROM "
					+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_SMART)
					+ " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_ID_SMART)
					+ " = " + id;
			return getOneRow(sql);
		} catch (KettleException e) {
			logger.error("获得模型对象内容出现错误。");
			throw new BIKettleException(e);
		}
	}

	@Override
	public List<Object[]> getSubDirectoryObjects(String dirId)
			throws BIKettleException {
		try {
			String sql = "SELECT "
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_ID_SMART)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_SMART_TYPE)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_DESCRIPTION)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_SMART_STATUS)
					+ " FROM "
					+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_SMART)
					+ " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_SMART_ID_SMART_DIRECTORY)
					+ " = " + dirId;
			return getRows(sql);
		} catch (KettleException e) {
			logger.error("获得目录下面的模型出现错误。");
			throw new BIKettleException(e);
		}
	}

}
