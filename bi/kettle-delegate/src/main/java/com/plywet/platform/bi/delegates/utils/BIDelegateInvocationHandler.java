package com.plywet.platform.bi.delegates.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.plywet.platform.bi.delegates.model.BIAdaptorInterface;

public class BIDelegateInvocationHandler implements InvocationHandler{
	private final Logger logger = Logger.getLogger(BIDelegateInvocationHandler.class);
	
	private Object obj = null; 
	
	public BIDelegateInvocationHandler(Object obj) {
		this.obj = obj;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;   
		try {
			((BIAdaptorInterface)obj).configRepository();
			result = method.invoke(obj, args);   
		} catch (Exception ex) {
			logger.error("调用exception:", ex);
			throw ex;
		} finally {
			((BIAdaptorInterface)obj).returnRepositoryQuietly();
		}
		return result;   
	}

}
