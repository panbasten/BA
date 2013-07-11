package com.plywet.platform.bi.web.rest;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.plywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.web.entity.AjaxResult;
import com.plywet.platform.bi.web.entity.AjaxResultEntity;
import com.plywet.platform.bi.web.service.BIDomainDelegates;
import com.plywet.platform.bi.web.service.impl.BIDomainService;

@Service("bi.resource.domainResource")
@Path("/domain")
public class BIDomainResource {

	private final Logger logger = Logger.getLogger(BIDomainResource.class);

	private static final String ID_EDITOR_CONTENT_NAVI_DOMAIN_BC = "editorContent-navi-domain-bc";
	private static final String ID_EDITOR_CONTENT_NAVI_DOMAIN_BP = "editorContent-navi-domain-bp";

	@Resource(name = "bi.service.domainService")
	private BIDomainDelegates domainService;

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
		return buildNaviContent(repository,
				BIDomainService.DIRECTORY_ROOT_ID_DOMAIN, true);
	}

	@GET
	@Path("/dir/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String flushNaviContent(
			@CookieParam("repository") String repository,
			@PathParam("id") String id) throws BIException {
		return buildNaviContent(repository, Long.parseLong(id), false);
	}

	private String buildNaviContent(String repository, Long idL, boolean isNew)
			throws BIException {
		try {
			// 1.为模型的面包屑页面创建一个自定义操作
			BreadCrumbMeta bce = domainService.getParentDirectories(repository,
					idL);
			AjaxResultEntity transBCResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_DOMAIN_BC).setCmd(
							isNew ? "widget.BreadCrumb" : "this.flush")
					.setData(bce);

			// 2.填充浏览面板内容
			BrowseMeta browseMeta = new BrowseMeta();
			domainService.getSubDirectory(repository, idL, browseMeta);
			domainService.getSubDirectoryObject(repository, idL, browseMeta);
			browseMeta.addClass("fly-browsepanel");

			AjaxResultEntity browseResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_DOMAIN_BP).setCmd(
							isNew ? "widget.BrowsePanel" : "this.flush")
					.setData(browseMeta);

			return AjaxResult.instance().addEntity(transBCResult).addEntity(
					browseResult).toJSONString();
		} catch (Exception ex) {
			logger.error("创建导航的模型内容页面出现错误。");
			throw new BIException("创建导航的模型内容页面出现错误。", ex);
		}
	}
}
