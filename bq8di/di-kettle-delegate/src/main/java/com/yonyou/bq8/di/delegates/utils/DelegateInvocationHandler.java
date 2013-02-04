package com.yonyou.bq8.di.delegates.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.yonyou.bq8.di.delegates.model.DIAdaptorInterface;

public class DelegateInvocationHandler implements InvocationHandler{
	private final Logger logger = Logger.getLogger(DelegateInvocationHandler.class);
	
	private Object obj = null; 
	
	public DelegateInvocationHandler(Object obj) {
		this.obj = obj;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;   
		try {
			((DIAdaptorInterface)obj).configRepository();
			result = method.invoke(obj, args);   
		} catch (Exception ex) {
			logger.error("调用exception:", ex);
			throw ex;
		} finally {
			((DIAdaptorInterface)obj).returnRepositoryQuietly();
		}
		return result;   
	}

}
