package com.plywet.platform.bi.core;

public class ContextHolder {
	private static ThreadLocal<String> repositoryNameHodler = new ThreadLocal<String>();
	private static ThreadLocal<String> repositoryTypeHodler = new ThreadLocal<String>();

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

}
