package com.plywet.platform.bi.delegates.intf;

import java.util.List;

import com.plywet.platform.bi.delegates.vo.Role;
import com.plywet.platform.bi.delegates.vo.User;
import com.plywet.platform.bi.exceptions.BIKettleException;

public interface BIUserAdaptor {
	public void delUser(long uid) throws BIKettleException;

	public User loadUser(long uid) throws BIKettleException;

	public void saveUser(User user) throws BIKettleException;

	public List<User> getAllUsers() throws BIKettleException;

	public void relateToRole(long uid, List<Long> roleIds)
			throws BIKettleException;

	public List<Role> getRelateRoles(long uid) throws BIKettleException;
}
