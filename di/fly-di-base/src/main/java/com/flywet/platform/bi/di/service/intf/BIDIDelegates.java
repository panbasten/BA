package com.flywet.platform.bi.di.service.intf;

import java.util.List;

import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.services.intf.BIDirectoryDelegates;

public interface BIDIDelegates extends BIDirectoryDelegates {

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

}
