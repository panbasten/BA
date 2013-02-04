package com.yonyou.bq8.di.web.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.pentaho.di.repository.IUser;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.filerep.KettleFileRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.springframework.stereotype.Service;

import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.core.utils.JSONUtils;
import com.yonyou.bq8.di.core.utils.Utils;
import com.yonyou.bq8.di.delegates.DIEnvironmentDelegate;
import com.yonyou.bq8.di.web.entity.ActionMessage;
import com.yonyou.bq8.di.web.model.ParameterContext;
import com.yonyou.bq8.di.web.utils.DISecurityUtils;
import com.yonyou.bq8.di.web.utils.DIWebUtils;

@Service("di.resource.identification")
@Path("/identification")
public class DIIdentification {
	private final Logger log = Logger.getLogger(DIIdentification.class);

	@GET
	@Path("/repositoryNames")
	@Produces(MediaType.APPLICATION_JSON)
	public String loadRepNames() throws DIException {
		try {
			String[] repNames = DIEnvironmentDelegate.instance().getRepNames();
			return JSONUtils.toJsonString(repNames);
		} catch (Exception ex) {
			throw new DIException("活动资源库命名出现错误。", ex);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String identification(String userinfo) throws DIException {
		ActionMessage am = new ActionMessage();
		Repository rep = null;
		String repository = null;
		try {
			ParameterContext paramContext = DIWebUtils
					.fillParameterContext(userinfo);

			repository = paramContext.getParameter("repository");
			String username = paramContext.getParameter("username");
			String password = paramContext.getParameter("password");

			if (Utils.isEmpty(repository)) {
				am.addErrorMessage("请选择一个资源库");
			}
			if (Utils.isEmpty(username)) {
				am.addErrorMessage("请输入用户名");
			}
			if (Utils.isEmpty(password)) {
				am.addErrorMessage("请输入登录密码");
			}

			if (!am.state()) {
				return am.toJSONString();
			}

			rep = DIEnvironmentDelegate.instance().borrowRep(repository, null);
			if (rep == null) {
				am.addErrorMessage("指定的资源库不存在!");
				return am.toJSONString();
			}

			IUser user = rep.loadUserInfo(username, password);
			if (user == null) {
				am.addErrorMessage("登录认证失败!");
				return am.toJSONString();
			}

			String cookie = populateCookie(repository, username, rep);
			am.setData(cookie);
		} catch (Exception ex) {
			log.error("identifacation exception:", ex);
			am.addErrorMessage(ex.getMessage());
		} finally {
			DIEnvironmentDelegate.instance().returnRep(repository, rep);
		}

		return am.toJSONString();
	}

	private String populateCookie(String repository, String username,
			Repository rep) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("repository", repository);
		if (rep instanceof KettleDatabaseRepository) {
			map.put("repositoryType", "db");
		} else if (rep instanceof KettleFileRepository) {
			map.put("repositoryType", "file");
		}

		// TODO 写入用户和有效时间的密文，和path信息
		String cookie = DISecurityUtils.createCookieString(map);
		return cookie;
	}
}
