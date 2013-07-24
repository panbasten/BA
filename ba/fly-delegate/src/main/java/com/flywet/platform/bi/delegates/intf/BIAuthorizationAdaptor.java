package com.flywet.platform.bi.delegates.intf;

import java.util.List;

import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.vo.Authorization;

public interface BIAuthorizationAdaptor {
	public void saveRoleAuth(long rid, List<Authorization> auths)
			throws BIKettleException;

	public void delRoleAuth(long rid) throws BIKettleException;

	public List<Authorization> getAuthorization(long rid)
			throws BIKettleException;
}
