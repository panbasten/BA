package com.flywet.platform.bi.delegates.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;

import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.delegates.anno.BIDelegate;
import com.flywet.platform.bi.delegates.intf.BIAuthorizationAdaptor;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;
import com.flywet.platform.bi.delegates.vo.Authorization;

@BIDelegate(type = "db")
public class BIAuthorizationAdaptorImpl extends BIAbstractDbAdaptor implements
		BIAuthorizationAdaptor {
	private final Logger logger = Logger
			.getLogger(BIAuthorizationAdaptor.class);

	@Override
	public void delRoleAuth(long rid) throws BIKettleException {
		try {
			String sql = "DELETE FROM "
					+ quoteTable(KettleDatabaseRepository.TABLE_R_AUTHORIZATION)
					+ " WHERE "
					+ quote(KettleDatabaseRepository.FIELD_AUTHORIZATION_RID)
					+ " = ? ";
			performDelete(sql, new LongObjectId(rid));
			getRepository().commit();
		} catch (KettleException e) {
			logger.error("del authorization exception:", e);
			getRepository().rollback();
			throw new BIKettleException(e.getMessage());
		}
	}

	@Override
	public List<Authorization> getAuthorization(long rid)
			throws BIKettleException {
		String sql = "SELECT "
				+ quote(KettleDatabaseRepository.FIELD_AUTHORIZATION_RID)
				+ ","
				+ quote(KettleDatabaseRepository.FIELD_AUTHORIZATION_OID)
				+ ","
				+ quote(KettleDatabaseRepository.FIELD_AUTHORIZATION_OTYPE)
				+ ","
				+ quote(KettleDatabaseRepository.FIELD_AUTHORIZATION_PERMISSION)
				+ " FROM "
				+ quoteTable(KettleDatabaseRepository.TABLE_R_AUTHORIZATION)
				+ " WHERE "
				+ quote(KettleDatabaseRepository.FIELD_AUTHORIZATION_RID)
				+ " = ?";

		try {
			List<RowMetaAndData> rmds = getRowsWithMeta(sql, rid);

			if (rmds == null || rmds.isEmpty()) {
				return Collections.emptyList();
			}

			List<Authorization> auths = new ArrayList<Authorization>();
			for (RowMetaAndData rmd : rmds) {
				Authorization auth = new Authorization();
				auth
						.setRid(rmd
								.getInteger(KettleDatabaseRepository.FIELD_AUTHORIZATION_RID));
				auth
						.setOid(rmd
								.getInteger(KettleDatabaseRepository.FIELD_AUTHORIZATION_OID));
				auth
						.setOtype(rmd
								.getInteger(KettleDatabaseRepository.FIELD_AUTHORIZATION_OTYPE));
				auth
						.setPermission(rmd
								.getInteger(KettleDatabaseRepository.FIELD_AUTHORIZATION_PERMISSION));
				auths.add(auth);
			}

			return auths;
		} catch (KettleException e) {
			logger.error("get authorization exception:", e);
			throw new BIKettleException(e.getMessage());
		}
	}

	@Override
	public void saveRoleAuth(long rid, List<Authorization> auths)
			throws BIKettleException {
		if (auths == null || auths.isEmpty()) {
			return;
		}

		try {
			String sql = "DELETE FROM "
					+ quoteTable(KettleDatabaseRepository.TABLE_R_AUTHORIZATION)
					+ " WHERE "
					+ quote(KettleDatabaseRepository.FIELD_AUTHORIZATION_RID)
					+ " = ?";
			performDelete(sql, new LongObjectId(rid));

			for (Authorization auth : auths) {
				RowMetaAndData rmd = new RowMetaAndData();
				rmd.addValue(KettleDatabaseRepository.FIELD_AUTHORIZATION_RID,
						ValueMetaInterface.TYPE_INTEGER, rid);
				rmd.addValue(KettleDatabaseRepository.FIELD_AUTHORIZATION_OID,
						ValueMetaInterface.TYPE_INTEGER, auth.getOid());
				rmd.addValue(
						KettleDatabaseRepository.FIELD_AUTHORIZATION_OTYPE,
						ValueMetaInterface.TYPE_INTEGER, auth.getOtypeLong());
				rmd.addValue(KettleDatabaseRepository.FIELD_AUTHORIZATION_RID,
						ValueMetaInterface.TYPE_INTEGER, auth.getPermission());
				insertTableRow(KettleDatabaseRepository.TABLE_R_AUTHORIZATION,
						rmd);
			}
			getRepository().commit();
		} catch (KettleException e) {
			logger.error("save authorization exception:", e);
			getRepository().rollback();
			throw new BIKettleException(e.getMessage());
		}
	}
}
