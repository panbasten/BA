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
	public TransMeta loadTransformation(Long id) throws BIKettleException;

	/**
	 * 创建一个转换
	 * 
	 * @param user
	 * @param dirId
	 * @param desc
	 * @return
	 * @throws BIKettleException
	 */
	public TransMeta createTransformation(IUser user, Long dirId, String desc)
			throws BIKettleException;

	/**
	 * 另存为转换
	 * 
	 * @param user
	 * @param dirId
	 * @param transId
	 * @param transName
	 * @return
	 * @throws BIKettleException
	 */
	public TransMeta saveAsTransformation(IUser user, Long dirId, Long transId,
			String transName) throws BIKettleException;

	/**
	 * 清除缓存
	 * 
	 * @param id
	 * @return
	 */
	public TransMeta clearCacheTransformation(Long id);

	/**
	 * 更新转换缓存
	 * 
	 * @param transMeta
	 */
	public void updateCacheTransformation(TransMeta transMeta);

}
