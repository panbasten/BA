package com.flywet.platform.bi.delegates.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleValueException;

import com.flywet.platform.bi.core.db.BIDatabaseRepositoryBase;
import com.flywet.platform.bi.core.model.NameValuePair;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.anno.BIDelegate;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.intf.BIPortalMenuAdaptor;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;
import com.flywet.platform.bi.delegates.vo.PortalMenu;

@BIDelegate(type = "db")
public class BIPortalMenuAdaptorImpl extends BIAbstractDbAdaptor implements
		BIPortalMenuAdaptor {
	private final Logger logger = Logger
			.getLogger(BIPortalMenuAdaptorImpl.class);

	private String getSQL() {
		return "SELECT "
				+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_ID_PORTAL_MENU)
				+ ","
				+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_CODE)
				+ ","
				+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_MODULE_CODE)
				+ ","
				+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_DESCRIPTION)
				+ ","
				+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_ID_PORTAL_MENU_PARENT)
				+ ","
				+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_HELPTEXT)
				+ ","
				+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_MENU_INDEX)
				+ ","
				+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_AUTHENTICATE)
				+ " FROM "
				+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_PORTAL_MENU);
	}

	private PortalMenu createPortalMenu(RowMetaAndData rmd)
			throws KettleValueException, KettleDatabaseException {
		PortalMenu ft = new PortalMenu();
		ft
				.setId(rmd
						.getInteger(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_ID_PORTAL_MENU));
		ft.setCode(rmd.getString(
				BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_CODE, null));
		ft.setModuleCode(rmd.getString(
				BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_MODULE_CODE, null));
		ft.setDesc(rmd.getString(
				BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_DESCRIPTION, null));
		ft
				.setParentId(rmd
						.getInteger(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_ID_PORTAL_MENU_PARENT));
		ft.setHelpText(rmd.getString(
				BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_HELPTEXT, null));
		ft.setIndex(Integer.parseInt(rmd.getString(
				BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_MENU_INDEX, "0")));
		ft.setAuthenticate("Y".equalsIgnoreCase(rmd.getString(
				BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_AUTHENTICATE, "Y")));

		String extSql = "SELECT "
				+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_ATTRIBUTE_CODE)
				+ ","
				+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_ATTRIBUTE_VALUE_STR)
				+ " FROM "
				+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_PORTAL_MENU_ATTRIBUTE)
				+ " WHERE "
				+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_ATTRIBUTE_ID_PORTAL_MENU)
				+ " = " + ft.getId();
		List<RowMetaAndData> extRmds = getRowsWithMeta(extSql);

		if (!Utils.isEmpty(extRmds)) {
			for (RowMetaAndData extRmd : extRmds) {
				NameValuePair pair = new NameValuePair();
				pair
						.setName(extRmd
								.getString(
										BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_ATTRIBUTE_CODE,
										null));
				pair
						.setValue(extRmd
								.getString(
										BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_ATTRIBUTE_VALUE_STR,
										null));
				ft.addExtAttr(pair);
			}
		}
		return ft;
	}

	@Override
	public PortalMenu getPortalMenuById(long id) throws BIKettleException {
		try {
			String sql = getSQL()
					+ " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_ID_PORTAL_MENU)
					+ " = " + id;
			RowMetaAndData rmd = getOneRowWithMeta(sql);
			if (rmd == null) {
				return null;
			}
			return createPortalMenu(rmd);
		} catch (Exception e) {
			logger.error("get portal menu exception:", e);
			throw new BIKettleException(e);
		}
	}

	@Override
	public List<PortalMenu> getPortalMenuByParent(long id)
			throws BIKettleException {
		try {
			String sql = getSQL()
					+ " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_ID_PORTAL_MENU_PARENT)
					+ " = "
					+ id
					+ " ORDER BY "
					+ quote(BIDatabaseRepositoryBase.FIELD_PORTAL_MENU_MENU_INDEX);
			List<RowMetaAndData> rmds = getRowsWithMeta(sql);
			if (Utils.isEmpty(rmds)) {
				return Collections.emptyList();
			}

			List<PortalMenu> fts = new ArrayList<PortalMenu>();
			for (RowMetaAndData rmd : rmds) {
				fts.add(createPortalMenu(rmd));
			}
			return fts;
		} catch (Exception e) {
			logger.error("get portal menu exception:", e);
			throw new BIKettleException(e);
		}
	}
}
