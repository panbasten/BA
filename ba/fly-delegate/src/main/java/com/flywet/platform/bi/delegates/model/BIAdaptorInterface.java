package com.flywet.platform.bi.delegates.model;

import com.flywet.platform.bi.delegates.exceptions.BIKettleException;

public interface BIAdaptorInterface {
	public void configRepository(String repositoryName)
			throws BIKettleException;

	public void configRepository() throws BIKettleException;

	public void returnRepositoryQuietly();
}
