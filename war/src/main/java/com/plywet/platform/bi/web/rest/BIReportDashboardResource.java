package com.plywet.platform.bi.web.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.xml.XMLUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.plywet.platform.bi.component.exceptions.BIComponentException;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.plywet.platform.bi.component.utils.PageTemplateResolverType;
import com.plywet.platform.bi.component.vo.ComponentPlugin;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.exception.BIJSONException;
import com.plywet.platform.bi.core.exception.BIPageException;
import com.plywet.platform.bi.core.utils.JSONUtils;
import com.plywet.platform.bi.report.meta.TemplateMeta;
import com.plywet.platform.bi.web.cache.TemplateCache;
import com.plywet.platform.bi.web.service.BIReportDelegates;

@Service("bi.resource.reportDashboardResource")
@Path("/dashboard")
public class BIReportDashboardResource {
	@Resource(name = "bi.service.reportService")
	private BIReportDelegates reportService;

	/**
	 * 打开一个Dashboard编辑页面
	 * 
	 * @param repository
	 * @param id
	 * @return
	 * @throws BIException
	 */
	@GET
	@Path("/open/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openDashboardEditor(
			@CookieParam("repository") String repository,
			@PathParam("id") String id) throws BIException {

		try {
			// 获得报表对象
			Object[] report = reportService.getReportObject(Long.valueOf(id));
			Document doc = PageTemplateInterpolator.getDomForEditorWithContent(
					(String) report[1],
					TemplateMeta.TEMPLATE_ATTRIBUTE_EDITOR_ID_PREFIX + id);

			// 保留编辑状态，对于集群应用需要使用共享缓存
			TemplateMeta templateMeta = new TemplateMeta(id, doc);
			TemplateCache.put(id, templateMeta);

			return getDashboardJson(id, templateMeta).toJSONString();
		} catch (Exception ex) {
			throw new BIException("创建Dashboard编辑页面出现错误。", ex);
		}
	}

	@GET
	@Path("/move/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String moveDashboard(@PathParam("id") String id,
			@QueryParam("source") String source,
			@QueryParam("target") String target) throws BIException {
		try {
			TemplateMeta templateMeta = TemplateCache.get(id);
			Document doc = templateMeta.getDoc();
			Node sourceNode = getNodeWithEditorId(doc, source);
			Node targetNode = getNodeWithEditorId(doc, target);

			String sourceType = XMLUtils.getTagOrAttribute(sourceNode,
					TemplateMeta.TEMPLATE_ATTRIBUTE_EDITOR_TYPE);

			String targetType = XMLUtils.getTagOrAttribute(targetNode,
					TemplateMeta.TEMPLATE_ATTRIBUTE_EDITOR_TYPE);

			ComponentPlugin sourcePlugin = PageTemplateResolverType
					.getPlugin(sourceType);
			ComponentPlugin targetPlugin = PageTemplateResolverType
					.getPlugin(targetType);

			// 如果源节点是GridLayoutItem
			if (sourcePlugin != null
					&& HTML.COMPONENT_TYPE_GRID_LAYOUT_ITEM
							.equalsIgnoreCase(sourcePlugin.getId())) {
				if (targetPlugin != null
						&& HTML.COMPONENT_TYPE_GRID_LAYOUT_ITEM
								.equalsIgnoreCase(targetPlugin.getId())) {
					XMLUtils.insertBefore(sourceNode, targetNode);
				} else if (targetPlugin != null
						&& HTML.COMPONENT_TYPE_GRID_LAYOUT
								.equalsIgnoreCase(targetPlugin.getId())) {
					XMLUtils.appendTo(sourceNode, targetNode);
				} else {
					throw new BIComponentException("不允许将栅格布局的项目拖拽到其他类型的节点。");
				}
			}
			// 如果目标节点类型是GridLayout，且源节点不是GridLayoutItem
			else if (targetPlugin != null
					&& HTML.COMPONENT_TYPE_GRID_LAYOUT
							.equalsIgnoreCase(targetPlugin.getId())) {
				// 创建一个GridLayoutItem
				Node node = templateMeta.createElement(HTML.COMPONENT_CATEGORY_LAYOUT,
						HTML.COMPONENT_TYPE_GRID_LAYOUT_ITEM);

				XMLUtils.appendTo(sourceNode, node);
				XMLUtils.appendTo(node, targetNode);
			}
			// 如果目标节点类型是容器属性或者是HTML容器，添加到其内部
			else if (isContainer(targetType)) {
				XMLUtils.appendTo(sourceNode, targetNode);
			}
			// 其他情况
			else {
				XMLUtils.insertBefore(sourceNode, targetNode);
			}

			return getDashboardJson(id, templateMeta).toJSONString();
		} catch (Exception ex) {
			throw new BIException("移动Dashboard页面元素出现错误。", ex);
		}
	}

	private boolean isContainer(String targetType) {
		ComponentPlugin targetPlugin = PageTemplateResolverType
				.getPlugin(targetType);
		if (targetPlugin == null) {
			// TODO 判断HTML可容纳的元素
		} else if (targetPlugin != null && targetPlugin.isContainer()) {
			return true;
		}
		return false;
	}

	private Node getNodeWithEditorId(Node node, String editorId) {
		return XMLUtils.selectSingleNode(node, "//*[@"
				+ TemplateMeta.TEMPLATE_ATTRIBUTE_EDITOR_ID + "='" + editorId
				+ "']");
	}

	@GET
	@Path("/append/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String appendDashboard(@PathParam("id") String id,
			@QueryParam("source") String source,
			@QueryParam("target") String target) throws BIException {
		try {
			TemplateMeta templateMeta = TemplateCache.get(id);
			// TODO
			return getDashboardJson(id, templateMeta).toJSONString();
		} catch (Exception ex) {
			throw new BIException("添加Dashboard页面元素出现错误。", ex);
		}
	}

	@SuppressWarnings("unchecked")
	private JSONObject getDashboardJson(String id, TemplateMeta templateMeta)
			throws BIJSONException, BIPageException, KettleException {
		Document doc = templateMeta.getDoc();
		Object[] domString = PageTemplateInterpolator.interpolate("report:"
				+ id, doc, FLYVariableResolver.instance());

		JSONObject jo = new JSONObject();
		JSONObject reportInfo = new JSONObject();
		reportInfo.put("id", id);
		jo.put("reportInfo", reportInfo);
		jo.put("dom", (String) domString[0]);
		jo.put("script", JSONUtils
				.convertToJSONArray((List<String>) domString[1]));
		jo.put("domStructure", templateMeta.getXML());

		return jo;
	}
}
