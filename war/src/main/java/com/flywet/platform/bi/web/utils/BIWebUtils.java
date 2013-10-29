package com.flywet.platform.bi.web.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.pentaho.di.core.Const;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.flywet.platform.bi.core.model.ParameterContext;
import com.flywet.platform.bi.core.utils.PropertyUtils;

@SuppressWarnings("unchecked")
public class BIWebUtils {

	private static ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"applicationContext-ui.xml");

	public static final String TEXT_PLAIN_DEFAULT_CHARSET = MediaType.TEXT_PLAIN
			+ "; charset=" + Const.XML_ENCODING;

	/**
	 * 返回内容至response
	 * 
	 * @param response
	 * @param value
	 * @throws IOException
	 */
	public static void responseWrite(String value, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html; charset=" + Const.XML_ENCODING);
		response.getWriter().print(value);
	}

	/**
	 * 提取请求参数字符串中的参数， 并以键值对的方式保存在ParameterContext中
	 * 
	 * @param paramsString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static ParameterContext fillParameterContext(String paramsString)
			throws UnsupportedEncodingException {
		return fillParameterContext(paramsString, Const.XML_ENCODING);
	}

	public static ParameterContext fillParameterContext(String paramsString,
			String charset) throws UnsupportedEncodingException {
		ParameterContext context = new ParameterContext();
		context.fillContextAndDecode(paramsString, charset);
		return context;
	}

	/**
	 * 获得文件类型的列表，包括文件和参数
	 * 
	 * @param request
	 * @return
	 * @throws FileUploadException
	 */
	public static List<FileItem> extractFileItems(HttpServletRequest request)
			throws FileUploadException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb

		ServletFileUpload upload = new ServletFileUpload(factory);
		String fileSizeMax = PropertyUtils.getProperty("fs.upload.maxsize");
		upload.setFileSizeMax(Long.parseLong(fileSizeMax));

		// 得到所有的文件
		return upload.parseRequest(request);
	}

	/**
	 * 从文件选项中提取参数
	 * 
	 * @param items
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> extractFormField(List<FileItem> items)
			throws UnsupportedEncodingException {
		Map<String, String> params = new HashMap<String, String>();
		for (FileItem item : items) {
			if (!item.isFormField()) {
				continue;
			}
			String fieldName = item.getFieldName();
			String value = item.getString(Const.XML_ENCODING);
			params.put(fieldName, value);
		}
		return params;
	}

	/**
	 * 获得系统注册的Bean
	 * 
	 * @param <T>
	 * @param beanName
	 * @return
	 */
	public <T> T getBean(String beanName) {
		return (T) ctx.getBean(beanName);
	}

}
