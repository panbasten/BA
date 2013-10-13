package com.flywet.cust.p001.portal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.FileType;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.log4j.Logger;
import org.pentaho.di.core.Const;

import com.flywet.cust.p001.db.CustomDatabaseRepositoryBase;
import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.component.web.ActionMessage;
import com.flywet.platform.bi.component.web.AjaxResult;
import com.flywet.platform.bi.component.web.AjaxResultEntity;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
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

	private static final String TEMPLATE_MONTH_PREDICT = "monthPredict.h";
	private static final String TEMPLATE_EXTEND_PREDICT = "extendPredict.h";
	private static final String TEMPLATE_MONTH_PREDICT_SCORE = "monthPredictScore.h";
	private static final String TEMPLATE_EXTEND_PREDICT_SCORE = "extendPredictScore.h";
	private static final String TEMPLATE_PREDICT_SETTING = "predictSetting.h";
	private static final String TEMPLATE_EXTEND_SETTING = "extendSetting.h";

	private static final String TEMPLATE_BUZ_NORMS = "buzNorms.h";
	private static final String TEMPLATE_BUZ_TIMED = "buzTimed.h";
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

			// 设置响应
			return ActionMessage.instance().success("已经提交统计尺度分析计算执行。")
					.toJSONString();
		} catch (Exception e) {
			log.error("提交统计尺度分析计算出现问题执行。");
		}

		return ActionMessage.instance().failure("提交统计尺度分析计算出现问题执行。")
				.toJSONString();
	}

	@Override
	public String extendPredictDataRun(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {

			// 设置响应
			return ActionMessage.instance().success("已经提交统计尺度分析计算执行。")
					.toJSONString();
		} catch (Exception e) {
			log.error("提交统计尺度分析计算出现问题执行。");
		}

		return ActionMessage.instance().failure("提交统计尺度分析计算出现问题执行。")
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

				node.addEvent("click", "downloadFile");
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
			params[0] = xun[0];
			params[1] = xun[1];
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
		return (String) r[0] + ":" + (String) r[1] + ":" + (Long) r[2];
	}

	private String getExtendPredictValue(Object[] r) {
		return (String) r[0] + "年" + (String) r[1] + "月"
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

	@Override
	public String extendSetting(String targetId, HashMap<String, Object> context)
			throws BIJSONException {

		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_EXTEND_SETTING, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开延伸期预测填报界面出现问题。");
		}

		return ActionMessage.instance().failure("打开当月延伸期预测填报界面出现问题。")
				.toJSONString();
	}

	@Override
	public String monthPredictScore(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buzTimed(String targetId, HashMap<String, Object> context)
			throws BIJSONException {
		// TODO Auto-generated method stub
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
		InputStream is = null;
		OutputStream os = null;

		File fullFile = new File(PropertyUtils.getProperty(fileName));
		try {
			String destFileStr = fullFile.getName();
			FileObject destFileObj = composeVfsObject(category, destFileStr,
					rootDir);

			is = item.getInputStream();
			os = destFileObj.getContent().getOutputStream();

			byte[] bytes = new byte[1024];
			int in = 0;
			while ((in = is.read(bytes)) != -1) {
				os.write(bytes);
			}
			os.flush();
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (os != null) {
				os.close();
			}
			if (is != null) {
				is.close();
			}
			item.delete();
		}
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String links(String targetId, HashMap<String, Object> context)
			throws BIJSONException {
		// TODO Auto-generated method stub
		return null;
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
	public String monthPredictEvaluate(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String scoreSetting(String targetId, HashMap<String, Object> context)
			throws BIJSONException {
		// TODO Auto-generated method stub
		return null;
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

			// 设置响应
			return ActionMessage.instance().success("已经提交月度海温预测分析计算执行。")
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

			// 设置响应
			return ActionMessage.instance().success("已经提交季度海温预测分析计算执行。")
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

}
