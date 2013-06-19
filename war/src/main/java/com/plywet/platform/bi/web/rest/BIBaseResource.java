package com.plywet.platform.bi.web.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.utils.JSONUtils;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.delegates.vo.FunctionType;
import com.plywet.platform.bi.web.entity.AjaxResult;
import com.plywet.platform.bi.web.entity.AjaxResultEntity;
import com.plywet.platform.bi.web.service.BIPageDelegates;

@Service("bi.resource.baseResource")
@Path("/base")
public class BIBaseResource {
	public static final String NAVIGATOR_TEMPLATE = "editor/editor_navi.h";

	public static final String TEMPLATE_SYS_LOGIN_SLIDE = "editor/sys/login_slide.h";

	public static final String ID_EDITOR_CONTENT_PANELS = "editorContentPanels";

	public static final String ID_EDITOR_NAVIS = "navis";
	public static final String ID_EDITOR_TRANS = "trans";
	public static final String ID_EDITOR_JOBS = "jobs";

	@Resource(name = "bi.service.pageServices")
	private BIPageDelegates pageDelegates;

	@GET
	@Path("/navigatorpage")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviPage(@CookieParam("repository") String repository)
			throws BIException {
		try {
			List<FunctionType> funcs = pageDelegates
					.getNavigatorsLevelOne(repository);

			FLYVariableResolver attrsMap = FLYVariableResolver.instance();
			attrsMap.addVariable("editorId", ID_EDITOR_NAVIS);
			attrsMap.addVariable("browseEntities", funcs);

			Object[] domString = PageTemplateInterpolator.interpolate(
					NAVIGATOR_TEMPLATE, attrsMap);

			// 1.为一级导航页面创建一个更新操作
			AjaxResultEntity naviResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_APPEND).setTargetId(
							ID_EDITOR_CONTENT_PANELS)
					.setDomAndScript(domString);

			return AjaxResult.instance().addEntity(naviResult).toJSONString();
		} catch (Exception ex) {
			throw new BIException("创建导航页面出现错误。", ex);
		}
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/slides")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSlideShows() throws BIException {
		try {
			// 获得登陆滚动页面
			Document doc = PageTemplateInterpolator
					.getDom(TEMPLATE_SYS_LOGIN_SLIDE);
			Object[] domString = PageTemplateInterpolator.interpolate(
					TEMPLATE_SYS_LOGIN_SLIDE, doc, FLYVariableResolver
							.instance());

			JSONObject jo = new JSONObject();
			jo.put("dom", (String) domString[0]);
			jo.put("script", JSONUtils
					.convertToJSONArray((List<String>) domString[1]));

			return jo.toJSONString();
		} catch (Exception ex) {
			throw new BIException("活动资源库命名出现错误。", ex);
		}
	}
}
