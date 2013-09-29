package com.flywet.platform.bi.web.service.impl;

import java.util.List;

import org.pentaho.di.repository.IUser;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.intf.BIUserAdaptor;
import com.flywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.flywet.platform.bi.delegates.vo.Role;
import com.flywet.platform.bi.delegates.vo.User;
import com.flywet.platform.bi.web.cache.IdentificationCache;
import com.flywet.platform.bi.web.service.BIUserDelegate;

@Service("bi.service.userService")
public class BIUserService implements BIUserDelegate {
	@Override
	public void delUser(long uid) throws BIException {
		BIUserAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIUserAdaptor.class);
		adaptor.delUser(uid);

		// 缓存
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
	public void relateToRole(long uid, List<Long> roleIds) throws BIException {
		BIUserAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIUserAdaptor.class);
		adaptor.relateToRole(uid, roleIds);
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
	public List<Role> getRelateRoles(long uid) throws BIException {
		BIUserAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIUserAdaptor.class);
		return adaptor.getRelateRoles(uid);
	}

}
