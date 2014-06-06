package com.flywet.platform.bi.delegates.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.RowMetaAndData;

import com.flywet.platform.bi.component.vo.NameValuePair;
import com.flywet.platform.bi.core.db.BIDatabaseRepositoryBase;
import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.anno.BIDelegate;
import com.flywet.platform.bi.delegates.enums.BIMetroCategory;
import com.flywet.platform.bi.delegates.intf.BIMetroAdaptor;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;
import com.flywet.platform.bi.delegates.vo.MetroItem;

@BIDelegate(type = "db")
public class BIMetroAdaptorImpl extends BIAbstractDbAdaptor implements
		BIMetroAdaptor {

	private final Logger logger = Logger.getLogger(BIMetroAdaptorImpl.class);

	@Override
	public List<MetroItem> getMetroItems() throws BIKettleException {
		try {

			String sql = "SELECT "
					+ quote(BIDatabaseRepositoryBase.FIELD_METRO_ID_METRO)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_METRO_METRO_OBJECT)
					+ ","
					+ quote(BIDatabaseRepositoryBase.FIELD_METRO_METRO_TYPE)
					+ " FROM "
					+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_METRO)
					+ " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_METRO_VALID)
					+ " = 'Y'";

			List<RowMetaAndData> items = getRowsWithMeta(sql);

			if (!Utils.isEmpty(items)) {

				List<MetroItem> metros = new ArrayList<MetroItem>();

				for (RowMetaAndData item : items) {
					MetroItem mItem = new MetroItem();
					mItem.setId(item
							.getInteger(BIDatabaseRepositoryBase.FIELD_METRO_ID_METRO));
					mItem.setData(item.getString(
							BIDatabaseRepositoryBase.FIELD_METRO_METRO_OBJECT,
							""));
					mItem.setType(BIMetroCategory
							.getCategoryById(item
									.getInteger(
											BIDatabaseRepositoryBase.FIELD_METRO_METRO_TYPE)
									.intValue()));

					String extSql = "SELECT "
							+ quote(BIDatabaseRepositoryBase.FIELD_METRO_ATTRIBUTE_CODE)
							+ ","
							+ quote(BIDatabaseRepositoryBase.FIELD_METRO_ATTRIBUTE_VALUE_STR)
							+ " FROM "
							+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_METRO_ATTRIBUTE)
							+ " WHERE "
							+ quote(BIDatabaseRepositoryBase.FIELD_METRO_ATTRIBUTE_ID_METRO)
							+ " = " + mItem.getId();
					List<RowMetaAndData> extRmds = getRowsWithMeta(extSql);
					if (!Utils.isEmpty(extRmds)) {
						for (RowMetaAndData extRmd : extRmds) {
							NameValuePair pair = new NameValuePair();
							String code = extRmd
									.getString(
											BIDatabaseRepositoryBase.FIELD_METRO_ATTRIBUTE_CODE,
											null);
							String val = extRmd
									.getString(
											BIDatabaseRepositoryBase.FIELD_METRO_ATTRIBUTE_VALUE_STR,
											null);
							pair.setName(code);
							pair.setValue(val);
							mItem.addExtAttr(pair);
						}
					}

					metros.add(mItem);
				}

				return metros;
			}

			return null;

		} catch (Exception e) {
			logger.error("get metro item exception:", e);
			throw new BIKettleException(e);
		}
	}

}
