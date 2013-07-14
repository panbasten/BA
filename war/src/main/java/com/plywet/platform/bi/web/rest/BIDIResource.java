package com.plywet.platform.bi.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.exception.BIJSONException;
import com.plywet.platform.bi.core.utils.PropertyUtils;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.delegates.enums.BIDirectoryCategory;
import com.plywet.platform.bi.web.entity.AjaxResult;
import com.plywet.platform.bi.web.entity.AjaxResultEntity;
import com.plywet.platform.bi.web.functions.DIFunctions;
import com.plywet.platform.bi.web.service.BIPageDelegates;

@Service("bi.resource.transJobResource")
@Path("/di")
public class BIDIResource extends AbastractDirectoryResource {
	private final Logger logger = Logger.getLogger(BIDIResource.class);

	private static Class<?> PKG = BIDIResource.class;

	public static final String TRANS_TEMPLATE = "editor/editor_trans.h";

	private static final BIDirectoryCategory DIR_CATEGORY = BIDirectoryCategory.DI;

	@Resource(name = "bi.service.pageServices")
	private BIPageDelegates pageDelegates;

	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentDI(
			@CookieParam("repository") String repository) throws BIException {
		// 注册方法
		DIFunctions.register();
		return super.buildNaviContent(pageDelegates, repository, DIR_CATEGORY
				.getRootId(), DIR_CATEGORY, true);
	}

	@GET
	@Path("/dir/remove/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeDirectory(@CookieParam("repository") String repository,
			@PathParam("id") String id) throws BIException {
		return super.removeDirectory(pageDelegates, repository, id,
				DIR_CATEGORY);
	}

	@GET
	@Path("/dir/create/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openDirectoryCreateDialog(
			@CookieParam("repository") String repository,
			@PathParam("id") String id, @QueryParam("targetId") String targetId)
			throws BIException {
		return super.openDirectoryCreateDialog(repository, id, targetId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dir/createsubmit")
	public String openDirectoryCreateSubmit(
			@CookieParam("repository") String repository, String body)
			throws BIJSONException {
		return super.openDirectoryCreateSubmit(pageDelegates, repository, body,
				DIR_CATEGORY);
	}

	@GET
	@Path("/dir/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String flushNaviContent(
			@CookieParam("repository") String repository,
			@PathParam("id") String id) throws BIException {
		return super.buildNaviContent(pageDelegates, repository, Long
				.parseLong(id), DIR_CATEGORY, false);
	}

	/**
	 * 创建转换编辑器页面
	 * 
	 * @throws BIException
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/trans/editor")
	@Produces(MediaType.TEXT_PLAIN)
	public String createTransPage() throws BIException {
		try {
			// 生成插件工具栏
			List<String> transStepBar = pageDelegates
					.getPluginNavigator(Utils.CATEGORY_DI_TRANS);
			List<BrowseMeta> transStepBrowses = new ArrayList<BrowseMeta>();

			for (int i = 0; i < transStepBar.size(); i++) {

				// 各个导航项下的组件
				BrowseMeta browseMeta = new BrowseMeta();

				pageDelegates.getPlugins(transStepBar.get(i), browseMeta,
						"fly-trans-step-plugin");

				// 增加额外属性
				List<BrowseNodeMeta> contents = (List<BrowseNodeMeta>) browseMeta
						.getContents();
				if (contents != null) {
					for (BrowseNodeMeta con : contents) {
						String id = con.getId();
						String pos = PropertyUtils
								.getProperty(PropertyUtils.UI_DIALOG_PREFIX
										+ id
										+ PropertyUtils.UI_DIALOG_POSTION_SUFFIX);
						if (pos != null) {
							con.addExtendAttribute("dialogPosition", pos);
						}
					}
				}

				transStepBrowses.add(browseMeta);
			}

			FLYVariableResolver attrsMap = FLYVariableResolver.instance();
			attrsMap.addVariable("editorId", BIBaseResource.ID_EDITOR_TRANS);
			attrsMap.addVariable("transStepBar", transStepBar);
			attrsMap.addVariable("transStepBrowses", transStepBrowses);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TRANS_TEMPLATE, attrsMap);

			// 创建一个更新操作
			AjaxResultEntity entity = AjaxResultEntity.instance().setOperation(
					Utils.RESULT_OPERATION_APPEND).setTargetId(
					BIBaseResource.ID_EDITOR_CONTENT_PANELS).setDomAndScript(
					domString);

			AjaxResult result = AjaxResult.instance();
			result.addEntity(entity);

			return result.toJSONString();
		} catch (Exception ex) {
			logger.error("创建转换编辑器页面出现错误。");
			throw new BIException("创建转换编辑器页面出现错误。", ex);
		}
	}
}
