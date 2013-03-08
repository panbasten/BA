package com.yonyou.bq8.di.web.rest;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.web.service.DIReportDelegates;

@Service("di.resource.reportResource")
@Path("/report")
public class DIReportResource {
	private final Logger logger = Logger.getLogger(DIReportResource.class);

	private static final String ID_EDITOR_CONTENT_NAVI_REPORT_BC = "editorContent-navi-report-bc";
	private static final String ID_EDITOR_CONTENT_NAVI_REPORT_BP = "editorContent-navi-report-bp";

	@Resource(name = "di.service.reportService")
	private DIReportDelegates reportService;

	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentReport(
			@CookieParam("repository") String repository) throws DIException {
		return buildNaviContent(repository, "0", true);
	}

	@GET
	@Path("/dir/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String flushNaviContent(
			@CookieParam("repository") String repository,
			@PathParam("id") String id) throws DIException {
		return buildNaviContent(repository, id, false);
	}

	private String buildNaviContent(String repository, String id, boolean isNew)
			throws DIException {
		try{
			
		} catch (Exception ex) {
			logger.error("创建导航的转换内容页面出现错误。");
			throw new DIException("创建导航的转换内容页面出现错误。", ex);
		}
	}
}
