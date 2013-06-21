package com.plywet.platform.bi.component.utils;

import org.pentaho.di.i18n.BaseMessages;

import com.plywet.platform.bi.core.utils.PropertyUtils;

/**
 * 用于EL表达式的系统通用方法
 * 
 * @author PeterPan
 * 
 */
public class FLYSystemFunctions {

	public static final String packageName = PropertyUtils
			.getProperty(PropertyUtils.PAGE_I18N_PACKAGE_NAME);

	public static String message(String key) {
		return BaseMessages.getString(packageName, key);
	}

}
