package com.yonyou.bq8.di.delegates.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.yonyou.bq8.di.core.ContextHolder;
import com.yonyou.bq8.di.delegates.anno.DIDelegate;

public class DIAdaptorFactory  {
	private static final Logger logger = Logger.getLogger(DIAdaptorFactory.class);
	
	private static final String DELEGATE_BASE_PACKAGE = "com.yonyou.bq8.di.delegates";
	private static Set<Class<?>> allClasses = null;
	
	private DIAdaptorFactory(){}
	
	static {
		init();
	}
	
	public static <T> T createAdaptor(Class<T> intf) {
		return createAdaptor(intf, ContextHolder.getRepositoryType());
	}
	
	public static <T> T createAdaptor(Class<T> intf, String repositoryType) {
		Class<?> targetClazz = null;
		for (Class<?> clazz : allClasses) {
			if (!intf.isAssignableFrom(clazz)) {
				continue;
			}
			
			//判断delegate类型是否匹配
			if (delegateTypeCheck(clazz,repositoryType)) {
				targetClazz = clazz;
				break;
			}
		}
		
		if (targetClazz == null) {
			return null;
		}
		
		try {
			T ddi = (T) Proxy.newProxyInstance(targetClazz.getClassLoader(), targetClazz
					.getInterfaces(), new DelegateInvocationHandler(
							targetClazz.newInstance()));
			return ddi;
		} catch (Exception e) {
			logger.error("创建Adaptor 失败", e);
		}
		return null;
	}
	
	private static boolean delegateTypeCheck(Class<?> clazz, String repositoryType) {
		Annotation[] annos = clazz.getAnnotations();
		for (Annotation anno : annos) {
			if (!anno.annotationType().equals(DIDelegate.class)) {
				continue;
			}
			String type = ((DIDelegate)anno).type();
			if (repositoryType.equalsIgnoreCase(type)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void init() {
		allClasses = new HashSet<Class<?>>();
		Set<Class<?>> classes = DelegateUtils.getClasses(DELEGATE_BASE_PACKAGE);
		if (classes == null || classes.isEmpty()) {
			return;
		}
		
		for (Class<?> clazz : classes) {
			if (annotationCheck(clazz)) {
				allClasses.add(clazz);
			}
		}
	}
	
	private static boolean annotationCheck(Class<?> clazz) {
		Annotation[] annos = clazz.getAnnotations();
		if (annos == null || annos.length == 0) {
			return false;
		}
		for (Annotation anno : annos) {
			if (anno.annotationType().equals(DIDelegate.class)) {
				return true;
			}
		}
		return true;
	}
	
}
