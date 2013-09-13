package com.flywet.platform.bi.core;

import org.json.simple.JSONObject;
import org.pentaho.di.repository.IUser;
import org.pentaho.di.repository.UserInfo;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.core.utils.Utils;

public class ContextHolder {
	private static ThreadLocal<String> repositoryNameHodler = new ThreadLocal<String>();
	private static ThreadLocal<String> repositoryTypeHodler = new ThreadLocal<String>();
	private static ThreadLocal<String> userNameHodler = new ThreadLocal<String>();
	private static ThreadLocal<String> userHodler = new ThreadLocal<String>();

	public static void setRepositoryName(String repositoryName) {
		repositoryNameHodler.set(repositoryName);
	}

	public static String getRepositoryName() {
		return repositoryNameHodler.get();
	}

	public static void setRepositoryType(String repositoryType) {
		repositoryTypeHodler.set(repositoryType);
	}

	public static String getRepositoryType() {
		return repositoryTypeHodler.get();
	}

	public static void setUserName(String userName) {
		userNameHodler.set(userName);
	}

	public static String getUserName() {
		return userNameHodler.get();
	}

	public static void setUser(String user) {
		userHodler.set(user);
	}

	public static String getUser() {
		return userHodler.get();
	}

	public static IUser getLoginUser() throws BIException {
		return getLoginUser(getUser());
	}

	public static IUser getLoginUser(String userInfo) throws BIException {
		try {
			JSONObject u = JSONUtils.convertStringToJSONObject(Utils
					.decodeURL(userInfo));
			return new UserInfo(u);
		} catch (Exception ex) {
			throw new BIException("转换用户对象出现错误。", ex);
		}
	}

}
