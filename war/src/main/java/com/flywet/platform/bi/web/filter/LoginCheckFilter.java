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

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String uri = request.getRequestURI();

		boolean pass = false;
		for (String exclude : excludeUris) {
			if (uri.indexOf(exclude) != -1) {
				pass = true;
				break;
			}
		}

		if (pass) {
			fc.doFilter(req, res);
			return;
		}
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
		if (excludes == null) {
			return;
		}

		if (StringUtils.isEmpty(excludes.trim())) {
			return;
		}
		excludeUris = excludes.split(",");
	}

}
