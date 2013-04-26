package com.plywet.platform.bi.web.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.delegates.vo.FunctionType;
import com.plywet.platform.bi.web.entity.AjaxResult;
import com.plywet.platform.bi.web.entity.AjaxResultEntity;
import com.plywet.platform.bi.web.service.BIPageDelegates;

@Service("bi.resource.baseResource")
@Path("/base")
public class BIBaseResource {
	public static final String NAVIGATOR_TEMPLATE = "editor/editor_navi.h";

	public static final String ID_EDITOR_CONTENT_PANELS = "editorContentPanels";

	public static final String ID_EDITOR_NAVIS = "navis";
	public static final String ID_EDITOR_TRANS = "trans";
	public static final String ID_EDITOR_JOBS = "jobs";
	public static final String ID_EDITOR_DASHBOARD = "dashboard";

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

	/**
	 * 创建用户信息弹出页
	 * 
	 * @throws BIException
	 */
	@GET
	@Path("/usersettingpage")
	@Produces(MediaType.TEXT_PLAIN)
	public void createUserSettingPage() throws BIException {
		try {
		} catch (Exception ex) {
			throw new BIException("创建用户信息弹出页出现错误。", ex);
		}
	}
}
