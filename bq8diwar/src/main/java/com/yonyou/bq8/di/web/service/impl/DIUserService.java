package com.yonyou.bq8.di.web.service.impl;

import java.util.List;

import org.pentaho.di.repository.IUser;
import org.springframework.stereotype.Service;

import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.delegates.intf.DIUserAdaptor;
import com.yonyou.bq8.di.delegates.utils.DIAdaptorFactory;
import com.yonyou.bq8.di.delegates.vo.Role;
import com.yonyou.bq8.di.delegates.vo.User;
import com.yonyou.bq8.di.web.service.DIUserDelegate;

@Service("di.service.userService")
public class DIUserService implements DIUserDelegate {
	@Override
	public void delUser(long uid) throws DIException {
		DIUserAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIUserAdaptor.class);
		adaptor.delUser(uid);
	}

	@Override
	public User getUserById(long uid) throws DIException {
		DIUserAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIUserAdaptor.class);
		User user = adaptor.loadUser(uid);
		return user;
	}

	@Override
	public void relateToRole(long uid, List<Long> roleIds) throws DIException{
		DIUserAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIUserAdaptor.class);
		adaptor.relateToRole(uid, roleIds);
	}

	@Override
	public void saveUser(User user) throws DIException{
		DIUserAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIUserAdaptor.class);
		adaptor.saveUser(user);
	}

	@Override
	public List<User> getAllUser() throws DIException{
		DIUserAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIUserAdaptor.class);
		return adaptor.getAllUsers();
	}

	@Override
	public List<Role> getRelateRoles(long uid) throws DIException {
		DIUserAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIUserAdaptor.class);
		return adaptor.getRelateRoles(uid);
	}

}
