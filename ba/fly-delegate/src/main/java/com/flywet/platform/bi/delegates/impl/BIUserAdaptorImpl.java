package com.flywet.platform.bi.delegates.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.IUser;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.UserInfo;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryBase;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositorySecurityProvider;

import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.anno.BIDelegate;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.intf.BIUserAdaptor;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;
import com.flywet.platform.bi.delegates.vo.Role;
import com.flywet.platform.bi.delegates.vo.User;

@BIDelegate(type = "db")
public class BIUserAdaptorImpl extends BIAbstractDbAdaptor implements
		BIUserAdaptor {
	private final Logger logger = Logger.getLogger(BIUserAdaptorImpl.class);

	@Override
	public void delUser(long uid) throws BIKettleException {
		KettleDatabaseRepositorySecurityProvider securityProvider = getRepository()
				.getSecurityProvider();
		try {
			securityProvider.delUser(new LongObjectId(uid));
		} catch (KettleException e) {
			logger.error("delete user exception:", e);
			throw new BIKettleException(e.getMessage());
		}
	}

	@Override
	public IUser convetToKettleUser(User user) {
		IUser iuser = new UserInfo();
		iuser.setDescription(user.getDesc());
		iuser.setEnabled("Y".equalsIgnoreCase(user.getEnabled()));
		iuser.setLogin(user.getLogin());
		iuser.setObjectId(new LongObjectId(user.getId()));
		iuser.setPassword(user.getPassword());
		iuser.setUsername(user.getName());
		return iuser;
	}

	@Override
	public User convetToBIUser(IUser iuser) {
		User user = new User();
		user.setDesc(iuser.getDescription());
		user.setEnabled(iuser.isEnabled() ? "Y" : "N");
		user.setId(Long.parseLong(iuser.getObjectId().getId()));
		user.setLogin(iuser.getLogin());
		user.setName(iuser.getUsername());
		user.setPassword(iuser.getPassword());
		return user;
	}

	@Override
	public List<User> getAllUsers() throws BIKettleException {
		KettleDatabaseRepositorySecurityProvider securityProvider = getRepository()
				.getSecurityProvider();
		try {
			List<IUser> iusers = securityProvider.getUsers();
			if (Utils.isEmpty(iusers)) {
				return Collections.emptyList();
			}

			List<User> users = new ArrayList<User>();
			for (IUser iuser : iusers) {
				User user = convetToBIUser(iuser);
				users.add(user);
			}

			return users;
		} catch (KettleException e) {
			logger.error("get all users exception:", e);
			throw new BIKettleException(e.getMessage());
		}
	}

	@Override
	public User loadUser(long uid) throws BIKettleException {
		KettleDatabaseRepositorySecurityProvider securityProvider = getRepository()
				.getSecurityProvider();
		try {
			IUser iuser = securityProvider.loadUserInfo(new LongObjectId(uid));
			return convetToBIUser(iuser);
		} catch (KettleException e) {
			logger.error("get user by id exception:", e);
			throw new BIKettleException(e.getMessage());
		}
	}

	@Override
	public User loadUser(String login) throws BIKettleException {
		KettleDatabaseRepositorySecurityProvider securityProvider = getRepository()
				.getSecurityProvider();
		try {
			IUser iuser = securityProvider.loadUserInfo(login);
			return convetToBIUser(iuser);
		} catch (KettleException e) {
			logger.error("get user by id exception:", e);
			throw new BIKettleException(e.getMessage());
		}
	}

	@Override
	public void relateToRole(long uid, List<Long> roleIds)
			throws BIKettleException {
		try {
			String delSql = "DELETE FROM "
					+ quoteTable(KettleDatabaseRepositoryBase.TABLE_R_USER_ROLE)
					+ " WHERE "
					+ quote(KettleDatabaseRepositoryBase.FIELD_USER_ROLE_UID)
					+ " = ?";
			this.execSql(delSql, new Object[] { uid });

			String insertSql = "INSERT INTO "
					+ quoteTable(KettleDatabaseRepositoryBase.TABLE_R_USER_ROLE)
					+ " VALUES(?,?)";
			for (Long rid : roleIds) {
				this.execSql(insertSql, new Object[] { rid, uid });
			}
			getRepository().commit();
		} catch (KettleException e) {
			logger.error("relate user to role exception:", e);
			throw new BIKettleException(e);
		}
	}

	@Override
	public void saveUser(User user) throws BIKettleException {
		KettleDatabaseRepositorySecurityProvider securityProvider = getRepository()
				.getSecurityProvider();
		try {
			IUser iuser = convetToKettleUser(user);
			securityProvider.saveUserInfo(iuser);
		} catch (Exception e) {
			logger.error("save user exception:", e);
			throw new BIKettleException(e);
		}
	}

	@Override
	public List<Role> getRelateRoles(long uid) throws BIKettleException {
		String sql = "SELECT "
				+ quote(KettleDatabaseRepositoryBase.FIELD_ROLE_ID_ROLE) + ","
				+ quote(KettleDatabaseRepositoryBase.FIELD_ROLE_NAME) + ","
				+ quote(KettleDatabaseRepositoryBase.FIELD_ROLE_DESCRIPTION)
				+ " FROM "
				+ quoteTable(KettleDatabaseRepositoryBase.TABLE_R_ROLE)
				+ " WHERE "
				+ quote(KettleDatabaseRepositoryBase.FIELD_ROLE_ID_ROLE)
				+ " IN(" + "SELECT "
				+ quote(KettleDatabaseRepositoryBase.FIELD_USER_ROLE_RID)
				+ " FROM "
				+ quoteTable(KettleDatabaseRepositoryBase.TABLE_R_USER_ROLE)
				+ " WHERE "
				+ quote(KettleDatabaseRepositoryBase.FIELD_USER_ROLE_UID)
				+ " = ?)";
		List<Object[]> rows = null;
		try {
			rows = getRows(sql, uid);
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

}
