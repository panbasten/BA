package com.flywet.platform.bi.web.service;

import java.util.List;

import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.delegates.vo.FunctionType;

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
	 * @return
	 * @throws BIException
	 */
	public List<FunctionType> getNavigatorsLevelOne() throws BIException;

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
	 * 根据functionType的parent id查找其下的子
	 * 
	 * @param parentId
	 * @return
	 * @throws BIException
	 */
	public List<FunctionType> getFunctionsByParent(long parentId)
			throws BIException;

}
