package com.yonyou.bq8.di.web.utils;

import java.util.Map;

import org.json.simple.JSONObject;

public class DISecurityUtils {
	/**
	 * 创建要写入的cookie字串
	 * 
	 * @param user
	 * @return
	 */
	public static String createCookieString(Map<String, String> map) {
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
}
