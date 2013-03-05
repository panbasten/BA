package com.yonyou.bq8.di.web.rest;

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

import com.yonyou.bq8.di.component.components.breadCrumb.BreadCrumbMeta;
import com.yonyou.bq8.di.component.components.breadCrumb.BreadCrumbNodeMeta;
import com.yonyou.bq8.di.component.components.browse.BrowseMeta;
import com.yonyou.bq8.di.component.components.browse.BrowseNodeMeta;
import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.component.utils.PageTemplateInterpolator;
import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.core.utils.BQFileUtils;
import com.yonyou.bq8.di.core.utils.JSONUtils;
import com.yonyou.bq8.di.core.utils.Utils;
import com.yonyou.bq8.di.delegates.utils.DIFileSystemCategory;
import com.yonyou.bq8.di.delegates.vo.FilesysDirectory;
import com.yonyou.bq8.di.delegates.vo.FilesysType;
import com.yonyou.bq8.di.web.entity.AjaxResult;
import com.yonyou.bq8.di.web.entity.AjaxResultEntity;
import com.yonyou.bq8.di.web.service.DIFileSystemDelegate;

@Service("di.resource.filesysResource")
@Path("/fs")
public class DIFileSystemResource {
	private final Logger log = Logger.getLogger(DIFileSystemResource.class);

	private static final String ID_EDITOR_CONTENT_NAVI_FILESYS_BC = "editorContent-navi-filesys-bc";
	private static final String ID_EDITOR_CONTENT_NAVI_FILESYS_BP = "editorContent-navi-filesys-bp";

	private static final String TEMPLATE_FILESYS_RENAME = "editor/filesys/rename.h";
	private static final String TEMPLATE_FILESYS_CREATE = "editor/filesys/create.h";
	private static final String TEMPLATE_FILESYS_UPLOAD = "editor/filesys/upload.h";

	// private final static String UPLOAD_TMPDIR =
	// DIPropertyUtils.getProperty("fs.upload");

	@Resource(name = "di.service.filesystemService")
	private DIFileSystemDelegate filesysService;

	/**
	 * 创建文件系统导航页面
	 * 
	 * @param repository
	 * @param act
	 * @return
	 * @throws DIException
	 */
	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentFileSystem(
			@CookieParam("repository") String repository,
			@QueryParam("act") String act) throws DIException {
		try {
			// 组装面包屑
			BreadCrumbMeta bce = new BreadCrumbMeta();
			BreadCrumbNodeMeta bcee = new BreadCrumbNodeMeta("0", "文件系统",
					"/fs/navi?act=flush", "");
			bce.addContent(bcee);
			bce.addEvent("click", "YonYou.browse.changeDir");

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
							"YonYou.browse.showOperationForDir");
					node.addEvent("dblclick", "YonYou.browse.changeDir");

					browse.addContent(node);

				}
			}

			browse.addClass("hb-browsepanel");

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
			throw new DIException("创建导航的文件系统内容页面出现错误。", ex);
		}
	}

	/**
	 * 显示每个文件系统类别下的根数据
	 * 
	 * @param category
	 * @return
	 * @throws DIException
	 */
	@GET
	@Path("/root/{category}")
	@Produces(MediaType.APPLICATION_JSON)
	public String displayRoot(@PathParam("category") String category)
			throws DIException {
		try {
			// 组装面包屑
			BreadCrumbMeta bce = new BreadCrumbMeta();
			BreadCrumbNodeMeta bcee = new BreadCrumbNodeMeta("", "文件系统",
					"/fs/navi?act=flush", "");
			bce.addContent(bcee);

			bcee = new BreadCrumbNodeMeta("", DIFileSystemCategory
					.getDescByCategory(category), "/fs/root/" + category, "");

			bce.addContent(bcee);
			bce.addEvent("click", "YonYou.browse.changeDir");

			AjaxResultEntity fileSysBCResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_FILESYS_BC).setCmd(
							"widget.BreadCrumb").setData(bce);

			// 组装浏览面板数据
			BrowseMeta browse = new BrowseMeta();
			populateBrowseEles(browse, category);

			browse.addExtendAttribute("category", category);
			browse.addClass("hb-browsepanel");

			// 组装结果对象
			AjaxResultEntity browseResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_FILESYS_BP).setData(browse);
			browseResult.setCmd("this.flush");

			return AjaxResult.instance().addEntity(fileSysBCResult).addEntity(
					browseResult).toJSONString();
		} catch (Exception ex) {
			log.error("创建导航的文件系统内容页面出现错误。");
			throw new DIException("创建导航的文件系统内容页面出现错误。", ex);
		}
	}

	/**
	 * 拼装文件系统内容
	 * 
	 * @return
	 * @throws DIException
	 */
	private void populateBrowseEles(BrowseMeta browse, String category)
			throws DIException {
		DIFileSystemCategory cate = DIFileSystemCategory
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

				node.addEvent("mouseup", "YonYou.browse.showOperationForDir");
				node.addEvent("dblclick", "YonYou.browse.changeDir");

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
		bce.addEvent("click", "YonYou.browse.changeDir");
		BreadCrumbNodeMeta bcee = new BreadCrumbNodeMeta("0", "文件系统",
				"/fs/navi?act=flush", "");
		bce.addContent(bcee);

		bcee = new BreadCrumbNodeMeta("0", DIFileSystemCategory
				.getCategoryByCode(category).getDesc(), "/fs/root/" + category,
				"");
		bce.addContent(bcee);

		String rootDirName = filesysService.getRootDesc(category, rootId);
		bcee = new BreadCrumbNodeMeta("", rootDirName, "/fs/items/list/"
				+ category, "/");
		bcee.addExtendAttribute("rootId", String.valueOf(rootId));
		bce.addContent(bcee);
		bce.addEvent("click", "YonYou.browse.changeDir");

		List<String> dirs = BQFileUtils.dirSplit(workDir);

		for (String dir : dirs) {
			bcee = new BreadCrumbNodeMeta("", dir,
					"/fs/items/list/" + category, "/" + dir);
			bcee.addExtendAttribute("rootId", String.valueOf(rootId));
			bce.addContent(bcee);
			bce.addEvent("click", "YonYou.browse.changeDir");
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
			node.setPath(BQFileUtils.dirAppend(workDir, child.getName()
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
				node.addEvent("mouseup", "YonYou.browse.showOperationForDir");
				node.addEvent("dblclick", "YonYou.browse.changeDir");
			} else {
				node.addEvent("mouseup", "YonYou.browse.showOperationForFile");
				node.addEvent("dblclick", "YonYou.browse.openFile");
			}
			browse.addContent(node);
		}

		browse.addExtendAttribute("path", workDir);
		browse.addExtendAttribute("rootId", String.valueOf(rootId));
		browse.addExtendAttribute("category", category);
		browse.addClass("hb-browsepanel");

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
		BQVariableResolver attrsMap = composeVariableMap(dataStr);
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
		BQVariableResolver attrsMap = composeVariableMap(dataStr);

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
		BQVariableResolver attrsMap = composeVariableMap(dataStr);

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

	private BQVariableResolver composeVariableMap(String dataStr)
			throws Exception {
		JSONObject dataObj = JSONUtils.convertStringToJSONObject(dataStr);
		long rootId = Long.parseLong(dataObj.get("rootId").toString());
		String workPath = dataObj.get("path").toString();
		String category = dataObj.get("category").toString();

		BQVariableResolver attrsMap = new BQVariableResolver();
		attrsMap.addVariable("rootId", rootId);
		attrsMap.addVariable("path", workPath);
		attrsMap.addVariable("category", category);
		return attrsMap;
	}

}
