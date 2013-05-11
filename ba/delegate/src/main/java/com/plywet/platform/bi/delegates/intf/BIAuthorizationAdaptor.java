package com.plywet.platform.bi.delegates.intf;

import java.util.List;

import com.plywet.platform.bi.delegates.exceptions.BIKettleException;
import com.plywet.platform.bi.delegates.vo.Authorization;

public interface BIAuthorizationAdaptor {
	public void saveRoleAuth(long rid, List<Authorization> auths)
			throws BIKettleException;

	public void delRoleAuth(long rid) throws BIKettleException;

	public List<Authorization> getAuthorization(long rid)
			throws BIKettleException;
}
