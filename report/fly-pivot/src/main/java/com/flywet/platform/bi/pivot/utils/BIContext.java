package com.flywet.platform.bi.pivot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.fileupload.FileItem;

import com.tonbeller.tbutils.res.Resources;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.convert.Converter;
import com.tonbeller.wcf.convert.ConverterFactory;
import com.tonbeller.wcf.expr.ExprContext;
import com.tonbeller.wcf.expr.ExprUtils;
import com.tonbeller.wcf.format.Formatter;
import com.tonbeller.wcf.format.FormatterFactory;

/**
 * BI多维分析的上下文，通过设定ID来控制MDX执行并发
 * 
 * @author PeterPan
 * 
 */
public class BIContext extends RequestContext {
	Formatter formatter = FormatterFactory.instance(getLocale());
	Converter converter = ConverterFactory.instance(formatter);
	ExprContext exprContext;

	HttpSession session;
	HttpServletRequest request;
	ServletContext context;

	Map attrs = new HashMap();
	Map parameters = new HashMap();
	String sessionId;

	BIContext _self = this;

	public BIContext(String sessionId) {
		this.sessionId = sessionId;
		session = new BISession();
		request = new BIServletRequest();
		context = new BIServletContext();
	}

	/**
	 * @see com.tonbeller.wcf.controller.RequestContext#getRequest()
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @see com.tonbeller.wcf.controller.RequestContext#getResponse()
	 */
	public HttpServletResponse getResponse() {
		return null;
	}

	/**
	 * @see com.tonbeller.wcf.controller.RequestContext#getServletContext()
	 */
	public ServletContext getServletContext() {
		return session.getServletContext();
	}

	/**
	 * @see com.tonbeller.wcf.controller.RequestContext#getSession()
	 */
	public HttpSession getSession() {
		return session;
	}

	/**
	 * @see com.tonbeller.wcf.controller.RequestContext#getConverter()
	 */
	public Converter getConverter() {
		return converter;
	}

	/**
	 * @see com.tonbeller.wcf.controller.RequestContext#getFormatter()
	 */
	public Formatter getFormatter() {
		return formatter;
	}

	public Map getParameters() {
		return request.getParameterMap();
	}

	public void setParameters(Map map) {
		((BIServletRequest) request).setParameterMap(map);
	}

	public String[] getParameters(String name) {
		return request.getParameterValues(name);
	}

	public String getParameter(String name) {
		String[] values = getParameters(name);
		if (values != null && values.length > 0)
			return values[0];
		return null;
	}

	public Locale getLocale() {
		return Locale.US;
	}

	/**
	 * Returns the exprContext.
	 * 
	 * @return ExprContext
	 */
	public ExprContext getExprContext() {
		return exprContext;
	}

	/**
	 * Sets the exprContext.
	 * 
	 * @param exprContext
	 *            The exprContext to set
	 */
	public void setExprContext(ExprContext exprContext) {
		this.exprContext = exprContext;
	}

	/**
	 * @see com.tonbeller.wcf.controller.RequestContext#getModelReference(String)
	 */
	public Object getModelReference(String expr) {
		return ExprUtils.getModelReference(exprContext, expr);
	}

	/**
	 * @see com.tonbeller.wcf.controller.RequestContext#setModelReference(String,
	 *      Object)
	 */
	public void setModelReference(String expr, Object value) {
		ExprUtils.setModelReference(exprContext, expr, value);
	}

	public boolean isUserInRole(String roleExpr) {
		return false;
	}

	public Resources getResources() {
		return Resources.instance(getLocale());
	}

	public Resources getResources(String bundleName) {
		return Resources.instance(getLocale(), bundleName);
	}

	public Resources getResources(Class clasz) {
		return Resources.instance(getLocale(), clasz);
	}

	public String getRemoteUser() {
		return "guest";
	}

	public String getRemoteDomain() {
		return null;
	}

	public boolean isAdmin() {
		return false;
	}

	public Object findBean(String name) {
		return exprContext.findBean(name);
	}

	public void setBean(String name, Object bean) {
		exprContext.setBean(name, bean);
	}

	public void setLocale(Locale locale) {
	}

	public FileItem getFileItem(String name) {
		return null;
	}

	public Map getFileParameters() {
		return null;
	}

	/**
	 * BI多维模型模拟ServletContext
	 * 
	 * @author PeterPan
	 * 
	 */
	public class BIServletContext implements ServletContext {

		@Override
		public Object getAttribute(String name) {
			return attrs.get(name);
		}

		@Override
		public Enumeration getAttributeNames() {
			Vector v = new Vector();
			v.addAll(attrs.keySet());
			return v.elements();
		}

		@Override
		public void removeAttribute(String name) {
			attrs.remove(name);
		}

		@Override
		public void setAttribute(String name, Object att) {
			removeAttribute(name);
			attrs.put(name, att);
		}

		@Override
		public ServletContext getContext(String uripath) {
			return this;
		}

		@Override
		public String getInitParameter(String name) {
			return null;
		}

		@Override
		public Enumeration getInitParameterNames() {
			return null;
		}

		@Override
		public int getMajorVersion() {
			return 0;
		}

		@Override
		public String getMimeType(String file) {
			return null;
		}

		@Override
		public int getMinorVersion() {
			return 0;
		}

		@Override
		public RequestDispatcher getNamedDispatcher(String name) {
			return null;
		}

		@Override
		public String getRealPath(String path) {
			return null;
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			return null;
		}

		@Override
		public URL getResource(String path) throws MalformedURLException {
			return null;
		}

		@Override
		public InputStream getResourceAsStream(String path) {
			return null;
		}

		@Override
		public Set getResourcePaths(String path) {
			return null;
		}

		@Override
		public String getServerInfo() {
			return null;
		}

		@Override
		public Servlet getServlet(String name) throws ServletException {
			return null;
		}

		@Override
		public String getServletContextName() {
			return null;
		}

		@Override
		public Enumeration getServletNames() {
			return null;
		}

		@Override
		public Enumeration getServlets() {
			return null;
		}

		@Override
		public void log(String msg) {

		}

		@Override
		public void log(Exception exception, String msg) {

		}

		@Override
		public void log(String message, Throwable throwable) {

		}

	}

	/**
	 * BI多维模型模拟Request
	 * 
	 * @author PeterPan
	 * 
	 */
	public class BIServletRequest implements HttpServletRequest {

		@Override
		public String getAuthType() {
			return null;
		}

		@Override
		public String getContextPath() {
			return null;
		}

		@Override
		public Cookie[] getCookies() {
			return null;
		}

		@Override
		public long getDateHeader(String name) {
			return 0;
		}

		@Override
		public String getHeader(String name) {
			return null;
		}

		@Override
		public Enumeration getHeaderNames() {
			return null;
		}

		@Override
		public Enumeration getHeaders(String name) {
			return null;
		}

		@Override
		public int getIntHeader(String name) {
			return 0;
		}

		@Override
		public String getMethod() {
			return null;
		}

		@Override
		public String getPathInfo() {
			return null;
		}

		@Override
		public String getPathTranslated() {
			return null;
		}

		@Override
		public String getQueryString() {
			return null;
		}

		@Override
		public String getRemoteUser() {
			return null;
		}

		@Override
		public String getRequestURI() {
			return null;
		}

		@Override
		public StringBuffer getRequestURL() {
			return null;
		}

		@Override
		public String getRequestedSessionId() {
			return null;
		}

		@Override
		public String getServletPath() {
			return null;
		}

		@Override
		public HttpSession getSession() {
			return session;
		}

		@Override
		public HttpSession getSession(boolean create) {
			return session;
		}

		@Override
		public Principal getUserPrincipal() {
			return null;
		}

		@Override
		public boolean isRequestedSessionIdFromCookie() {
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromURL() {
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromUrl() {
			return false;
		}

		@Override
		public boolean isRequestedSessionIdValid() {
			return false;
		}

		@Override
		public boolean isUserInRole(String role) {
			return false;
		}

		@Override
		public Object getAttribute(String name) {
			return attrs.get(name);
		}

		@Override
		public Enumeration getAttributeNames() {
			Vector v = new Vector();
			v.addAll(attrs.keySet());
			return v.elements();
		}

		@Override
		public String getCharacterEncoding() {
			return null;
		}

		@Override
		public int getContentLength() {
			return 0;
		}

		@Override
		public String getContentType() {
			return null;
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			return null;
		}

		@Override
		public String getLocalAddr() {
			return null;
		}

		@Override
		public String getLocalName() {
			return null;
		}

		@Override
		public int getLocalPort() {
			return 0;
		}

		@Override
		public Locale getLocale() {
			return null;
		}

		@Override
		public Enumeration getLocales() {
			return null;
		}

		@Override
		public String getParameter(String name) {
			String[] values = getParameters(name);
			if (values != null && values.length > 0)
				return values[0];
			return null;
		}

		public void setParameterMap(Map parameters) {
			_self.parameters = parameters;
		}

		@Override
		public Map getParameterMap() {
			return parameters;
		}

		@Override
		public Enumeration getParameterNames() {
			Vector v = new Vector();
			v.addAll(parameters.keySet());
			return v.elements();
		}

		@Override
		public String[] getParameterValues(String name) {
			return (String[]) parameters.get(name);
		}

		@Override
		public String getProtocol() {
			return null;
		}

		@Override
		public BufferedReader getReader() throws IOException {
			return null;
		}

		@Override
		public String getRealPath(String path) {
			return null;
		}

		@Override
		public String getRemoteAddr() {
			return null;
		}

		@Override
		public String getRemoteHost() {
			return null;
		}

		@Override
		public int getRemotePort() {
			return 0;
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			return null;
		}

		@Override
		public String getScheme() {
			return null;
		}

		@Override
		public String getServerName() {
			return null;
		}

		@Override
		public int getServerPort() {
			return 0;
		}

		@Override
		public boolean isSecure() {
			return false;
		}

		@Override
		public void removeAttribute(String name) {
			attrs.remove(name);
		}

		@Override
		public void setAttribute(String name, Object att) {
			removeAttribute(name);
			attrs.put(name, att);
		}

		@Override
		public void setCharacterEncoding(String env)
				throws UnsupportedEncodingException {

		}

	}

	/**
	 * BI多维模型模拟Session
	 * 
	 * @author PeterPan
	 * 
	 */
	public class BISession implements HttpSession {

		public long getCreationTime() {
			return 0;
		}

		public String getId() {
			return sessionId;
		}

		public long getLastAccessedTime() {
			return 0;
		}

		public ServletContext getServletContext() {
			return context;
		}

		public void setMaxInactiveInterval(int arg0) {
		}

		public int getMaxInactiveInterval() {
			return 0;
		}

		/** @deprecated */
		public javax.servlet.http.HttpSessionContext getSessionContext() {
			return null;
		}

		public Object getAttribute(String id) {
			return attrs.get(id);
		}

		/** @deprecated */
		public Object getValue(String id) {
			return getAttribute(id);
		}

		public Enumeration getAttributeNames() {
			Vector v = new Vector();
			v.addAll(attrs.keySet());
			return v.elements();
		}

		/** @deprecated */
		public String[] getValueNames() {
			return (String[]) attrs.keySet().toArray(new String[0]);
		}

		public void setAttribute(String id, Object att) {
			removeAttribute(id);
			attrs.put(id, att);
			if (att instanceof HttpSessionBindingListener) {
				HttpSessionBindingEvent e = new HttpSessionBindingEvent(this,
						id);
				((HttpSessionBindingListener) att).valueBound(e);
			}
		}

		/** @deprecated */
		public void putValue(String id, Object attr) {
			setAttribute(id, attr);
		}

		public void removeAttribute(String id) {
			Object attr = attrs.remove(id);
			if (attr instanceof HttpSessionBindingListener) {
				HttpSessionBindingEvent e = new HttpSessionBindingEvent(this,
						id);
				((HttpSessionBindingListener) attr).valueUnbound(e);
			}
		}

		/** @deprecated */
		public void removeValue(String id) {
			removeAttribute(id);
		}

		public void invalidate() {
		}

		public boolean isNew() {
			return false;
		}
	}
}
