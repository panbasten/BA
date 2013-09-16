package com.flywet.platform.bi.web.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.component.web.ActionMessage;
import com.flywet.platform.bi.component.web.AjaxResult;
import com.flywet.platform.bi.component.web.AjaxResultEntity;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.vo.User;
import com.flywet.platform.bi.web.model.ParameterContext;
import com.flywet.platform.bi.web.service.BIUserDelegate;
import com.flywet.platform.bi.web.utils.BIWebUtils;

@Service("bi.resource.userResource")
@Path("/user")
public class BIUserResource {
	private Logger logger = Logger.getLogger(BIUserResource.class);

	private static final String TEMPLATE_USER_CREATE = "editor/sys/privilege/createuser.h";
	private static final String TEMPLATE_USER_LIST = "editor/user/list.h";

	@Resource(name = "bi.service.userService")
	private BIUserDelegate userDelegate;

	@GET
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public String openCreate(@QueryParam("targetId") String targetId)
			throws Exception {
		Object[] domString = PageTemplateInterpolator.interpolate(
				TEMPLATE_USER_CREATE, new FLYVariableResolver());

		return AjaxResult.instanceDialogContent(targetId, domString)
				.toJSONString();
	}

	@POST
	@Path("/create/save")
	@Produces(MediaType.APPLICATION_JSON)
	public String save(String body) throws Exception {
		ActionMessage am = new ActionMessage();
		try {
			ParameterContext parameterContext = BIWebUtils
					.fillParameterContext(body);

			User user = new User();
			user.setLogin(parameterContext.getParameter("login"));
			user.setName(parameterContext.getParameter("name"));
			user.setPassword(parameterContext.getParameter("password"));
			user.setDesc(parameterContext.getParameter("desc"));
			user.setEnabled(parameterContext.getParameter("enabled"));

			userDelegate.saveUser(user);

			am.addMessage("保存用户信息成功");
		} catch (BIException e) {
			logger.error("保存用户信息失败", e);
			am.addMessage("保存用户信息失败");
		}

		return am.toJSONString();
	}

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listUsers(@QueryParam("targetId") String targetId)
			throws BIException {
		FLYVariableResolver vr = new FLYVariableResolver();
		List<User> userList = userDelegate.getAllUser();
		vr.addVariable("userList", userList);

		Object[] domString = PageTemplateInterpolator.interpolate(
				TEMPLATE_USER_LIST, vr);

		AjaxResultEntity emptyEntity = new AjaxResultEntity();
		emptyEntity.setOperation(Utils.RESULT_OPERATION_EMPTY);
		emptyEntity.setTargetId(targetId);

		AjaxResultEntity content = AjaxResultEntity.instance().setOperation(
				Utils.RESULT_OPERATION_APPEND).setTargetId(targetId)
				.setDomAndScript(domString);

		String res = AjaxResult.instance().addEntity(emptyEntity).addEntity(
				content).toJSONString();
		return res;
	}

}
