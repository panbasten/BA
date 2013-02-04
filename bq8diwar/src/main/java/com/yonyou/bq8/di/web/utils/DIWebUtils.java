package com.yonyou.bq8.di.web.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletResponse;

import org.pentaho.di.core.Const;

import com.yonyou.bq8.di.web.model.ParameterContext;

public class DIWebUtils {
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
		context.fillContext(URLDecoder.decode(paramsString, charset));
		return context;
	}
}
