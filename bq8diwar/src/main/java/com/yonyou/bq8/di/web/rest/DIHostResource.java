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
import org.springframework.stereotype.Service;

import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.PageTemplateInterpolator;
import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.core.utils.Utils;
import com.yonyou.bq8.di.delegates.vo.DIHost;
import com.yonyou.bq8.di.web.entity.ActionMessage;
import com.yonyou.bq8.di.web.entity.AjaxResult;
import com.yonyou.bq8.di.web.entity.AjaxResultEntity;
import com.yonyou.bq8.di.web.model.ParameterContext;
import com.yonyou.bq8.di.web.service.DIFileSystemDelegate;
import com.yonyou.bq8.di.web.utils.DIWebUtils;

@Service("di.resource.hostResource")
@Path("/host")
public class DIHostResource {
	private static final String TEMPLATE_FILESYS_HOST = "editor/filesys/host.h";
	
	@Resource(name = "di.service.filesystemService")
	private DIFileSystemDelegate filesysService;
	
	/**
	 * 打开主机设置页面
	 * @param targetId 页面容器ID
	 * @param hostIdStr 主机id
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/setting")
	public String openAddHost(@QueryParam("targetId")String targetId, 
			@QueryParam("hostId")String hostIdStr) throws Exception {
		BQVariableResolver attrsMap = new BQVariableResolver();
		DIHost host = null;
		if (StringUtils.isNotBlank(hostIdStr)) {//编辑已有
			long hostId = Long.parseLong(hostIdStr);
			host = filesysService.getFtphostById(hostId);
		} else {//新增
			host = new DIHost();
		}
		
		attrsMap.addVariable("host", host);
		
		Object[] domString = PageTemplateInterpolator.interpolate(TEMPLATE_FILESYS_HOST, attrsMap);

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
	 * 保存主机信息
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/setting")
	@Produces(MediaType.APPLICATION_JSON)
	public String saveSetting(String body) throws Exception{
		ActionMessage am = new ActionMessage();
		
		ParameterContext parameterContext = DIWebUtils.fillParameterContext(body);
		
		DIHost host = new DIHost();
		host.setCode(parameterContext.getParameter("code"));
		host.setDesc(parameterContext.getParameter("desc"));
		host.setIp(parameterContext.getParameter("ip"));
		host.setMode(parameterContext.getParameter("mode"));
		host.setNotes(parameterContext.getParameter("notes"));
		host.setPassword(parameterContext.getParameter("password"));
		host.setPort(Integer.parseInt(parameterContext.getParameter("port")));
		host.setType(parameterContext.getParameter("type"));
		host.setUsername(parameterContext.getParameter("username"));
		
		String id = parameterContext.getParameter("id");
		try {
			if (Long.parseLong(id) == 0L) {//新增
				filesysService.addHost(host);
			} else {//编辑已有
				host.setId(Long.parseLong(id));
				filesysService.updateHost(host);
			}
			am.addMessage("保存主机信息成功");
		} catch (DIException e) {
			e.printStackTrace();
			am.addMessage("保存主机信息失败");
			
		}
		
		return am.toJSONString();
	}
	
	/**
	 * 删除指定的主机信息
	 * @param hostId
	 * @return
	 * @throws DIException
	 */
	@DELETE
	@Path("/{hostId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeHost(@PathParam("hostId") String hostId) throws DIException {
		ActionMessage am = new ActionMessage();
		
		try {
			long id = Long.parseLong(hostId);
			filesysService.delHost(id);
			am.addMessage("移除主机信息成功");
		} catch (DIException e) {
			e.printStackTrace();
			am.addMessage("移除主机信息失败");
		}
		
		return am.toJSONString();
	}
	
}
