package com.yonyou.bq8.di.delegates.model;

import com.yonyou.bq8.di.exceptions.DIKettleException;

public interface DIAdaptorInterface {
	public void configRepository(String repositoryName) throws DIKettleException;
	public void configRepository() throws DIKettleException;
	public void returnRepositoryQuietly();
}
