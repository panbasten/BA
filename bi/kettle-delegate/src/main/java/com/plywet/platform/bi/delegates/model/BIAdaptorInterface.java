package com.plywet.platform.bi.delegates.model;

import com.plywet.platform.bi.exceptions.BIKettleException;

public interface BIAdaptorInterface {
	public void configRepository(String repositoryName)
			throws BIKettleException;

	public void configRepository() throws BIKettleException;

	public void returnRepositoryQuietly();
}
