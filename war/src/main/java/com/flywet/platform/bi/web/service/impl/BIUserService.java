package com.flywet.platform.bi.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.pentaho.di.core.Const;
import org.pentaho.di.repository.IUser;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.core.ContextHolder;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.PropertyUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.enums.AuthorizationObjectCategory;
import com.flywet.platform.bi.delegates.enums.PermissionCategory;
import com.flywet.platform.bi.delegates.intf.BIUserAdaptor;
import com.flywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.flywet.platform.bi.delegates.vo.Authorization;
import com.flywet.platform.bi.delegates.vo.Role;
import com.flywet.platform.bi.delegates.vo.User;
import com.flywet.platform.bi.services.intf.BIRoleDelegate;
import com.flywet.platform.bi.services.intf.BIUserDelegate;
import com.flywet.platform.bi.web.cache.IdentificationCache;

@Service("bi.service.userService")
public class BIUserService implements BIUserDelegate {

	@Resource(name = "bi.service.roleService")
	private BIRoleDelegate roleService;

	@Override
	public void delUser(long uid) throws BIException {
		BIUserAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIUserAdaptor.class);
		adaptor.delUser(uid);

		IdentificationCache.clearUserCache(uid);
	}

	@Override
	public IUser convetToKettleUser(User user) {
		BIUserAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIUserAdaptor.class);
		return adaptor.convetToKettleUser(user);
	}

	@Override
	public User convetToBIUser(IUser iuser) {
		BIUserAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIUserAdaptor.class);
		return adaptor.convetToBIUser(iuser);
	}

	@Override
	public User getCurrentUser() throws BIException {
		String login = ContextHolder.getLoginName();
		if (Const.isEmpty(login)) {
			boolean allow = Utils.toBoolean(PropertyUtils
					.getProperty(PropertyUtils.PORTAL_ANONYMOUS_ALLOW), false);
			// 如果当前用户为空，并且允许匿名用户，返回匿名用户
			if (allow) {
				return getAnomyous();
			}
			return null;
		}
		return getUserByLogin(login);
	}

	@Override
	public User getUserById(long uid) throws BIException {
		User user = IdentificationCache.matchUserCache(uid);
		if (user == null) {
			BIUserAdaptor adaptor = BIAdaptorFactory
					.createAdaptor(BIUserAdaptor.class);
			user = adaptor.loadUser(uid);
			IdentificationCache.putUserCache(user);
		}
		return user;
	}

	@Override
	public User getAnomyous() throws BIException {
		return getUserById(IdentificationCache.ANOMYOUS_USER);
	}

	@Override
	public User getUserByLogin(String login) throws BIException {
		User user = IdentificationCache.matchUserCache(login);
		if (user == null) {
			BIUserAdaptor adaptor = BIAdaptorFactory
					.createAdaptor(BIUserAdaptor.class);
			user = adaptor.loadUser(login);
			IdentificationCache.putUserCache(user);
		}
		return user;
	}

	@Override
	public void saveUser(User user) throws BIException {
		BIUserAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIUserAdaptor.class);
		adaptor.saveUser(user);

		IdentificationCache.putUserCache(user);
	}

	@Override
	public List<User> getAllUser() throws BIException {
		BIUserAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIUserAdaptor.class);
		return adaptor.getAllUsers();
	}

	@Override
	public void relateToRole(long uid, List<Long> roleIds) throws BIException {
		BIUserAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIUserAdaptor.class);
		adaptor.relateToRole(uid, roleIds);

		User user = getUserById(uid);
		user.setRoles(null);
	}

	@Override
	public List<Role> getRelateRoles(long uid) throws BIException {
		User user = getUserById(uid);
		List<Role> roles = user.getRoles();
		if (roles == null) {
			BIUserAdaptor adaptor = BIAdaptorFactory
					.createAdaptor(BIUserAdaptor.class);
			roles = adaptor.getRelateRoles(uid);
			user.setRoles(roles);
		}

		return roles;
	}

	@Override
	public boolean authenticate(AuthorizationObjectCategory cate, long id)
			throws BIException {
		User currentUser = getCurrentUser();
		if (currentUser == null) {
			return false;
		}
		return authenticate(currentUser.getId(), cate, id);
	}

	@Override
	public boolean authenticate(AuthorizationObjectCategory cate, long id,
			PermissionCategory permission) throws BIException {
		User currentUser = getCurrentUser();
		if (currentUser == null) {
			return false;
		}
		return authenticate(currentUser.getId(), cate, id, permission);
	}

	@Override
	public boolean authenticate(long uid, AuthorizationObjectCategory cate,
			long id) throws BIException {
		return authenticate(uid, cate, id, PermissionCategory.ALL);
	}

	@Override
	public boolean authenticate(long uid, AuthorizationObjectCategory cate,
			long id, PermissionCategory permission) throws BIException {
		List<Role> roles = getRelateRoles(uid);

		if (roles != null && roles.size() > 0) {
			for (Role r : roles) {
				long rid = r.getRid();

				// 如果是超级管理员角色，默认拥有所有权限
				if (roleService.isSuperAdmin(rid)) {
					return true;
				}

				// 验证角色的权限
				List<Authorization> ra = roleService.getAuthorization(rid);
				if (ra != null) {
					for (Authorization a : ra) {
						if (a.getOtype() == cate && a.getOid() == id) {
							return (a.getPermission() & permission.getValue()) > 0;
						}
					}
				}
			}
		}
		return false;
	}

}
