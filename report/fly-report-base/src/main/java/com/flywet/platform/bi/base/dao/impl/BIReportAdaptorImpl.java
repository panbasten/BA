package com.flywet.platform.bi.base.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;

import com.flywet.platform.bi.base.dao.intf.BIReportAdaptor;
import com.flywet.platform.bi.core.db.BIDatabaseRepositoryBase;
import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.delegates.anno.BIDelegate;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;

@BIDelegate(type = "db")
public class BIReportAdaptorImpl extends BIAbstractDbAdaptor implements
		BIReportAdaptor {

	private final Logger logger = Logger.getLogger(BIReportAdaptorImpl.class);

	@Override
	public List<Object[]> getSubDirectoryObjects(String dirId)
			throws BIKettleException {
		try {
			String sql = "SELECT "
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_ID_REPORT)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_REPORT_TYPE)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_IS_REF)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_DESCRIPTION)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_REPORT_STATUS)
					+ " FROM "
					+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_REPORT)
					+ " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_ID_REPORT_DIRECTORY)
					+ " = " + dirId;
			return getRows(sql);
		} catch (KettleException e) {
			logger.error("获得目录下面的报表出现错误。");
			throw new BIKettleException(e);
		}
	}

	@Override
	public Object[] getReportObject(String id) throws BIKettleException {
		try {
			String sql = "SELECT "
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_ID_REPORT)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_ID_REPORT_DIRECTORY)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_REPORT_OBJECT)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_REPORT_TYPE)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_IS_REF)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_ID_REF_REPORT)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_DESCRIPTION)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_REPORT_STATUS)
					+ " FROM "
					+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_REPORT)
					+ " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_ID_REPORT)
					+ " = " + id;
			return getOneRow(sql);
		} catch (KettleException e) {
			logger.error("获得报表对象内容出现错误。");
			throw new BIKettleException(e);
		}
	}

	public void updateReportObject(Long id, String xml)
			throws BIKettleException {
		try {
			String sql = "UPDATE "
					+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_REPORT)
					+ " SET "
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_REPORT_OBJECT)
					+ " = '?'" + " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_REPORT_ID_REPORT)
					+ " = ?";

			RowMetaInterface rowMeta = new RowMeta();
			ValueMetaInterface reportObjectVm = new ValueMeta(
					BIDatabaseRepositoryBase.FIELD_REPORT_REPORT_OBJECT,
					ValueMetaInterface.TYPE_STRING,
					ValueMetaInterface.STORAGE_TYPE_BINARY_STRING);
			rowMeta.addValueMeta(reportObjectVm);
			ValueMetaInterface reportIdVm = new ValueMeta(
					BIDatabaseRepositoryBase.FIELD_REPORT_ID_REPORT,
					ValueMetaInterface.TYPE_INTEGER);
			rowMeta.addValueMeta(reportIdVm);

			execSql(sql, rowMeta, new Object[] { xml, id });
		} catch (KettleException e) {
			throw new BIKettleException(e);
		}
	}

}
