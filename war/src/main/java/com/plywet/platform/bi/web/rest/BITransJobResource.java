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
import org.pentaho.di.core.Const;
import org.springframework.stereotype.Service;

import com.plywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.exception.BIJSONException;
import com.plywet.platform.bi.core.utils.PropertyUtils;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.web.entity.ActionMessage;
import com.plywet.platform.bi.web.entity.AjaxResult;
import com.plywet.platform.bi.web.entity.AjaxResultEntity;
import com.plywet.platform.bi.web.model.ParameterContext;
import com.plywet.platform.bi.web.service.BIPageDelegates;
import com.plywet.platform.bi.web.service.impl.BIPageServices;
import com.plywet.platform.bi.web.utils.BIWebUtils;

@Service("bi.resource.transJobResource")
@Path("/transjob")
public class BITransJobResource {
	private final Logger logger = Logger.getLogger(BITransJobResource.class);

	private static Class<?> PKG = BITransJobResource.class;

	public static final String TRANS_TEMPLATE = "editor/editor_trans.h";

	private static final String TEMPLATE_TRANS_JOB_FOLDER_CREATE = "editor/transjob/folder_create.h";

	private static final String ID_EDITOR_CONTENT_NAVI_TRANS_BC = "editorContent-navi-trans-bc";
	private static final String ID_EDITOR_CONTENT_NAVI_TRANS_BP = "editorContent-navi-trans-bp";

	@Resource(name = "bi.service.pageServices")
	private BIPageDelegates pageDelegates;

	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentTrans(
			@CookieParam("repository") String repository) throws BIException {
		return buildNaviContent(repository, BIPageServices.DIRECTORY_ROOT_ID,
				true);
	}

	@GET
	@Path("/dir/create/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openDirectoryCreateDialog(
			@CookieParam("repository") String repository,
			@PathParam("id") String id, @QueryParam("targetId") String targetId)
			throws BIException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();
			attrsMap.addVariable("dirId", id);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TEMPLATE_TRANS_JOB_FOLDER_CREATE, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			logger.error("打开创建目录界面出现问题。");
		}
		return ActionMessage.instance().failure("打开创建目录界面出现问题。").toJSONString();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dir/createsubmit")
	public String openDirectoryCreateSubmit(String body) throws BIJSONException {
		ActionMessage am = new ActionMessage();
		try {
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);

			String desc = paramContext.getParameter("desc");

			long dirId = paramContext.getIntParameter("dirId", 0);

			if (Const.isEmpty(desc)) {
				return am.addErrorMessage("新增目录名称不能为空。").toJSONString();
			}

			am.addMessage("新增目录成功");
		} catch (Exception e) {
			am.addErrorMessage("新增目录出现错误。");
		}
		return am.toJSONString();
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
			BreadCrumbMeta bce = pageDelegates.getParentDirectories(repository,
					idL);
			AjaxResultEntity transBCResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_TRANS_BC).setCmd(
							isNew ? "widget.BreadCrumb" : "this.flush")
					.setData(bce);

			// 2.填充浏览面板内容
			BrowseMeta browseMeta = new BrowseMeta();
			pageDelegates.getSubDirectory(repository, idL, browseMeta);
			pageDelegates.getSubDirectoryObject(repository, idL, browseMeta);
			browseMeta.addClass("fly-browsepanel");

			// 添加额外属性
			browseMeta.addExtendAttribute("dirId", String.valueOf(idL));

			AjaxResultEntity browseResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_TRANS_BP).setCmd(
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
					.getPluginNavigator(Utils.CATEGORY_TRANS);
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
			throw new BIException("创建转换编辑器页面出现错误。", ex);
		}
	}
}
