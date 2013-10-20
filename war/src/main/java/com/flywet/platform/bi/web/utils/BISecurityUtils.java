package com.flywet.platform.bi.web.utils;

import java.util.Map;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;

import com.flywet.platform.bi.core.ContextHolder;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.PropertyUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.BIEnvironmentDelegate;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.pools.RepPool;

public class BISecurityUtils {
	/**
	 * 创建要写入的cookie字串
	 * 
	 * @param user
	 * @return
	 */
	public static String createCookieString(Map<String, Object> map) {
		return JSONObject.toJSONString(map);
	}

	/**
	 * 校验token合法性
	 * 
	 * @return
	 */
	public static boolean checkToken(String token) {
		return true;
	}

	/**
	 * 检查并注册默认资源库
	 * 
	 * @return
	 * @throws BIKettleException
	 */
	public static String checkRepository() throws BIException {
		String repository = ContextHolder.getRepositoryName();
		// 如果repository为空，检查是否可以匿名登录
		if (Const.isEmpty(repository)) {
			boolean allow = Utils.toBoolean(PropertyUtils
					.getProperty(PropertyUtils.PORTAL_ANONYMOUS_ALLOW), false);
			if (allow) {
				repository = PropertyUtils
						.getProperty(PropertyUtils.PORTAL_ANONYMOUS_REPOSITORY);
				if (Const.isEmpty(repository)) {
					String[] repNames = RepPool.instance().getRepNames();
					if (repNames != null && repNames.length > 0) {
						repository = repNames[0];
					}
				}
			}

			// 如果repository仍为空，返回空值
			if (!Const.isEmpty(repository)) {
				// 注册一个默认的资源库
				registerRepository(repository);
			}
		}

		return repository;
	}

	/**
	 * 注册资源库到本地线程
	 * 
	 * @param rep
	 * @throws BIKettleException
	 */
	private static void registerRepository(String rep) throws BIException {
		BIEnvironmentDelegate ed = BIEnvironmentDelegate.instance();
		ed.init();

		ContextHolder.setRepositoryName(rep);
		ContextHolder.setRepositoryType(RepPool.instance().getRepType(rep));
	}
}
