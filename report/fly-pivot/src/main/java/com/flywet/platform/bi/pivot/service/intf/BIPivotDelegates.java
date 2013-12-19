package com.flywet.platform.bi.pivot.service.intf;

import com.flywet.platform.bi.core.exception.BIException;

/**
 * 多维报表代理服务接口
 * 
 * @author PeterPan
 * 
 */
public interface BIPivotDelegates {

	public void queryMdx() throws BIException;

}
