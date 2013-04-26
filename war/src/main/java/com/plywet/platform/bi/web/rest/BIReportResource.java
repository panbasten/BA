package com.plywet.platform.bi.web.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.plywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.plywet.platform.bi.component.utils.PageTemplateResolverType;
import com.plywet.platform.bi.component.vo.ComponentPlugin;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.utils.JSONUtils;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.core.utils.XmlUtils;
import com.plywet.platform.bi.web.entity.AjaxResult;
import com.plywet.platform.bi.web.entity.AjaxResultEntity;
import com.plywet.platform.bi.web.service.BIReportDelegates;
import com.plywet.platform.bi.web.service.impl.BIReportService;

@Service("bi.resource.reportResource")
@Path("/report")
public class BIReportResource {
	private final Logger logger = Logger.getLogger(BIReportResource.class);

	private static final String ICON_PATH = "resources/images/plugins/";

	public static final String DASHBOARD_TEMPLATE = "editor/editor_dashboard.h";

	private static final String ID_EDITOR_CONTENT_NAVI_REPORT_BC = "editorContent-navi-report-bc";
	private static final String ID_EDITOR_CONTENT_NAVI_REPORT_BP = "editorContent-navi-report-bp";

	@Resource(name = "bi.service.reportService")
	private BIReportDelegates reportService;

	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentReport(
			@CookieParam("repository") String repository) throws BIException {
		return buildNaviContent(repository,
				BIReportService.DIRECTORY_ROOT_ID_REPORT, true);
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
			// 1.为转换的面包屑页面创建一个自定义操作
			BreadCrumbMeta bce = reportService.getParentDirectories(repository,
					idL);
			AjaxResultEntity transBCResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_REPORT_BC).setCmd(
							isNew ? "widget.BreadCrumb" : "this.flush")
					.setData(bce);

			// 2.填充浏览面板内容
			BrowseMeta browseMeta = new BrowseMeta();
			reportService.getSubDirectory(repository, idL, browseMeta);
			reportService.getSubDirectoryObject(repository, idL, browseMeta);
			browseMeta.addClass("fly-browsepanel");

			AjaxResultEntity browseResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_REPORT_BP).setCmd(
							isNew ? "widget.BrowsePanel" : "this.flush")
					.setData(browseMeta);

			return AjaxResult.instance().addEntity(transBCResult).addEntity(
					browseResult).toJSONString();
		} catch (Exception ex) {
			logger.error("创建导航的转换内容页面出现错误。");
			throw new BIException("创建导航的转换内容页面出现错误。", ex);
		}
	}

	/**
	 * 打开一个Dashboard编辑页面
	 * 
	 * @param repository
	 * @param id
	 * @return
	 * @throws BIException
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/dashboard/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openDashboardEditor(
			@CookieParam("repository") String repository,
			@PathParam("id") String id) throws BIException {

		try {
			Document doc = PageTemplateInterpolator.getDomForEditor(
					"editor/test/test.h", "dom_" + id);

			// 保留编辑状态，对于集群应用需要使用共享缓存

			FLYVariableResolver attrsMap = FLYVariableResolver.instance();
			Object[] domString = PageTemplateInterpolator.interpolate(
					"editor/test/test.h", doc, attrsMap);
			String dom = (String) domString[0];
			List<String> script = (List<String>) domString[1];

			String domStructure = XmlUtils.toXMLString(doc);

			JSONObject jo = new JSONObject();
			jo.put("dom", dom);
			jo.put("script", JSONUtils.convertToJSONArray(script));
			jo.put("domStructure", domStructure);

			return jo.toJSONString();
		} catch (Exception ex) {
			throw new BIException("创建Dashboard编辑页面出现错误。", ex);
		}
	}

	@GET
	@Path("/dashboard/{id}/")
	@Produces(MediaType.TEXT_PLAIN)
	public String operatorDashboardEditor(
			@CookieParam("repository") String repository,
			@PathParam("id") String id) throws BIException {
		try {
			return "";
		} catch (Exception ex) {
			throw new BIException("创建Dashboard页面出现错误。", ex);
		}
	}

	/**
	 * 初始化加载Dashboard编辑器页面
	 * 
	 * @return
	 * @throws BIException
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/dashboard/editor")
	@Produces(MediaType.TEXT_PLAIN)
	public String loadDashboardEditor() throws BIException {
		try {
			// 生成插件工具栏
			List<String> dashboardStepBar = PageTemplateResolverType
					.getCategoryNames();
			List<BrowseMeta> dashboardStepBrowses = new ArrayList<BrowseMeta>();
			Map<String, List<ComponentPlugin>> categories = PageTemplateResolverType
					.getCategories();

			for (String f : PageTemplateResolverType.getCategoryCodes()) {
				List<ComponentPlugin> cps = categories.get(f);
				BrowseMeta browseMeta = new BrowseMeta();

				for (ComponentPlugin cp : cps) {
					if (cp.isIgnoreInDesigner()) {
						continue;
					}
					BrowseNodeMeta plugin = new BrowseNodeMeta();
					plugin.setId(cp.getId());
					plugin.setCategory(cp.getCategory());
					plugin.addClass("fly-dashboard-step-plugin");
					plugin.addAttribute(HTML.ATTR_TYPE, Utils.DOM_LEAF);
					plugin.addAttribute(HTML.ATTR_ICON, ICON_PATH
							+ cp.getIconfile());
					plugin.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, cp
							.getDescription());
					plugin.addAttribute(HTML.ATTR_TITLE, cp.getTooltip());

					Map dataMap = new HashMap();
					dataMap.put("props", cp.getAttributesJSONArray());
					plugin.setData(dataMap);

					browseMeta.addContent(plugin);
				}

				dashboardStepBrowses.add(browseMeta);
			}

			FLYVariableResolver attrsMap = FLYVariableResolver.instance();
			attrsMap.addVariable("editorId", BIBaseResource.ID_EDITOR_DASHBOARD);
			attrsMap.addVariable("dashboardStepBar", dashboardStepBar);
			attrsMap.addVariable("dashboardStepBrowses", dashboardStepBrowses);

			Object[] domString = PageTemplateInterpolator.interpolate(
					DASHBOARD_TEMPLATE, attrsMap);

			// 创建一个更新操作
			AjaxResultEntity entity = AjaxResultEntity.instance().setOperation(
					Utils.RESULT_OPERATION_APPEND).setTargetId(
					BIBaseResource.ID_EDITOR_CONTENT_PANELS).setDomAndScript(
					domString);

			AjaxResult result = AjaxResult.instance();
			result.addEntity(entity);

			return result.toJSONString();
		} catch (Exception ex) {
			throw new BIException("初始化加载Dashboard编辑器页面出现错误。", ex);
		}
	}
}
