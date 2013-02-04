package com.yonyou.bq8.di.delegates.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;

import com.yonyou.bq8.di.delegates.anno.DIDelegate;
import com.yonyou.bq8.di.delegates.intf.DIAuthorizationAdaptor;
import com.yonyou.bq8.di.delegates.model.AbstractDbAdaptor;
import com.yonyou.bq8.di.delegates.vo.Authorization;
import com.yonyou.bq8.di.exceptions.DIKettleException;

@DIDelegate(type="db")
public class DIAuthorizationAdaptorImpl extends AbstractDbAdaptor implements
		DIAuthorizationAdaptor {
	private final Logger logger = Logger.getLogger(DIAuthorizationAdaptor.class);
	
	@Override
	public void delRoleAuth(long rid) throws DIKettleException {
		try {
			getRepository().connectionDelegate.performDelete("DELETE FROM " + this.quoteTable(KettleDatabaseRepository.TABLE_R_AUTHORIZATION) + 
				        " WHERE " + this.quote(KettleDatabaseRepository.FIELD_AUTHORIZATION_RID) + " = ? ", new LongObjectId(rid));
			getRepository().commit();
		} catch (KettleException e) {
			logger.error("del authorization exception:", e);
			getRepository().rollback();
			throw new DIKettleException(e.getMessage());
		}
	}

	@Override
	public List<Authorization> getAuthorization(long rid) throws DIKettleException {
		String sql = "SELECT " + KettleDatabaseRepository.FIELD_AUTHORIZATION_RID 
						+ "," + KettleDatabaseRepository.FIELD_AUTHORIZATION_FID
						+ "," + KettleDatabaseRepository.FIELD_AUTHORIZATION_PERMISSION
						+ " FROM " + KettleDatabaseRepository.TABLE_R_AUTHORIZATION
						+ " WHERE " + KettleDatabaseRepository.FIELD_AUTHORIZATION_RID 
						+ " = ?";
		sql = this.replaceParam(sql, String.valueOf(rid));
		
		try {
			List<RowMetaAndData> rmds = getRepository().connectionDelegate.getRowsWithMeta(sql);
		
			if (rmds == null || rmds.isEmpty()) {
				return Collections.emptyList();
			}
		
			List<Authorization> auths = new ArrayList<Authorization>();
			for (RowMetaAndData rmd : rmds) {
				Authorization auth = new Authorization();
				auth.setRid(rmd.getInteger(KettleDatabaseRepository.FIELD_AUTHORIZATION_RID));
				auth.setFid(rmd.getInteger(KettleDatabaseRepository.FIELD_AUTHORIZATION_FID));
				auth.setPermission(rmd.getInteger(KettleDatabaseRepository.FIELD_AUTHORIZATION_PERMISSION));
				auths.add(auth);
			}
			
			return auths;
		} catch (KettleException e) {
			logger.error("get authorization exception:", e);
			throw new DIKettleException(e.getMessage());
		}
	}

	@Override
	public void saveRoleAuth(long rid, List<Authorization> auths) throws DIKettleException {
		if (auths == null || auths.isEmpty()) {
			return;
		}
		
		try {
			getRepository().connectionDelegate.performDelete("DELETE FROM " + KettleDatabaseRepository.TABLE_R_AUTHORIZATION 
							+ " WHERE " + KettleDatabaseRepository.FIELD_AUTHORIZATION_RID 
							+ " = ?", new LongObjectId(rid));
			
			for (Authorization auth : auths) {
				RowMetaAndData rmd = new RowMetaAndData();
				rmd.addValue(KettleDatabaseRepository.FIELD_AUTHORIZATION_RID, ValueMetaInterface.TYPE_INTEGER, rid);
				rmd.addValue(KettleDatabaseRepository.FIELD_AUTHORIZATION_FID, ValueMetaInterface.TYPE_INTEGER, auth.getFid());
				rmd.addValue(KettleDatabaseRepository.FIELD_AUTHORIZATION_RID, ValueMetaInterface.TYPE_INTEGER, auth.getPermission());
				getRepository().connectionDelegate.insertTableRow(KettleDatabaseRepository.TABLE_R_AUTHORIZATION, rmd);
			}
			getRepository().commit();
		} catch (KettleException e) {
			logger.error("save authorization exception:", e);
			getRepository().rollback();
			throw new DIKettleException(e.getMessage());
		}
	}

}
