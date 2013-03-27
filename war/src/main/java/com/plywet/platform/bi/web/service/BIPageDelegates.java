package com.plywet.platform.bi.web.service;

import java.util.List;

import org.pentaho.di.repository.RepositoryElementInterface;
import org.pentaho.di.trans.TransMeta;

import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.exception.BIJSONException;
import com.plywet.platform.bi.delegates.vo.FunctionType;
import com.plywet.platform.bi.exceptions.BIKettleException;

/**
 * 页面服务层接口类
 * 
 * @author Peter Pan
 * 
 */
public interface BIPageDelegates extends BIDirectoryDelegates {

	/**
	 * 获取第一级导航项
	 * 
	 * @param repository
	 * @return
	 * @throws BIException
	 */
	public List<FunctionType> getNavigatorsLevelOne(String repository)
			throws BIException;

	/**
	 * 根据组件类别获取其下的所有组件
	 * 
	 * @param category
	 *            组件类别
	 * @param browseMeta
	 *            Browse对象
	 * 
	 * @exception BIJSONException
	 */
	public void getPlugins(String category, BrowseMeta browseMeta)
			throws BIJSONException;

	public void getPlugins(String category, BrowseMeta browseMeta,
			String nodeClass) throws BIJSONException;

	/**
	 * 获取类别下组件导航项
	 * 
	 * @param category
	 * @return
	 */
	public List<String> getPluginNavigator(String category);

	/**
	 * 根据functiontype的parent id查找其下的子
	 * 
	 * @param repository
	 * @param parentId
	 * @return
	 * @throws BIException
	 */
	public List<FunctionType> getFunctionsByParent(String repository,
			long parentId) throws BIException;

	/**
	 * 保存资源库对象，包括：RepositoryObjectType枚举类型中的
	 * 
	 * @param repository
	 * @param repositoryElement
	 * @throws BIKettleException
	 */
	public void save(String repository,
			RepositoryElementInterface repositoryElement)
			throws BIKettleException;

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