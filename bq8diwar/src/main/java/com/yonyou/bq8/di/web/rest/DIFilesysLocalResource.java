package com.yonyou.bq8.di.web.rest;

import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.PageTemplateInterpolator;
import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.core.utils.Utils;
import com.yonyou.bq8.di.delegates.vo.FilesysDirectory;
import com.yonyou.bq8.di.web.entity.ActionMessage;
import com.yonyou.bq8.di.web.entity.AjaxResult;
import com.yonyou.bq8.di.web.entity.AjaxResultEntity;
import com.yonyou.bq8.di.web.model.ParameterContext;
import com.yonyou.bq8.di.web.service.DIFileSystemDelegate;
import com.yonyou.bq8.di.web.utils.DIWebUtils;

@Service("di.resource.localResource")
@Path("/fslocal")
public class DIFilesysLocalResource {
	private final Logger logger = Logger.getLogger(DIFileSystemResource.class);
	
	private static final String TEMPLATE_FILESYS_LOCAL = "editor/filesys/local.h";
	
	@Resource(name = "di.service.filesystemService")
	private DIFileSystemDelegate filesysService;
	
	/**
	 * 打开服务器本地文件配置页面
	 * @param targetId 页面显示的页面容器ID
	 * @param rootId 选中的根目录id，新建的目录时rootid为null
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/setting")
	public String openSetting(@QueryParam("targetId")String targetId, 
			@QueryParam("rootId")String rootId) throws Exception {
		BQVariableResolver attrsMap = new BQVariableResolver();
		FilesysDirectory directory = null;
		if (StringUtils.isNotBlank(rootId)) {
			long hostId = Long.parseLong(rootId);
			directory = filesysService.getLocalRootById(hostId);
		} else {
			directory = new FilesysDirectory();
		}
		
		attrsMap.addVariable("directory", directory);
		
		Object[] domString = PageTemplateInterpolator.interpolate(TEMPLATE_FILESYS_LOCAL, attrsMap);

		AjaxResultEntity emptyEntity = new AjaxResultEntity();
		emptyEntity.setOperation(Utils.RESULT_OPERATION_EMPTY);
		emptyEntity.setTargetId(targetId);

		AjaxResultEntity content = AjaxResultEntity.instance()
				.setOperation(Utils.RESULT_OPERATION_APPEND).setTargetId(
						targetId).setDomAndScript(domString);

		return AjaxResult.instance().addEntity(emptyEntity).
					addEntity(content).toJSONString();
	}
	
	/**
	 * 保存服务器本地根目录信息
	 * @param body 根目录配置信息
	 * @return	结果消息-json格式
	 * @throws Exception
	 */
	@POST
	@Path("/setting")
	@Produces(MediaType.APPLICATION_JSON)
	public String saveSetting(String body) throws Exception{
		ActionMessage am = new ActionMessage();
		
		ParameterContext parameterContext = DIWebUtils.fillParameterContext(body);
		
		FilesysDirectory directory = new FilesysDirectory();
		
		directory.setDesc(parameterContext.getParameter("desc"));
		directory.setNotes(parameterContext.getParameter("notes"));
		directory.setPath(parameterContext.getParameter("path"));
		
		String id = parameterContext.getParameter("id");
		try {
			if (Long.parseLong(id) == 0L) {
				filesysService.addLocalRoot(directory);
			} else {
				directory.setId(Long.parseLong(id));
				filesysService.updateLocalRoot(directory);
			}
			am.addMessage("保存服务器文件系统成功");
		} catch (DIException e) {
			logger.error("save directory exception:", e);
			am.addMessage("保存服务器文件系统失败");
		}
		
		return am.toJSONString();
	}
	
	/**
	 * 删除指定的服务器根目录
	 * @param rootId 根目录标识
	 * @return
	 * @throws DIException
	 */
	@DELETE
	@Path("/{rootId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeHost(@PathParam("rootId") String rootId) throws DIException {
		ActionMessage am = new ActionMessage();
		
		try {
			long id = Long.parseLong(rootId);
			filesysService.deleteLocalRoot(id);
			am.addMessage("移除服务器文件系统成功");
		} catch (DIException e) {
			logger.error("remove directory exception:", e);
			am.addMessage("移除服务器文件系统失败");
		}
		
		return am.toJSONString();
	}

}
