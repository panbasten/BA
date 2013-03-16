package com.plywet.platform.bi.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.delegates.intf.BIUserAdaptor;
import com.plywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.plywet.platform.bi.delegates.vo.Role;
import com.plywet.platform.bi.delegates.vo.User;
import com.plywet.platform.bi.web.service.BIUserDelegate;

@Service("bi.service.userService")
public class BIUserService implements BIUserDelegate {
	@Override
	public void delUser(long uid) throws BIException {
		BIUserAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIUserAdaptor.class);
		adaptor.delUser(uid);
	}

	@Override
	public User getUserById(long uid) throws BIException {
		BIUserAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIUserAdaptor.class);
		User user = adaptor.loadUser(uid);
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
