package com.plywet.platform.bi.web.rest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileType;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.springframework.stereotype.Service;

import com.plywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.plywet.platform.bi.component.components.breadCrumb.BreadCrumbNodeMeta;
import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.exception.BIJSONException;
import com.plywet.platform.bi.core.utils.FileUtils;
import com.plywet.platform.bi.core.utils.JSONUtils;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.delegates.enums.BIFileSystemCategory;
import com.plywet.platform.bi.delegates.vo.FilesysDirectory;
import com.plywet.platform.bi.delegates.vo.FilesysType;
import com.plywet.platform.bi.web.entity.ActionMessage;
import com.plywet.platform.bi.web.entity.AjaxResult;
import com.plywet.platform.bi.web.entity.AjaxResultEntity;
import com.plywet.platform.bi.web.model.ParameterContext;
import com.plywet.platform.bi.web.service.BIFileSystemDelegate;
import com.plywet.platform.bi.web.utils.BIWebUtils;

@Service("bi.resource.filesysResource")
@Path("/fs")
public class BIFileSystemResource {
	private final Logger log = Logger.getLogger(BIFileSystemResource.class);

	private static final String ID_EDITOR_CONTENT_NAVI_FILESYS_BC = "editorContent-navi-filesys-bc";
	private static final String ID_EDITOR_CONTENT_NAVI_FILESYS_BP = "editorContent-navi-filesys-bp";

	private static final String TEMPLATE_FILESYS_EDIT = "editor/filesys/edit.h";
	private static final String TEMPLATE_FILESYS_RENAME = "editor/filesys/rename.h";
	private static final String TEMPLATE_FILESYS_CREATE = "editor/filesys/create.h";

	private static final String TEMPLATE_FILESYS_FOLDER_CREATE = "editor/filesys/folder_create.h";

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
			browseResult.addScript("Plywet.filesys.controlBtn();");

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
			browseResult.addScript("Plywet.filesys.controlBtn('root');");

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
	 * 列出某category下某个目录的子页面
	 * 
	 * @param category
	 * @param dataStr
	 * @return
	 * @throws BIJSONException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/items/list/{category}")
	public String listDirectory(@PathParam("category") String category,
			@QueryParam("data") String dataStr) throws BIJSONException {
		String msg = "";
		try {
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
					.getCategoryByCode(category).getDesc(), "/fs/root/"
					+ category, "");
			bce.addContent(bcee);

			String rootDirName = filesysService.getRootDesc(category, rootId);
			bcee = new BreadCrumbNodeMeta("", rootDirName, "/fs/items/list/"
					+ category, "/");
			bcee.addExtendAttribute("rootId", String.valueOf(rootId));
			bce.addContent(bcee);
			bce.addEvent("click", "Plywet.browse.changeDir");

			List<String> dirs = FileUtils.dirSplit(workDir);

			for (int i = 0; i < dirs.size(); i++) {
				String dir = "";
				for (int j = 0; j <= i; j++) {
					dir += ("/" + dirs.get(j));
				}
				bcee = new BreadCrumbNodeMeta("", dirs.get(i),
						"/fs/items/list/" + category, dir);
				bcee.addExtendAttribute("rootId", String.valueOf(rootId));
				bce.addContent(bcee);
				bce.addEvent("click", "Plywet.browse.changeDir");
			}

			AjaxResultEntity fileSysBCResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_FILESYS_BC).setCmd(
							"widget.BreadCrumb").setData(bce);

			// 构造浏览面板内容
			FileObject fileObj = filesysService.composeVfsObject(category,
					workDir, rootId);
			FileObject[] children = fileObj.getChildren();

			// 排序
			Arrays.sort(children, new FileObjectComparator());

			BrowseMeta browse = new BrowseMeta();

			for (FileObject child : children) {
				boolean isFolder = child.getType().equals(FileType.FOLDER);
				BrowseNodeMeta node = new BrowseNodeMeta();
				node.setCategory(category);
				node.setPath(FileUtils.dirAppend(workDir, child.getName()
						.getBaseName()));
				node.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, child
						.getName().getBaseName());
				node.addAttribute(HTML.ATTR_TYPE, isFolder ? Utils.DOM_NODE
						: Utils.DOM_LEAF);
				node
						.addAttribute(HTML.ATTR_NAME, child.getName()
								.getBaseName());
				node.addAttribute(HTML.ATTR_SRC, "/fs/items/list/" + category);
				node.addExtendAttribute("rootId", String.valueOf(rootId));

				if (isFolder) {
					node.addAttribute(BrowseNodeMeta.ATTR_ICON_STYLE, "ui-fs-"
							+ category + "-folder-icon");
					node.addEvent("mouseup",
							"Plywet.browse.showOperationForDir");
					node.addEvent("dblclick", "Plywet.browse.changeDir");
				} else {
					String ext = child.getName().getExtension();
					if (!Const.isEmpty(ext)) {
						node.addAttribute(BrowseNodeMeta.ATTR_ICON_STYLE,
								"ui-fs-file-" + ext.toLowerCase() + "-icon");
					}
					node.addEvent("mouseup",
							"Plywet.browse.showOperationForFile");
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
			browseResult.addScript("Plywet.filesys.controlBtn('item');");

			return AjaxResult.instance().addEntity(fileSysBCResult).addEntity(
					browseResult).toJSONString();
		} catch (FileSystemException e) {
			msg = "获得文件夹列表出现错误，可能该文件夹不存在或者没有访问权限。";
			log.error(msg);
		} catch (Exception e) {
			msg = "获得文件夹列表出现错误。";
			log.error(msg);
		}

		return ActionMessage.instance().failure(msg).toJSONString();
	}

	/**
	 * 为具体文件系统添加一个文件夹，弹出一个创建页面
	 * 
	 * @param category
	 * @param targetId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/items/folder/create/{category}")
	public String openFolderCreateDialog(
			@PathParam("category") String category,
			@QueryParam("targetId") String targetId,
			@QueryParam("rootId") String rootId, @QueryParam("path") String path)
			throws Exception {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();
			attrsMap.addVariable("category", category);
			attrsMap.addVariable("rootId", rootId);
			attrsMap.addVariable("path", path);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TEMPLATE_FILESYS_FOLDER_CREATE, attrsMap);

			// 设置响应
			AjaxResultEntity emptyEntity = new AjaxResultEntity();
			emptyEntity.setOperation(Utils.RESULT_OPERATION_EMPTY);
			emptyEntity.setTargetId(targetId);

			AjaxResultEntity content = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_APPEND).setTargetId(
							targetId).setDomAndScript(domString);

			return AjaxResult.instance().addEntity(emptyEntity).addEntity(
					content).toJSONString();
		} catch (Exception e) {
			log.error("打开新增界面出现问题。");
		}
		return ActionMessage.instance().failure("打开新增界面出现问题。").toJSONString();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/items/folder/createsubmit")
	public String openFolderCreateSubmit(String body) throws BIJSONException {
		ActionMessage am = new ActionMessage();
		try {
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);

			String category = paramContext.getParameter("category");
			String desc = paramContext.getParameter("desc");
			long rootId = Long.parseLong(paramContext.getParameter("rootId"));
			String path = paramContext.getParameter("path");

			if (Const.isEmpty(desc)) {
				return am.addErrorMessage("新增目录名称不能为空。").toJSONString();
			}

			FileObject fileObj = filesysService.composeVfsObject(category, path
					+ "/" + desc, rootId);
			fileObj.createFolder();
			am.addMessage("新增目录成功");
		} catch (Exception e) {
			am.addErrorMessage("新增目录出现错误。");
		}
		return am.toJSONString();
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
	@Path("/items/create/{category}")
	public String openCreateDialog(@PathParam("category") String category,
			@QueryParam("targetId") String targetId) throws Exception {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();
			attrsMap.addVariable("category", category);

			FilesysDirectory dir = FilesysDirectory.instance();
			attrsMap.addVariable("dir", dir);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TEMPLATE_FILESYS_CREATE, attrsMap);

			// 设置响应
			AjaxResultEntity emptyEntity = new AjaxResultEntity();
			emptyEntity.setOperation(Utils.RESULT_OPERATION_EMPTY);
			emptyEntity.setTargetId(targetId);

			AjaxResultEntity content = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_APPEND).setTargetId(
							targetId).setDomAndScript(domString);

			return AjaxResult.instance().addEntity(emptyEntity).addEntity(
					content).toJSONString();
		} catch (Exception e) {
			log.error("打开新增界面出现问题。");
		}
		return ActionMessage.instance().failure("打开新增界面出现问题。").toJSONString();

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/items/createsubmit")
	public String openCreateSubmit(String body) throws BIJSONException {
		ActionMessage am = new ActionMessage();
		try {
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);

			String category = paramContext.getParameter("category");
			String path = paramContext.getParameter("path");
			String desc = paramContext.getParameter("desc");
			String notes = paramContext.getParameter("notes");

			FilesysDirectory dir = FilesysDirectory.instance().setPath(path)
					.setDesc(desc).setNotes(notes);
			filesysService.createFilesysObject(category, dir);
			am.addMessage("新增目录成功");
		} catch (Exception e) {
			am.addErrorMessage("新增目录出现错误。");
		}
		return am.toJSONString();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/items/edit/{category}/{id}")
	public String openEditDialog(@PathParam("id") String id,
			@PathParam("category") String category,
			@QueryParam("targetId") String targetId) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();
			attrsMap.addVariable("id", id);
			attrsMap.addVariable("category", category);

			FilesysDirectory dir = filesysService.getFilesysObject(category,
					Long.valueOf(id));
			attrsMap.addVariable("dir", dir);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TEMPLATE_FILESYS_EDIT, attrsMap);

			// 设置响应
			AjaxResultEntity emptyEntity = new AjaxResultEntity();
			emptyEntity.setOperation(Utils.RESULT_OPERATION_EMPTY);
			emptyEntity.setTargetId(targetId);

			AjaxResultEntity content = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_APPEND).setTargetId(
							targetId).setDomAndScript(domString);

			return AjaxResult.instance().addEntity(emptyEntity).addEntity(
					content).toJSONString();
		} catch (Exception e) {
			log.error("打开编辑界面出现问题。");
		}
		return ActionMessage.instance().failure("打开编辑界面出现问题。").toJSONString();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/items/editsubmit")
	public String openEditSubmit(String body) throws BIJSONException {
		ActionMessage am = new ActionMessage();
		try {
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);

			long rootId = Long.parseLong(paramContext.getParameter("rootId"));
			String category = paramContext.getParameter("category");
			String path = paramContext.getParameter("path");
			String desc = paramContext.getParameter("desc");
			String notes = paramContext.getParameter("notes");

			FilesysDirectory dir = FilesysDirectory.instance().setId(rootId)
					.setPath(path).setDesc(desc).setNotes(notes);
			filesysService.updateFilesysObject(category, dir);
			am.addMessage("编辑目录成功");
		} catch (Exception e) {
			am.addErrorMessage("编辑目录出现错误。");
		}
		return am.toJSONString();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/items/remove/{category}/{id}")
	public String remove(@PathParam("category") String category,
			@PathParam("id") String id) throws BIJSONException {
		ActionMessage am = new ActionMessage();
		try {
			filesysService.removeFilesysObject(category, id);
			am.addMessage("删除目录成功");
		} catch (Exception e) {
			am.addErrorMessage("删除目录出现错误。");
		}
		return am.toJSONString();
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
		// 获得页面
		FLYVariableResolver attrsMap = composeVariableMap(dataStr);
		attrsMap.addVariable("srcName", srcName);
		Object[] domString = PageTemplateInterpolator.interpolate(
				TEMPLATE_FILESYS_RENAME, attrsMap);

		// 设置响应
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

	/**
	 * 目录及文件排序
	 * 
	 * @author PeterPan
	 * 
	 */
	class FileObjectComparator implements Comparator<FileObject> {

		public int compare(FileObject fo1, FileObject fo2) {
			try {
				if (fo1 != null && fo2 != null) {
					boolean isFolder1 = fo1.getType().equals(FileType.FOLDER), isFolder2 = fo2
							.getType().equals(FileType.FOLDER);
					// fo1是文件夹，fo2不是
					if (isFolder1 && !isFolder2) {
						return -1;
					}
					// fo2是文件夹，fo1不是
					else if (isFolder2 && !isFolder1) {
						return 1;
					}
					// 比较名称
					else {
						String name1 = fo1.getName().getBaseName(), name2 = fo2
								.getName().getBaseName();
						return name1.compareToIgnoreCase(name2);
					}
				}
			} catch (FileSystemException e) {
				log.error("文件对象排序出现错误。");
			}
			return 0;
		}
	}

}
