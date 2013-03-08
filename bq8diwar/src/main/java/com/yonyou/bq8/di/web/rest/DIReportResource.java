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

import com.yonyou.bq8.di.component.components.breadCrumb.BreadCrumbMeta;
import com.yonyou.bq8.di.component.components.browse.BrowseMeta;
import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.core.utils.Utils;
import com.yonyou.bq8.di.web.entity.AjaxResult;
import com.yonyou.bq8.di.web.entity.AjaxResultEntity;
import com.yonyou.bq8.di.web.service.DIReportDelegates;
import com.yonyou.bq8.di.web.service.impl.DIReportService;

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
		return buildNaviContent(repository,
				DIReportService.DIRECTORY_ROOT_ID_REPORT, true);
	}

	@GET
	@Path("/dir/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String flushNaviContent(
			@CookieParam("repository") String repository,
			@PathParam("id") String id) throws DIException {
		return buildNaviContent(repository, Long.parseLong(id), false);
	}

	private String buildNaviContent(String repository, Long idL, boolean isNew)
			throws DIException {
		try {
			try {
				// 1.为转换的面包屑页面创建一个自定义操作
				BreadCrumbMeta bce = reportService.getParentDirectories(
						repository, idL);
				AjaxResultEntity transBCResult = AjaxResultEntity.instance()
						.setOperation(Utils.RESULT_OPERATION_CUSTOM)
						.setTargetId(ID_EDITOR_CONTENT_NAVI_REPORT_BC).setCmd(
								isNew ? "widget.BreadCrumb" : "this.flush")
						.setData(bce);

				// 2.填充浏览面板内容
				BrowseMeta browseMeta = new BrowseMeta();
				reportService.getSubDirectory(repository, idL, browseMeta);
				reportService
						.getSubDirectoryObject(repository, idL, browseMeta);
				browseMeta.addClass("hb-browsepanel");

				AjaxResultEntity browseResult = AjaxResultEntity.instance()
						.setOperation(Utils.RESULT_OPERATION_CUSTOM)
						.setTargetId(ID_EDITOR_CONTENT_NAVI_REPORT_BP).setCmd(
								isNew ? "widget.BrowsePanel" : "this.flush")
						.setData(browseMeta);

				return AjaxResult.instance().addEntity(transBCResult)
						.addEntity(browseResult).toJSONString();
			} catch (Exception ex) {
				logger.error("创建导航的转换内容页面出现错误。");
				throw new DIException("创建导航的转换内容页面出现错误。", ex);
			}

		} catch (Exception ex) {
			logger.error("创建导航的转换内容页面出现错误。");
			throw new DIException("创建导航的转换内容页面出现错误。", ex);
		}
	}
}
