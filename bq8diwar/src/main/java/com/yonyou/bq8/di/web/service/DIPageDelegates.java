package com.yonyou.bq8.di.web.service;

import java.util.List;

import org.pentaho.di.repository.RepositoryElementInterface;
import org.pentaho.di.trans.TransMeta;

import com.yonyou.bq8.di.component.components.breadCrumb.BreadCrumbMeta;
import com.yonyou.bq8.di.component.components.browse.BrowseMeta;
import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.core.exception.DIJSONException;
import com.yonyou.bq8.di.delegates.vo.FunctionType;
import com.yonyou.bq8.di.exceptions.DIKettleException;

/**
 * 页面服务层接口类
 * 
 * @author Peter Pan
 * 
 */
public interface DIPageDelegates {

	/**
	 * 获取第一级导航项
	 * 
	 * @param repository
	 * @return
	 * @throws DIException
	 */
	public List<FunctionType> getNavigatorsLevelOne(String repository)
			throws DIKettleException, DIException;

	/**
	 * 获得父目录对象
	 * 
	 * @param repository
	 * @param id
	 * @return
	 * @throws DIException
	 */
	public BreadCrumbMeta getParentDirectories(String repository, Long id)
			throws DIException;

	/**
	 * 获得子目录对象
	 * 
	 * @param id
	 * @param browse
	 * @throws DIException
	 */
	public void getSubDirectory(String repository, Long id, BrowseMeta browse)
			throws DIException;

	/**
	 * 获得目录下面的子对象
	 * 
	 * @param category
	 * @param id
	 * @param browse
	 * @throws DIException
	 */
	public void getSubDirectoryObject(String repository, String category,
			Long id, BrowseMeta browse) throws DIException;

	/**
	 * 根据组件类别获取其下的所有组件
	 * 
	 * @param category
	 *            组件类别
	 * @param browseMeta
	 *            Browse对象
	 * 
	 * @exception DIJSONException
	 */
	public void getPlugins(String category, BrowseMeta browseMeta)
			throws DIJSONException;

	public void getPlugins(String category, BrowseMeta browseMeta,
			String nodeClass) throws DIJSONException;

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
	 * @throws DIException
	 */
	public List<FunctionType> getFunctionsByParent(String repository,
			long parentId) throws DIException;

	/**
	 * 保存资源库对象，包括：RepositoryObjectType枚举类型中的
	 * 
	 * @param repository
	 * @param repositoryElement
	 * @throws DIKettleException
	 */
	public void save(String repository,
			RepositoryElementInterface repositoryElement)
			throws DIKettleException;

	/**
	 * 通过ID获得转换
	 * 
	 * @param id
	 * @throws DIKettleException
	 */
	public TransMeta loadTransformation(String repository, Long id)
			throws DIKettleException;

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
