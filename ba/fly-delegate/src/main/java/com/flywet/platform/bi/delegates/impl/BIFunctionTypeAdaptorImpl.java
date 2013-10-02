package com.flywet.platform.bi.delegates.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.RowMetaAndData;

import com.flywet.platform.bi.core.db.BIDatabaseRepositoryBase;
import com.flywet.platform.bi.core.model.NameValuePair;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.anno.BIDelegate;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.intf.BIFunctionTypeAdaptor;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;
import com.flywet.platform.bi.delegates.vo.FunctionType;

@BIDelegate(type = "db")
public class BIFunctionTypeAdaptorImpl extends BIAbstractDbAdaptor implements
		BIFunctionTypeAdaptor {
	private final Logger logger = Logger
			.getLogger(BIFunctionTypeAdaptorImpl.class);

	@Override
	public List<FunctionType> getFunctionByParent(long id)
			throws BIKettleException {
		try {
			String sql = "SELECT "
					+ quote(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_CODE)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_MODULE_CODE)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_DESCRIPTION)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE_PARENT)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_HELPTEXT)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_TYPE_INDEX)
					+ " FROM "
					+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_FUNC_TYPE)
					+ " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE_PARENT)
					+ " = "
					+ id
					+ " ORDER BY "
					+ quote(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_TYPE_INDEX);
			List<RowMetaAndData> rmds = getRowsWithMeta(sql);
			if (Utils.isEmpty(rmds)) {
				return Collections.emptyList();
			}

			List<FunctionType> fts = new ArrayList<FunctionType>();
			for (RowMetaAndData rmd : rmds) {
				FunctionType ft = new FunctionType();
				ft
						.setId(rmd
								.getInteger(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE));
				ft.setCode(rmd.getString(
						BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_CODE, null));
				ft.setModuleCode(rmd.getString(
						BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_MODULE_CODE,
						null));
				ft.setDesc(rmd.getString(
						BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_DESCRIPTION,
						null));
				ft
						.setParentId(rmd
								.getInteger(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_ID_FUNC_TYPE_PARENT));
				ft
						.setHelpText(rmd
								.getString(
										BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_HELPTEXT,
										null));
				ft.setIndex(Integer.parseInt(rmd.getString(
						BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_TYPE_INDEX,
						"0")));

				String extSql = "SELECT "
						+ quote(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_ATTRIBUTE_CODE)
						+ ","
						+ quote(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_ATTRIBUTE_VALUE_STR)
						+ " FROM "
						+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_FUNC_TYPE_ATTRIBUTE)
						+ " WHERE "
						+ quote(BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_ATTRIBUTE_ID_FUNC_TYPE)
						+ " = " + ft.getId();
				List<RowMetaAndData> extRmds = getRowsWithMeta(extSql);

				if (!Utils.isEmpty(extRmds)) {
					for (RowMetaAndData extRmd : extRmds) {
						NameValuePair pair = new NameValuePair();
						pair
								.setName(extRmd
										.getString(
												BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_ATTRIBUTE_CODE,
												null));
						pair
								.setValue(extRmd
										.getString(
												BIDatabaseRepositoryBase.FIELD_FUNC_TYPE_ATTRIBUTE_VALUE_STR,
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
