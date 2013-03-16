package com.plywet.platform.bi.web.service;

import java.util.List;

import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.delegates.vo.Role;
import com.plywet.platform.bi.delegates.vo.User;

public interface BIUserDelegate {
	public List<User> getAllUser() throws BIException;

	public User getUserById(long uid) throws BIException;

	public void delUser(long uid) throws BIException;

	public void saveUser(User user) throws BIException;

	public void relateToRole(long uid, List<Long> roleIds) throws BIException;

	public List<Role> getRelateRoles(long uid) throws BIException;
}
