package com.flywet.platform.bi.di.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.flywet.platform.bi.component.components.tree.FLYTreeMeta;
import com.flywet.platform.bi.component.components.tree.TreeNodeMeta;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.component.web.AjaxResult;
import com.flywet.platform.bi.component.web.AjaxResultEntity;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.utils.PropertyUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.enums.BIDirectoryCategory;
import com.flywet.platform.bi.delegates.utils.BIWebUtils;
import com.flywet.platform.bi.di.function.DIFunctions;
import com.flywet.platform.bi.rest.AbastractDirectoryResource;
import com.flywet.platform.bi.services.intf.BIPageDelegates;

@Service("bi.resource.diResource")
@Path("/di")
public class BIDIResource extends AbastractDirectoryResource {
	private final Logger logger = Logger.getLogger(BIDIResource.class);

	private static Class<?> PKG = BIDIResource.class;

	public static final String ID_EDITOR_TRANS = "trans";
	public static final String ID_EDITOR_JOBS = "jobs";

	public static final String TRANS_TEMPLATE = "editor/editor_trans.h";
	public static final String ID_EDITOR_CONTENT_PANELS = "editorContentPanels";

	private static final BIDirectoryCategory DIR_CATEGORY = BIDirectoryCategory.DI;

	@Resource(name = "bi.service.pageServices")
	private BIPageDelegates pageDelegates;

	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentDI() throws BIException {
		// 注册方法
		DIFunctions.register();
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
	@Path("/dir/edit/{pid}/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openDirectoryEditDialog(@PathParam("pid") String pid,
			@PathParam("id") String id, @QueryParam("desc") String desc,
			@QueryParam("targetId") String targetId) throws BIException {
		return super.openDirectoryEditDialog(pid, id, desc, targetId);
	}

	@POST
	@Produces(BIWebUtils.TEXT_PLAIN_DEFAULT_CHARSET)
	@Path("/dir/editsubmit")
	public String openDirectoryEditSubmit(String body) throws BIJSONException {
		return super.openDirectoryEditSubmit(pageDelegates, body, DIR_CATEGORY);
	}

	@GET
	@Path("/dir/create/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openDirectoryCreateDialog(@PathParam("id") String id,
			@QueryParam("targetId") String targetId) throws BIException {
		return super.openDirectoryCreateDialog(id, targetId);
	}

	@POST
	@Produces(BIWebUtils.TEXT_PLAIN_DEFAULT_CHARSET)
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
			attrsMap.addVariable("editorId", ID_EDITOR_TRANS);
			attrsMap.addVariable("transStepBar", transStepBar);
			attrsMap.addVariable("transStepBrowses", transStepBrowses);

			FLYTreeMeta transObjectTree = new FLYTreeMeta();
			TreeNodeMeta node1 = new TreeNodeMeta();
			node1.setId("id1");
			node1.setText("数据库");
			transObjectTree.addContent(node1);

			TreeNodeMeta node11 = new TreeNodeMeta();
			node11.setId("id1-1");
			node11.setText("连接名称1");
			node1.addContent(node11);

			TreeNodeMeta node2 = new TreeNodeMeta();
			node2.setId("id2");
			node2.setText("转换");
			transObjectTree.addContent(node2);

			TreeNodeMeta node21 = new TreeNodeMeta();
			node21.setId("id2-1");
			node21.setText("转换名称1");
			node2.addContent(node21);

			attrsMap.addVariable("transObjectTree", transObjectTree);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TRANS_TEMPLATE, attrsMap);

			// 创建一个更新操作
			AjaxResultEntity entity = AjaxResultEntity.instance().setOperation(
					Utils.RESULT_OPERATION_APPEND).setTargetId(
					ID_EDITOR_CONTENT_PANELS).setDomAndScript(domString);

			AjaxResult result = AjaxResult.instance();
			result.addEntity(entity);

			return result.toJSONString();
		} catch (Exception ex) {
			logger.error("创建转换编辑器页面出现错误。");
			throw new BIException("创建转换编辑器页面出现错误。", ex);
		}
	}
}
