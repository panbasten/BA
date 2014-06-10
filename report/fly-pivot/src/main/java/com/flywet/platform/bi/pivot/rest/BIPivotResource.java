package com.flywet.platform.bi.pivot.rest;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.flywet.platform.bi.base.enums.BIReportCategory;
import com.flywet.platform.bi.base.rest.AbstractReportResource;
import com.flywet.platform.bi.base.service.intf.BIReportDelegates;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.component.web.AjaxResult;
import com.flywet.platform.bi.component.web.AjaxResultEntity;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.FileUtils;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.pivot.cache.BIPivotReportCache;
import com.flywet.platform.bi.pivot.model.PivotReport;
import com.flywet.platform.bi.pivot.model.context.IResourceContext;
import com.flywet.platform.bi.pivot.model.factory.PivotReportFactory;
import com.flywet.platform.bi.pivot.service.intf.BIPivotDelegates;
import com.flywet.platform.bi.rest.BIBaseResource;

@Service("bi.resource.pivotResource")
@Path("/pivot")
public class BIPivotResource extends AbstractReportResource {

	private final Logger logger = Logger.getLogger(BIPivotResource.class);

	@Resource(name = "bi.service.reportService")
	private BIReportDelegates reportService;

	@Resource(name = "bi.service.pivotService")
	private BIPivotDelegates pivotService;

	/**
	 * 获取资源，用于portal
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIException
	 */
	public Object[] getResourceForPortal(String targetId,
			HashMap<String, Object> context) throws BIException {

		Object[] rtn = new Object[2];

		String param = (String) context.get("param");
		String id = param.substring(0, param.indexOf("_"));
		String name = param.substring(param.indexOf("_") + 1);

		PivotReport pr = getPivotById(id);
		IResourceContext pc = (IResourceContext) pr.findByName(name);

		String n = Const.NVL(pc.getName(), "");
		if (!Const.isEmpty(pc.getExt())) {
			n = n + "." + pc.getExt();
		}

		rtn[0] = n;
		rtn[1] = pc.getData();

		return rtn;
	}

	/**
	 * 显示图片，用于portal
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIException
	 */
	public byte[] showImageForPortal(String targetId,
			HashMap<String, Object> context) throws BIException {

		String param = (String) context.get("param");
		String id = param.substring(0, param.indexOf("_"));
		String name = param.substring(param.indexOf("_") + 1);

		PivotReport pr = getPivotById(id);
		IResourceContext pc = (IResourceContext) pr.findByName(name);

		return pc.getData();
	}

	/**
	 * 打开一个多维报表，用于Portal
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIException
	 */
	@SuppressWarnings("unchecked")
	public String openPivotForPortal(String targetId,
			HashMap<String, Object> context) throws BIException {
		try {
			JSONObject pvt = pivotService.query(getPivotById((String) context
					.get("param")));
			JSONObject rtn = JSONUtils.convertToJSONObject(context);
			rtn.put("targetId", targetId);
			rtn.put("data", pvt);

			return rtn.toJSONString();
		} catch (BIException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			logger.error("创建多维报表编辑页面出现错误。");
			throw new BIException("创建多维报表编辑页面出现错误。", ex);
		}
	}

	@GET
	@Path("/{id}/showImage/{name}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public void showImage(@PathParam("id") String id,
			@PathParam("name") String name,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws BIException {
		try {

			response.setContentType("application/octet-stream");
			request.setCharacterEncoding(Const.XML_ENCODING);
			response.setCharacterEncoding(Const.XML_ENCODING);

			PivotReport pr = getPivotById(id);
			IResourceContext pc = (IResourceContext) pr.findByName(name);

			response.setContentType("application/octet-stream");
			request.setCharacterEncoding(Const.XML_ENCODING);
			response.setCharacterEncoding(Const.XML_ENCODING);

			FileUtils.write(pc.getData(), response.getOutputStream());
		} catch (Exception e) {
			logger.error("获取报表图片资源出现错误。", e);
			throw new BIException("获取报表图片资源出现错误。", e);
		}
	}

	/**
	 * 通过报表id和资源name获得资源流
	 * 
	 * @param id
	 * @param name
	 * @param request
	 * @param response
	 * @throws BIException
	 */
	@GET
	@Path("/{id}/resource/{name}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public void getPivotResource(@PathParam("id") String id,
			@PathParam("name") String name,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws BIException {
		try {

			response.setContentType("application/octet-stream");
			request.setCharacterEncoding(Const.XML_ENCODING);
			response.setCharacterEncoding(Const.XML_ENCODING);

			PivotReport pr = getPivotById(id);
			IResourceContext pc = (IResourceContext) pr.findByName(name);

			response.setContentType("application/octet-stream");
			request.setCharacterEncoding(Const.XML_ENCODING);
			response.setCharacterEncoding(Const.XML_ENCODING);

			String n = Const.NVL(name, "");
			if (!Const.isEmpty(pc.getExt())) {
				n = n + "." + pc.getExt();
			}

			String fileName = Const.replace(n, " ", "%20");
			// 保证另存为文件名为中文
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes(), "ISO8859_1"));

			FileUtils.write(pc.getData(), response.getOutputStream());
		} catch (Exception e) {
			logger.error("获取报表图片资源出现错误。", e);
			throw new BIException("获取报表图片资源出现错误。", e);
		}
	}

	/**
	 * 打开一个多维报表编辑页面
	 * 
	 * @param id
	 * @return
	 * @throws BIException
	 */
	@GET
	@Path("/{id}/open")
	@Produces(MediaType.TEXT_PLAIN)
	public String openPivotEditor(@PathParam("id") String id)
			throws BIException {
		try {
			JSONObject rtn = pivotService.query(getPivotById(id));
			return rtn.toJSONString();
		} catch (BIException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			logger.error("创建多维报表编辑页面出现错误。");
			throw new BIException("创建多维报表编辑页面出现错误。", ex);
		}
	}

	private PivotReport getPivotById(String id) throws NumberFormatException,
			BIException {
		PivotReport pr = BIPivotReportCache.getPivotReportEditor(Long
				.valueOf(id));

		if (pr == null) {
			// 获得报表对象
			Object[] report = reportService.getReportObject(Long.valueOf(id));
			Document doc = PageTemplateInterpolator
					.getDomWithContent((String) report[1]);
			Node prNode = XMLHandler.getSubNode(doc, "PivotReport");

			pr = PivotReportFactory.resolver(prNode);

			pr.addAttr("reportId", id);

			// TODO 添加缓存
			// BIPivotReportCache.putPivotReportEditor(Long.valueOf(id), pr);
		}

		return pr;
	}

	/**
	 * 初始化加载多维报表编辑器页面
	 * 
	 * @return
	 * @throws BIException
	 */
	@Override
	@GET
	@Path("/editor")
	@Produces(MediaType.TEXT_PLAIN)
	public String loadEditor() throws BIException {
		try {
			String cate = BIReportCategory.REPORT_TYPE_PIVOT_REPORT
					.getCategory();

			FLYVariableResolver attrsMap = FLYVariableResolver.instance();
			attrsMap.addVariable("editorId", cate);

			Object[] domString = PageTemplateInterpolator.interpolate(
					getTemplateString(cate), attrsMap);

			// 创建一个更新操作
			AjaxResultEntity entity = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_APPEND)
					.setTargetId(BIBaseResource.ID_EDITOR_CONTENT_PANELS)
					.setDomAndScript(domString);

			AjaxResult result = AjaxResult.instance();
			result.addEntity(entity);

			return result.toJSONString();
		} catch (Exception ex) {
			logger.error("初始化加载多维报表编辑器页面出现错误。");
			throw new BIException("初始化加载多维报表编辑器页面出现错误。", ex);
		}
	}

}
