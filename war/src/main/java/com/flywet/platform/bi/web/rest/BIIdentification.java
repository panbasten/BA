package com.flywet.platform.bi.web.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.repository.IUser;
import org.pentaho.di.repository.Repository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.component.web.ActionMessage;
import com.flywet.platform.bi.core.ContextHolder;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BISecurityException;
import com.flywet.platform.bi.core.model.ParameterContext;
import com.flywet.platform.bi.core.pools.RepPool;
import com.flywet.platform.bi.core.sec.KeyGenerater;
import com.flywet.platform.bi.core.sec.SignProvider;
import com.flywet.platform.bi.core.sec.WebMarshal;
import com.flywet.platform.bi.core.utils.FileUtils;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.vo.User;
import com.flywet.platform.bi.services.intf.BIUserDelegate;
import com.flywet.platform.bi.web.i18n.BIWebMessages;
import com.flywet.platform.bi.web.utils.BISecurityUtils;
import com.flywet.platform.bi.web.utils.BIWebUtils;

@Service("bi.resource.identification")
@Path("/identification")
public class BIIdentification {
	private final Logger log = Logger.getLogger(BIIdentification.class);

	public static final String TEMPLATE_SYS_USER_INFO = "editor/sys/user_info.h";
	public static final String TEMPLATE_SYS_LOGIN_SLIDE = "portal/sys/login_slide.h";

	private static final String KEY = "FLYWET@2013";

	public static final String REPOSITORYNAME = "repository";
	public static final String REPOSITORYTYPE = "repositoryType";
	public static final String USERNAME = "username";
	public static final String LOGINNAME = "loginname";
	public static final String TOEDITOR = "toeditor";

	public static final String PASSWORD = "password";

	@Resource(name = "bi.service.userService")
	private BIUserDelegate userService;

	@GET
	@Path("/repositoryNames")
	@Produces(MediaType.APPLICATION_JSON)
	public String loadRepNames() throws BIException {
		try {
			String[] repNames = RepPool.instance().getRepNames();
			return JSONUtils.toJsonString(repNames);
		} catch (Exception ex) {
			throw new BIException("活动资源库命名出现错误。", ex);
		}
	}

	@GET
	@Path("/messages")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMessages() throws BIException {
		try {
			return BIWebMessages.getMessages().toJSONString();
		} catch (Exception ex) {
			throw new BIException("获得页面多语资源出现错误。", ex);
		}
	}

	/**
	 * 是否存在公钥
	 * 
	 * @return
	 */
	@GET
	@Path("/existPubKey")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean existPubKey() {
		File pubKeyFile = new File(SignProvider.getPublicKeyFilePath());
		return pubKeyFile.exists();
	}

	private void createPubKey(KeyGenerater kg) throws BIException {
		try {

			File pubKeyFile = new File(SignProvider.getPublicKeyFilePath());

			if (pubKeyFile.exists()) {
				pubKeyFile.delete();
			}

			FileOutputStream out = null;
			try {
				out = new FileOutputStream(pubKeyFile);
				out.write(kg.getPubKey().getBytes());
			} catch (IOException e) {
				log.error("create public key file exception:", e);
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						log.error("create public key file exception:", e);
					}
				}
			}

		} catch (Exception ex) {
			log.error("createPubKey exception:", ex);
		}
	}

	private void createPriKey(String key) throws BIException {
		try {

			File priKeyFile = new File(SignProvider.getPrivateKeyFilePath());

			if (priKeyFile.exists()) {
				priKeyFile.delete();
			}

			FileOutputStream out = null;
			try {
				out = new FileOutputStream(priKeyFile);
				out.write(key.getBytes());
			} catch (IOException e) {
				log.error("create private key file exception:", e);
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						log.error("create private key file exception:", e);
					}
				}
			}

		} catch (Exception ex) {
			log.error("createPriKey exception:", ex);
		}
	}

	/**
	 * 创建公钥，并下载私钥
	 * 
	 * @param request
	 * @param response
	 * @param body
	 * @throws IOException
	 */
	@GET
	@Path("/createKey")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public void createKey(@Context HttpServletRequest request,
			@Context HttpServletResponse response, String body)
			throws IOException {
		try {
			KeyGenerater kg = KeyGenerater.instance(KEY);

			createPubKey(kg);

			String key = kg.getPriKey();
			key = key + ";" + WebMarshal.getInstance().getMachineCode();

			createPriKey(key);

			response.setContentType("application/octet-stream");
			request.setCharacterEncoding(Const.XML_ENCODING);
			response.setCharacterEncoding(Const.XML_ENCODING);
			String fileName = "ba.prikey";
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);

			FileUtils.write(FileUtils.getInputStream(key), response
					.getOutputStream());

		} catch (Exception e) {
			log.error("download private key file exception:", e);
		}
	}

	/**
	 * 创建用户信息弹出页
	 * 
	 * @throws BIException
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/usersettingpage")
	@Produces(MediaType.TEXT_PLAIN)
	public String createUserSettingPage() throws BIException {
		try {
			String loginname = ContextHolder.getLoginName();
			User user = userService.getUserByLogin(loginname);
			FLYVariableResolver resolver = FLYVariableResolver.instance();
			resolver.addVariable("user", user);

			Document doc = PageTemplateInterpolator
					.getDom(TEMPLATE_SYS_USER_INFO);
			Object[] domString = PageTemplateInterpolator.interpolate(
					TEMPLATE_SYS_USER_INFO, doc, resolver);

			JSONObject jo = new JSONObject();
			jo.put(USERNAME, user.getName());
			jo.put("dom", (String) domString[0]);
			jo.put("script", JSONUtils
					.convertToJSONArray((List<String>) domString[1]));

			return jo.toJSONString();
		} catch (Exception ex) {
			throw new BIException("创建用户信息弹出页出现错误。", ex);
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

			repository = paramContext.getParameter(REPOSITORYNAME);
			String username = paramContext.getParameter(USERNAME);
			String password = paramContext.getParameter(PASSWORD);

			boolean toeditor = paramContext.getBooleanParameter(TOEDITOR);

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
			if (Utils.isEmpty(password)) {
				am.addErrorMessage("请输入登录密码");
			}

			if (!am.state()) {
				return am.toJSONString();
			}

			rep = RepPool.instance().borrowRep(repository, null);
			if (rep == null) {
				am.addErrorMessage("指定的资源库不存在!");
				return am.toJSONString();
			}

			IUser user = rep.loadUserInfo(username, password);
			if (user == null) {
				am.addErrorMessage("登录认证失败!");
				return am.toJSONString();
			}

			String cookie = populateCookie(repository, user, toeditor);
			am.setData(cookie);
		} catch (Exception ex) {
			log.error("identifacation exception:", ex);
			am.addErrorMessage(ex.getMessage());
		} finally {
			RepPool.instance().returnRep(repository, rep);
		}

		return am.toJSONString();
	}

	private String populateCookie(String repository, IUser user,
			boolean toeditor) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(USERNAME, user.getUsername());
		map.put(LOGINNAME, user.getLogin());

		map.put(REPOSITORYNAME, repository);
		map.put(REPOSITORYTYPE, RepPool.instance().getRepType(repository));
		map.put(TOEDITOR, toeditor);

		// TODO 写入用户和有效时间的密文，和path信息
		String cookie = BISecurityUtils.createCookieString(map);
		return cookie;
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/slides")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSlideShows() throws BIException {
		try {
			// 获得登陆滚动页面
			Document doc = PageTemplateInterpolator
					.getDom(TEMPLATE_SYS_LOGIN_SLIDE);
			Object[] domString = PageTemplateInterpolator.interpolate(
					TEMPLATE_SYS_LOGIN_SLIDE, doc, FLYVariableResolver
							.instance());

			JSONObject jo = new JSONObject();
			jo.put("dom", (String) domString[0]);
			jo.put("script", JSONUtils
					.convertToJSONArray((List<String>) domString[1]));

			return jo.toJSONString();
		} catch (Exception ex) {
			throw new BIException("获得资源库命名出现错误。", ex);
		}
	}

}
