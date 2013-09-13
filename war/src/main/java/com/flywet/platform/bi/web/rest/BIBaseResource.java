package com.flywet.platform.bi.web.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.vo.FunctionType;
import com.flywet.platform.bi.web.entity.AjaxResult;
import com.flywet.platform.bi.web.entity.AjaxResultEntity;
import com.flywet.platform.bi.web.service.BIPageDelegates;

@Service("bi.resource.baseResource")
@Path("/base")
public class BIBaseResource {
	public static final String NAVIGATOR_TEMPLATE = "editor/editor_navi.h";

	public static final String ID_EDITOR_CONTENT_PANELS = "editorContentPanels";

	public static final String ID_EDITOR_NAVIS = "navis";
	public static final String ID_EDITOR_TRANS = "trans";
	public static final String ID_EDITOR_JOBS = "jobs";

	@Resource(name = "bi.service.pageServices")
	private BIPageDelegates pageDelegates;

	@GET
	@Path("/navigatorpage")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviPage() throws BIException {
		try {
			List<FunctionType> funcs = pageDelegates.getNavigatorsLevelOne();

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

}
