package com.flywet.platform.bi.delegates.intf;

import java.util.List;

import org.pentaho.di.repository.IUser;

import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.delegates.vo.Role;

public interface BIRoleAdaptor {
	public void deleteRole(long rid) throws BIKettleException;

	public List<Role> getAllRoles() throws BIKettleException;

	public Role getRoleById(long roleId) throws BIKettleException;

	public void saveRole(Role role) throws BIKettleException;

	public List<IUser> getUsers(long roleId) throws BIKettleException;
}
