package com.flywet.platform.bi.delegates.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.flywet.platform.bi.core.ContextHolder;
import com.flywet.platform.bi.core.utils.ClassUtils;
import com.flywet.platform.bi.delegates.anno.BIDelegate;

public class BIAdaptorFactory {
	private static final Logger logger = Logger
			.getLogger(BIAdaptorFactory.class);

	private static final String DELEGATE_BASE_PACKAGE = "com.flywet.platform.bi";
	private static Set<Class<?>> allClasses = null;

	private BIAdaptorFactory() {
	}

	static {
		init();
	}

	@SuppressWarnings("unchecked")
	public static <T> T createCustomAdaptor(Class<T> cls) {
		try {
			T ddi = (T) Proxy.newProxyInstance(cls.getClassLoader(), cls
					.getInterfaces(), new BIDelegateInvocationHandler(cls
					.newInstance()));
			return ddi;
		} catch (Exception e) {
			logger.error("创建Adaptor失败", e);
		}
		return null;
	}

	public static <T> T createAdaptor(Class<T> intf) {
		return createAdaptor(intf, ContextHolder.getRepositoryType());
	}

	@SuppressWarnings("unchecked")
	public static <T> T createAdaptor(Class<T> intf, String repositoryType) {
		Class<?> targetClazz = null;
		for (Class<?> clazz : allClasses) {
			if (!intf.isAssignableFrom(clazz)) {
				continue;
			}

			// 判断delegate类型是否匹配
			if (delegateTypeCheck(clazz, repositoryType)) {
				targetClazz = clazz;
				break;
			}
		}

		if (targetClazz == null) {
			return null;
		}

		try {
			T ddi = (T) Proxy.newProxyInstance(targetClazz.getClassLoader(),
					targetClazz.getInterfaces(),
					new BIDelegateInvocationHandler(targetClazz.newInstance()));
			return ddi;
		} catch (Exception e) {
			logger.error("创建Adaptor失败", e);
		}
		return null;
	}

	private static boolean delegateTypeCheck(Class<?> clazz,
			String repositoryType) {
		Annotation[] annos = clazz.getAnnotations();
		for (Annotation anno : annos) {
			if (!anno.annotationType().equals(BIDelegate.class)) {
				continue;
			}
			String type = ((BIDelegate) anno).type();
			if (repositoryType.equalsIgnoreCase(type)) {
				return true;
			}
		}

		return false;
	}

	public static void init() {
		allClasses = new HashSet<Class<?>>();
		Set<Class<?>> classes = ClassUtils
				.getClasses(DELEGATE_BASE_PACKAGE);
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
			if (anno.annotationType().equals(BIDelegate.class)) {
				return true;
			}
		}
		return false;
	}

}
