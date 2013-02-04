package com.yonyou.bq8.di.delegates.intf;

import java.util.List;

import com.yonyou.bq8.di.delegates.vo.Role;
import com.yonyou.bq8.di.delegates.vo.User;
import com.yonyou.bq8.di.exceptions.DIKettleException;

public interface DIUserAdaptor {
	public void delUser(long uid) throws DIKettleException;
	public User loadUser(long uid) throws DIKettleException;
	public void saveUser(User user) throws DIKettleException;
	public List<User> getAllUsers() throws DIKettleException;
	public void relateToRole(long uid, List<Long> roleIds) throws DIKettleException;
	public List<Role> getRelateRoles(long uid) throws DIKettleException;
}
