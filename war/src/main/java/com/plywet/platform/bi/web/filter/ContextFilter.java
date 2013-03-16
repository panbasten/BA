package com.plywet.platform.bi.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.plywet.platform.bi.core.ContextHolder;

public class ContextFilter implements Filter {
	private static final String REPOSITORYNAME = "repository";
	private static final String REPOSITORYTYPE = "repositoryType";

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			fc.doFilter(req, res);
			return;
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(REPOSITORYNAME)) {
				ContextHolder.setRepositoryName(cookie.getValue());
				continue;
			}

			if (cookie.getName().equals(REPOSITORYTYPE)) {
				ContextHolder.setRepositoryType(cookie.getValue());
				continue;
			}
		}
		fc.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
