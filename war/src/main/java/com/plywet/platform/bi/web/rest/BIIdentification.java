package com.plywet.platform.bi.web.rest;

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

import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.exception.BISecurityException;
import com.plywet.platform.bi.core.sec.WebMarshal;
import com.plywet.platform.bi.core.utils.JSONUtils;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.delegates.BIEnvironmentDelegate;
import com.plywet.platform.bi.web.entity.ActionMessage;
import com.plywet.platform.bi.web.model.ParameterContext;
import com.plywet.platform.bi.web.utils.BISecurityUtils;
import com.plywet.platform.bi.web.utils.BIWebUtils;

@Service("bi.resource.identification")
@Path("/identification")
public class BIIdentification {
	private final Logger log = Logger.getLogger(BIIdentification.class);

	@GET
	@Path("/repositoryNames")
	@Produces(MediaType.APPLICATION_JSON)
	public String loadRepNames() throws BIException {
		try {
			String[] repNames = BIEnvironmentDelegate.instance().getRepNames();
			return JSONUtils.toJsonString(repNames);
		} catch (Exception ex) {
			throw new BIException("活动资源库命名出现错误。", ex);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String identification(String userinfo) throws BIException {
		ActionMessage am = new ActionMessage();
		Repository rep = null;
		String repository = null;
		try {
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(userinfo);

			repository = paramContext.getParameter("repository");
			String username = paramContext.getParameter("username");
			String password = paramContext.getParameter("password");

			try {
				WebMarshal.getInstance();
			} catch (BISecurityException e) {
				am.addErrorMessage(e.getMessage().trim());
			}

			if (Utils.isEmpty(repository)) {
				am.addErrorMessage("请选择一个资源库");
			}
			if (Utils.isEmpty(username)) {
				am.addErrorMessage("请输入用户名");
			}
//			if (Utils.isEmpty(password)) {
//				am.addErrorMessage("请输入登录密码");
//			}

			if (!am.state()) {
				return am.toJSONString();
			}

			rep = BIEnvironmentDelegate.instance().borrowRep(repository, null);
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
			BIEnvironmentDelegate.instance().returnRep(repository, rep);
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
		String cookie = BISecurityUtils.createCookieString(map);
		return cookie;
	}
}
