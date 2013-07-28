package com.flywet.platform.bi.web.utils;

import java.util.Map;

import org.json.simple.JSONObject;
import org.pentaho.di.repository.IUser;
import org.pentaho.di.repository.UserInfo;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.JSONUtils;

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

	public static IUser getLoginUser(String userInfo) throws BIException {
		try {
			JSONObject u = JSONUtils.convertStringToJSONObject(BIWebUtils
					.decode(userInfo));
			return new UserInfo(u);
		} catch (Exception ex) {
			throw new BIException("转换用户对象出现错误。", ex);
		}
	}
}
