package com.flywet.platform.bi.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.utils.BISecurityUtils;

/**
 * 所有请求（除排除之外的请求），需要在cookie中有登录用户名称
 * 
 * @author PeterPan
 * 
 */
public class LoginCheckFilter implements Filter {
	private final Logger logger = Logger.getLogger(LoginCheckFilter.class);

	private static final String USERNAME = "username";

	private String[] excludeUris = new String[] {};

	private String[] anonymousUris = new String[] {};

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String uri = request.getRequestURI();

		boolean pass = false;
		boolean isAnonymous = false;
		for (String exclude : excludeUris) {
			if (uri.indexOf(exclude) != -1) {
				pass = true;
				break;
			}
		}

		for (String anonymous : anonymousUris) {
			if (uri.indexOf(anonymous) != -1) {
				pass = true;
				isAnonymous = true;
				break;
			}
		}

		// 是否添加默认资源库
		if (isAnonymous) {
			try {
				BISecurityUtils.checkRepository();
			} catch (BIException e) {
				throw new ServletException("检查默认资源库失败。");
			}
		}

		// 是否忽略登录检查
		if (pass) {
			fc.doFilter(req, res);
			return;
		}

		// 检查是否登录
		if (!isLogined(request)) {
			req.getRequestDispatcher("/").forward(req, res);
			return;
		}

		fc.doFilter(req, res);
	}

	private boolean isLogined(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return false;
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(USERNAME)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String excludes = filterConfig.getInitParameter("excludes");
		if (excludes != null && !StringUtils.isEmpty(excludes.trim())) {
			excludeUris = excludes.split(",");
		}

		String anonymous = filterConfig.getInitParameter("anonymous");
		if (anonymous != null && !StringUtils.isEmpty(anonymous.trim())) {
			anonymousUris = anonymous.split(",");
		}
	}
}
