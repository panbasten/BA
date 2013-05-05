package com.plywet.platform.bi.web.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.vfs.AllFileSelector;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileType;
import org.apache.log4j.Logger;
import org.apache.wink.common.internal.utils.MediaTypeUtils;
import org.json.simple.JSONObject;
import org.pentaho.pms.util.Const;
import org.springframework.stereotype.Service;

import com.plywet.platform.bi.core.utils.FileUtils;
import com.plywet.platform.bi.core.utils.JSONUtils;
import com.plywet.platform.bi.core.utils.PropertyUtils;
import com.plywet.platform.bi.web.entity.ActionMessage;
import com.plywet.platform.bi.web.model.ParameterContext;
import com.plywet.platform.bi.web.service.BIFileSystemDelegate;
import com.plywet.platform.bi.web.utils.BIWebUtils;

/**
 * 文件系统操作资源
 * 
 * @author han
 * 
 */
@Service("bi.resource.fileOperation")
@Path("/fsop")
public class BIFileOperationResource {
	private final Logger log = Logger.getLogger(BIFileSystemResource.class);

	@Resource(name = "bi.service.filesystemService")
	private BIFileSystemDelegate filesysService;

	/**
	 * 文件上传 支持多文件的上传
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Path("/upload")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaTypeUtils.MULTIPART_FORM_DATA)
	public String uploadFiles(@Context HttpServletRequest request)
			throws Exception {
		ActionMessage resultMsg = new ActionMessage();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb

		ServletFileUpload upload = new ServletFileUpload(factory);
		String fileSizeMax = PropertyUtils.getProperty("fs.upload.maxsize");
		upload.setFileSizeMax(Long.parseLong(fileSizeMax));

		List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
		// 提取参数
		Map<String, String> dataObj = extractParams(items);

		long rootId = Long.parseLong(dataObj.get("rootId"));
		String workDir = dataObj.get("path");
		String category = dataObj.get("category");

		// 遍历文件并逐次上传
		for (FileItem item : items) {
			if (item.isFormField()) {
				continue;
			}

			InputStream is = null;
			OutputStream os = null;

			File fullFile = new File(item.getName());
			try {
				String destFileStr = FileUtils.dirAppend(workDir, fullFile
						.getName());
				FileObject destFileObj = filesysService.composeVfsObject(
						category, destFileStr, rootId);

				is = item.getInputStream();
				os = destFileObj.getContent().getOutputStream();

				byte[] bytes = new byte[1024];
				int in = 0;
				while ((in = is.read(bytes)) != -1) {
					os.write(bytes);
				}
				os.flush();
			} catch (IOException ioe) {
				log.error("read or write file exception:", ioe);
				resultMsg.addErrorMessage("上传文件" + fullFile.getName() + "失败");
				return resultMsg.toJSONString();
			} finally {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
				item.delete();
			}
		}
		resultMsg.addMessage("上传操作成功");
		return resultMsg.toJSONString();
	}

	/**
	 * 创建目录
	 * 
	 * @param body
	 *            目录配置信息
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public String createFolder(String body) throws Exception {
		ActionMessage am = new ActionMessage();
		try {
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);

			long rootId = Long.parseLong(paramContext.getParameter("rootId"));
			String workPath = paramContext.getParameter("path");
			String category = paramContext.getParameter("category");
			String dirName = paramContext.getParameter("dirName");

			FileObject fileObj = filesysService.composeVfsObject(category,
					workPath + "/" + dirName, rootId);
			fileObj.createFolder();
			am.addMessage("创建目录" + fileObj.getName().getBaseName() + "成功");
		} catch (Exception e) {
			am.addErrorMessage("创建目录操作失败");
		}
		return am.toJSONString();
	}

	private Map<String, String> extractParams(List<FileItem> items)
			throws UnsupportedEncodingException, Exception {
		Map<String, String> params = new HashMap<String, String>();
		for (FileItem item : items) {
			if (!item.isFormField()) {
				continue;
			}
			String fieldName = item.getFieldName();
			String value = item.getString(Const.XML_ENCODING);
			params.put(fieldName, value);
		}
		return params;
	}

	/**
	 * 删除指定的文件项
	 * 
	 * @param dataStr
	 *            文件标识信息，包括path，根目录id，类别等
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public String delete(@QueryParam("data") String dataStr) throws Exception {
		ActionMessage am = new ActionMessage();
		try {
			JSONObject dataObj = JSONUtils.convertStringToJSONObject(dataStr);
			long rootId = Long.parseLong(dataObj.get("rootId").toString());
			String workDir = dataObj.get("path").toString();
			String category = dataObj.get("category").toString();

			FileObject fileObj = filesysService.composeVfsObject(category,
					workDir, rootId);
			if (fileObj.getType().equals(FileType.FOLDER)
					&& fileObj.getChildren().length != 0) {
				am.addErrorMessage("目录不为空，不能执行删除操作。");
				return am.toJSONString();
			}

			fileObj.delete();
			am.addMessage("删除" + fileObj.getName().getBaseName() + "成功");
		} catch (Exception e) {
			log.error("delete operation exception:", e);
			am.addErrorMessage("删除操作失败");
		}
		return am.toJSONString();
	}

	/**
	 * 下载文件
	 * 
	 * @param dataStr
	 *            文件标识信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GET
	@Path("/download")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public void download(@QueryParam("data") String dataStr,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws IOException {
		InputStream is = null;

		try {
			JSONObject dataObj = JSONUtils.convertStringToJSONObject(dataStr);
			long rootId = Long.parseLong(dataObj.get("rootId").toString());
			String workPath = dataObj.get("path").toString();
			String category = dataObj.get("category").toString();
			// 拼装文件信息
			FileObject fileObj = filesysService.composeVfsObject(category,
					workPath, rootId);

			is = fileObj.getContent().getInputStream();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ fileObj.getName().getBaseName() + "\"");
			byte[] b = new byte[1024];
			int i;
			OutputStream os = response.getOutputStream();
			while ((i = is.read(b)) != -1) {
				os.write(b, 0, i);
			}
			os.flush();
		} catch (Exception e) {
			log.error("download file exception:", e);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * 重命名文件或者目录
	 * 
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/rename")
	@Produces(MediaType.APPLICATION_JSON)
	public String rename(String body) throws Exception {
		ActionMessage am = new ActionMessage();
		try {
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(URLDecoder.decode(body, "utf-8"));

			long rootId = Long.parseLong(paramContext.getParameter("rootId"));
			String workPath = paramContext.getParameter("path");
			String category = paramContext.getParameter("category");
			String newName = paramContext.getParameter("newName");

			FileObject oldFileObj = filesysService.composeVfsObject(category,
					workPath, rootId);
			File newFile = new File(new File(workPath).getParentFile(), newName);
			FileObject newFileObj = filesysService.composeVfsObject(category,
					newFile.getPath(), rootId);
			oldFileObj.moveTo(newFileObj);
			am.addMessage("重命名" + newFileObj.getName().getBaseName() + "成功");
		} catch (Exception e) {
			log.error("rename file exception:", e);
			am.addErrorMessage("删除操作失败");
		}
		return am.toJSONString();
	}

	/**
	 * 粘贴文件操作
	 * 
	 * @param currentCase
	 *            当前的场景
	 * @param operateCase
	 *            被选中用来粘贴文件时的操作场景信息
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/paste")
	@Produces(MediaType.APPLICATION_JSON)
	public String paste(@QueryParam("currentCase") String currentCase,
			@QueryParam("operateCase") String operateCase) throws Exception {
		ActionMessage am = new ActionMessage();
		try {
			JSONObject currentCaseObj = JSONUtils
					.convertStringToJSONObject(URLDecoder.decode(currentCase,
							"utf-8"));

			long currentRootId = Long.parseLong(currentCaseObj.get("rootId")
					.toString());
			String currentWorkPath = currentCaseObj.get("path").toString();
			String currentCategory = currentCaseObj.get("category").toString();

			JSONObject operateCaseObj = JSONUtils
					.convertStringToJSONObject(URLDecoder.decode(operateCase,
							"utf-8"));
			long operateRootId = Long.parseLong(operateCaseObj.get("rootId")
					.toString());
			String operateWorkPath = operateCaseObj.get("path").toString();
			String operateCategory = operateCaseObj.get("category").toString();
			String operation = operateCaseObj.get("operation").toString();

			FileObject oldFileObj = filesysService.composeVfsObject(
					operateCategory, operateWorkPath, operateRootId);
			FileObject newFileObj = filesysService
					.composeVfsObject(currentCategory, FileUtils
							.directoryComplete(currentWorkPath)
							+ oldFileObj.getName().getBaseName(), currentRootId);

			if ("CUT".equalsIgnoreCase(operation)) {
				oldFileObj.moveTo(newFileObj);
			} else {
				newFileObj.copyFrom(oldFileObj, new AllFileSelector());
			}

			am.addMessage("粘贴" + newFileObj.getName().getBaseName() + "成功");
		} catch (Exception e) {
			log.error("rename file exception:", e);
			am.addErrorMessage("粘贴操作失败");
		}
		return am.toJSONString();
	}
}
