package com.plywet.platform.bi.web.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.pentaho.di.core.plugins.PluginInterface;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.springframework.stereotype.Service;

import com.plywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.plywet.platform.bi.component.components.flow.FlowChartData;
import com.plywet.platform.bi.component.components.flow.FlowChartMeta;
import com.plywet.platform.bi.component.components.flow.FlowElementSet;
import com.plywet.platform.bi.component.components.flow.FlowHop;
import com.plywet.platform.bi.component.components.flow.FlowStep;
import com.plywet.platform.bi.component.components.flow.step.PictureStep;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.utils.JSONUtils;
import com.plywet.platform.bi.core.utils.PropertyUtils;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.web.entity.ActionMessage;
import com.plywet.platform.bi.web.entity.AjaxResult;
import com.plywet.platform.bi.web.entity.AjaxResultEntity;
import com.plywet.platform.bi.web.model.ParameterContext;
import com.plywet.platform.bi.web.service.BIPageDelegates;
import com.plywet.platform.bi.web.service.impl.BIPageServices;
import com.plywet.platform.bi.web.utils.DIWebUtils;

@Service("bi.resource.transJobResource")
@Path("/transjob")
public class BITransJobResource {
	private final Logger logger = Logger.getLogger(BITransJobResource.class);

	public static final String TRANS_TEMPLATE = "editor/editor_trans.h";

	private static final String TRANS_SETTING_TEMPLATE = "editor/trans/setting.h";

	public static final String TRANS_STEP_TEMPLATE_PREFIX = "editor/steps/";

	private static final String ID_EDITOR_CONTENT_NAVI_TRANS_BC = "editorContent-navi-trans-bc";
	private static final String ID_EDITOR_CONTENT_NAVI_TRANS_BP = "editorContent-navi-trans-bp";

	@Resource(name = "bi.service.pageServices")
	private BIPageDelegates pageDelegates;

	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentTrans(
			@CookieParam("repository") String repository) throws BIException {
		return buildNaviContent(repository, BIPageServices.DIRECTORY_ROOT_ID,
				true);
	}

	@GET
	@Path("/dir/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String flushNaviContent(
			@CookieParam("repository") String repository,
			@PathParam("id") String id) throws BIException {
		return buildNaviContent(repository, Long.parseLong(id), false);
	}

	private String buildNaviContent(String repository, Long idL, boolean isNew)
			throws BIException {
		try {

			// 1.为转换的面包屑页面创建一个自定义操作
			BreadCrumbMeta bce = pageDelegates.getParentDirectories(repository,
					idL);
			AjaxResultEntity transBCResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_TRANS_BC).setCmd(
							isNew ? "widget.BreadCrumb" : "this.flush")
					.setData(bce);

			// 2.填充浏览面板内容
			BrowseMeta browseMeta = new BrowseMeta();
			pageDelegates.getSubDirectory(repository, idL, browseMeta);
			pageDelegates.getSubDirectoryObject(repository, idL, browseMeta);
			browseMeta.addClass("fly-browsepanel");

			AjaxResultEntity browseResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_TRANS_BP).setCmd(
							isNew ? "widget.BrowsePanel" : "this.flush")
					.setData(browseMeta);

			return AjaxResult.instance().addEntity(transBCResult).addEntity(
					browseResult).toJSONString();
		} catch (Exception ex) {
			logger.error("创建导航的转换内容页面出现错误。");
			throw new BIException("创建导航的转换内容页面出现错误。", ex);
		}
	}

	/**
	 * 通过图形保存转换
	 * 
	 * @param repository
	 * @param id
	 * @param body
	 * @return
	 * @throws BIException
	 */
	@POST
	@Path("/trans/{id}/save")
	@Produces(MediaType.TEXT_PLAIN)
	public String saveTrans(@CookieParam("repository") String repository,
			@PathParam("id") String id, String body) throws BIException {
		try {
			Long idL = Long.parseLong(id);
			ParameterContext paramContext = DIWebUtils
					.fillParameterContext(body);
			String val = paramContext.getParameter("val");

			TransMeta transMeta = pageDelegates.loadTransformation(repository,
					idL);
			FlowChartData data = new FlowChartData();
			data.construct(JSONUtils.convertStringToJSONObject(val));

			// 修改TransMeta
			modifyTrans(transMeta, data);

			// 保存
			pageDelegates.save(repository, transMeta);

			// 更新缓存
			pageDelegates.updateCacheTransformation(repository, idL, transMeta);
			return ActionMessage.instance().success(
					"保存转换【" + transMeta.getName() + "】成功!").toJSONString();
		} catch (Exception ex) {
			logger.error("保存转换[" + id + "]出现错误。");
			throw new BIException("保存转换[" + id + "]出现错误。", ex);
		}
	}

	/**
	 * 创建新步骤
	 * 
	 * @param flowStep
	 * @return
	 * @throws BIException
	 */
	private StepMeta newStep(FlowStep flowStep) throws BIException {
		String pluginName = flowStep.getStringAttribute(FlowStep.PROVIDER);
		try {
			PluginRegistry registry = PluginRegistry.getInstance();
			PluginInterface stepPlugin = registry.findPluginWithId(
					StepPluginType.class, pluginName);

			StepMetaInterface info = (StepMetaInterface) registry
					.loadClass(stepPlugin);
			info.setDefault();

			String name = (String) flowStep.getExtendData().get("stepName");
			StepMeta sm = new StepMeta(stepPlugin.getIds()[0], name, info);

			setStepGraphAttribute(sm, flowStep);

			return sm;

		} catch (Exception ex) {
			logger.error("创建步骤[" + pluginName + "]出现错误。");
			throw new BIException("创建步骤[" + pluginName + "]出现错误。", ex);
		}
	}

	private void setStepGraphAttribute(StepMeta sm, FlowStep flowStep) {
		sm.setLocation(flowStep.getIntAttribute(FlowStep.D_X), flowStep
				.getIntAttribute(FlowStep.D_Y));
	}

	private void setHopGraphAttribute(TransHopMeta hm, FlowHop flowHop) {
		long[] x = flowHop.getX();
		long[] y = flowHop.getY();
	}

	private void modifyTrans(TransMeta transMeta, FlowChartData data)
			throws BIException {
		// step
		List<StepMeta> stepMetaList = new ArrayList<StepMeta>();
		Map<String, StepMeta> stepMetaMap = new HashMap<String, StepMeta>();
		for (FlowStep flowStep : data.getElementSet().getSteps()) {
			StepMeta sm = transMeta.findStep((String) flowStep.getExtendData()
					.get("stepName"));
			if (sm == null) {
				// 创建新的StepMeta
				sm = newStep(flowStep);
			} else {
				setStepGraphAttribute(sm, flowStep);
			}
			stepMetaMap.put(flowStep.getId(), sm);
			stepMetaList.add(sm);
		}

		int len = transMeta.nrSteps();
		for (int i = 0; i < len; i++) {
			transMeta.removeStep(0);
		}
		for (StepMeta stepMeta : stepMetaList) {
			transMeta.addStep(stepMeta);
		}

		// hop
		List<TransHopMeta> hopMetaList = new ArrayList<TransHopMeta>();
		for (FlowHop flowHop : data.getElementSet().getHops()) {
			String fromStepId = flowHop.getFromEl().getId();
			String toStepId = flowHop.getToEl().getId();

			TransHopMeta hm = transMeta.findTransHop(stepMetaMap
					.get(fromStepId), stepMetaMap.get(toStepId));
			if (hm == null) {
				hm = new TransHopMeta(stepMetaMap.get(fromStepId), stepMetaMap
						.get(toStepId));
			}

			setHopGraphAttribute(hm, flowHop);

			hopMetaList.add(hm);
		}

		len = transMeta.nrTransHops();
		for (int i = 0; i < len; i++) {
			transMeta.removeTransHop(0);
		}
		for (TransHopMeta hopMeta : hopMetaList) {
			transMeta.addTransHop(hopMeta);
		}
	}

	@GET
	@Path("/trans/{id}/setting")
	@Produces(MediaType.TEXT_PLAIN)
	public String openSetting(@CookieParam("repository") String repository,
			@PathParam("id") String id, @QueryParam("targetId") String targetId)
			throws BIException {
		try {
			FLYVariableResolver attrsMap = FLYVariableResolver.instance();

			Object[] domString = PageTemplateInterpolator.interpolate(
					TRANS_SETTING_TEMPLATE, attrsMap);

			// 返回页面控制
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception ex) {
			throw new BIException("创建导航页面出现错误。", ex);
		}
	}

	@GET
	@Path("/trans/{id}/discard")
	@Produces(MediaType.TEXT_PLAIN)
	public String discardTrans(@CookieParam("repository") String repository,
			@PathParam("id") String id) throws BIException {
		Long idL = Long.parseLong(id);
		pageDelegates.clearCacheTransformation(repository, idL);
		return ActionMessage.instance().success().toJSONString();
	}

	@GET
	@Path("/trans/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openTransEditor(@CookieParam("repository") String repository,
			@PathParam("id") String id) throws BIException {
		PluginRegistry registry = PluginRegistry.getInstance();
		Long idL = Long.parseLong(id);
		TransMeta transMeta = pageDelegates.loadTransformation(repository, idL);
		FlowChartMeta meta = new FlowChartMeta();
		meta.init();
		FlowElementSet els = meta.getFlowChartData().getElementSet();

		meta.setId("trans-" + id);
		meta.getFlowChartData().addExtendData("transId", id);

		Map<String, FlowStep> stepMap = new HashMap<String, FlowStep>();
		for (StepMeta stepMeta : transMeta.getSteps()) {
			PictureStep s = new PictureStep();
			s.setId(stepMeta.getObjectId().getId());
			s.addAttribute(PictureStep.D_X, stepMeta.getLocation().x);
			s.addAttribute(PictureStep.D_Y, stepMeta.getLocation().y);
			s.addAttribute(PictureStep.B_TEXT, new String[] { stepMeta
					.getName() });
			s.addAttribute(PictureStep.PROVIDER, stepMeta.getStepID());
			PluginInterface plugin = registry.findPluginWithId(
					StepPluginType.class, stepMeta.getStepID());
			s.addAttribute(PictureStep.S_IMG_SRC, "resources/images/entities/"
					+ plugin.getImageFile());
			s.addExtendData("stepName", stepMeta.getName());

			String pos = PropertyUtils
					.getProperty(PropertyUtils.UI_DIALOG_PREFIX
							+ stepMeta.getStepID()
							+ PropertyUtils.UI_DIALOG_POSTION_SUFFIX);
			if (pos != null) {
				s.addExtendData("dialogPosition", pos);
			}

			els.addStep(s);
			stepMap.put(stepMeta.getObjectId().getId(), s);
		}

		int hnr = transMeta.nrTransHops();
		for (int i = 0; i < hnr; i++) {
			TransHopMeta hopMeta = transMeta.getTransHop(i);
			FlowHop h = new FlowHop();
			h.setFromEl(stepMap
					.get(hopMeta.getFromStep().getObjectId().getId()));
			h.setToEl(stepMap.get(hopMeta.getToStep().getObjectId().getId()));
			els.addHop(h);
		}

		return meta.getFormJo().toJSONString();
	}

	/**
	 * 打开Step的设置页面
	 * 
	 * @param repository
	 * @param transId
	 * @param stepName
	 * @return
	 * @throws BIException
	 */
	@GET
	@Path("/transstep/{transId}/{stepName}/{stepTypeId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openTransStepEditor(
			@CookieParam("repository") String repository,
			@PathParam("transId") String transId,
			@PathParam("stepName") String stepName,
			@PathParam("stepTypeId") String stepTypeId,
			@QueryParam("targetId") String targetId) throws BIException {
		try {
			Long idL = Long.parseLong(transId);
			TransMeta transMeta = pageDelegates.loadTransformation(repository,
					idL);
			StepMeta stepMeta = transMeta.findStep(stepName);
			if (stepMeta == null) {
				PluginRegistry registry = PluginRegistry.getInstance();
				PluginInterface plugin = registry.findPluginWithId(
						StepPluginType.class, stepTypeId);
				stepMeta = new StepMeta(stepName, (StepMetaInterface) registry
						.loadClass(plugin));
				transMeta.addStep(stepMeta);
			}

			FLYVariableResolver attrsMap = FLYVariableResolver.instance();

			attrsMap.addVariable("transMeta", transMeta);
			attrsMap.addVariable("stepMeta", stepMeta);

			String formId = "form:" + targetId;
			attrsMap.addVariable("formId", formId);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TRANS_STEP_TEMPLATE_PREFIX
							+ stepMeta.getStepMetaInterface()
									.getDialogFileName(), attrsMap);

			// 返回页面控制
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception ex) {
			throw new BIException("打开Step的设置页面出现错误。", ex);
		}

	}

	/**
	 * 创建转换编辑器页面
	 * 
	 * @throws BIException
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/trans/editor")
	@Produces(MediaType.TEXT_PLAIN)
	public String createTransPage() throws BIException {
		try {
			// 生成插件工具栏
			List<String> transStepBar = pageDelegates
					.getPluginNavigator(Utils.CATEGORY_TRANS);
			List<BrowseMeta> transStepBrowses = new ArrayList<BrowseMeta>();

			for (int i = 0; i < transStepBar.size(); i++) {

				// 各个导航项下的组件
				BrowseMeta browseMeta = new BrowseMeta();

				pageDelegates.getPlugins(transStepBar.get(i), browseMeta,
						"fly-trans-step-plugin");

				// 增加额外属性
				List<BrowseNodeMeta> contents = (List<BrowseNodeMeta>) browseMeta
						.getContents();
				if (contents != null) {
					for (BrowseNodeMeta con : contents) {
						String id = con.getId();
						String pos = PropertyUtils
								.getProperty(PropertyUtils.UI_DIALOG_PREFIX
										+ id
										+ PropertyUtils.UI_DIALOG_POSTION_SUFFIX);
						if (pos != null) {
							con.addExtendAttribute("dialogPosition", pos);
						}
					}
				}

				transStepBrowses.add(browseMeta);
			}

			FLYVariableResolver attrsMap = FLYVariableResolver.instance();
			attrsMap.addVariable("editorId", BIBaseResource.ID_EDITOR_TRANS);
			attrsMap.addVariable("transStepBar", transStepBar);
			attrsMap.addVariable("transStepBrowses", transStepBrowses);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TRANS_TEMPLATE, attrsMap);

			// 创建一个更新操作
			AjaxResultEntity entity = AjaxResultEntity.instance().setOperation(
					Utils.RESULT_OPERATION_APPEND).setTargetId(
					BIBaseResource.ID_EDITOR_CONTENT_PANELS).setDomAndScript(
					domString);

			AjaxResult result = AjaxResult.instance();
			result.addEntity(entity);

			return result.toJSONString();
		} catch (Exception ex) {
			throw new BIException("创建转换编辑器页面出现错误。", ex);
		}
	}
}
