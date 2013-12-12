package com.flywet.platform.bi.services.intf;

import java.util.List;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.vo.FunctionType;

/**
 * 功能页面接口类
 * 
 * @author Peter Pan
 * 
 */
public interface BIFunctionDelegates {

	/**
	 * 获取第一级导航项
	 * 
	 * @return
	 * @throws BIException
	 */
	public List<FunctionType> getNavigatorsLevelOne() throws BIException;

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
