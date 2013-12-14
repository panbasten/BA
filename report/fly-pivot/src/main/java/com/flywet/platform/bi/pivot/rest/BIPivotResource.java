package com.flywet.platform.bi.pivot.rest;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.flywet.platform.bi.base.cache.TemplateCache;
import com.flywet.platform.bi.base.model.TemplateMeta;
import com.flywet.platform.bi.base.rest.AbstractReportResource;
import com.flywet.platform.bi.base.service.intf.BIReportDelegates;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.core.exception.BIException;

@Service("bi.resource.pivotResource")
@Path("/pivot")
public class BIPivotResource extends AbstractReportResource {

	private final Logger logger = Logger.getLogger(BIPivotResource.class);

	@Resource(name = "bi.service.reportService")
	private BIReportDelegates reportService;

	/**
	 * 打开一个多维报表编辑页面
	 * 
	 * @param id
	 * @return
	 * @throws BIException
	 */
	@GET
	@Path("/open/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openPivotEditor(@PathParam("id") String id)
			throws BIException {
		try {
			// 获得报表对象
			Object[] report = reportService.getReportObject(Long.valueOf(id));
			Document doc = PageTemplateInterpolator
					.getDomWithContent((String) report[1]);

			// 保留编辑状态，对于集群应用需要使用共享缓存
			TemplateMeta templateMeta = new TemplateMeta(id, doc);
			TemplateCache.put(id, templateMeta);

			return getReportPageJson(id, templateMeta).toJSONString();
		} catch (Exception ex) {
			logger.error("创建多维报表编辑页面出现错误。");
			throw new BIException("创建多维报表编辑页面出现错误。", ex);
		}
	}

}
