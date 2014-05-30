package com.flywet.platform.bi.component.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.flywet.platform.bi.component.anno.FLYComponent;
import com.flywet.platform.bi.core.utils.ClassUtils;

/**
 * 通过注解方式添加的组件的工厂
 * 
 * @author PeterPan
 * 
 */
public class FLYComponentFactory {

	private static final String COMPONENT_BASE_PACKAGE = "com.flywet.platform.bi.component";
	private static List<String> allFileNames = null;

	private FLYComponentFactory() {

	}

	static {
		init();
	}

	public static List<String> getAllFileNames() {
		return allFileNames;
	}

	public static void init() {
		allFileNames = new ArrayList<String>();
		Set<Class<?>> classes = ClassUtils.getClasses(COMPONENT_BASE_PACKAGE);
		if (classes == null || classes.isEmpty()) {
			return;
		}

		for (Class<?> clazz : classes) {
			addAnnotation(clazz);
		}
	}

	private static void addAnnotation(Class<?> clazz) {
		FLYComponent anno = clazz.getAnnotation(FLYComponent.class);
		if (anno != null) {
			allFileNames.add(anno.fileName());
		}
	}
}
