package com.flywet.platform.bi.web.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.component.utils.PageTemplateResolverType;
import com.flywet.platform.bi.component.vo.ComponentPlugin;
import com.flywet.platform.bi.component.web.AjaxResult;
import com.flywet.platform.bi.component.web.AjaxResultEntity;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.enums.BIDirectoryCategory;
import com.flywet.platform.bi.delegates.enums.BIReportCategory;
import com.flywet.platform.bi.services.intf.BIPageDelegates;
import com.flywet.platform.bi.services.intf.BIReportDelegates;

@Service("bi.resource.reportResource")
@Path("/report")
public class BIReportResource extends AbastractDirectoryResource {
	private final Logger logger = Logger.getLogger(BIReportResource.class);

	private static final String ICON_PATH = "resources/images/plugins/";

	public static final String DASHBOARD_TEMPLATE_PREFIX = "editor/editor_";

	public final static long DIRECTORY_ROOT_ID = 1L;

	@Resource(name = "bi.service.reportService")
	private BIReportDelegates reportService;

	@Resource(name = "bi.service.pageServices")
	private BIPageDelegates pageDelegates;

	private static final BIDirectoryCategory DIR_CATEGORY = BIDirectoryCategory.REPORT;

	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentReport() throws BIException {
		return super.buildNaviContent(pageDelegates, DIR_CATEGORY.getRootId(),
				DIR_CATEGORY, true);
	}

	@GET
	@Path("/dir/remove/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeDirectory(@PathParam("id") String id)
			throws BIException {
		return super.removeDirectory(pageDelegates, id, DIR_CATEGORY);
	}

	@GET
	@Path("/dir/create/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openDirectoryCreateDialog(@PathParam("id") String id,
			@QueryParam("targetId") String targetId) throws BIException {
		return super.openDirectoryCreateDialog(id, targetId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dir/createsubmit")
	public String openDirectoryCreateSubmit(String body) throws BIJSONException {
		return super.openDirectoryCreateSubmit(pageDelegates, body,
				DIR_CATEGORY);
	}

	@GET
	@Path("/dir/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String flushNaviContent(@PathParam("id") String id)
			throws BIException {
		return super.buildNaviContent(pageDelegates, Long.parseLong(id),
				DIR_CATEGORY, false);
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
			String cate = BIReportCategory.REPORT_TYPE_DASHBOARD.getCategory();

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
					plugin.addClass("fly-" + cate + "-step-plugin");
					plugin.addAttribute(HTML.ATTR_TYPE, Utils.DOM_LEAF);
					plugin.addAttribute(HTML.ATTR_ICON, ICON_PATH
							+ cp.getIconfile());
					plugin.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, cp
							.getDescription());
					plugin.addAttribute(HTML.ATTR_TITLE, cp.getTooltip());

					Map dataMap = new HashMap();
					dataMap.put("props", cp.getAttributesJSONArray());
					JSONArray ja = cp.getSignalsJSONArray();
					if (ja != null) {
						dataMap.put("signals", ja);
					}
					ja = cp.getSlotsJSONArray();
					if (ja != null) {
						dataMap.put("slots", ja);
					}
					plugin.setData(dataMap);

					browseMeta.addContent(plugin);
				}

				dashboardStepBrowses.add(browseMeta);
			}

			FLYVariableResolver attrsMap = FLYVariableResolver.instance();
			attrsMap.addVariable("editorId", cate);
			attrsMap.addVariable("dashboardStepBar", dashboardStepBar);
			attrsMap.addVariable("dashboardStepBrowses", dashboardStepBrowses);

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
			throw new BIException("初始化加载Dashboard编辑器页面出现错误。", ex);
		}
	}

	private String getTemplateString(String cate) {
		return DASHBOARD_TEMPLATE_PREFIX + cate + ".h";
	}
}
