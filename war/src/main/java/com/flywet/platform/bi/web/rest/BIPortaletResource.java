package com.flywet.platform.bi.web.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.vfs.FileObject;
import org.apache.log4j.Logger;
import org.pentaho.di.core.Const;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.component.web.ActionMessage;
import com.flywet.platform.bi.core.ContextHolder;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.core.utils.PropertyUtils;
import com.flywet.platform.bi.core.utils.ReflectionUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.flywet.platform.bi.delegates.vo.PortalMenu;
import com.flywet.platform.bi.web.service.BIFileSystemDelegate;
import com.flywet.platform.bi.web.service.BIPortalDelegates;

@Service("bi.resource.portalet")
@Path("/portalet")
public class BIPortaletResource {
	private final Logger log = Logger.getLogger(BIPortaletResource.class);

	@Resource(name = "bi.service.portalServices")
	private BIPortalDelegates portalDelegates;

	@Resource(name = "bi.service.filesystemService")
	private BIFileSystemDelegate filesysService;

	/**
	 * 获得文件
	 * 
	 * @param dataStr
	 *            文件标识信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GET
	@Path("/getfile")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public void getFile(@QueryParam("path") String workPath,
			@QueryParam("rootPath") String rootPathProp,
			@QueryParam("category") String categoryProp,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response, String body)
			throws IOException {
		InputStream is = null;

		try {
			String rootDir = PropertyUtils.getProperty(rootPathProp);
			String category = PropertyUtils.getProperty(categoryProp);
			// 拼装文件信息
			FileObject fileObj = filesysService.composeVfsObject(category,
					workPath, rootDir);

			is = fileObj.getContent().getInputStream();
			response.setContentType("application/octet-stream");
			request.setCharacterEncoding(Const.XML_ENCODING);
			response.setCharacterEncoding(Const.XML_ENCODING);
			String fileName = Const.replace(fileObj.getName().getBaseName(),
					" ", "%20");
			// 保证另存为文件名为中文
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes(), "ISO8859_1"));
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
	 * 打开Portal菜单，通过注册ID
	 * 
	 * @param id
	 * @param targetId
	 * @return
	 * @throws BIException
	 */
	@GET
	@Path("/menu/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String openPortalDialog(@PathParam("id") String id,
			@QueryParam("targetId") String targetId) throws BIException {
		try {
			// 通过ID获得注册的菜单
			PortalMenu pm = portalDelegates.getPortalMenuById(Long.valueOf(id));

			Map<String, Object> context = getDefaultContext(id, pm
					.getExtAttr("param"));

			return invokeMethod(pm.getExtAttr("cls"), pm.getExtAttr("method"),
					context, targetId);
		} catch (Exception ex) {
			throw new BIException("打开Portal的菜单出现错误。", ex);
		}
	}

	@GET
	@Path("/menu/{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public String updatePortalDialog(@PathParam("id") String id,
			@QueryParam("targetId") String targetId,
			@QueryParam("method") String method,
			@QueryParam("param") String param) throws BIException {
		try {
			// 通过ID获得注册的菜单
			PortalMenu pm = portalDelegates.getPortalMenuById(Long.valueOf(id));
			method = (Const.isEmpty(method)) ? pm.getExtAttr("method") : method;
			param = (Const.isEmpty(param)) ? pm.getExtAttr("param") : param;

			Map<String, Object> context = getDefaultContext(id, param);

			return invokeMethod(pm.getExtAttr("cls"), method, context, targetId);
		} catch (Exception ex) {
			throw new BIException("打开Portal的菜单出现错误。", ex);
		}
	}

	private Map<String, Object> getDefaultContext(String id, String param)
			throws UnsupportedEncodingException {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("id", id);
		if (!Const.isEmpty(param)) {
			context.put("param", Utils.decodeURL(param));
		}

		return context;
	}

	/**
	 * 调用业务方法
	 * 
	 * @param cls
	 * @param method
	 * @param param
	 * @return
	 * @throws BIException
	 */
	private String invokeMethod(String cls, String method,
			Map<String, Object> context, String targetId) throws BIException {
		try {
			Class<?> clazz = Class.forName(cls);
			Object prog = BIAdaptorFactory.createCustomAdaptor(clazz);

			return (String) ReflectionUtils.invokeMethod(prog, method,
					targetId, context);

		} catch (Exception ex) {
			log.error("打开Portal的菜单出现错误。");
		}
		return ActionMessage.instance().failure("打开Portal的菜单出现错误。")
				.toJSONString();
	}

	@GET
	@Path("/menus")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPortalMenus() throws BIException {
		try {
			String repository = ContextHolder.getRepositoryName();

			// 如果repository仍为空，返回空值
			if (Const.isEmpty(repository)) {
				return "[]";
			} else {
				List<PortalMenu> meus = portalDelegates
						.getPortalMenusByParent(0L);
				return JSONUtils.convertToJSONArray(meus).toJSONString();
			}
		} catch (Exception ex) {
			throw new BIException("获得Portal的菜单出现错误。", ex);
		}
	}
}
