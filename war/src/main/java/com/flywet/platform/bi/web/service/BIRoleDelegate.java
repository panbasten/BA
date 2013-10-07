package com.flywet.platform.bi.web.service;

import java.util.List;

import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.vo.Authorization;
import com.flywet.platform.bi.delegates.vo.Role;

public interface BIRoleDelegate {
	public List<Role> getAllRoles() throws BIKettleException;

	public Role getRoleById(long roleId) throws BIKettleException;

	public void saveRole(Role role) throws BIKettleException;

	public void delRole(long roleId) throws BIKettleException;

	public List<Authorization> getAuthorization(long rid)
			throws BIKettleException;

	public void saveRoleAuthorization(long rid, List<Authorization> auths)
			throws BIKettleException;

	public boolean isSuperAdmin(long rid);

	public boolean isAdmin(long rid);

	public boolean isAnomyous(long rid);
}
