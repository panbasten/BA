package com.plywet.platform.bi.web.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileType;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.plywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.plywet.platform.bi.component.components.breadCrumb.BreadCrumbNodeMeta;
import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.utils.FileUtils;
import com.plywet.platform.bi.core.utils.JSONUtils;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.delegates.enums.BIFileSystemCategory;
import com.plywet.platform.bi.delegates.vo.FilesysDirectory;
import com.plywet.platform.bi.delegates.vo.FilesysType;
import com.plywet.platform.bi.web.entity.AjaxResult;
import com.plywet.platform.bi.web.entity.AjaxResultEntity;
import com.plywet.platform.bi.web.service.BIFileSystemDelegate;

@Service("bi.resource.filesysResource")
@Path("/fs")
public class BIFileSystemResource {
	private final Logger log = Logger.getLogger(BIFileSystemResource.class);

	private static final String ID_EDITOR_CONTENT_NAVI_FILESYS_BC = "editorContent-navi-filesys-bc";
	private static final String ID_EDITOR_CONTENT_NAVI_FILESYS_BP = "editorContent-navi-filesys-bp";

	private static final String TEMPLATE_FILESYS_RENAME = "editor/filesys/rename.h";
	private static final String TEMPLATE_FILESYS_CREATE = "editor/filesys/create.h";
	private static final String TEMPLATE_FILESYS_UPLOAD = "editor/filesys/upload.h";

	@Resource(name = "bi.service.filesystemService")
	private BIFileSystemDelegate filesysService;

	/**
	 * 创建文件系统导航页面
	 * 
	 * @param repository
	 * @param act
	 * @return
	 * @throws BIException
	 */
	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentFileSystem(
			@CookieParam("repository") String repository,
			@QueryParam("act") String act) throws BIException {
		try {
			// 组装面包屑
			BreadCrumbMeta bce = new BreadCrumbMeta();
			BreadCrumbNodeMeta bcee = new BreadCrumbNodeMeta("0", "文件系统",
					"/fs/navi?act=flush", "");
			bce.addContent(bcee);
			bce.addEvent("click", "Plywet.browse.changeDir");

			AjaxResultEntity fileSysBCResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_FILESYS_BC).setCmd(
							"widget.BreadCrumb").setData(bce);

			// 组装浏览面板内容
			BrowseMeta browse = new BrowseMeta();

			List<FilesysType> filesysTypes = filesysService.getFilesysTypes();
			if (filesysTypes != null && !filesysTypes.isEmpty()) {
				for (FilesysType filesysType : filesysTypes) {
					BrowseNodeMeta node = new BrowseNodeMeta();
					node.setId(String.valueOf(filesysType.getId()));
					node.setCategory(filesysType.getCode());
					node.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME,
							filesysType.getDesc());
					node.addAttribute(HTML.ATTR_TYPE, Utils.DOM_NODE);
					node.addAttribute(HTML.ATTR_SRC, "/fs/root/"
							+ filesysType.getCode());
					node.addAttribute(BrowseNodeMeta.ATTR_ICON_STYLE, "ui-fs-"
							+ filesysType.getCode() + "-icon");

					node.addEvent("mouseup",
							"Plywet.browse.showOperationForDir");
					node.addEvent("dblclick", "Plywet.browse.changeDir");

					browse.addContent(node);

				}
			}

			browse.addClass("fly-browsepanel");

			// 组装结果对象
			AjaxResultEntity browseResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_FILESYS_BP).setData(browse);
			browseResult.setCmd("flush".equals(act) ? "this.flush"
					: "widget.BrowsePanel");

			return AjaxResult.instance().addEntity(fileSysBCResult).addEntity(
					browseResult).toJSONString();
		} catch (Exception ex) {
			log.error("创建导航的文件系统内容页面出现错误。");
			throw new BIException("创建导航的文件系统内容页面出现错误。", ex);
		}
	}

	/**
	 * 显示每个文件系统类别下的根数据
	 * 
	 * @param category
	 * @return
	 * @throws BIException
	 */
	@GET
	@Path("/root/{category}")
	@Produces(MediaType.APPLICATION_JSON)
	public String displayRoot(@PathParam("category") String category)
			throws BIException {
		try {
			// 组装面包屑
			BreadCrumbMeta bce = new BreadCrumbMeta();
			BreadCrumbNodeMeta bcee = new BreadCrumbNodeMeta("", "文件系统",
					"/fs/navi?act=flush", "");
			bce.addContent(bcee);

			bcee = new BreadCrumbNodeMeta("", BIFileSystemCategory
					.getDescByCategory(category), "/fs/root/" + category, "");

			bce.addContent(bcee);
			bce.addEvent("click", "Plywet.browse.changeDir");

			AjaxResultEntity fileSysBCResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_FILESYS_BC).setCmd(
							"widget.BreadCrumb").setData(bce);

			// 组装浏览面板数据
			BrowseMeta browse = new BrowseMeta();
			populateBrowseEles(browse, category);

			browse.addExtendAttribute("category", category);
			browse.addClass("fly-browsepanel");

			// 组装结果对象
			AjaxResultEntity browseResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_FILESYS_BP).setData(browse);
			browseResult.setCmd("this.flush");

			return AjaxResult.instance().addEntity(fileSysBCResult).addEntity(
					browseResult).toJSONString();
		} catch (Exception ex) {
			log.error("创建导航的文件系统内容页面出现错误。");
			throw new BIException("创建导航的文件系统内容页面出现错误。", ex);
		}
	}

	/**
	 * 拼装文件系统内容
	 * 
	 * @return
	 * @throws BIException
	 */
	private void populateBrowseEles(BrowseMeta browse, String category)
			throws BIException {
		BIFileSystemCategory cate = BIFileSystemCategory
				.getCategoryByCode(category);
		List<FilesysDirectory> filesysDirectorys = filesysService
				.getFilesysRoots(cate);
		if (filesysDirectorys != null && !filesysDirectorys.isEmpty()) {
			for (FilesysDirectory filesysDirectory : filesysDirectorys) {
				BrowseNodeMeta node = new BrowseNodeMeta();
				node.setId(String.valueOf(filesysDirectory.getId()));
				node.setCategory(category);
				node.setPath("/");
				node.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME,
						filesysDirectory.getDesc());
				node.addAttribute(HTML.ATTR_TYPE, Utils.DOM_NODE);
				node.addAttribute(HTML.ATTR_NAME, filesysDirectory.getPath());
				node.addAttribute(HTML.ATTR_SRC, "/fs/items/list/" + category);
				node.addAttribute(BrowseNodeMeta.ATTR_ICON_STYLE, "ui-fs-"
						+ category + "-icon");
				node.addExtendAttribute("rootId", String
						.valueOf(filesysDirectory.getId()));

				node.addEvent("mouseup", "Plywet.browse.showOperationForDir");
				node.addEvent("dblclick", "Plywet.browse.changeDir");

				browse.addContent(node);
			}
		}
	}

	/**
	 * 列出某category下某个目录的子
	 * 
	 * @param category
	 * @param dataStr
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/items/list/{category}")
	public String listDirectory(@PathParam("category") String category,
			@QueryParam("data") String dataStr) throws Exception {
		// 初始化变量
		JSONObject dataObj = JSONUtils.convertStringToJSONObject(dataStr);
		long rootId = Long.parseLong(dataObj.get("rootId").toString());
		String workDir = dataObj.get("path").toString();

		// 构造面包屑
		BreadCrumbMeta bce = new BreadCrumbMeta();
		bce.addEvent("click", "Plywet.browse.changeDir");
		BreadCrumbNodeMeta bcee = new BreadCrumbNodeMeta("0", "文件系统",
				"/fs/navi?act=flush", "");
		bce.addContent(bcee);

		bcee = new BreadCrumbNodeMeta("0", BIFileSystemCategory
				.getCategoryByCode(category).getDesc(), "/fs/root/" + category,
				"");
		bce.addContent(bcee);

		String rootDirName = filesysService.getRootDesc(category, rootId);
		bcee = new BreadCrumbNodeMeta("", rootDirName, "/fs/items/list/"
				+ category, "/");
		bcee.addExtendAttribute("rootId", String.valueOf(rootId));
		bce.addContent(bcee);
		bce.addEvent("click", "Plywet.browse.changeDir");

		List<String> dirs = FileUtils.dirSplit(workDir);

		for (String dir : dirs) {
			bcee = new BreadCrumbNodeMeta("", dir,
					"/fs/items/list/" + category, "/" + dir);
			bcee.addExtendAttribute("rootId", String.valueOf(rootId));
			bce.addContent(bcee);
			bce.addEvent("click", "Plywet.browse.changeDir");
		}

		AjaxResultEntity fileSysBCResult = AjaxResultEntity.instance()
				.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
						ID_EDITOR_CONTENT_NAVI_FILESYS_BC).setCmd(
						"widget.BreadCrumb").setData(bce);

		// 构造浏览面板内容
		FileObject fileObj = filesysService.composeVfsObject(category, workDir,
				rootId);
		FileObject[] children = fileObj.getChildren();
		BrowseMeta browse = new BrowseMeta();

		for (FileObject child : children) {
			boolean isFolder = child.getType().equals(FileType.FOLDER);
			BrowseNodeMeta node = new BrowseNodeMeta();
			node.setCategory(category);
			node.setPath(FileUtils.dirAppend(workDir, child.getName()
					.getBaseName()));
			node.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, child.getName()
					.getBaseName());
			node.addAttribute(HTML.ATTR_TYPE, isFolder ? Utils.DOM_NODE
					: Utils.DOM_LEAF);
			node.addAttribute(HTML.ATTR_NAME, child.getName().getBaseName());
			node.addAttribute(HTML.ATTR_SRC, "/fs/items/list/" + category);
			node.addAttribute(BrowseNodeMeta.ATTR_ICON_STYLE, "ui-fs-"
					+ category + "-icon");
			node.addExtendAttribute("rootId", String.valueOf(rootId));

			if (isFolder) {
				node.addEvent("mouseup", "Plywet.browse.showOperationForDir");
				node.addEvent("dblclick", "Plywet.browse.changeDir");
			} else {
				node.addEvent("mouseup", "Plywet.browse.showOperationForFile");
				node.addEvent("dblclick", "Plywet.browse.openFile");
			}
			browse.addContent(node);
		}

		browse.addExtendAttribute("path", workDir);
		browse.addExtendAttribute("rootId", String.valueOf(rootId));
		browse.addExtendAttribute("category", category);
		browse.addClass("fly-browsepanel");

		AjaxResultEntity browseResult = AjaxResultEntity.instance()
				.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
						ID_EDITOR_CONTENT_NAVI_FILESYS_BP).setData(browse);
		browseResult.setCmd("this.flush");

		return AjaxResult.instance().addEntity(fileSysBCResult).addEntity(
				browseResult).toJSONString();
	}

	/**
	 * 打开重命名设置页面
	 * 
	 * @param targetId
	 * @param srcName
	 *            原始名称
	 * @param dataStr
	 *            目录标识信息
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/items/rename")
	public String openRename(@QueryParam("targetId") String targetId,
			@QueryParam("srcName") String srcName,
			@QueryParam("data") String dataStr) throws Exception {
		FLYVariableResolver attrsMap = composeVariableMap(dataStr);
		attrsMap.addVariable("srcName", srcName);
		Object[] domString = PageTemplateInterpolator.interpolate(
				TEMPLATE_FILESYS_RENAME, attrsMap);

		AjaxResultEntity emptyEntity = new AjaxResultEntity();
		emptyEntity.setOperation(Utils.RESULT_OPERATION_EMPTY);
		emptyEntity.setTargetId(targetId);

		AjaxResultEntity content = AjaxResultEntity.instance().setOperation(
				Utils.RESULT_OPERATION_APPEND).setTargetId(targetId)
				.setDomAndScript(domString);

		return AjaxResult.instance().addEntity(emptyEntity).addEntity(content)
				.toJSONString();
	}

	/**
	 * 打开创建目录设置页面
	 * 
	 * @param targetId
	 *            页面容器ID
	 * @param dataStr
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/items/create")
	public String openCreate(@QueryParam("targetId") String targetId,
			@QueryParam("data") String dataStr) throws Exception {
		FLYVariableResolver attrsMap = composeVariableMap(dataStr);

		Object[] domString = PageTemplateInterpolator.interpolate(
				TEMPLATE_FILESYS_CREATE, attrsMap);

		AjaxResultEntity emptyEntity = new AjaxResultEntity();
		emptyEntity.setOperation(Utils.RESULT_OPERATION_EMPTY);
		emptyEntity.setTargetId(targetId);

		AjaxResultEntity content = AjaxResultEntity.instance().setOperation(
				Utils.RESULT_OPERATION_APPEND).setTargetId(targetId)
				.setDomAndScript(domString);

		return AjaxResult.instance().addEntity(emptyEntity).addEntity(content)
				.toJSONString();
	}

	/**
	 * 打开上传页面
	 * 
	 * @param targetId
	 * @param dataStr
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/items/upload")
	public String openUpload(@QueryParam("targetId") String targetId,
			@QueryParam("data") String dataStr) throws Exception {
		FLYVariableResolver attrsMap = composeVariableMap(dataStr);

		Object[] domString = PageTemplateInterpolator.interpolate(
				TEMPLATE_FILESYS_UPLOAD, attrsMap);

		AjaxResultEntity emptyEntity = new AjaxResultEntity();
		emptyEntity.setOperation(Utils.RESULT_OPERATION_EMPTY);
		emptyEntity.setTargetId(targetId);

		AjaxResultEntity content = AjaxResultEntity.instance().setOperation(
				Utils.RESULT_OPERATION_APPEND).setTargetId(targetId)
				.setDomAndScript(domString);

		return AjaxResult.instance().addEntity(emptyEntity).addEntity(content)
				.toJSONString();
	}

	private FLYVariableResolver composeVariableMap(String dataStr)
			throws Exception {
		JSONObject dataObj = JSONUtils.convertStringToJSONObject(dataStr);
		long rootId = Long.parseLong(dataObj.get("rootId").toString());
		String workPath = dataObj.get("path").toString();
		String category = dataObj.get("category").toString();

		FLYVariableResolver attrsMap = new FLYVariableResolver();
		attrsMap.addVariable("rootId", rootId);
		attrsMap.addVariable("path", workPath);
		attrsMap.addVariable("category", category);
		return attrsMap;
	}

}
