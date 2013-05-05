package com.plywet.platform.bi.delegates.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryBase;

import com.plywet.platform.bi.core.model.NameValuePair;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.delegates.anno.BIDelegate;
import com.plywet.platform.bi.delegates.intf.BIFunctionTypeAdaptor;
import com.plywet.platform.bi.delegates.model.BIAbstractDbAdaptor;
import com.plywet.platform.bi.delegates.vo.FunctionType;
import com.plywet.platform.bi.exceptions.BIKettleException;

@BIDelegate(type = "db")
public class BIFunctionTypeDbAdaptorImpl extends BIAbstractDbAdaptor implements
		BIFunctionTypeAdaptor {
	private final Logger logger = Logger
			.getLogger(BIFunctionTypeDbAdaptorImpl.class);

	/*
	 * @Override public List<Object[]> getFunctionLevelOne() throws
	 * DIKettleException { try { String sql = "SELECT " +
	 * quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE) + "," +
	 * quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_CODE) + "," +
	 * quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_DESCRIPTION) +
	 * " FROM " + quoteTable(KettleDatabaseRepositoryBase.TABLE_R_FUNC_TYPE) +
	 * " WHERE " +
	 * quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE_PARENT) +
	 * " = 0" + " ORDER BY " +
	 * quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_TYPE_INDEX); return
	 * getRows(sql); } catch (KettleException e) { throw new
	 * DIKettleException(e); } }
	 */

	@Override
	public List<FunctionType> getFunctionByParent(long id)
			throws BIKettleException {
		try {
			String sql = "SELECT "
					+ KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_CODE
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_DESCRIPTION
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE_PARENT
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_HELPTEXT
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_TYPE_INDEX
					+ " FROM "
					+ KettleDatabaseRepositoryBase.TABLE_R_FUNC_TYPE
					+ " WHERE "
					+ KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE_PARENT
					+ " = " + id + " ORDER BY "
					+ KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_TYPE_INDEX;
			List<RowMetaAndData> rmds = getRowsWithMeta(sql);
			if (Utils.isEmpty(rmds)) {
				return Collections.emptyList();
			}

			List<FunctionType> fts = new ArrayList<FunctionType>();
			for (RowMetaAndData rmd : rmds) {
				FunctionType ft = new FunctionType();
				ft
						.setId(rmd
								.getInteger(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE));
				ft
						.setCode(rmd
								.getString(
										KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_CODE,
										null));
				ft
						.setDesc(rmd
								.getString(
										KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_DESCRIPTION,
										null));
				ft
						.setParentId(rmd
								.getInteger(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE_PARENT));
				ft.setHelpText(rmd.getString(
						KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_HELPTEXT,
						null));
				ft
						.setIndex(Integer
								.parseInt(rmd
										.getString(
												KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_TYPE_INDEX,
												"0")));

				String extSql = "SELECT "
						+ KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_EXTATTR_FID
						+ ","
						+ KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_EXTATTR_ATTR_NAME
						+ ","
						+ KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_EXTATTR_ATTR_VALUE
						+ " FROM "
						+ KettleDatabaseRepositoryBase.TABLE_R_FUNC_TYPE_EXTATTR
						+ " WHERE "
						+ KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_EXTATTR_FID
						+ " = " + ft.getId();
				List<RowMetaAndData> extRmds = getRowsWithMeta(extSql);
				if (!Utils.isEmpty(extRmds)) {
					for (RowMetaAndData extRmd : extRmds) {
						NameValuePair pair = new NameValuePair();
						pair
								.setName(extRmd
										.getString(
												KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_EXTATTR_ATTR_NAME,
												null));
						pair
								.setValue(extRmd
										.getString(
												KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_EXTATTR_ATTR_VALUE,
												null));
						ft.addExtAttr(pair);
					}
				}
				fts.add(ft);
			}
			return fts;
		} catch (KettleException e) {
			logger.error("get function type exception:", e);
			throw new BIKettleException(e);
		}
	}
}
