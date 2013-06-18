package com.plywet.platform.bi.web.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.pentaho.di.i18n.BaseMessages;
import org.springframework.stereotype.Service;

import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.sec.WebMarshal;
import com.plywet.platform.bi.core.utils.DateUtils;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.delegates.vo.FunctionType;
import com.plywet.platform.bi.web.entity.AjaxResult;
import com.plywet.platform.bi.web.entity.AjaxResultEntity;
import com.plywet.platform.bi.web.service.BIPageDelegates;

@Service("bi.resource.sysToolsResource")
@Path("/systools")
public class BISysToolsResource {
	private final Logger logger = Logger.getLogger(BISysToolsResource.class);

	private static final String ID_EDITOR_CONTENT_NAVI_SYSTOOLS_BP = "editorContent-navi-systools-bp";

	private static final String ABOUT_TEMPLATE = "editor/sys/about.h";

	@Resource(name = "bi.service.pageServices")
	private BIPageDelegates pageDelegates;

	private static Class<?> PKG = BISysToolsResource.class;

	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentSystemTools(
			@CookieParam("repository") String repository,
			@QueryParam("parentId") long parentId) throws BIException {
		try {
			List<FunctionType> funcs = pageDelegates.getFunctionsByParent(
					repository, parentId);
			StringBuffer sb = new StringBuffer();
			if (funcs != null && !funcs.isEmpty()) {
				for (int i = 0; i < funcs.size(); i++) {
					FunctionType ft = funcs.get(i);
					sb.append("<div class=\"func-line-"
							+ ((i % 2 == 0) ? "odd" : "even") + "\">");
					sb.append("<div class=\"func-title-con\">");
					sb.append("<div class=\"func-item-text\">" + ft.getDesc()
							+ "</div>");
					sb.append("</div>");

					List<FunctionType> children = ft.getChildren();
					if (children == null) {
						continue;
					}
					for (FunctionType child : children) {
						if (child.getAuth().equals(WebMarshal.CHECK_MODULE_OK)) {

							sb
									.append("<div id=\"func_"
											+ child.getId()
											+ "\" class=\"func-item-con\" ext=\""
											+ child.getExtAttrString()
											+ "\" title=\""
											+ child.getDesc()
											+ "\" onclick=\"Plywet.sysTools.openTool(this)\" onmouseover=\"$(this).addClass('ui-state-hover')\" onmouseout=\"$(this).removeClass('ui-state-hover')\">");
							sb
									.append("<div class=\"func-item\" style=\"background:url('resources/images/functype/"
											+ child.getCode()
											+ ".png') no-repeat\"></div>");
							sb.append("<div class=\"func-item-text\">"
									+ child.getDesc() + "</div>");
							sb.append("</div>");

						} else {
							sb
									.append("<div id=\"func_"
											+ child.getId()
											+ "\" class=\"func-item-con ui-state-disabled\" title=\""
											+ child.getAuth() + "\">");
							sb
									.append("<div class=\"func-item\" style=\"background:url('resources/images/functype/"
											+ child.getCode()
											+ ".png') no-repeat\"></div>");
							sb.append("<div class=\"func-item-text\">"
									+ child.getDesc() + "</div>");
							sb.append("</div>");
						}
					}
					sb.append("</div>");

					if (i < funcs.size() - 1) {
						sb
								.append("<div class=\"split-line-"
										+ ((i % 2 == 0) ? "odd" : "even")
										+ "\"></div>");
					}
				}
			}

			// 1.为一级导航页面创建一个更新操作
			AjaxResultEntity naviResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_APPEND).setTargetId(
							ID_EDITOR_CONTENT_NAVI_SYSTOOLS_BP).setDom(
							sb.toString());

			return AjaxResult.instance().addEntity(naviResult).toJSONString();
		} catch (Exception ex) {
			logger.error("创建导航的功能内容页面出现错误。");
			throw new BIException("创建导航的功能内容页面出现错误。", ex);
		}
	}

	@GET
	@Path("/about")
	@Produces(MediaType.TEXT_PLAIN)
	public String openSetting(@CookieParam("repository") String repository,
			@QueryParam("targetId") String targetId) throws BIException {
		try {

			FLYVariableResolver attrsMap = FLYVariableResolver.instance();

			WebMarshal wm = WebMarshal.getInstance();

			String ver = BaseMessages.getString(PKG,
					"Page.SysTools.About.License.Version", wm
							.getLicVersionString());
			attrsMap.addVariable("licVersion", ver);

			int diff = DateUtils.diffDays(wm.getMaxValidDate());

			String validDays = "";
			if (diff > 1000 || diff < 0) {
			} else {
				validDays = BaseMessages.getString(PKG,
						"Page.SysTools.About.License.Valid.Days", String
								.valueOf(diff));
			}
			attrsMap.addVariable("validDays", validDays);

			attrsMap.addVariable("customer", wm.getCustomerFullName());

			String version = BaseMessages.getString(PKG,
					"Page.SysTools.About.Server.Version", wm.getVersion());
			attrsMap.addVariable("version", version);

			String licTerms = BaseMessages.getString(PKG,
					"Page.SysTools.About.License.Terms");
			attrsMap.addVariable("licTerms", licTerms);

			String licTo = BaseMessages.getString(PKG,
					"Page.SysTools.About.License.To");
			attrsMap.addVariable("licTo", licTo);

			String[] moduleCodes = wm.getModuleCodes();
			for (int m = 0; m < moduleCodes.length; m++) {
				moduleCodes[m] = moduleCodes[m].toLowerCase();
			}
			attrsMap.addVariable("moduleCodes", moduleCodes);
			attrsMap.addVariable("moduleValids", wm.getModuleValids());
			attrsMap.addVariable("moduleHelpTexts", wm.getModuleHelpTexts());

			String[] licCopyright = new String[] {
					BaseMessages.getString(PKG,
							"Page.SysTools.About.License.Copyright.Line1"),
					BaseMessages.getString(PKG,
							"Page.SysTools.About.License.Copyright.Line2"),
					BaseMessages.getString(PKG,
							"Page.SysTools.About.License.Copyright.Line3"),
					BaseMessages.getString(PKG,
							"Page.SysTools.About.License.Copyright.Line4"), };
			attrsMap.addVariable("licCopyright", licCopyright);

			// 返回页面控制
			Object[] domString = PageTemplateInterpolator.interpolate(
					ABOUT_TEMPLATE, attrsMap);
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception ex) {
			throw new BIException("创建导航页面出现错误。", ex);
		}
	}

}
