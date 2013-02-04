package com.yonyou.bq8.di.delegates.intf;

import java.util.List;

import org.pentaho.di.repository.IUser;

import com.yonyou.bq8.di.delegates.vo.Role;
import com.yonyou.bq8.di.exceptions.DIKettleException;

public interface DIRoleAdaptor {
	public void deleteRole(long rid) throws DIKettleException;
	public List<Role> getAllRoles() throws DIKettleException;
	public Role getRoleById(long roleId) throws DIKettleException;
	public void saveRole(Role role) throws DIKettleException;
	public List<IUser> getUsers(long roleId) throws DIKettleException;
}
