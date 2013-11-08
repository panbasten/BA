package com.flywet.platform.bi.delegates.intf;

import java.util.List;

import org.pentaho.di.repository.IUser;

import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.delegates.vo.Role;
import com.flywet.platform.bi.delegates.vo.User;

public interface BIUserAdaptor {
	public void delUser(long uid) throws BIKettleException;

	public User loadUser(long uid) throws BIKettleException;

	public User loadUser(String login) throws BIKettleException;

	public void saveUser(User user) throws BIKettleException;

	public List<User> getAllUsers() throws BIKettleException;

	public void relateToRole(long uid, List<Long> roleIds)
			throws BIKettleException;

	public List<Role> getRelateRoles(long uid) throws BIKettleException;

	public IUser convetToKettleUser(User user);

	public User convetToBIUser(IUser iuser);
}
