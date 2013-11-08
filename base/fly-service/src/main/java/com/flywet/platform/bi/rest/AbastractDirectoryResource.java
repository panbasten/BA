package com.flywet.platform.bi.rest;

import org.apache.log4j.Logger;
import org.pentaho.di.core.Const;

import com.flywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.component.web.ActionMessage;
import com.flywet.platform.bi.component.web.AjaxResult;
import com.flywet.platform.bi.component.web.AjaxResultEntity;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.model.ParameterContext;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.enums.BIDirectoryCategory;
import com.flywet.platform.bi.delegates.utils.BIWebUtils;
import com.flywet.platform.bi.services.intf.BIPageDelegates;

public class AbastractDirectoryResource {

	private final Logger logger = Logger
			.getLogger(AbastractDirectoryResource.class);

	private static final String ID_EDITOR_CONTENT_NAVI_PREFIX = "editorContent-navi-";
	private static final String ID_EDITOR_CONTENT_NAVI_BC = "-bc";
	private static final String ID_EDITOR_CONTENT_NAVI_BP = "-bp";

	private static Class<?> PKG = AbastractDirectoryResource.class;

	private static final String TEMPLATE_FOLDER_CREATE = "editor/sys/folder_create.h";
	private static final String TEMPLATE_FOLDER_EDIT = "editor/sys/folder_edit.h";

	/**
	 * 删除一个目录
	 * 
	 * @param pageDelegates
	 * @param id
	 * @return
	 * @throws BIException
	 */
	protected String removeDirectory(BIPageDelegates pageDelegates, String id,
			BIDirectoryCategory cate) throws BIException {
		ActionMessage am = new ActionMessage();
		try {
			pageDelegates.removeDirectoryObject(Long.valueOf(id), cate);
		} catch (BIException e) {
			logger.error(e.getMessage());
			am.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("删除目录出现问题。");
			am.addErrorMessage("删除目录出现问题。");
		}

		return am.toJSONString();
	}

	/**
	 * 编辑目录对话框
	 * 
	 * @param pid
	 * @param id
	 * @param targetId
	 * @return
	 * @throws BIException
	 */
	protected String openDirectoryEditDialog(String pid, String id,
			String desc, String targetId) throws BIException {
		String msg = "";
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();
			attrsMap.addVariable("pDirId", pid);
			attrsMap.addVariable("dirId", id);
			attrsMap.addVariable("desc", desc);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TEMPLATE_FOLDER_EDIT, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (BIException e) {
			logger.error(e.getMessage());
			msg = e.getMessage();
		} catch (Exception e) {
			logger.error("打开编辑目录界面出现问题。");
			msg = "打开编辑目录界面出现问题。";
		}
		return ActionMessage.instance().failure(msg).toJSONString();
	}

	/**
	 * 编辑目录对话框的提交操作
	 * 
	 * @param pageDelegates
	 * @param body
	 * @param cate
	 * @return
	 * @throws BIJSONException
	 */
	protected String openDirectoryEditSubmit(BIPageDelegates pageDelegates,
			String body, BIDirectoryCategory cate) throws BIJSONException {
		ActionMessage am = new ActionMessage();
		try {
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);

			String desc = paramContext.getParameter("desc");

			// 默认是根目录
			long pDirId = paramContext.getLongParameter("pDirId");
			long dirId = paramContext.getLongParameter("dirId");

			if (Const.isEmpty(desc)) {
				return am.addErrorMessage("新增目录名称不能为空。").toJSONString();
			}

			// 保存目录
			pageDelegates.editDirectoryObject(pDirId, dirId, desc, cate);

			am.addMessage("新增目录成功");
		} catch (Exception e) {
			am.addErrorMessage("新增目录出现错误。");
		}
		return am.toJSONString();
	}

	/**
	 * 新建目录对话框
	 * 
	 * @param id
	 * @param targetId
	 * @return
	 * @throws BIException
	 */
	protected String openDirectoryCreateDialog(String id, String targetId)
			throws BIException {
		String msg = "";
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();
			attrsMap.addVariable("dirId", id);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TEMPLATE_FOLDER_CREATE, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (BIException e) {
			logger.error(e.getMessage());
			msg = e.getMessage();
		} catch (Exception e) {
			logger.error("打开创建目录界面出现问题。");
			msg = "打开创建目录界面出现问题。";
		}
		return ActionMessage.instance().failure(msg).toJSONString();
	}

	/**
	 * 新建目录对话框的提交操作
	 * 
	 * @param pageDelegates
	 * @param body
	 * @param rootId
	 * @return
	 * @throws BIJSONException
	 */
	protected String openDirectoryCreateSubmit(BIPageDelegates pageDelegates,
			String body, BIDirectoryCategory cate) throws BIJSONException {
		ActionMessage am = new ActionMessage();
		try {
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);

			String desc = paramContext.getParameter("desc");

			// 默认是根目录
			long dirId = paramContext.getLongParameter("dirId", cate
					.getRootId());

			if (Const.isEmpty(desc)) {
				return am.addErrorMessage("新增目录名称不能为空。").toJSONString();
			}

			// 保存目录
			pageDelegates.newDirectoryObject(dirId, desc, cate);

			am.addMessage("新增目录成功");
		} catch (Exception e) {
			am.addErrorMessage("新增目录出现错误。");
		}
		return am.toJSONString();
	}

	protected String buildNaviContent(BIPageDelegates pageDelegates, Long idL,
			BIDirectoryCategory category, boolean isNew) throws BIException {
		try {

			// 1.为转换的面包屑页面创建一个自定义操作
			BreadCrumbMeta bce = pageDelegates.getParentDirectories(idL,
					category);
			AjaxResultEntity transBCResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_PREFIX + category.getId()
									+ ID_EDITOR_CONTENT_NAVI_BC).setCmd(
							isNew ? "widget.BreadCrumb" : "this.flush")
					.setData(bce);

			// 2.填充浏览面板内容
			BrowseMeta browseMeta = new BrowseMeta();
			pageDelegates.getSubDirectory(idL, browseMeta, category);
			pageDelegates.getSubDirectoryObject(idL, browseMeta, category);
			browseMeta.addClass("fly-browsepanel");

			// 添加额外属性
			browseMeta.addExtendAttribute("dirId", String.valueOf(idL));

			AjaxResultEntity browseResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_PREFIX + category.getId()
									+ ID_EDITOR_CONTENT_NAVI_BP).setCmd(
							isNew ? "widget.BrowsePanel" : "this.flush")
					.setData(browseMeta);

			return AjaxResult.instance().addEntity(transBCResult).addEntity(
					browseResult).toJSONString();
		} catch (Exception ex) {
			logger.error("创建导航的转换内容页面出现错误。");
			throw new BIException("创建导航的转换内容页面出现错误。", ex);
		}
	}
}
