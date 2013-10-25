package com.flywet.platform.bi.web.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.plugins.PluginInterface;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.DatabaseImpact;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.component.components.flow.FlowChartData;
import com.flywet.platform.bi.component.components.flow.FlowChartMeta;
import com.flywet.platform.bi.component.components.flow.FlowElementSet;
import com.flywet.platform.bi.component.components.flow.FlowHop;
import com.flywet.platform.bi.component.components.flow.FlowStep;
import com.flywet.platform.bi.component.components.flow.step.PictureStep;
import com.flywet.platform.bi.component.components.grid.GridDataObject;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.component.web.ActionMessage;
import com.flywet.platform.bi.component.web.AjaxResult;
import com.flywet.platform.bi.core.ContextHolder;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.model.ParameterContext;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.core.utils.PropertyUtils;
import com.flywet.platform.bi.delegates.vo.User;
import com.flywet.platform.bi.services.intf.BITransDelegates;
import com.flywet.platform.bi.services.intf.BIUserDelegate;
import com.flywet.platform.bi.web.model.CheckResultObject;
import com.flywet.platform.bi.web.model.NamedParameterObject;
import com.flywet.platform.bi.web.utils.BIWebUtils;

@Service("bi.resource.transResource")
@Path("/trans")
public class BITransResource {

	private final Logger log = Logger.getLogger(BITransResource.class);

	private static Class<?> PKG = BITransResource.class;

	private static final String TRANS_SETTING_TEMPLATE = "editor/trans/setting.h";

	private static final String TRANS_RUN_TEMPLATE = "editor/trans/run.h";

	private static final String TRANS_CHECK_TEMPLATE = "editor/trans/check.h";

	private static final String TRANS_ANALYSE_DB_TEMPLATE = "editor/trans/analyseDB.h";

	private static final String TRANS_STEP_TEMPLATE_PREFIX = "editor/trans/steps/";

	private static final String TRANS_CREATE_TEMPLATE = "editor/trans/create.h";

	private static final String TRANS_SAVEAS_TEMPLATE = "editor/trans/saveas.h";

	@Resource(name = "bi.service.transServices")
	private BITransDelegates transDelegates;

	@Resource(name = "bi.service.userService")
	private BIUserDelegate userService;

	@GET
	@Path("/create/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openTransCreateDialog(@PathParam("id") String id,
			@QueryParam("targetId") String targetId) throws BIException {
		String msg = "";
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();
			attrsMap.addVariable("dirId", id);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TRANS_CREATE_TEMPLATE, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			msg = e.getMessage();
		} catch (Exception e) {
			log.error("打开创建转换界面出现问题。");
			msg = "打开创建转换界面出现问题。";
		}
		return ActionMessage.instance().failure(msg).toJSONString();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/createsubmit")
	public String openTransCreateSubmit(String body) throws BIJSONException {
		ActionMessage am = new ActionMessage();
		try {
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);

			User user = userService
					.getUserByLogin(ContextHolder.getLoginName());

			String desc = paramContext.getParameter("desc");

			// 当前目录
			long dirId = paramContext.getLongParameter("dirId");

			if (Const.isEmpty(desc)) {
				return am.addErrorMessage("新增转换名称不能为空。").toJSONString();
			}

			// 保存转换
			TransMeta transMeta = transDelegates.createTransformation(
					userService.convetToKettleUser(user), dirId, desc);
			transDelegates.updateCacheTransformation(transMeta);

			am.addMessage("新增转换成功");
		} catch (BIException e) {
			log.error(e.getMessage());
			am.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			log.error("新增转换出现错误。");
			am.addErrorMessage("新增转换出现错误。");
		}
		return am.toJSONString();
	}

	/**
	 * 打开另存为对话框
	 * 
	 * @param id
	 * @param targetId
	 * @return
	 * @throws BIException
	 */
	@GET
	@Path("/saveas/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String saveAsTransDialog(@PathParam("id") String id,
			@QueryParam("targetId") String targetId) throws BIException {
		String msg = "";
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			// 转换所在目录
			TransMeta transMeta = transDelegates.loadTransformation(Long
					.valueOf(id));
			RepositoryDirectoryInterface dir = transMeta
					.getRepositoryDirectory();

			attrsMap.addVariable("transId", id);
			attrsMap.addVariable("transName", transMeta.getName());
			attrsMap.addVariable("dirId", dir.getObjectId().getId());

			Object[] domString = PageTemplateInterpolator.interpolate(
					TRANS_SAVEAS_TEMPLATE, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			msg = e.getMessage();
		} catch (Exception e) {
			log.error("打开另存为对话框面出现问题。");
			msg = "打开另存为对话框出现问题。";
		}
		return ActionMessage.instance().failure(msg).toJSONString();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/saveassubmit")
	public String saveAsTransSubmit(String body) throws BIJSONException {
		ActionMessage am = new ActionMessage();
		try {
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);

			User user = userService
					.getUserByLogin(ContextHolder.getLoginName());

			// 页面设置
			long dirId = paramContext.getLongParameter("dirId");
			long transId = paramContext.getLongParameter("transId");
			String transName = paramContext.getParameter("transName");

			// 保存
			TransMeta transMeta = transDelegates.saveAsTransformation(
					userService.convetToKettleUser(user), dirId, transId,
					transName);

			transDelegates.updateCacheTransformation(transMeta);

			am.addMessage("新增转换成功");
		} catch (BIException e) {
			log.error(e.getMessage());
			am.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			log.error("新增转换出现错误。");
			am.addErrorMessage("新增转换出现错误。");
		}
		return am.toJSONString();
	}

	/**
	 * 通过图形保存转换
	 * 
	 * @param repository
	 * @param id
	 * @param body
	 * @return
	 * @throws BIJSONException
	 * @throws BIException
	 */
	@POST
	@Path("/{id}/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveTrans(@PathParam("id") String id, String body)
			throws BIJSONException {
		ActionMessage am = ActionMessage.instance();
		try {
			Long idL = Long.parseLong(id);
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);
			String val = paramContext.getParameter("val");
			boolean silence = paramContext.getBooleanParameter("silence");

			TransMeta transMeta = transDelegates.loadTransformation(idL);
			FlowChartData data = new FlowChartData();
			data.construct(JSONUtils.convertStringToJSONObject(val));

			// 修改TransMeta
			modifyTrans(transMeta, data);

			// 设置修改信息
			transMeta.setModifiedDate(new Date());
			transMeta.setModifiedUser(ContextHolder.getLoginName());

			// 保存
			transDelegates.save(transMeta);

			// 更新缓存
			transDelegates.updateCacheTransformation(transMeta);

			if (silence) {
				return am.success().toJSONString();
			}
			return am.success("保存转换【" + transMeta.getName() + "】成功!")
					.toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			am.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			log.error("保存转换[" + id + "]出现错误。");
			am.addErrorMessage("保存转换[" + id + "]出现错误。");
		}
		return am.toJSONString();
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
			log.error("创建步骤[" + pluginName + "]出现错误。");
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

	@GET
	@Path("/{id}/analyse")
	@Produces(MediaType.TEXT_PLAIN)
	public String openAnalyse(@PathParam("id") String id,
			@QueryParam("targetId") String targetId) throws BIException {
		ActionMessage am = ActionMessage.instance();
		try {
			FLYVariableResolver attrsMap = FLYVariableResolver.instance();
			attrsMap.addVariable("formId", "trans_" + id);

			// 分析一个转换实例对数据库的影响
			TransMeta transMeta = transDelegates.loadTransformation(Long
					.parseLong(id));
			List<DatabaseImpact> impacts = new ArrayList<DatabaseImpact>();
			transMeta.analyseImpact(impacts, null);

			GridDataObject gd = GridDataObject.instance().putObjects(impacts);

			attrsMap.addVariable("impacts", gd);
			attrsMap.addVariable("impactsNum", impacts.size());

			Object[] domString = PageTemplateInterpolator.interpolate(
					TRANS_ANALYSE_DB_TEMPLATE, attrsMap);

			// 返回页面控制
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			am.addErrorMessage(e.getMessage());
		} catch (Exception ex) {
			log.error("创建分析转换对数据库影响页面出现错误。");
			am.addErrorMessage("创建分析转换对数据库影响页面出现错误。");
		}
		return am.toJSONString();
	}

	@GET
	@Path("/{id}/check")
	@Produces(MediaType.TEXT_PLAIN)
	public String openCheck(@PathParam("id") String id,
			@QueryParam("targetId") String targetId) throws BIException {
		ActionMessage am = ActionMessage.instance();
		try {
			FLYVariableResolver attrsMap = FLYVariableResolver.instance();
			attrsMap.addVariable("formId", "trans_" + id);

			// 检查一个转换实例
			TransMeta transMeta = transDelegates.loadTransformation(Long
					.parseLong(id));
			List<CheckResultInterface> remarks = new ArrayList<CheckResultInterface>();
			transMeta.checkSteps(remarks, false, null);

			List<CheckResultObject> cro = new ArrayList<CheckResultObject>();
			for (CheckResultInterface cri : remarks) {
				cro.add(CheckResultObject.instance(cri));
			}

			GridDataObject gd = GridDataObject.instance().putObjects(cro);
			attrsMap.addVariable("remarks", gd);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TRANS_CHECK_TEMPLATE, attrsMap);

			// 返回页面控制
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			am.addErrorMessage(e.getMessage());
		} catch (Exception ex) {
			log.error("创建校验转换页面出现错误。");
			am.addErrorMessage("创建校验转换页面出现错误。");
		}
		return am.toJSONString();
	}

	/**
	 * 运行一个转换的实例
	 * 
	 * @param id
	 * @param targetId
	 * @return
	 * @throws BIException
	 */
	@GET
	@Path("/{id}/run")
	@Produces(MediaType.TEXT_PLAIN)
	public String openRun(@PathParam("id") String id,
			@QueryParam("targetId") String targetId) throws BIException {
		ActionMessage am = ActionMessage.instance();
		try {
			FLYVariableResolver attrsMap = FLYVariableResolver.instance();
			attrsMap.addVariable("formId", "trans_" + id);

			// TODO 运行一个转换的实例

			Object[] domString = PageTemplateInterpolator.interpolate(
					TRANS_RUN_TEMPLATE, attrsMap);

			// 返回页面控制
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			am.addErrorMessage(e.getMessage());
		} catch (Exception ex) {
			log.error("创建转换执行页面出现错误。");
			am.addErrorMessage("创建转换执行页面出现错误。");
		}
		return am.toJSONString();
	}

	@POST
	@Path("/{id}/run/do")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String doRun(@PathParam("id") String id, String body)
			throws BIJSONException {
		ActionMessage am = ActionMessage.instance();
		String name = id;
		try {

			String FORM_PREFIX = "trans_" + id + ":";
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);

			// TODO

			return am.addMessage("保存转换【" + name + "】设置成功！").toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			am.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			log.error("保存转换【" + name + "】设置出现错误。");
			am.addErrorMessage("保存转换【" + name + "】设置出现错误。");
		}

		return am.toJSONString();
	}

	@GET
	@Path("/{id}/setting")
	@Produces(MediaType.TEXT_PLAIN)
	public String openSetting(@PathParam("id") String id,
			@QueryParam("targetId") String targetId) throws BIException {
		ActionMessage am = ActionMessage.instance();
		try {
			FLYVariableResolver attrsMap = FLYVariableResolver.instance();
			attrsMap.addVariable("formId", "trans_" + id);
			Long idL = Long.parseLong(id);
			TransMeta transMeta = transDelegates.loadTransformation(idL);
			attrsMap.addVariable("transMeta", transMeta);

			// 命名参数
			String[] keyArr = transMeta.listParameters();

			GridDataObject gd = GridDataObject.instance().setMinRows(
					HTML.DEFAULT_GRID_ROW_NUMBER);
			for (String key : keyArr) {
				NamedParameterObject np = NamedParameterObject.instance();
				np.setKey(key);
				np.setValue(transMeta.getParameterDefault(key));
				np.setDesc(transMeta.getParameterDescription(key));

				gd.putObject(np);
			}

			attrsMap.addVariable("parameters", gd);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TRANS_SETTING_TEMPLATE, attrsMap);

			// 返回页面控制
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			am.addErrorMessage(e.getMessage());
		} catch (Exception ex) {
			log.error("创建转换设置页面出现错误。");
			am.addErrorMessage("创建转换设置页面出现错误。");
		}
		return am.toJSONString();
	}

	@POST
	@Path("/{id}/setting/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveSetting(@PathParam("id") String id, String body)
			throws BIJSONException {
		ActionMessage am = ActionMessage.instance();
		String name = id;
		try {

			String FORM_PREFIX = "trans_" + id + ":";
			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);
			TransMeta transMeta = transDelegates.loadTransformation(Long
					.parseLong(id));
			name = paramContext.getParameter(FORM_PREFIX + "name");

			// 1.Base
			setSettingBase(transMeta, paramContext, FORM_PREFIX);

			// 2.NamedParameter
			setSettingNamedParameter(transMeta, paramContext, FORM_PREFIX);

			// 保存transMeta
			return am.addMessage("保存转换【" + name + "】设置成功！").toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			am.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			log.error("保存转换【" + name + "】设置出现错误。");
			am.addErrorMessage("保存转换【" + name + "】设置出现错误。");
		}

		return am.toJSONString();
	}

	private void setSettingBase(TransMeta transMeta,
			ParameterContext paramContext, String FORM_PREFIX) {
		// name
		String name = paramContext.getParameter(FORM_PREFIX + "name");
		transMeta.setName(name);

		// directoryId TODO

		// description
		String description = paramContext.getParameter(FORM_PREFIX
				+ "description");
		transMeta.setDescription(description);

		// extendedDescription
		String extendedDescription = paramContext.getParameter(FORM_PREFIX
				+ "extendedDescription");
		transMeta.setExtendedDescription(extendedDescription);

		// transstatus
		int transstatus = paramContext.getIntParameter(FORM_PREFIX
				+ "transstatus", 0);
		transMeta.setTransstatus(transstatus);

		// transversion
		String transversion = paramContext.getParameter(FORM_PREFIX
				+ "transversion");
		transMeta.setTransversion(transversion);

		// 修改者，修改时间 TODO

	}

	private void setSettingNamedParameter(TransMeta transMeta,
			ParameterContext paramContext, String FORM_PREFIX) throws Exception {
		JSONArray ja = JSONUtils.convertStringToJSONArray(paramContext
				.getParameter(FORM_PREFIX + "parameters"
						+ HTML.DATA_GRID_SUFFIX));

		transMeta.clearParameters();

		for (int i = 0; i < ja.size(); i++) {
			JSONObject jo = (JSONObject) ja.get(i);
			String key = JSONUtils.NVL_JSON(jo, "key", "");
			String value = JSONUtils.NVL_JSON(jo, "value", "");
			String desc = JSONUtils.NVL_JSON(jo, "desc", "");
			if (!Const.isEmpty(key)) {
				transMeta.addParameterDefinition(key, value, desc);
			}
		}
	}

	@GET
	@Path("/{id}/discard")
	@Produces(MediaType.TEXT_PLAIN)
	public String discardTrans(@PathParam("id") String id) throws BIException {
		Long idL = Long.parseLong(id);
		transDelegates.clearCacheTransformation(idL);
		return ActionMessage.instance().success().toJSONString();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openTransEditor(@PathParam("id") String id)
			throws BIException {
		PluginRegistry registry = PluginRegistry.getInstance();
		Long idL = Long.parseLong(id);
		TransMeta transMeta = transDelegates.loadTransformation(idL);
		FlowChartMeta meta = new FlowChartMeta();
		meta.init();
		FlowElementSet els = meta.getFlowChartData().getElementSet();

		meta.setId("trans-" + id);
		meta.getFlowChartData().addExtendData("transId", id);

		Map<String, FlowStep> stepMap = new HashMap<String, FlowStep>();
		for (StepMeta stepMeta : transMeta.getSteps()) {
			PictureStep s = new PictureStep();
			s.setId(stepMeta.getName());
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
			stepMap.put(stepMeta.getName(), s);
		}

		int hnr = transMeta.nrTransHops();
		for (int i = 0; i < hnr; i++) {
			TransHopMeta hopMeta = transMeta.getTransHop(i);
			FlowHop h = new FlowHop();
			h.setId("transHop_" + i);
			h.setFromEl(stepMap.get(hopMeta.getFromStep().getName()));
			h.setToEl(stepMap.get(hopMeta.getToStep().getName()));
			els.addHop(h);
		}

		return meta.getFormJo().toJSONString();
	}

	@POST
	@Path("/step/{transId}/{stepMetaName}/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveTransStep(@PathParam("transId") String transId,
			@PathParam("stepMetaName") String stepMetaName,
			@QueryParam("dx") String dx, @QueryParam("dy") String dy,
			String body) throws BIJSONException {
		ActionMessage am = ActionMessage.instance();
		String transName = transId;
		try {

			Long idL = Long.valueOf(transId);
			TransMeta transMeta = transDelegates.loadTransformation(idL);

			transName = transMeta.getName();

			StepMeta stepMeta = transMeta.findStep(stepMetaName);
			stepMeta.setLocation(Integer.valueOf(dx), Integer.valueOf(dy));

			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);

			// 设置具体的stepMeta
			stepMeta.getStepMetaInterface().loadPage(
					paramContext.getParameterHolder());
			return am.addMessage(
					"保存转换【" + transName + "】步骤【" + stepMetaName + "】设置成功！")
					.toJSONString();

		} catch (BIException e) {
			log.error(e.getMessage());
			am.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			log.error("保存转换【" + transName + "】步骤【" + stepMetaName + "】设置出现错误。");
			am.addErrorMessage("保存转换【" + transName + "】步骤【" + stepMetaName
					+ "】设置出现错误。");
		}
		return am.toJSONString();
	}

	/**
	 * 打开Step的设置页面
	 * 
	 * @param transId
	 * @param stepName
	 * @return
	 * @throws BIException
	 */
	@GET
	@Path("/step/{transId}/{stepName}/{stepTypeId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openTransStepEditor(@PathParam("transId") String transId,
			@PathParam("stepName") String stepName,
			@PathParam("stepTypeId") String stepTypeId,
			@QueryParam("targetId") String targetId) throws BIException {
		ActionMessage am = ActionMessage.instance();
		try {
			Long idL = Long.parseLong(transId);
			TransMeta transMeta = transDelegates.loadTransformation(idL);
			StepMeta stepMeta = transMeta.findStep(stepName);
			if (stepMeta == null) {
				PluginRegistry registry = PluginRegistry.getInstance();
				PluginInterface plugin = registry.findPluginWithId(
						StepPluginType.class, stepTypeId);
				stepMeta = new StepMeta(stepName, (StepMetaInterface) registry
						.loadClass(plugin));
				stepMeta.getStepMetaInterface().setDefault();
				transMeta.addStep(stepMeta);
			}

			FLYVariableResolver attrsMap = FLYVariableResolver.instance();

			attrsMap.addVariable("transMeta", transMeta);
			attrsMap.addVariable("stepMeta", stepMeta);
			attrsMap.addVariable("stepMetaInterface", stepMeta
					.getStepMetaInterface());

			attrsMap.addVariable("transId", transId);
			attrsMap.addVariable("stepMetaName", stepName);

			String formId = "form:dialog-trans-step";
			attrsMap.addVariable("formId", formId);

			Object[] domString = PageTemplateInterpolator.interpolate(
					TRANS_STEP_TEMPLATE_PREFIX
							+ stepMeta.getStepMetaInterface()
									.getDialogFileName(), attrsMap);

			// 返回页面控制
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			am.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			log.error("打开Step的设置页面出现错误。");
			am.addErrorMessage("打开Step的设置页面出现错误。");
		}
		return am.toJSONString();

	}

}
