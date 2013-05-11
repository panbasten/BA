package com.plywet.platform.bi.delegates.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.encryption.Encr;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.repository.IUser;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.UserInfo;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryBase;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositorySecurityProvider;

import com.plywet.platform.bi.delegates.anno.BIDelegate;
import com.plywet.platform.bi.delegates.exceptions.BIKettleException;
import com.plywet.platform.bi.delegates.intf.BIRoleAdaptor;
import com.plywet.platform.bi.delegates.model.BIAbstractDbAdaptor;
import com.plywet.platform.bi.delegates.vo.Role;

@BIDelegate(type = "db")
public class BIRoleAdaptorImpl extends BIAbstractDbAdaptor implements
		BIRoleAdaptor {
	private final Logger logger = Logger.getLogger(BIUserAdaptorImpl.class);

	@Override
	public void deleteRole(long rid) throws BIKettleException {
		KettleDatabaseRepositorySecurityProvider securityProvider = getRepository()
				.getSecurityProvider();
		try {
			securityProvider.deleteRole(new LongObjectId(rid));
		} catch (KettleException e) {
			logger.error("delete role exception:", e);
			throw new BIKettleException(e.getMessage());
		}
	}

	@Override
	public List<Role> getAllRoles() throws BIKettleException {
		String sql = "SELECT "
				+ KettleDatabaseRepositoryBase.FIELD_ROLE_ID_ROLE + ","
				+ KettleDatabaseRepositoryBase.FIELD_ROLE_NAME + ","
				+ KettleDatabaseRepositoryBase.FIELD_ROLE_DESCRIPTION
				+ " FROM " + KettleDatabaseRepositoryBase.TABLE_R_ROLE;
		List<Object[]> rows = null;
		try {
			rows = getRepository().connectionDelegate.getRows(sql);
		} catch (KettleDatabaseException e) {
			logger.error("get roles exception:", e);
			throw new BIKettleException(e.getMessage());
		}

		if (rows == null) {
			return Collections.emptyList();
		}
		List<Role> roles = new ArrayList<Role>();
		for (Object[] row : rows) {
			Role role = new Role();
			role.setRid(Long.parseLong(row[0].toString()));
			role.setRoleName(row[1].toString());
			role.setDesc(row[2].toString());
			roles.add(role);
		}
		return roles;
	}

	@Override
	public Role getRoleById(long roleId) throws BIKettleException {
		try {
			RowMetaAndData r = getRepository().connectionDelegate.getOneRow(
					KettleDatabaseRepositoryBase.TABLE_R_ROLE,
					KettleDatabaseRepositoryBase.FIELD_ROLE_ID_ROLE,
					new LongObjectId(roleId));
			if (r == null
					|| r
							.getInteger(KettleDatabaseRepositoryBase.FIELD_ROLE_ID_ROLE) == null) {
				return null;
			}

			Role role = new Role();
			role
					.setRid(r
							.getInteger(KettleDatabaseRepositoryBase.FIELD_ROLE_ID_ROLE));
			role.setRoleName(r.getString(
					KettleDatabaseRepositoryBase.FIELD_ROLE_NAME, null));
			role.setDesc(r.getString(
					KettleDatabaseRepositoryBase.FIELD_ROLE_DESCRIPTION, null));
			return role;
		} catch (KettleException e) {
			logger.error("get role exception:", e);
			throw new BIKettleException(e.getMessage());
		}
	}

	@Override
	public void saveRole(Role role) throws BIKettleException {
		RowMetaAndData r = new RowMetaAndData();
		r.addValue(KettleDatabaseRepositoryBase.FIELD_ROLE_NAME,
				ValueMetaInterface.TYPE_STRING, role.getRoleName());
		r.addValue(KettleDatabaseRepositoryBase.FIELD_ROLE_DESCRIPTION,
				ValueMetaInterface.TYPE_STRING, role.getDesc());

		try {
			if (role.getRid() == 0) { // 新增
				long batchId = getNextBatchId(
						KettleDatabaseRepositoryBase.TABLE_R_ROLE,
						KettleDatabaseRepositoryBase.FIELD_ROLE_ID_ROLE);
				r.addValue(KettleDatabaseRepositoryBase.FIELD_ROLE_ID_ROLE,
						ValueMetaInterface.TYPE_INTEGER, batchId);
				getRepository().connectionDelegate.insertTableRow(
						KettleDatabaseRepositoryBase.TABLE_R_ROLE, r);
			} else {
				r.addValue(KettleDatabaseRepositoryBase.FIELD_ROLE_ID_ROLE,
						ValueMetaInterface.TYPE_INTEGER, role.getRid());
				getRepository().connectionDelegate.updateTableRow(
						KettleDatabaseRepositoryBase.TABLE_R_ROLE,
						KettleDatabaseRepositoryBase.FIELD_ROLE_ID_ROLE, r);
			}
			getRepository().commit();
		} catch (KettleException e) {
			logger.error("save role exception:", e);
			getRepository().rollback();
			throw new BIKettleException(e.getMessage());
		}
	}

	@Override
	public List<IUser> getUsers(long roleId) throws BIKettleException {
		String sql = "SELECT "
				+ KettleDatabaseRepositoryBase.FIELD_USER_ID_USER + ","
				+ KettleDatabaseRepositoryBase.FIELD_USER_LOGIN + ","
				+ KettleDatabaseRepositoryBase.FIELD_USER_PASSWORD + ","
				+ KettleDatabaseRepositoryBase.FIELD_USER_NAME + ","
				+ KettleDatabaseRepositoryBase.FIELD_USER_DESCRIPTION + ","
				+ KettleDatabaseRepositoryBase.FIELD_USER_ENABLED + " FROM "
				+ KettleDatabaseRepositoryBase.TABLE_R_USER + " WHERE "
				+ KettleDatabaseRepositoryBase.FIELD_USER_ID_USER + " IN("
				+ "SELECT " + KettleDatabaseRepositoryBase.FIELD_USER_ROLE_UID
				+ " FROM " + KettleDatabaseRepositoryBase.TABLE_R_USER_ROLE
				+ " WHERE " + KettleDatabaseRepositoryBase.FIELD_USER_ROLE_RID
				+ " = " + roleId + ")";
		List<Object[]> rows = null;
		try {
			rows = getRepository().connectionDelegate.getRows(sql);
		} catch (KettleDatabaseException e) {
			logger.error("get roles exception:", e);
			throw new BIKettleException(e.getMessage());
		}

		if (rows == null) {
			return Collections.emptyList();
		}

		List<IUser> users = new ArrayList<IUser>();
		for (Object[] row : rows) {
			IUser userInfo = new UserInfo();
			userInfo.setObjectId(new LongObjectId(Long.parseLong(row[0]
					.toString())));
			userInfo.setLogin(row[1].toString());
			userInfo.setPassword(Encr.decryptPassword(row[2].toString()));
			userInfo.setUsername(row[3].toString());
			userInfo.setDescription(row[4].toString());
			userInfo.setEnabled("Y".equalsIgnoreCase(row[5].toString()) ? true
					: false);
			users.add(userInfo);
		}
		return users;
	}

}
