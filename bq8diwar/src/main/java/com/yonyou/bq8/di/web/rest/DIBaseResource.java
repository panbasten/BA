package com.yonyou.bq8.di.web.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.PageTemplateInterpolator;
import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.core.utils.Utils;
import com.yonyou.bq8.di.delegates.vo.FunctionType;
import com.yonyou.bq8.di.web.entity.AjaxResult;
import com.yonyou.bq8.di.web.entity.AjaxResultEntity;
import com.yonyou.bq8.di.web.service.DIPageDelegates;

@Service("di.resource.baseResource")
@Path("/base")
public class DIBaseResource {
	public static final String NAVIGATOR_TEMPLATE = "editor/editor_navi.h";

	public static final String ID_EDITOR_CONTENT_PANELS = "editorContentPanels";

	public static final String ID_EDITOR_NAVIS = "navis";
	public static final String ID_EDITOR_TRANS = "trans";
	public static final String ID_EDITOR_JOBS = "jobs";
	public static final String ID_EDITOR_FORM = "form";

	@Resource(name = "di.service.pageServices")
	private DIPageDelegates pageDelegates;

	@GET
	@Path("/navigatorpage")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviPage(@CookieParam("repository") String repository)
			throws DIException {
		try {
			List<FunctionType> funcs = pageDelegates
					.getNavigatorsLevelOne(repository);

			BQVariableResolver attrsMap = BQVariableResolver.instance();
			attrsMap.addVariable("editorId", ID_EDITOR_NAVIS);
			attrsMap.addVariable("browseEntities", funcs);

			Object[] domString = PageTemplateInterpolator.interpolate(
					NAVIGATOR_TEMPLATE, attrsMap);

			// 1.为一级导航页面创建一个更新操作
			AjaxResultEntity naviResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_APPEND).setTargetId(
							ID_EDITOR_CONTENT_PANELS).setDomAndScript(domString);

			return AjaxResult.instance().addEntity(naviResult).toJSONString();
		} catch (Exception ex) {
			throw new DIException("创建导航页面出现错误。", ex);
		}
	}

	/**
	 * 创建用户信息弹出页
	 * 
	 * @throws DIException
	 */
	@GET
	@Path("/usersettingpage")
	@Produces(MediaType.TEXT_PLAIN)
	public void createUserSettingPage() throws DIException {
		try {
		} catch (Exception ex) {
			throw new DIException("创建用户信息弹出页出现错误。", ex);
		}
	}
}
