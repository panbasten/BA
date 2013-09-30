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

import com.flywet.platform.bi.core.ContextHolder;
import com.flywet.platform.bi.web.rest.BIIdentification;

public class ContextFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		
		ContextHolder.clearLogin();
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(BIIdentification.REPOSITORYNAME)) {
					ContextHolder.setRepositoryName(cookie.getValue());
					continue;
				}

				if (cookie.getName().equals(BIIdentification.REPOSITORYTYPE)) {
					ContextHolder.setRepositoryType(cookie.getValue());
					continue;
				}

				if (cookie.getName().equals(BIIdentification.USERNAME)) {
					ContextHolder.setUserName(cookie.getValue());
					continue;
				}

				if (cookie.getName().equals(BIIdentification.LOGINNAME)) {
					ContextHolder.setLoginName(cookie.getValue());
					continue;
				}
			}
		}
		fc.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
