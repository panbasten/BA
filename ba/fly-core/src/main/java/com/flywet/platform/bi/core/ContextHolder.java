package com.flywet.platform.bi.core;

public class ContextHolder {
	private static ThreadLocal<String> repositoryNameHodler = new ThreadLocal<String>();
	private static ThreadLocal<String> repositoryTypeHodler = new ThreadLocal<String>();
	private static ThreadLocal<String> userNameHodler = new ThreadLocal<String>();
	private static ThreadLocal<String> loginNameHodler = new ThreadLocal<String>();

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

	public static void setLoginName(String user) {
		loginNameHodler.set(user);
	}

	public static String getLoginName() {
		return loginNameHodler.get();
	}

	public static void clearLogin() {
		setUserName(null);
		setLoginName(null);
	}

}
