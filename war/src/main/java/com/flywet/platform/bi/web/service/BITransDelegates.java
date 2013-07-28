package com.flywet.platform.bi.web.service;

import org.pentaho.di.repository.IUser;
import org.pentaho.di.trans.TransMeta;

import com.flywet.platform.bi.delegates.exceptions.BIKettleException;

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
	 * 创建一个转换
	 * 
	 * @param user
	 * @param repository
	 * @param dirId
	 * @param desc
	 * @return
	 * @throws BIKettleException
	 */
	public TransMeta createTransformation(IUser user, String repository,
			Long dirId, String desc) throws BIKettleException;

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
	 * @param transMeta
	 */
	public void updateCacheTransformation(String repository, TransMeta transMeta);

}
