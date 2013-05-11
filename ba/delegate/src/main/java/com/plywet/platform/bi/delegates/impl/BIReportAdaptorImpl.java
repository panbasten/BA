package com.plywet.platform.bi.delegates.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryBase;

import com.plywet.platform.bi.delegates.anno.BIDelegate;
import com.plywet.platform.bi.delegates.exceptions.BIKettleException;
import com.plywet.platform.bi.delegates.intf.BIReportAdaptor;
import com.plywet.platform.bi.delegates.model.BIAbstractDbAdaptor;

@BIDelegate(type = "db")
public class BIReportAdaptorImpl extends BIAbstractDbAdaptor implements
		BIReportAdaptor {

	private final Logger logger = Logger.getLogger(BIReportAdaptorImpl.class);

	@Override
	public List<Object[]> getSubDirectoryObjects(String dirId)
			throws BIKettleException {
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
			throw new BIKettleException(e);
		}
	}
	
	
	

}
