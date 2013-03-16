package com.plywet.platform.bi.web.rest;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.web.entity.AjaxResult;

@Service("bi.resource.domainResource")
@Path("/domain")
public class BIDomainResource {

	private final Logger logger = Logger.getLogger(BIDomainResource.class);

	/**
	 * 创建信息域页面
	 * 
	 * @throws BIException
	 */
	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentDomain(
			@CookieParam("repository") String repository) throws BIException {
		try {
			return AjaxResult.instance().toJSONString();
		} catch (Exception e) {
			logger.error("创建导航的信息域内容页面出现错误");
			throw new BIException("创建导航的信息域内容页面出现错误", e);
		}
	}
}
