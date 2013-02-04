package com.yonyou.bq8.di.web.service;

import java.util.List;

import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.delegates.vo.Role;
import com.yonyou.bq8.di.delegates.vo.User;

public interface DIUserDelegate {
	public List<User> getAllUser() throws DIException;
	public User getUserById(long uid) throws DIException;
	public void delUser(long uid) throws DIException;
	public void saveUser(User user) throws DIException;
	public void relateToRole(long uid,List<Long> roleIds) throws DIException;
	public List<Role> getRelateRoles(long uid) throws DIException;
}
