package com.plywet.platform.bi.web.service;

import org.pentaho.di.trans.TransMeta;

import com.plywet.platform.bi.delegates.exceptions.BIKettleException;

public interface BITransDelegates extends BIRepositoryDelegates {
	/**
	 * 通过ID获得转换
	 * 
	 * @param id
	 * @throws BIKettleException
	 */
	public TransMeta loadTransformation(String repository, Long id)
			throws BIKettleException;

	/**
	 * 清除缓存
	 * 
	 * @param repository
	 * @param id
	 * @return
	 */
	public TransMeta clearCacheTransformation(String repository, Long id);

	/**
	 * 更新转换缓存
	 * 
	 * @param repository
	 * @param id
	 * @param transMeta
	 */
	public void updateCacheTransformation(String repository, Long id,
			TransMeta transMeta);

}
