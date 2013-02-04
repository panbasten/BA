package com.yonyou.bq8.di.delegates.impl;

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

import com.yonyou.bq8.di.core.utils.Utils;
import com.yonyou.bq8.di.delegates.anno.DIDelegate;
import com.yonyou.bq8.di.delegates.intf.DIUserAdaptor;
import com.yonyou.bq8.di.delegates.model.AbstractDbAdaptor;
import com.yonyou.bq8.di.delegates.vo.Role;
import com.yonyou.bq8.di.delegates.vo.User;
import com.yonyou.bq8.di.exceptions.DIKettleException;

@DIDelegate(type="db")
public class DIUserAdaptorImpl extends AbstractDbAdaptor implements
		DIUserAdaptor {
	private final Logger logger = Logger.getLogger(DIUserAdaptorImpl.class);
	
	@Override
	public void delUser(long uid) throws DIKettleException {
		KettleDatabaseRepositorySecurityProvider securityProvider = getRepository().getSecurityProvider();
		try {
			securityProvider.delUser(new LongObjectId(uid));
		} catch (KettleException e) {
			logger.error("delete user exception:", e);
			throw new DIKettleException(e.getMessage());
		}
	}
	
	private IUser convetToKettleUser(User user) {
		IUser iuser = new UserInfo();
		iuser.setDescription(user.getDesc());
		iuser.setEnabled("Y".equalsIgnoreCase(user.getEnabled()));
		iuser.setLogin(user.getLogin());
		iuser.setObjectId(new LongObjectId(user.getId()));
		iuser.setPassword(user.getPassword());
		iuser.setUsername(user.getName());
		return iuser;
	}
	
	private User convetToDIUser(IUser iuser) {
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
	public List<User> getAllUsers() throws DIKettleException {
		KettleDatabaseRepositorySecurityProvider securityProvider = getRepository().getSecurityProvider();
		try {
			List<IUser> iusers = securityProvider.getUsers();
			if (Utils.isEmpty(iusers)) {
				return Collections.emptyList();
			}
			
			List<User> users = new ArrayList<User>();
			for (IUser iuser : iusers) {
				User user = convetToDIUser(iuser);
				users.add(user);
			}
			
			return users;
		} catch (KettleException e) {
			logger.error("get all users exception:",e);
			throw new DIKettleException(e.getMessage());
		}
	}

	@Override
	public User loadUser(long uid) throws DIKettleException {
		KettleDatabaseRepositorySecurityProvider securityProvider = getRepository().getSecurityProvider();
		try {
			IUser iuser = securityProvider.loadUserInfo(new LongObjectId(uid));
			return convetToDIUser(iuser);
		} catch (KettleException e) {
			logger.error("get user by id exception:",e);
			throw new DIKettleException(e.getMessage());
		}
	}

	@Override
	public void relateToRole(long uid, List<Long> roleIds) throws DIKettleException {
		try {
			String delSql = "DELETE FROM " + KettleDatabaseRepositoryBase.TABLE_R_USER_ROLE
						+ " WHERE " 
						+ KettleDatabaseRepositoryBase.FIELD_USER_ROLE_UID 
						+ " = ?";
			delSql = replaceParam(delSql, String.valueOf(uid));
			this.execSql(delSql);
			
			String insertSql = "INSERT INTO " + KettleDatabaseRepositoryBase.TABLE_R_USER_ROLE
				+ " VALUES(?," + uid + ")";
			for (Long rid : roleIds) {
				String execSql = this.replaceParam(insertSql, String.valueOf(rid));
				this.execSql(execSql);
			}
			getRepository().commit();
		} catch (KettleException e) {
			logger.error("relate user to role exception:", e);
			throw new DIKettleException(e);
		}
	}

	@Override
	public void saveUser(User user) throws DIKettleException {
		KettleDatabaseRepositorySecurityProvider securityProvider = getRepository().getSecurityProvider();
		try {
			IUser iuser = convetToKettleUser(user);
			securityProvider.saveUserInfo(iuser);
		} catch (Exception e) {
			logger.error("save user exception:", e);
			throw new DIKettleException(e);
		}
	}

	@Override
	public List<Role> getRelateRoles(long uid) throws DIKettleException {
		String sql = "SELECT "
				+ KettleDatabaseRepositoryBase.FIELD_ROLE_ID_ROLE + ","
				+ KettleDatabaseRepositoryBase.FIELD_ROLE_NAME + ","
				+ KettleDatabaseRepositoryBase.FIELD_ROLE_DESCRIPTION
				+ " FROM " + KettleDatabaseRepositoryBase.TABLE_R_ROLE 
				+ " WHERE "
				+ KettleDatabaseRepositoryBase.FIELD_ROLE_ID_ROLE + " IN("
				+ "SELECT " + KettleDatabaseRepositoryBase.FIELD_USER_ROLE_RID
				+ " FROM " + KettleDatabaseRepositoryBase.TABLE_R_USER_ROLE
				+ " WHERE " + KettleDatabaseRepositoryBase.FIELD_USER_ROLE_UID
				+ " = " + uid + ")";
		List<Object[]> rows = null;
		try {
			rows = getRepository().connectionDelegate.getRows(sql);
		} catch (KettleDatabaseException e) {
			logger.error("get roles exception:", e);
			throw new DIKettleException(e.getMessage());
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
