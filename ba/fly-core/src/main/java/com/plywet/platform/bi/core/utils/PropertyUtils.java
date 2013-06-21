package com.plywet.platform.bi.core.utils;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.i18n.BaseMessages;

public class PropertyUtils {
	private final static Logger logger = Logger.getLogger(PropertyUtils.class);

	public static final String REPOSITORY_POOL_MAXIDLE = "repository.pool.maxidle";
	public static final String REPOSITORY_POOL_MAXACTIVE = "repository.pool.maxactive";

	public static final String APPLICATION_TYPE = "application.type";
	public static final String APPLICATION_TYPE_RELEASE = "release";
	public static final String APPLICATION_TYPE_DEBUG = "debug";

	public static final String UI_DIALOG_PREFIX = "ui.dialog.";
	public static final String UI_DIALOG_POSTION_SUFFIX = ".position";

	public static final String PAGE_I18N_PACKAGE_NAME = "page.i18n.package.name";

	/**
	 * 属性配置集合
	 */
	private static Properties properties = null;

	static {
		try {
			loadProperties();
		} catch (KettleException e) {
			logger.error("load ba.properties exception:", e);
		}
	}

	private PropertyUtils() {
	}

	/**
	 * 根据键值获取对应配置项的字符串值
	 * 
	 * @param key
	 *            配置项键值
	 * @return 属性值
	 */
	public static String getProperty(String key) {
		if (properties == null) {
			return null;
		}

		return properties.getProperty(key);
	}

	/**
	 * 根据键值获取配置项的数字值
	 * 
	 * @param key
	 *            属性键值
	 * @return
	 */
	public static int getIntProperty(String key) {
		if (properties == null) {
			return 0;
		}

		String val = properties.getProperty(key);
		if (StringUtils.isEmpty(val)) {
			return 0;
		}

		return Integer.parseInt(val);
	}

	/**
	 * 加载配置文件内容至properties 优先从用户目录获取 其次从classpath下获取
	 * 
	 * @throws KettleException
	 */
	private static void loadProperties() throws KettleException {
		properties = EnvUtil.readProperties(Const.KETTLE_PROPERTIES);
	}

	/**
	 * 获得国际化翻译
	 * 
	 * @param codedString
	 * @return
	 */
	public static String getCodedTranslation(String codedString) {
		if (codedString == null)
			return null;

		if (codedString.startsWith("i18n:")) {
			String[] parts = codedString.split(":");
			if (parts.length != 3)
				return codedString;
			else {
				return BaseMessages.getString(parts[1], parts[2]);
			}
		} else {
			return codedString;
		}
	}

}
