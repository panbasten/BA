package com.flywet.platform.bi.component.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.core.xml.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIPageException;
import com.flywet.platform.bi.core.utils.FileUtils;

/**
 * 进行参数替换的工具类
 * 
 * @author PeterPan
 * 
 */
public class PageTemplateInterpolator {

	public static final String URL_PREFIX_PACKAGE = "package:";

	/**
	 * 获得模板的dom和javascript代码
	 * 
	 * @param fileUrl
	 * @param attrs
	 * @return
	 * @throws BIPageException
	 */
	public static Object[] interpolate(String fileUrl, FLYVariableResolver attrs)
			throws BIPageException {
		return interpolate(fileUrl, new ArrayList<String>(), attrs, null);
	}

	public static Object[] interpolate(String fileUrl, Document doc,
			FLYVariableResolver attrs) throws BIPageException {
		return interpolate(fileUrl, doc, new ArrayList<String>(), attrs, null);
	}

	/**
	 * 获得指定节点ID的dom和javascript片段
	 * 
	 * @param fileUrl
	 * @param attrs
	 * @param nodeId
	 * @return
	 * @throws BIPageException
	 */
	public static Object[] interpolate(String fileUrl,
			FLYVariableResolver attrs, String nodeId) throws BIPageException {
		return interpolate(fileUrl, new ArrayList<String>(), attrs, nodeId);
	}

	/**
	 * 直接传入的url对应的文件内容进行参数替换
	 * 
	 * @param fileUrl
	 * @param attrs
	 * @return
	 * @throws IOException
	 */
	public static Object[] interpolate(String fileUrl, List<String> script,
			FLYVariableResolver attrs) throws BIPageException {
		return interpolate(fileUrl, script, attrs, null);
	}

	public static Object[] interpolate(String fileUrl, List<String> script,
			FLYVariableResolver attrs, String nodeId) throws BIPageException {
		try {
			String domString = PageTemplateCache.getDomByUrl(fileUrl);
			if (domString == null) {
				domString = FLYPageTemplateUtils
						.readPageTemplateFileContent(fileUrl);
				PageTemplateCache.put(fileUrl, domString);
			}

			if (domString == null) {
				return new Object[] { StringUtils.EMPTY, "" };
			}

			// 首先解析成dom对象进行替换
			try {
				Node doc = XMLHandler.loadXMLFile(new ByteArrayInputStream(
						domString.getBytes(Const.XML_ENCODING)));
				return interpolate(fileUrl, doc, script, attrs, nodeId);
			} catch (Exception e) {
				String html = FLYExpressionResolver.evaluate(domString, attrs);
				return new Object[] { html, script };
			}

		} catch (BIPageException e) {
			throw e;
		} catch (Exception e) {
			throw new BIPageException("解析页面标签出现错误.", e);
		}
	}

	public static Object[] interpolate(String fileUrl, Node doc,
			List<String> script, FLYVariableResolver attrs, String nodeId)
			throws BIPageException {
		try {
			HTMLWriter html = HTMLWriter.instance();
			if (nodeId != null && !"".equals(nodeId)) {
				doc = XMLUtils.getSubNodeById(doc, nodeId);
				PageTemplateResolver.resolverNode(doc, html, script, attrs,
						fileUrl);
			} else {
				PageTemplateResolver.resolverSubNode(doc, html, script, attrs,
						fileUrl);
			}

			return new Object[] { html.toString(), script };
		} catch (BIPageException e) {
			throw e;
		} catch (Exception e) {
			throw new BIPageException("解析页面标签出现错误.", e);
		}
	}

	/**
	 * 读取包中的页面文件
	 * 
	 * @param packageClass
	 * @param fileName
	 * @param attrs
	 * @return
	 * @throws BIPageException
	 */
	public static Object[] interpolate(Class<?> packageClass, String fileName,
			FLYVariableResolver attrs) throws BIPageException {
		return interpolate(packageClass, fileName, new ArrayList<String>(),
				attrs, null);
	}

	public static Object[] interpolate(Class<?> packageClass, String fileName,
			List<String> script, FLYVariableResolver attrs)
			throws BIPageException {
		return interpolate(packageClass, fileName, script, attrs, null);
	}

	/**
	 * 读取包中的页面文件，指定特定节点
	 * 
	 * @param packageClass
	 * @param fileName
	 * @param attrs
	 * @param nodeId
	 * @return
	 * @throws BIPageException
	 */
	public static Object[] interpolate(Class<?> packageClass, String fileName,
			FLYVariableResolver attrs, String nodeId) throws BIPageException {
		return interpolate(packageClass, fileName, new ArrayList<String>(),
				attrs, nodeId);
	}

	public static Object[] interpolate(Class<?> packageClass, String fileName,
			List<String> script, FLYVariableResolver attrs, String nodeId)
			throws BIPageException {
		try {
			String filePath = getPackagePath(packageClass, fileName);
			String domString = PageTemplateCache.getDomByUrl(filePath);
			if (domString == null) {
				domString = FLYPageTemplateUtils
						.readPageTemplateFileContentFromPackage(filePath,
								packageClass);
				PageTemplateCache.put(filePath, domString);
			}

			if (domString == null) {
				return new Object[] { StringUtils.EMPTY, "" };
			}

			// 首先解析成dom对象进行替换
			try {
				Node doc = XMLHandler.loadXMLFile(new ByteArrayInputStream(
						domString.getBytes(Const.XML_ENCODING)));
				return interpolate(URL_PREFIX_PACKAGE + packageClass.getName(),
						doc, script, attrs, nodeId);
			} catch (Exception e) {
				String html = FLYExpressionResolver.evaluate(domString, attrs);
				return new Object[] { html, script };
			}

		} catch (BIPageException e) {
			throw e;
		} catch (Exception e) {
			throw new BIPageException("解析页面标签出现错误.", e);
		}
	}

	/**
	 * 读取包中的整体文件，只替换变量，不解析标签
	 * 
	 * @param packageClass
	 * @param fileName
	 * @param attrs
	 * @return
	 * @throws BIPageException
	 */
	public static String interpolateText(Class<?> packageClass,
			String fileName, FLYVariableResolver attrs) throws BIPageException {
		try {
			String filePath = getPackagePath(packageClass, fileName);
			String str = PageTemplateCache.getDomByUrl(filePath);
			if (str == null) {
				str = FLYPageTemplateUtils
						.readPageTemplateFileContentFromPackage(filePath,
								packageClass);
				PageTemplateCache.put(filePath, str);
			}

			if (str == null) {
				return "";
			}

			return FLYExpressionResolver.evaluate(str, attrs);

		} catch (BIPageException e) {
			throw e;
		} catch (Exception e) {
			throw new BIPageException("解析文件出现错误.", e);
		}
	}

	public static String interpolateText(String fileUrl,
			FLYVariableResolver attrs) throws BIPageException {
		try {
			String str = PageTemplateCache.getDomByUrl(fileUrl);
			if (str == null) {
				str = FLYPageTemplateUtils.readPageTemplateFileContent(fileUrl);
				PageTemplateCache.put(fileUrl, str);
			}

			if (str == null) {
				return "";
			}

			return FLYExpressionResolver.evaluate(str, attrs);

		} catch (BIPageException e) {
			throw e;
		} catch (Exception e) {
			throw new BIPageException("解析文件出现错误.", e);
		}
	}

	/**
	 * 通过模板的url获得dom
	 * 
	 * @param fileUrl
	 * @return
	 * @throws BIPageException
	 */
	public static Document getDom(String fileUrl) throws BIPageException {
		try {
			String domString = PageTemplateCache.getDomByUrl(fileUrl);
			if (domString == null) {
				domString = FLYPageTemplateUtils
						.readPageTemplateFileContent(fileUrl);
				PageTemplateCache.put(fileUrl, domString);
			}

			if (domString == null) {
				return null;
			}
			return XMLHandler.loadXMLFile(new ByteArrayInputStream(domString
					.getBytes(Const.XML_ENCODING)));

		} catch (Exception e) {
			throw new BIPageException("获得页面DOM出现错误.", e);
		}
	}

	public static String getPackagePath(Class<?> packageClass, String fileName) {
		return getPackagePath(packageClass.getPackage().getName(), fileName);
	}

	public static String getPackagePath(String packageClass, String fileName) {
		return FileUtils.getPackagePath(packageClass, "/pages/" + fileName);
	}

	public static Document getDom(Class<?> packageClass, String fileName)
			throws BIPageException {
		try {
			String filePath = getPackagePath(packageClass, fileName);
			String domString = PageTemplateCache.getDomByUrl(filePath);
			if (domString == null) {
				domString = FLYPageTemplateUtils
						.readPageTemplateFileContentFromPackage(filePath,
								packageClass);
				PageTemplateCache.put(filePath, domString);
			}

			if (domString == null) {
				return null;
			}

			return XMLHandler.loadXMLFile(new ByteArrayInputStream(domString
					.getBytes(Const.XML_ENCODING)));
		} catch (Exception e) {
			throw new BIPageException("获得页面DOM出现错误.", e);
		}

	}

	/**
	 * 通过Dom的字符串获得Dom对象
	 * 
	 * @param domString
	 * @return
	 * @throws BIPageException
	 */
	public static Document getDomWithContent(String domString)
			throws BIPageException {
		try {
			if (domString == null) {
				return null;
			}
			return XMLHandler.loadXMLFile(new ByteArrayInputStream(domString
					.getBytes(Const.XML_ENCODING)));

		} catch (Exception e) {
			throw new BIPageException("获得页面DOM出现错误.", e);
		}
	}

}
