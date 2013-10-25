package com.flywet.cust.p001.portal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.FileType;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.log4j.Logger;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.TransMeta;

import com.flywet.cust.p001.db.CustomDatabaseRepositoryBase;
import com.flywet.cust.p001.vo.ExtendPredictEvaVo;
import com.flywet.cust.p001.vo.MonthPredictEvaVo;
import com.flywet.cust.p001.vo.MonthPredictScoreVo;
import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.flywet.platform.bi.component.components.grid.GridDataObject;
import com.flywet.platform.bi.component.components.selectMenu.OptionsData;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.component.web.ActionMessage;
import com.flywet.platform.bi.component.web.AjaxResult;
import com.flywet.platform.bi.component.web.AjaxResultEntity;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.model.ParameterContext;
import com.flywet.platform.bi.core.model.TransPoolWapper;
import com.flywet.platform.bi.core.pools.TransPool;
import com.flywet.platform.bi.core.utils.DateUtils;
import com.flywet.platform.bi.core.utils.FileUtils;
import com.flywet.platform.bi.core.utils.PropertyUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.enums.BIFileSystemCategory;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;

public class ForecastAdaptorImpl extends BIAbstractDbAdaptor implements
		ForecastAdaptor {
	private final Logger log = Logger.getLogger(ForecastAdaptorImpl.class);

	private static Class<?> PKG = ForecastAdaptorImpl.class;

	public static final String PORTAL_ONLY_PARAM = "param";

	private static final String[] YUN_DESC = new String[] { "上旬", "中旬", "下旬" };

	private static final String[] CITY_CODES = new String[] { "zjk", "cd",
			"qhd", "ts", "lf", "cz", "hs", "bd", "sjz", "xt", "hd" };

	private static final String[] CITY_NAMES = new String[] { "张家口", "承德",
			"秦皇岛", "唐山", "廊坊", "沧州", "衡水", "保定", "石家庄", "邢台", "邯郸" };

	private static final String TEMPLATE_MONTH_PREDICT = "monthPredict.h";
	private static final String TEMPLATE_EXTEND_PREDICT = "extendPredict.h";
	private static final String TEMPLATE_MONTH_PREDICT_SCORE = "monthPredictScore.h";
	private static final String TEMPLATE_EXTEND_PREDICT_SCORE = "extendPredictScore.h";
	private static final String TEMPLATE_PREDICT_SETTING = "predictSetting.h";
	private static final String TEMPLATE_EXTEND_SETTING = "extendSetting.h";

	private static final String TEMPLATE_BUZ_NORMS = "buzNorms.h";
	// private static final String TEMPLATE_BUZ_TIMED = "buzTimed.h";
	private static final String TEMPLATE_DATA_UPDATE = "dataUpdate.h";
	private static final String TEMPLATE_DATA_UPLOAD = "dataUpload.h";
	private static final String TEMPLATE_EXTEND_PREDICT_DATA = "extendPredictData.h";
	private static final String TEMPLATE_EXTEND_PREDICT_PRECIPITATION = "extendPredictPrecipitation.h";
	private static final String TEMPLATE_LINKS = "links.h";

	private static final String TEMPLATE_MONTH_PREDICT_DATA = "monthPredictData.h";
	private static final String TEMPLATE_MONTH_PREDICT_EVALUATE = "monthPredictEvaluate.h";
	private static final String TEMPLATE_SCORE_SETTING = "scoreSetting.h";
	private static final String TEMPLATE_SST_MONTH_PREDICT = "sstMonthPredict.h";
	private static final String TEMPLATE_SST_QUARTER_PREDICT = "sstQuarterPredict.h";

	private static final String TEMPLATE_MONTH_PREDICT_EVALUATE_SETTING = "monthPredictEvaluateSetting.h";
	private static final String TEMPLATE_MONTH_PREDICT_PRECIPTATION_SETTING = "extendPredictPrecipitationSetting.h";

	// 月预测数据存储位置
	private static final String PROP_MONTH_PREDICT_FILE_ROOT_PATH = "custom.portal.monthPredict.file.rootPath";
	private static final String PROP_MONTH_PREDICT_FILE_CATEGORY = "custom.portal.monthPredict.file.category";

	// 11城市月数据
	private static final String PROP_ELEVEN_MONTH_CITY_FILE_ROOT_PATH = "custom.portal.11MonthCity.file.rootPath";
	private static final String PROP_ELEVEN_MONTH_CITY_FILE_CATEGORY = "custom.portal.11MonthCity.file.category";
	private static final String PROP_ELEVEN_MONTH_CITY_FILE_FILENAME = "custom.portal.11MonthCity.file.fileName";

	// 11城市季数据
	private static final String PROP_ELEVEN_QUARTER_CITY_FILE_ROOT_PATH = "custom.portal.11QuarterCity.file.rootPath";
	private static final String PROP_ELEVEN_QUARTER_CITY_FILE_CATEGORY = "custom.portal.11QuarterCity.file.category";
	private static final String PROP_ELEVEN_QUARTER_CITY_FILE_FILENAME = "custom.portal.11QuarterCity.file.fileName";

	// 142县站数据
	private static final String PROP_142_STA_FILE_ROOT_PATH = "custom.portal.142sta.file.rootPath";
	private static final String PROP_142_STA_FILE_CATEGORY = "custom.portal.142sta.file.category";
	private static final String PROP_142_STA_FILE_LAST_FILENAME = "custom.portal.142sta.last.file.fileName";
	private static final String PROP_142_STA_FILE_HISTORY_FILENAME = "custom.portal.142sta.history.file.fileName";

	// 海温数据
	private static final String PROP_SST_FILE_ROOT_PATH = "custom.portal.sst.file.rootPath";
	private static final String PROP_SST_FILE_CATEGORY = "custom.portal.sst.file.category";
	private static final String PROP_SST_FILE_FILENAME = "custom.portal.sst.file.fileName";

	// 数据上传
	private static final String PROP_UPLOAD_FILE_ROOT_PATH = "custom.portal.upload.file.rootPath";
	private static final String PROP_UPLOAD_FILE_CATEGORY = "custom.portal.upload.file.category";

	// 业务规范
	private static final String PROP_BUZ_NORM_FILE_ROOT_PATH = "custom.portal.buzNorm.file.rootPath";
	private static final String PROP_BUZ_NORM_FILE_CATEGORY = "custom.portal.buzNorm.file.category";

	private static final String TRANS_RUN_PROC = "run_proc.xml";

	// 海温月预测-海温预测分析计算
	private static final String PROP_MONTH_FORECAST_RUN = "custom.portal.month.forecast.run";

	// 海温季度预测-海温预测分析计算
	private static final String PROP_SEASON_FORECAST_RUN = "custom.portal.season.forecast.run";

	// 制作评分数据-月预测-统计将尺度技术
	private static final String PROP_FORECAST_EVALUATION_RUN = "custom.portal.forecast.evaluation.run";

	// 制作评分数据-延伸期预测-制作142站预测上传文件
	private static final String PROP_PROCESS_FORECAST_RUN = "custom.portal.process.forecast.run";

	@Override
	public String monthPredictUpdate(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			String currentMonth = (String) context.get(PORTAL_ONLY_PARAM);

			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			attrsMap.addVariable("menuId", context.get("id"));
			attrsMap.addVariable("currentMonth", currentMonth);
			attrsMap.addVariable("currentMonthFiles", getBrowse(
					PROP_MONTH_PREDICT_FILE_ROOT_PATH,
					PROP_MONTH_PREDICT_FILE_CATEGORY, currentMonth));

			// 设置响应
			AjaxResult ar = AjaxResult.instance();

			String[] targetIds = targetId.split(",");
			for (String id : targetIds) {
				Object[] domString = PageTemplateInterpolator.interpolate(PKG,
						TEMPLATE_MONTH_PREDICT, attrsMap, id);
				AjaxResultEntity acc = AjaxResultEntity.instance()
						.setOperation(Utils.RESULT_OPERATION_UPDATE)
						.setTargetId(id).setDomAndScript(domString);
				ar.addEntity(acc);
			}

			return ar.toJSONString();
		} catch (Exception e) {
			log.error("打开预测产品-月预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开预测产品-月预测界面出现问题。")
				.toJSONString();
	}

	@Override
	public String monthPredict(String targetId, HashMap<String, Object> context)
			throws BIJSONException {
		try {
			// 获得目录列表
			FileObject root = getFileObject(PROP_MONTH_PREDICT_FILE_CATEGORY,
					PropertyUtils
							.getProperty(PROP_MONTH_PREDICT_FILE_ROOT_PATH));
			FileObject[] children = root.getChildren();

			if (children == null || children.length < 1) {
				return ActionMessage.instance().failure("预测产品-月预测无记录。")
						.toJSONString();
			}

			// 目录倒序排序
			Arrays.sort(children, new FileObjectDescComparator());

			List<String[]> monthes = new ArrayList<String[]>();
			for (FileObject fo : children) {
				monthes.add(new String[] { fo.getName().getBaseName(),
						getMonthName(fo.getName().getBaseName()) });
			}

			String currentMonth = children[0].getName().getBaseName();

			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			attrsMap.addVariable("menuId", context.get("id"));
			attrsMap.addVariable("monthes", monthes);
			attrsMap.addVariable("currentMonth", currentMonth);
			attrsMap.addVariable("currentMonthFiles", getBrowse(
					PROP_MONTH_PREDICT_FILE_ROOT_PATH,
					PROP_MONTH_PREDICT_FILE_CATEGORY, currentMonth));

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_MONTH_PREDICT, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开预测产品-月预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开预测产品-月预测界面出现问题。")
				.toJSONString();
	}

	@Override
	public String monthPredictDataRun(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			InputStream is = getTransMetaString(TRANS_RUN_PROC);
			TransMeta tm = new TransMeta(is, null, true, null, null);
			TransPoolWapper wapper = new TransPoolWapper("sstMonthPredict", tm);
			wapper.putParam("cmd", PropertyUtils
					.getProperty(PROP_FORECAST_EVALUATION_RUN));
			TransPool.instance().offer(wapper);

			// 设置响应
			return ActionMessage.instance().success("已经提交统计尺度分析计算执行。")
					.toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			return ActionMessage.instance().failure(e.getMessage())
					.toJSONString();
		} catch (Exception e) {
			log.error("提交统计尺度分析计算执行出现问题。");
		}

		return ActionMessage.instance().failure("提交统计尺度分析计算执行出现问题。")
				.toJSONString();
	}

	@Override
	public String extendPredictDataRun(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			InputStream is = getTransMetaString(TRANS_RUN_PROC);
			TransMeta tm = new TransMeta(is, null, true, null, null);
			TransPoolWapper wapper = new TransPoolWapper("sstMonthPredict", tm);
			wapper.putParam("cmd", PropertyUtils
					.getProperty(PROP_PROCESS_FORECAST_RUN));
			TransPool.instance().offer(wapper);

			// 设置响应
			return ActionMessage.instance().success("已经提交统计尺度分析计算执行。")
					.toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			return ActionMessage.instance().failure(e.getMessage())
					.toJSONString();
		} catch (Exception e) {
			log.error("提交统计尺度分析计算执行出现问题。");
		}

		return ActionMessage.instance().failure("提交统计尺度分析计算执行出现问题。")
				.toJSONString();
	}

	private String getMonthName(String monthCode) {
		if (monthCode != null && monthCode.length() == 6) {
			return monthCode.substring(0, 4) + "年"
					+ Integer.valueOf(monthCode.substring(4)) + "月";
		}
		return Const.NVL(monthCode, "");
	}

	private void checkOrCreateDir(String rootPathProp, String categoryProp,
			String workDir) throws BIException, FileSystemException {
		FileObject fileObj = getFileObject(categoryProp, workDir, rootPathProp);

		if (!fileObj.exists()) {
			fileObj.createFolder();
		}
	}

	private BrowseMeta getBrowse(String rootPathProp, String categoryProp,
			String workDir) throws BIException, FileSystemException {
		String category = PropertyUtils.getProperty(categoryProp);

		// 拼装文件信息
		FileObject fileObj = getFileObject(categoryProp, workDir, rootPathProp);
		FileObject[] children = fileObj.getChildren();

		// 排序
		Arrays.sort(children, new FileObjectComparator());

		BrowseMeta browse = new BrowseMeta();

		for (FileObject child : children) {
			boolean isFolder = child.getType().equals(FileType.FOLDER);
			BrowseNodeMeta node = new BrowseNodeMeta();
			node.setCategory(category);
			node.setPath(FileUtils.dirAppend(workDir, child.getName()
					.getBaseName()));
			node.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, child.getName()
					.getBaseName());
			node.addAttribute(HTML.ATTR_TYPE, isFolder ? Utils.DOM_NODE
					: Utils.DOM_LEAF);
			node.addAttribute(HTML.ATTR_NAME, child.getName().getBaseName());

			if (!isFolder) {
				String ext = child.getName().getExtension();
				if (!Const.isEmpty(ext)) {
					node.addAttribute(BrowseNodeMeta.ATTR_ICON_STYLE,
							"ui-fs-file-" + ext.toLowerCase() + "-icon");
					node.addExtendAttribute("ext", ext.toLowerCase());
				}

				node.addExtendAttribute("rootPath", rootPathProp);
				node.addExtendAttribute("category", categoryProp);

				node.addEvent("dblclick", "Flywet.PortalAction.downloadFile");
			}
			browse.addContent(node);
		}

		browse.addClass("fly-browsepanel");

		return browse;
	}

	private FileObject getFileObject(String categoryProp, String workDir,
			String rootPathProp) throws BIException {
		try {
			String category = PropertyUtils.getProperty(categoryProp);
			String rootPath = PropertyUtils.getProperty(rootPathProp);

			BIFileSystemCategory cate = BIFileSystemCategory
					.getCategoryByCode(category);
			String vfsPath = cate.getVfsPath(workDir, rootPath);
			return getFileObject(categoryProp, vfsPath);
		} catch (BIException e) {
			throw e;
		} catch (Exception e) {
			log.error("get vfs fileobject exception", e);
			throw new BIException("读取文件系统[" + rootPathProp + "," + workDir
					+ "]失败");
		}

	}

	private FileObject getFileObject(String categoryProp, String vfsPath)
			throws BIException {
		try {
			String category = PropertyUtils.getProperty(categoryProp);

			FileSystemManager fsManager = VFS.getManager();
			FileSystemOptions opts = new FileSystemOptions();

			if (BIFileSystemCategory.FILESYS_TYPE_SFTP.getCategory().equals(
					category)) {
				SftpFileSystemConfigBuilder.getInstance()
						.setStrictHostKeyChecking(opts, "no");
			}

			return fsManager.resolveFile(vfsPath, opts);
		} catch (Exception e) {
			log.error("get vfs fileobject exception", e);
			throw new BIException("读取文件系统" + vfsPath + "失败");
		}
	}

	@Override
	public String extendPredictUpdate(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			String currentXun = (String) context.get(PORTAL_ONLY_PARAM);
			String[] xun = currentXun.split(":");

			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_DESCRIPTION)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_YEAR)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_MONTH)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_XUN)
					+ " = ?";
			Object[] params = new Object[3];
			params[0] = Long.valueOf(xun[0]);
			params[1] = Long.valueOf(xun[1]);
			params[2] = Long.valueOf(xun[2]);

			Object[] row = getOneRow(sql, params);

			String extendDesc = Const.NVL((String) row[0], "");
			extendDesc = Const.replace(extendDesc, Const.CR, "<br/>");

			attrsMap.addVariable("xun", currentXun);
			attrsMap.addVariable("xun_desc", getExtendPredictValue(params));
			attrsMap.addVariable("extend_desc", extendDesc);

			// 设置响应
			AjaxResult ar = AjaxResult.instance();

			String[] targetIds = targetId.split(",");
			for (String id : targetIds) {
				Object[] domString = PageTemplateInterpolator.interpolate(PKG,
						TEMPLATE_EXTEND_PREDICT, attrsMap, id);
				AjaxResultEntity acc = AjaxResultEntity.instance()
						.setOperation(Utils.RESULT_OPERATION_UPDATE)
						.setTargetId(id).setDomAndScript(domString);
				ar.addEntity(acc);
			}

			return ar.toJSONString();
		} catch (Exception e) {
			log.error("打开预测产品-延伸期预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开预测产品-延伸期预测界面出现问题。")
				.toJSONString();
	}

	@Override
	public String extendPredict(String targetId, HashMap<String, Object> context)
			throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_YEAR)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_MONTH)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_XUN)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_DESCRIPTION)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT)
					+ " ORDER BY "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_YEAR)
					+ " DESC , "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_MONTH)
					+ " DESC , "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_XUN);

			List<Object[]> rows = getRows(sql);

			List<String[]> menus = new ArrayList<String[]>();
			for (Object[] r : rows) {
				menus.add(new String[] { getExtendPredictKey(r),
						getExtendPredictValue(r) });
			}

			Object[] current = rows.get(0);

			String extendDesc = Const.NVL((String) current[3], "");
			extendDesc = Const.replace(extendDesc, Const.CR, "<br/>");

			attrsMap.addVariable("menuId", context.get("id"));
			attrsMap.addVariable("xun", getExtendPredictKey(current));
			attrsMap.addVariable("xun_desc", getExtendPredictValue(current));
			attrsMap.addVariable("menus", menus);
			attrsMap.addVariable("extend_desc", extendDesc);

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_EXTEND_PREDICT, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开预测产品-延伸期预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开预测产品-延伸期预测界面出现问题。")
				.toJSONString();
	}

	private String getExtendPredictKey(Object[] r) {
		return (Long) r[0] + ":" + (Long) r[1] + ":" + (Long) r[2];
	}

	private String getExtendPredictValue(Object[] r) {
		return (Long) r[0] + "年" + (Long) r[1] + "月"
				+ YUN_DESC[((Long) r[2]).intValue()];
	}

	@Override
	public String predictSetting(String targetId,
			HashMap<String, Object> context) throws BIJSONException {

		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			String currentMonth = DateUtils.formatDate(new Date(), "yyyyMM");

			checkOrCreateDir(PROP_MONTH_PREDICT_FILE_ROOT_PATH,
					PROP_MONTH_PREDICT_FILE_CATEGORY, currentMonth);

			String currentMonthText = DateUtils.formatDate(new Date(),
					"yyyy年MM月");
			attrsMap.addVariable("currentMonth", currentMonth);
			attrsMap.addVariable("currentMonthText", currentMonthText);
			attrsMap.addVariable("currentMonthFiles", getBrowse(
					PROP_MONTH_PREDICT_FILE_ROOT_PATH,
					PROP_MONTH_PREDICT_FILE_CATEGORY, currentMonth));

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_PREDICT_SETTING, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开当月预测填报界面出现问题。");
		}

		return ActionMessage.instance().failure("打开当月预测填报界面出现问题。")
				.toJSONString();
	}

	@Override
	public String predictSettingUpdate(String targetId,
			HashMap<String, Object> context) throws BIJSONException {

		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			Date now = new Date();

			String currentMonth = DateUtils.formatDate(now, "yyyyMM");

			checkOrCreateDir(PROP_MONTH_PREDICT_FILE_ROOT_PATH,
					PROP_MONTH_PREDICT_FILE_CATEGORY, currentMonth);

			String currentMonthText = DateUtils.formatDate(now, "yyyy年MM月");
			attrsMap.addVariable("currentMonth", currentMonth);
			attrsMap.addVariable("currentMonthText", currentMonthText);
			attrsMap.addVariable("currentMonthFiles", getBrowse(
					PROP_MONTH_PREDICT_FILE_ROOT_PATH,
					PROP_MONTH_PREDICT_FILE_CATEGORY, currentMonth));

			AjaxResult ar = AjaxResult.instance();

			String[] targetIds = targetId.split(",");
			for (String id : targetIds) {
				Object[] domString = PageTemplateInterpolator.interpolate(PKG,
						TEMPLATE_PREDICT_SETTING, attrsMap, id);
				AjaxResultEntity acc = AjaxResultEntity.instance()
						.setOperation(Utils.RESULT_OPERATION_UPDATE)
						.setTargetId(id).setDomAndScript(domString);
				ar.addEntity(acc);
			}

			return ar.toJSONString();

		} catch (Exception e) {
			log.error("打开当月预测填报界面出现问题。");
		}

		return ActionMessage.instance().failure("打开当月预测填报界面出现问题。")
				.toJSONString();
	}

	@SuppressWarnings("deprecation")
	@Override
	public String extendSetting(String targetId, HashMap<String, Object> context)
			throws BIJSONException {

		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			Date now = new Date();
			String currentText = DateUtils.formatDate(now, "yyyy年MM月"), content = "";
			long year = now.getYear() + 1900, month = now.getMonth() + 1, date = now
					.getDate(), xun;

			if (date <= 10) {
				xun = 0;
			} else if (date <= 20) {
				xun = 1;
			} else {
				xun = 2;
			}

			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_DESCRIPTION)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_YEAR)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_MONTH)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_XUN)
					+ " = ?";

			Object[] row = getOneRow(sql, new Object[] { year, month, xun });
			if (row != null) {
				content = (String) row[0];
			}

			currentText = currentText + YUN_DESC[Long.valueOf(xun).intValue()];

			attrsMap.addVariable("currentText", currentText);
			attrsMap.addVariable("content", content);
			attrsMap.addVariable("year", year);
			attrsMap.addVariable("month", month);
			attrsMap.addVariable("xun", xun);

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_EXTEND_SETTING, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开延伸期预测当月填报界面出现问题。");
		}

		return ActionMessage.instance().failure("打开延伸期预测当月填报界面出现问题。")
				.toJSONString();
	}

	@Override
	public String extendSettingUpdate(String targetId, ParameterContext context)
			throws BIJSONException {
		try {
			String content = context.getStringParameter("content", "");
			long year = context.getLongParameter("year");
			long month = context.getLongParameter("month");
			long xun = context.getLongParameter("xun");

			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_ID_EXTEND_PREDICT)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_YEAR)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_MONTH)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_XUN)
					+ " = ?";

			Object[] row = getOneRow(sql, new Object[] { year, month, xun });
			long rid;
			if (row != null) {
				rid = (Long) row[0];
				RowMetaAndData rmd = new RowMetaAndData();
				rmd
						.addValue(
								CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_ID_EXTEND_PREDICT,
								ValueMetaInterface.TYPE_INTEGER, rid);
				rmd.addValue(
						CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_YEAR,
						ValueMetaInterface.TYPE_INTEGER, year);
				rmd
						.addValue(
								CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_MONTH,
								ValueMetaInterface.TYPE_INTEGER, month);
				rmd.addValue(
						CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_XUN,
						ValueMetaInterface.TYPE_INTEGER, xun);
				rmd
						.addValue(
								CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_DESCRIPTION,
								ValueMetaInterface.TYPE_STRING, content);
				updateTableRow(
						CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT,
						CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_ID_EXTEND_PREDICT,
						rmd);
			} else {
				rid = getNextBatchId(
						CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT,
						CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_ID_EXTEND_PREDICT);
				RowMetaAndData rmd = new RowMetaAndData();
				rmd
						.addValue(
								CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_ID_EXTEND_PREDICT,
								ValueMetaInterface.TYPE_INTEGER, rid);
				rmd.addValue(
						CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_YEAR,
						ValueMetaInterface.TYPE_INTEGER, year);
				rmd
						.addValue(
								CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_MONTH,
								ValueMetaInterface.TYPE_INTEGER, month);
				rmd.addValue(
						CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_XUN,
						ValueMetaInterface.TYPE_INTEGER, xun);
				rmd
						.addValue(
								CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_DESCRIPTION,
								ValueMetaInterface.TYPE_STRING, content);
				insertTableRow(
						CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT,
						rmd);
			}
			return ActionMessage.instance().success("更新当前延伸期预测成功。")
					.toJSONString();
		} catch (Exception e) {
			log.error("打开延伸期预测当月更新行为出现问题。");
		}

		return ActionMessage.instance().failure("打开延伸期预测当月更新行为出现问题。")
				.toJSONString();
	}

	@Override
	public String extendPredictScore(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_EXTEND_PREDICT_SCORE, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开预测评分-延伸期预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开预测评分-延伸期预测界面出现问题。")
				.toJSONString();
	}

	class FileObjectComparator implements Comparator<FileObject> {

		public int compare(FileObject fo1, FileObject fo2) {
			try {
				if (fo1 != null && fo2 != null) {
					boolean isFolder1 = fo1.getType().equals(FileType.FOLDER), isFolder2 = fo2
							.getType().equals(FileType.FOLDER);

					// fo1是文件夹，fo2不是
					if (isFolder1 && !isFolder2) {
						return -1;
					}
					// fo2是文件夹，fo1不是
					else if (isFolder2 && !isFolder1) {
						return 1;
					}
					// 比较名称
					else {
						// 比较扩展名
						String ext1 = fo1.getName().getExtension(), ext2 = fo2
								.getName().getExtension();
						if (!ext1.equalsIgnoreCase(ext2)) {
							return ext1.compareToIgnoreCase(ext2);
						}

						String name1 = fo1.getName().getBaseName(), name2 = fo2
								.getName().getBaseName();
						return name1.compareToIgnoreCase(name2);
					}
				}
			} catch (FileSystemException e) {
				log.error("文件对象排序出现错误。");
			}
			return 0;
		}
	}

	class FileObjectDescComparator implements Comparator<FileObject> {

		public int compare(FileObject fo1, FileObject fo2) {
			try {
				if (fo1 != null && fo2 != null) {
					boolean isFolder1 = fo1.getType().equals(FileType.FOLDER), isFolder2 = fo2
							.getType().equals(FileType.FOLDER);
					// fo1是文件夹，fo2不是
					if (isFolder1 && !isFolder2) {
						return 1;
					}
					// fo2是文件夹，fo1不是
					else if (isFolder2 && !isFolder1) {
						return -1;
					}
					// 比较名称
					else {
						String name1 = fo1.getName().getBaseName(), name2 = fo2
								.getName().getBaseName();
						return name2.compareToIgnoreCase(name1);
					}
				}
			} catch (FileSystemException e) {
				log.error("文件对象倒序排序出现错误。");
			}
			return 0;
		}
	}

	@Override
	public String buzNorms(String targetId, HashMap<String, Object> context)
			throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			attrsMap.addVariable("normFiles", getBrowse(
					PROP_BUZ_NORM_FILE_ROOT_PATH, PROP_BUZ_NORM_FILE_CATEGORY,
					""));

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_BUZ_NORMS, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开业务规范界面出现问题。");
		}

		return ActionMessage.instance().failure("打开业务规范界面出现问题。").toJSONString();
	}

	@Override
	public String buzNormsUpdate(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			attrsMap.addVariable("normFiles", getBrowse(
					PROP_BUZ_NORM_FILE_ROOT_PATH, PROP_BUZ_NORM_FILE_CATEGORY,
					""));

			AjaxResult ar = AjaxResult.instance();

			String[] targetIds = targetId.split(",");
			for (String id : targetIds) {
				Object[] domString = PageTemplateInterpolator.interpolate(PKG,
						TEMPLATE_BUZ_NORMS, attrsMap, id);
				AjaxResultEntity acc = AjaxResultEntity.instance()
						.setOperation(Utils.RESULT_OPERATION_UPDATE)
						.setTargetId(id).setDomAndScript(domString);
				ar.addEntity(acc);
			}

			return ar.toJSONString();

		} catch (Exception e) {
			log.error("打开业务规范界面出现问题。");
		}

		return ActionMessage.instance().failure("打开业务规范界面出现问题。").toJSONString();
	}

	@Override
	public String buzTimed(String targetId, HashMap<String, Object> context)
			throws BIJSONException {
		// DO NOTHING
		return null;
	}

	@Override
	public String dataUpdate(String targetId, HashMap<String, Object> context)
			throws BIJSONException {
		try {

			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			attrsMap.addVariable("menuId", context.get("id"));

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_DATA_UPDATE, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开设置-数据更新界面出现问题。");
		}

		return ActionMessage.instance().failure("打开设置-数据更新界面出现问题。")
				.toJSONString();
	}

	@Override
	public String dataUpdateFiles(ArrayList<FileItem> items,
			HashMap<String, String> dataObj) throws BIJSONException {
		ActionMessage resultMsg = new ActionMessage();
		try {
			// 遍历文件并逐次上传
			for (FileItem item : items) {
				if (item.isFormField() || Const.isEmpty(item.getName())) {
					continue;
				}

				// 11城市月数据
				if ("fs1".equals(item.getFieldName())) {
					dataUpdateFile(item, PROP_ELEVEN_MONTH_CITY_FILE_ROOT_PATH,
							PROP_ELEVEN_MONTH_CITY_FILE_CATEGORY,
							PROP_ELEVEN_MONTH_CITY_FILE_FILENAME);
				}
				// 11城市季数据
				else if ("fs2".equals(item.getFieldName())) {
					dataUpdateFile(item,
							PROP_ELEVEN_QUARTER_CITY_FILE_ROOT_PATH,
							PROP_ELEVEN_QUARTER_CITY_FILE_CATEGORY,
							PROP_ELEVEN_QUARTER_CITY_FILE_FILENAME);
				}
				// 142县站数据(上月)
				else if ("fs3".equals(item.getFieldName())) {
					dataUpdateFile(item, PROP_142_STA_FILE_ROOT_PATH,
							PROP_142_STA_FILE_CATEGORY,
							PROP_142_STA_FILE_LAST_FILENAME);
				}
				// 142县站数据(历史)
				else if ("fs4".equals(item.getFieldName())) {
					dataUpdateFile(item, PROP_142_STA_FILE_ROOT_PATH,
							PROP_142_STA_FILE_CATEGORY,
							PROP_142_STA_FILE_HISTORY_FILENAME);
				}
				// 海温数据
				else if ("fs5".equals(item.getFieldName())) {
					dataUpdateFile(item, PROP_SST_FILE_ROOT_PATH,
							PROP_SST_FILE_CATEGORY, PROP_SST_FILE_FILENAME);
				}
			}
			resultMsg.addMessage("上传操作成功");
			return resultMsg.toJSONString();
		} catch (Exception ioe) {
			log.error("read or write file exception:", ioe);
			resultMsg.addErrorMessage("上传文件失败");
			return resultMsg.toJSONString();
		}
	}

	private void dataUpdateFile(FileItem item, String rootDir, String category,
			String fileName) throws BIException, IOException {
		File fullFile = new File(PropertyUtils.getProperty(fileName));
		String destFileStr = fullFile.getName();
		FileObject destFileObj = composeVfsObject(category, destFileStr,
				rootDir);

		FileUtils.write(item, destFileObj.getContent().getOutputStream());
	}

	private FileObject composeVfsObject(String categoryProp, String workDir,
			String rootDirProp) throws BIException {
		try {
			String rootDir = PropertyUtils.getProperty(rootDirProp);
			String category = PropertyUtils.getProperty(categoryProp);
			FileSystemManager fsManager = VFS.getManager();
			FileSystemOptions opts = new FileSystemOptions();

			BIFileSystemCategory cate = BIFileSystemCategory
					.getCategoryByCode(category);

			String vfsPath = cate.getVfsPath(workDir, rootDir);

			if (BIFileSystemCategory.FILESYS_TYPE_SFTP.getCategory().equals(
					category)) {
				SftpFileSystemConfigBuilder.getInstance()
						.setStrictHostKeyChecking(opts, "no");
			}

			FileObject fileObj = fsManager.resolveFile(vfsPath, opts);
			return fileObj;
		} catch (FileSystemException e) {
			log.error("get vfs fileobject exception", e);
			throw new BIException("读取文件系统" + workDir + "失败");
		}
	}

	@Override
	public String dataUpload(String targetId, HashMap<String, Object> context)
			throws BIJSONException {
		try {

			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			attrsMap.addVariable("menuId", context.get("id"));

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_DATA_UPLOAD, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开设置-上传数据界面出现问题。");
		}

		return ActionMessage.instance().failure("打开设置-上传数据界面出现问题。")
				.toJSONString();
	}

	@Override
	public String extendPredictData(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {

			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			attrsMap.addVariable("menuId", context.get("id"));

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_EXTEND_PREDICT_DATA, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开制作评分数据-延伸期预测出现问题。");
		}

		return ActionMessage.instance().failure("打开制作评分数据-延伸期预测出现问题。")
				.toJSONString();
	}

	@Override
	public String extendPredictPrecipitation(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_YEAR)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_MONTH)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S1)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S2)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S3)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S4)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S5)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S6)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S7)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S8)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S9)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT_EVA)
					+ " ORDER BY "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_YEAR)
					+ " DESC , "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_MONTH)
					+ " DESC";

			List<Object[]> rows = getRows(sql);

			GridDataObject grid = GridDataObject.instance();
			if (rows != null) {
				for (Object[] row : rows) {
					String yearMonth = row[0] + "年" + row[1] + "月";
					ExtendPredictEvaVo vo = ExtendPredictEvaVo.instance()
							.setYearMonth(yearMonth).setS1((Long) row[2])
							.setS2((Long) row[3]).setS3((Long) row[4]).setS4(
									(Long) row[5]).setS5((Long) row[6]).setS6(
									(Long) row[7]).setS7((Long) row[8]).setS8(
									(Long) row[9]).setS9((Long) row[10]);
					grid.putObject(vo);
				}
			}

			attrsMap.addVariable("extendPredictEva", grid);

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_EXTEND_PREDICT_PRECIPITATION, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开预测方法评估-延伸期降水过程预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开预测方法评估-延伸期降水过程预测界面出现问题。")
				.toJSONString();
	}

	@SuppressWarnings("deprecation")
	@Override
	public String monthPredictEvaluateSetting(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			Date now = new Date();
			long year = now.getYear() + 1900, month = now.getMonth();
			if (month == 0) {
				year = year - 1;
				month = 12;
			}

			String currentText = year + "年" + month + "月";

			OptionsData tempOpts = OptionsData.instance();
			tempOpts.addOption("-1", "偏低");
			tempOpts.addOption("0", "正常");
			tempOpts.addOption("1", "偏高");

			OptionsData preOpts = OptionsData.instance();
			preOpts.addOption("-1", "偏少");
			preOpts.addOption("0", "正常");
			preOpts.addOption("1", "偏多");

			attrsMap.addVariable("tempOpts", tempOpts.getOptions());
			attrsMap.addVariable("preOpts", preOpts.getOptions());

			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_CITY)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S1)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S2)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S3)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S4)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S5)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S6)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S7)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S8)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_EVA_D)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_YEAR)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_MONTH)
					+ " = ?";

			List<Object[]> rows = getRows(sql, new Object[] { year, month });

			Map<String, Object[]> detailMap = new HashMap<String, Object[]>();

			if (rows != null) {
				for (Object[] row : rows) {
					detailMap.put((String) row[0], row);
				}
			}

			for (String code : CITY_CODES) {
				if (!detailMap.containsKey(code)) {
					detailMap.put(code, new String[] { code, "0", "0", "0",
							"0", "0", "0", "0", "0" });
				}
			}

			attrsMap.addVariable("detailMap", detailMap);

			attrsMap.addVariable("currentText", currentText);
			attrsMap.addVariable("year", year);
			attrsMap.addVariable("month", month);

			attrsMap.addVariable("cityCodes", CITY_CODES);
			attrsMap.addVariable("cityNames", CITY_NAMES);

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_MONTH_PREDICT_EVALUATE_SETTING, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开方法评估-上月预测评估填报界面出现问题。");
		}

		return ActionMessage.instance().failure("打开方法评估-上月预测评估填报界面出现问题。")
				.toJSONString();
	}

	private long getEvaluateScore(long t1, long t2) {
		if (t1 == t2) {
			return 100;
		} else if (Math.abs(t1 - t2) <= 1) {
			return 60;
		} else {
			return 0;
		}
	}

	private long getAvgScore(long t) {
		return Math.round(t / CITY_CODES.length);
	}

	@Override
	public String monthPredictEvaluateSettingSubmit(String targetId,
			ParameterContext context) throws BIJSONException {
		try {
			long year = context.getLongParameter("year");
			long month = context.getLongParameter("month");

			// 删除旧的记录
			String delSql = "DELETE FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_EVA_D)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_YEAR)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_MONTH)
					+ " = ?";
			execSql(delSql, new Object[] { year, month });

			List<String> cityL = context.getParameterValues("city");
			List<String> s1 = context.getParameterValues("s1");
			List<String> s2 = context.getParameterValues("s2");
			List<String> s3 = context.getParameterValues("s3");
			List<String> s4 = context.getParameterValues("s4");
			List<String> s5 = context.getParameterValues("s5");
			List<String> s6 = context.getParameterValues("s6");
			List<String> s7 = context.getParameterValues("s7");
			List<String> s8 = context.getParameterValues("s8");

			long[] avgS = new long[6];

			// 添加新的明细
			if (cityL != null) {
				for (int i = 0; i < cityL.size(); i++) {
					long s1L = Long.valueOf(s1.get(i)), s2L = Long.valueOf(s2
							.get(i)), s3L = Long.valueOf(s3.get(i)), s4L = Long
							.valueOf(s4.get(i)), s5L = Long.valueOf(s5.get(i)), s6L = Long
							.valueOf(s6.get(i)), s7L = Long.valueOf(s7.get(i)), s8L = Long
							.valueOf(s8.get(i));

					avgS[0] += getEvaluateScore(s1L, s4L);
					avgS[1] += getEvaluateScore(s2L, s4L);
					avgS[2] += getEvaluateScore(s3L, s4L);
					avgS[3] += getEvaluateScore(s5L, s8L);
					avgS[4] += getEvaluateScore(s6L, s8L);
					avgS[5] += getEvaluateScore(s7L, s8L);

					RowMetaAndData rmd = new RowMetaAndData();
					rmd
							.addValue(
									CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_YEAR,
									ValueMetaInterface.TYPE_INTEGER, year);
					rmd
							.addValue(
									CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_MONTH,
									ValueMetaInterface.TYPE_INTEGER, month);
					rmd
							.addValue(
									CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_CITY,
									ValueMetaInterface.TYPE_STRING, cityL
											.get(i));
					rmd
							.addValue(
									CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S1,
									ValueMetaInterface.TYPE_INTEGER, s1L);
					rmd
							.addValue(
									CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S2,
									ValueMetaInterface.TYPE_INTEGER, s2L);
					rmd
							.addValue(
									CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S3,
									ValueMetaInterface.TYPE_INTEGER, s3L);
					rmd
							.addValue(
									CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S4,
									ValueMetaInterface.TYPE_INTEGER, s4L);
					rmd
							.addValue(
									CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S5,
									ValueMetaInterface.TYPE_INTEGER, s5L);
					rmd
							.addValue(
									CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S6,
									ValueMetaInterface.TYPE_INTEGER, s6L);
					rmd
							.addValue(
									CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S7,
									ValueMetaInterface.TYPE_INTEGER, s7L);
					rmd
							.addValue(
									CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_D_S8,
									ValueMetaInterface.TYPE_INTEGER, s8L);

					insertTableRow(
							CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_EVA_D,
							rmd);
				}
			}

			// 添加统计
			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_ID_MONTH_PREDICT_EVA)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_EVA)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_YEAR)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_MONTH)
					+ " = ?";

			Object[] row = getOneRow(sql, new Object[] { year, month });
			long rid;
			if (row != null) {
				rid = (Long) row[0];
			} else {
				rid = getNextBatchId(
						CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_EVA,
						CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_ID_MONTH_PREDICT_EVA);
			}

			RowMetaAndData rmd = new RowMetaAndData();
			rmd
					.addValue(
							CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_ID_MONTH_PREDICT_EVA,
							ValueMetaInterface.TYPE_INTEGER, rid);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_YEAR,
					ValueMetaInterface.TYPE_INTEGER, year);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_MONTH,
					ValueMetaInterface.TYPE_INTEGER, month);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S1,
					ValueMetaInterface.TYPE_STRING, getAvgScore(avgS[0]));
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S2,
					ValueMetaInterface.TYPE_STRING, getAvgScore(avgS[1]));
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S3,
					ValueMetaInterface.TYPE_STRING, getAvgScore(avgS[2]));
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S4,
					ValueMetaInterface.TYPE_STRING, getAvgScore(avgS[3]));
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S5,
					ValueMetaInterface.TYPE_STRING, getAvgScore(avgS[4]));
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S6,
					ValueMetaInterface.TYPE_STRING, getAvgScore(avgS[5]));

			if (row != null) {
				updateTableRow(
						CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_EVA,
						CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_ID_MONTH_PREDICT_EVA,
						rmd);
			} else {
				insertTableRow(
						CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_EVA,
						rmd);
			}

			return ActionMessage.instance().success("更新上月预测评估填报成功。")
					.toJSONString();
		} catch (Exception e) {
			log.error("更新上月预测评估填报行为出现问题。");
		}

		return ActionMessage.instance().failure("更新上月预测评估填报行为出现问题。")
				.toJSONString();
	}

	@SuppressWarnings("deprecation")
	@Override
	public String extendPredictPrecipitationSetting(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			Date now = new Date();
			long year = now.getYear() + 1900, month = now.getMonth();
			if (month == 0) {
				year = year - 1;
				month = 12;
			}

			String currentText = year + "年" + month + "月";

			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S1)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S2)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S3)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S4)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S5)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S6)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S7)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S8)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S9)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_DESCRIPTION)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT_EVA)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_YEAR)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_MONTH)
					+ " = ?";

			Object[] row = getOneRow(sql, new Object[] { year, month });
			if (row != null) {
				for (int i = 0; i < 9; i++) {
					attrsMap.addVariable("s" + (i + 1), row[i]);
				}
				attrsMap.addVariable("desc", row[9]);
			} else {
				for (int i = 0; i < 9; i++) {
					attrsMap.addVariable("s" + (i + 1), "");
				}
				attrsMap.addVariable("desc", "");
			}

			attrsMap.addVariable("currentText", currentText);
			attrsMap.addVariable("year", year);
			attrsMap.addVariable("month", month);

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_MONTH_PREDICT_PRECIPTATION_SETTING, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开方法评估-上月延伸期降水过程预测评估填报界面出现问题。");
		}

		return ActionMessage.instance()
				.failure("打开方法评估-上月延伸期降水过程预测评估填报界面出现问题。").toJSONString();
	}

	@Override
	public String extendPredictPrecipitationSettingSubmit(String targetId,
			ParameterContext context) throws BIJSONException {
		try {
			long year = context.getLongParameter("year");
			long month = context.getLongParameter("month");

			long s1 = context.getLongParameter("s1");
			long s2 = context.getLongParameter("s2");
			long s3 = context.getLongParameter("s3");
			long s4 = context.getLongParameter("s4");
			long s5 = context.getLongParameter("s5");
			long s6 = context.getLongParameter("s6");
			long s7 = context.getLongParameter("s7");
			long s8 = context.getLongParameter("s8");
			long s9 = context.getLongParameter("s9");
			String desc = context.getStringParameter("desc", "");

			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_ID_MONTH_PREDICT_EVA)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT_EVA)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_YEAR)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_MONTH)
					+ " = ?";

			Object[] row = getOneRow(sql, new Object[] { year, month });
			long rid;
			if (row != null) {
				rid = (Long) row[0];
			} else {
				rid = getNextBatchId(
						CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT_EVA,
						CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_ID_MONTH_PREDICT_EVA);
			}

			RowMetaAndData rmd = new RowMetaAndData();
			rmd
					.addValue(
							CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_ID_MONTH_PREDICT_EVA,
							ValueMetaInterface.TYPE_INTEGER, rid);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_YEAR,
					ValueMetaInterface.TYPE_INTEGER, year);
			rmd
					.addValue(
							CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_MONTH,
							ValueMetaInterface.TYPE_INTEGER, month);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S1,
					ValueMetaInterface.TYPE_INTEGER, s1);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S2,
					ValueMetaInterface.TYPE_INTEGER, s2);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S3,
					ValueMetaInterface.TYPE_INTEGER, s3);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S4,
					ValueMetaInterface.TYPE_INTEGER, s4);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S5,
					ValueMetaInterface.TYPE_INTEGER, s5);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S6,
					ValueMetaInterface.TYPE_INTEGER, s6);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S7,
					ValueMetaInterface.TYPE_INTEGER, s7);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S8,
					ValueMetaInterface.TYPE_INTEGER, s8);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_S9,
					ValueMetaInterface.TYPE_INTEGER, s9);
			rmd
					.addValue(
							CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_DESCRIPTION,
							ValueMetaInterface.TYPE_STRING, desc);

			if (row != null) {
				updateTableRow(
						CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT_EVA,
						CustomDatabaseRepositoryBase.FIELD_EXTEND_PREDICT_EVA_ID_MONTH_PREDICT_EVA,
						rmd);
			} else {
				insertTableRow(
						CustomDatabaseRepositoryBase.TABLE_C_EXTEND_PREDICT_EVA,
						rmd);
			}

			return ActionMessage.instance().success("更新上月延伸期降水过程预测评估成功。")
					.toJSONString();
		} catch (Exception e) {
			log.error("上月延伸期降水过程预测评估填报更新行为出现问题。");
		}

		return ActionMessage.instance().failure("上月延伸期降水过程预测评估填报更新行为出现问题。")
				.toJSONString();
	}

	@Override
	public String links(String targetId, HashMap<String, Object> context)
			throws BIJSONException {
		try {

			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_LINKS, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开相关网址链接界面出现问题。");
		}

		return ActionMessage.instance().failure("打开相关网址链接界面出现问题。")
				.toJSONString();
	}

	@Override
	public String monthPredictData(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {

			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			attrsMap.addVariable("menuId", context.get("id"));

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_MONTH_PREDICT_DATA, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开制作评分数据-月预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开制作评分数据-月预测界面出现问题。")
				.toJSONString();
	}

	@Override
	public String monthPredictDataUpload(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		// TODO
		// BIFileSystemDelegate filesysService = ReflectionUtils
		// .getBean("bi.service.filesystemService");
		return ActionMessage.instance().success("月预测评分数据上传成功。").toJSONString();
	}

	@Override
	public String extendPredictDataUpload(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		// TODO
		return ActionMessage.instance().success("延伸期预测评分数据上传成功。")
				.toJSONString();
	}

	@Override
	public String monthPredictEvaluate(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_YEAR)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_MONTH)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S1)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S2)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S3)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S4)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S5)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_S6)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_EVA)
					+ " ORDER BY "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_YEAR)
					+ " DESC , "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_EVA_MONTH)
					+ " DESC";

			List<Object[]> rows = getRows(sql);

			GridDataObject grid = GridDataObject.instance();
			if (rows != null) {
				for (Object[] row : rows) {
					String yearMonth = row[0] + "年" + row[1] + "月";
					MonthPredictEvaVo vo = MonthPredictEvaVo.instance()
							.setYearMonth(yearMonth).setS1((Long) row[2])
							.setS2((Long) row[3]).setS3((Long) row[4]).setS4(
									(Long) row[5]).setS5((Long) row[6]).setS6(
									(Long) row[7]);
					grid.putObject(vo);
				}
			}

			attrsMap.addVariable("monthPredictEva", grid);

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_MONTH_PREDICT_EVALUATE, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开预测方法评估-月预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开预测方法评估-月预测界面出现问题。")
				.toJSONString();
	}

	@Override
	public String monthPredictScore(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_YEAR)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_MONTH)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S1)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S2)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S3)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S4)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S5)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S6)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S7)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_SCORE)
					+ " ORDER BY "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_YEAR)
					+ " DESC , "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_MONTH)
					+ " DESC";

			List<Object[]> rows = getRows(sql);

			GridDataObject grid = GridDataObject.instance();
			if (rows != null) {
				for (Object[] row : rows) {
					String yearMonth = row[0] + "年" + row[1] + "月";
					MonthPredictScoreVo vo = MonthPredictScoreVo.instance()
							.setYearMonth(yearMonth).setS1((String) row[2])
							.setS2((String) row[3]).setS3((String) row[4])
							.setS4((String) row[5]).setS5((String) row[6])
							.setS6((String) row[7]).setS7((String) row[8]);
					grid.putObject(vo);
				}
			}

			attrsMap.addVariable("monthPredictScore", grid);

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_MONTH_PREDICT_SCORE, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开预测评分-月预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开预测评分-月预测界面出现问题。")
				.toJSONString();
	}

	private String[] getScoreArray(String str) {
		String[] s = StringUtils.split(str, "/");
		for (int i = 0; i < s.length; i++) {
			s[i] = StringUtils.trimToEmpty(s[i]);
		}
		return s;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String scoreSetting(String targetId, HashMap<String, Object> context)
			throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			Date now = new Date();
			long year = now.getYear() + 1900, month = now.getMonth();
			if (month == 0) {
				year = year - 1;
				month = 12;
			}

			String currentText = year + "年" + month + "月";

			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S1)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S2)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S3)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S4)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S5)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S6)
					+ ","
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S7)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_SCORE)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_YEAR)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_MONTH)
					+ " = ?";

			Object[] row = getOneRow(sql, new Object[] { year, month });
			if (row != null) {
				for (int i = 0; i < 7; i++) {
					String[] s = getScoreArray((String) row[i]);
					attrsMap.addVariable("s" + (i + 1), s);
				}
			} else {
				for (int i = 0; i < 7; i++) {
					String[] s = new String[2];
					s[0] = "";
					s[1] = "";
					attrsMap.addVariable("s" + (i + 1), s);
				}
			}

			attrsMap.addVariable("currentText", currentText);
			attrsMap.addVariable("year", year);
			attrsMap.addVariable("month", month);

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_SCORE_SETTING, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开延伸期预测当月填报界面出现问题。");
		}

		return ActionMessage.instance().failure("打开延伸期预测当月填报界面出现问题。")
				.toJSONString();
	}

	@Override
	public String scoreSettingUpdate(String targetId, ParameterContext context)
			throws BIJSONException {
		try {
			long year = context.getLongParameter("year");
			long month = context.getLongParameter("month");

			String s1 = context.getStringParameter("s1_0", "") + " / "
					+ context.getStringParameter("s1_1", "");
			String s2 = context.getStringParameter("s2_0", "") + " / "
					+ context.getStringParameter("s2_1", "");
			String s3 = context.getStringParameter("s3_0", "") + " / "
					+ context.getStringParameter("s3_1", "");
			String s4 = context.getStringParameter("s4_0", "") + " / "
					+ context.getStringParameter("s4_1", "");
			String s5 = context.getStringParameter("s5_0", "") + " / "
					+ context.getStringParameter("s5_1", "");
			String s6 = context.getStringParameter("s6_0", "") + " / "
					+ context.getStringParameter("s6_1", "");
			String s7 = context.getStringParameter("s7_0", "") + " / "
					+ context.getStringParameter("s7_1", "");

			String sql = "SELECT "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_ID_MONTH_PREDICT_SCORE)
					+ " FROM "
					+ quoteTable(CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_SCORE)
					+ " WHERE "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_YEAR)
					+ " = ? AND "
					+ quote(CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_MONTH)
					+ " = ?";

			Object[] row = getOneRow(sql, new Object[] { year, month });
			long rid;
			if (row != null) {
				rid = (Long) row[0];
			} else {
				rid = getNextBatchId(
						CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_SCORE,
						CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_ID_MONTH_PREDICT_SCORE);
			}

			RowMetaAndData rmd = new RowMetaAndData();
			rmd
					.addValue(
							CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_ID_MONTH_PREDICT_SCORE,
							ValueMetaInterface.TYPE_INTEGER, rid);
			rmd
					.addValue(
							CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_YEAR,
							ValueMetaInterface.TYPE_INTEGER, year);
			rmd
					.addValue(
							CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_MONTH,
							ValueMetaInterface.TYPE_INTEGER, month);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S1,
					ValueMetaInterface.TYPE_STRING, s1);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S2,
					ValueMetaInterface.TYPE_STRING, s2);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S3,
					ValueMetaInterface.TYPE_STRING, s3);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S4,
					ValueMetaInterface.TYPE_STRING, s4);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S5,
					ValueMetaInterface.TYPE_STRING, s5);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S6,
					ValueMetaInterface.TYPE_STRING, s6);
			rmd.addValue(
					CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_S7,
					ValueMetaInterface.TYPE_STRING, s7);

			if (row != null) {
				updateTableRow(
						CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_SCORE,
						CustomDatabaseRepositoryBase.FIELD_MONTH_PREDICT_SCORE_ID_MONTH_PREDICT_SCORE,
						rmd);
			} else {
				insertTableRow(
						CustomDatabaseRepositoryBase.TABLE_C_MONTH_PREDICT_SCORE,
						rmd);
			}

			return ActionMessage.instance().success("更新当前延伸期预测成功。")
					.toJSONString();
		} catch (Exception e) {
			log.error("打开延伸期预测当月更新行为出现问题。");
		}

		return ActionMessage.instance().failure("打开延伸期预测当月更新行为出现问题。")
				.toJSONString();
	}

	@Override
	public String sstMonthPredict(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {

			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			attrsMap.addVariable("menuId", context.get("id"));

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_SST_MONTH_PREDICT, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开分析工具-海温月预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开分析工具-海温月预测界面出现问题。")
				.toJSONString();
	}

	@Override
	public String sstMonthPredictRun(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			InputStream is = getTransMetaString(TRANS_RUN_PROC);
			TransMeta tm = new TransMeta(is, null, true, null, null);
			TransPoolWapper wapper = new TransPoolWapper("sstMonthPredict", tm);
			wapper.putParam("cmd", PropertyUtils
					.getProperty(PROP_MONTH_FORECAST_RUN));
			TransPool.instance().offer(wapper);

			// 设置响应
			return ActionMessage.instance().success("已经提交月度海温预测分析计算执行。")
					.toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			return ActionMessage.instance().failure(e.getMessage())
					.toJSONString();
		} catch (Exception e) {
			log.error("提交月度海温预测分析计算出现问题。");
		}

		return ActionMessage.instance().failure("提交月度海温预测分析计算出现问题。")
				.toJSONString();
	}

	@Override
	public String sstQuarterPredictRun(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			InputStream is = getTransMetaString(TRANS_RUN_PROC);
			TransMeta tm = new TransMeta(is, null, true, null, null);
			TransPoolWapper wapper = new TransPoolWapper("sstQuarterPredict",
					tm);
			wapper.putParam("cmd", PropertyUtils
					.getProperty(PROP_SEASON_FORECAST_RUN));
			TransPool.instance().offer(wapper);

			// 设置响应
			return ActionMessage.instance().success("已经提交季度海温预测分析计算执行。")
					.toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			return ActionMessage.instance().failure(e.getMessage())
					.toJSONString();
		} catch (Exception e) {
			log.error("提交季度海温预测分析计算出现问题。");
		}

		return ActionMessage.instance().failure("提交季度海温预测分析计算出现问题。")
				.toJSONString();
	}

	@Override
	public String sstQuarterPredict(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			attrsMap.addVariable("menuId", context.get("id"));

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_SST_QUARTER_PREDICT, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开分析工具-海温季预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开分析工具-海温季预测界面出现问题。")
				.toJSONString();
	}

	private InputStream getTransMetaString(String relativePath)
			throws BIException {
		try {
			return FileUtils.getInputStreamByRelativePath("di/" + relativePath,
					PKG);
		} catch (Exception e) {
			throw new BIException("获得转换内容出现错误。");
		}
	}

}
