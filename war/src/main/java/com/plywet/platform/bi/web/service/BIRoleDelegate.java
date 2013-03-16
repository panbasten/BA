package com.plywet.platform.bi.web.service;

import java.util.List;

import com.plywet.platform.bi.delegates.vo.Authorization;
import com.plywet.platform.bi.delegates.vo.Role;
import com.plywet.platform.bi.exceptions.BIKettleException;

public interface BIRoleDelegate {
	public List<Role> getAllRoles() throws BIKettleException;

	public Role getRoleById(long roleId) throws BIKettleException;

	public void saveRole(Role role) throws BIKettleException;

	public void delRole(long roleId) throws BIKettleException;

	public List<Authorization> getAuthorization(long rid)
			throws BIKettleException;

	public void saveRoleAuthorization(long rid, List<Authorization> auths)
			throws BIKettleException;
}
