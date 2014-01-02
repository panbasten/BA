package com.flywet.platform.bi.pivot.rest;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLHandler;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.flywet.platform.bi.base.enums.BIReportCategory;
import com.flywet.platform.bi.base.rest.AbstractReportResource;
import com.flywet.platform.bi.base.service.intf.BIReportDelegates;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.component.web.AjaxResult;
import com.flywet.platform.bi.component.web.AjaxResultEntity;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.pivot.cache.BIPivotReportCache;
import com.flywet.platform.bi.pivot.model.PivotReport;
import com.flywet.platform.bi.pivot.model.factory.PivotReportFactory;
import com.flywet.platform.bi.pivot.service.intf.BIPivotDelegates;
import com.flywet.platform.bi.rest.BIBaseResource;

@Service("bi.resource.pivotResource")
@Path("/pivot")
public class BIPivotResource extends AbstractReportResource {

	private final Logger logger = Logger.getLogger(BIPivotResource.class);

	@Resource(name = "bi.service.reportService")
	private BIReportDelegates reportService;

	@Resource(name = "bi.service.pivotService")
	private BIPivotDelegates pivotService;

	/**
	 * 打开一个多维报表编辑页面
	 * 
	 * @param id
	 * @return
	 * @throws BIException
	 */
	@GET
	@Path("/{id}/open")
	@Produces(MediaType.TEXT_PLAIN)
	public String openPivotEditor(@PathParam("id") String id)
			throws BIException {
		try {

			PivotReport pr = BIPivotReportCache.getPivotReportEditor(Long
					.valueOf(id));

			if (pr == null) {
				// 获得报表对象
				Object[] report = reportService.getReportObject(Long
						.valueOf(id));
				Document doc = PageTemplateInterpolator
						.getDomWithContent((String) report[1]);
				Node prNode = XMLHandler.getSubNode(doc, "PivotReport");

				pr = PivotReportFactory.resolver(prNode);
			}

			JSONObject rtn = pivotService.query(pr);

			return rtn.toJSONString();
		} catch (Exception ex) {
			logger.error("创建多维报表编辑页面出现错误。");
			throw new BIException("创建多维报表编辑页面出现错误。", ex);
		}
	}

	/**
	 * 初始化加载多维报表编辑器页面
	 * 
	 * @return
	 * @throws BIException
	 */
	@Override
	@GET
	@Path("/editor")
	@Produces(MediaType.TEXT_PLAIN)
	public String loadEditor() throws BIException {
		try {
			String cate = BIReportCategory.REPORT_TYPE_PIVOT_REPORT
					.getCategory();

			FLYVariableResolver attrsMap = FLYVariableResolver.instance();
			attrsMap.addVariable("editorId", cate);

			Object[] domString = PageTemplateInterpolator.interpolate(
					getTemplateString(cate), attrsMap);

			// 创建一个更新操作
			AjaxResultEntity entity = AjaxResultEntity.instance().setOperation(
					Utils.RESULT_OPERATION_APPEND).setTargetId(
					BIBaseResource.ID_EDITOR_CONTENT_PANELS).setDomAndScript(
					domString);

			AjaxResult result = AjaxResult.instance();
			result.addEntity(entity);

			return result.toJSONString();
		} catch (Exception ex) {
			logger.error("初始化加载多维报表编辑器页面出现错误。");
			throw new BIException("初始化加载多维报表编辑器页面出现错误。", ex);
		}
	}

}
