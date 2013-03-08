package com.yonyou.bq8.di.delegates.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryBase;

import com.yonyou.bq8.di.delegates.anno.DIDelegate;
import com.yonyou.bq8.di.delegates.intf.DIReportAdaptor;
import com.yonyou.bq8.di.delegates.model.AbstractDbAdaptor;
import com.yonyou.bq8.di.exceptions.DIKettleException;

@DIDelegate(type = "db")
public class DIReportAdaptorImpl extends AbstractDbAdaptor implements
		DIReportAdaptor {

	private final Logger logger = Logger.getLogger(DIReportAdaptorImpl.class);

	@Override
	public List<Object[]> getSubDirectoryObjects(String dirId)
			throws DIKettleException {
		try {
			String sql = "SELECT "
					+ KettleDatabaseRepositoryBase.FIELD_REPORT_ID_REPORT
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_REPORT_REPORT_TYPE
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_REPORT_IS_REF
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_REPORT_DESCRIPTION
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_REPORT_REPORT_STATUS
					+ " FROM "
					+ KettleDatabaseRepositoryBase.TABLE_R_REPORT
					+ " WHERE "
					+ KettleDatabaseRepositoryBase.FIELD_REPORT_ID_REPORT_DIRECTORY
					+ " = " + dirId;
			return getRows(sql);
		} catch (KettleException e) {
			logger.error("获得目录下面的报表出现错误。");
			throw new DIKettleException(e);
		}
	}

}
