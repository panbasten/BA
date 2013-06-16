package com.plywet.platform.bi.delegates.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryBase;

import com.plywet.platform.bi.core.model.NameValuePair;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.delegates.anno.BIDelegate;
import com.plywet.platform.bi.delegates.exceptions.BIKettleException;
import com.plywet.platform.bi.delegates.intf.BIFunctionTypeAdaptor;
import com.plywet.platform.bi.delegates.model.BIAbstractDbAdaptor;
import com.plywet.platform.bi.delegates.vo.FunctionType;

@BIDelegate(type = "db")
public class BIFunctionTypeDbAdaptorImpl extends BIAbstractDbAdaptor implements
		BIFunctionTypeAdaptor {
	private final Logger logger = Logger
			.getLogger(BIFunctionTypeDbAdaptorImpl.class);

	@Override
	public List<FunctionType> getFunctionByParent(long id)
			throws BIKettleException {
		try {
			String sql = "SELECT "
					+ quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE)
					+ ","
					+ quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_CODE)
					+ ","
					+ quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_MODULE_CODE)
					+ ","
					+ quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_DESCRIPTION)
					+ ","
					+ quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE_PARENT)
					+ ","
					+ quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_HELPTEXT)
					+ ","
					+ quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_TYPE_INDEX)
					+ " FROM "
					+ quoteTable(KettleDatabaseRepositoryBase.TABLE_R_FUNC_TYPE)
					+ " WHERE "
					+ quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE_PARENT)
					+ " = " + id + " ORDER BY "
					+ quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_TYPE_INDEX);
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
						.setModuleCode(rmd
								.getString(
										KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_MODULE_CODE,
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
						+ quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ATTRIBUTE_CODE)
						+ ","
						+ quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ATTRIBUTE_VALUE_STR)
						+ " FROM "
						+ quoteTable(KettleDatabaseRepositoryBase.TABLE_R_FUNC_TYPE_ATTRIBUTE)
						+ " WHERE "
						+ quote(KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ATTRIBUTE_ID_FUNC_TYPE)
						+ " = " + ft.getId();
				List<RowMetaAndData> extRmds = getRowsWithMeta(extSql);

				if (!Utils.isEmpty(extRmds)) {
					for (RowMetaAndData extRmd : extRmds) {
						NameValuePair pair = new NameValuePair();
						pair
								.setName(extRmd
										.getString(
												KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ATTRIBUTE_CODE,
												null));
						pair
								.setValue(extRmd
										.getString(
												KettleDatabaseRepositoryBase.FIELD_FUNC_TYPE_ATTRIBUTE_VALUE_STR,
												null));
						ft.addExtAttr(pair);
					}
				}

				// 添加权限
				ft.setAuth();

				fts.add(ft);
			}
			return fts;
		} catch (Exception e) {
			logger.error("get function type exception:", e);
			throw new BIKettleException(e);
		}
	}
}
