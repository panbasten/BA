package com.yonyou.bq8.di.component.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.Const;

public class BQPageTemplateUtils {
	private static String TEMPLATE_HOME = null;
	
	/**
	 * 通过文件的web url读取文件内容
	 * 
	 * @param url
	 *            文件的web url
	 * @return
	 * @throws IOException
	 */
	public static String readPageTemplateFileContent(String url) throws IOException {
		File file = getPageTemplateFile(url);
		return FileUtils.readFileToString(file, "utf-8");
	}

	/**
	 * Web相对路径计算
	 * 
	 * @param originalPath
	 *            被相对的父目录
	 * @param raletiveFilePath
	 *            相对目录
	 * @return
	 */
	public static String reletivePathCal(String originalPath,
			String raletiveFilePath) {
		File original = new File(originalPath);

		if (StringUtils.isEmpty(raletiveFilePath)) {
			return originalPath;
		}

		// 以./开头说明是当前路径
		if (StringUtils.startsWith(raletiveFilePath, "./")) {
			return originalPath
					+ raletiveFilePath.replaceFirst(".", StringUtils.EMPTY);
		}

		if (StringUtils.startsWith(raletiveFilePath, "../")) {
			while (StringUtils.contains(raletiveFilePath, "../")) {
				raletiveFilePath = raletiveFilePath.replaceFirst("../",
						StringUtils.EMPTY);
				if (original.getParentFile() != null) {
					original = original.getParentFile();
				}
			}
			return originalPath + "/" + raletiveFilePath;
		}

		// 默认直接追加
		return originalPath + "/" + raletiveFilePath;
	}

	/**
	 * 通过基于Web的相对路径获得文件
	 * 
	 * @param path
	 * @return
	 */
	public static File getPageTemplateFile(String path) {
		File dir = new File(Const.getPageTemplateDirectory());

		String filePath = reletivePathCal(dir.getPath(), path);
		File custTemplateFile = new File(filePath);
		if (custTemplateFile.exists()) {
			return custTemplateFile;
		}
		
		File sysTemplate = new File(reletivePathCal(TEMPLATE_HOME, path));
		return sysTemplate;
	}
	
	public static void configTemplateHome (String home) {
		TEMPLATE_HOME = home;
	}
}
