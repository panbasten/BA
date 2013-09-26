package com.flywet.cust.p001.portal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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
import com.flywet.platform.bi.core.utils.FileUtils;
import com.flywet.platform.bi.core.utils.PropertyUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.enums.BIFileSystemCategory;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;

public class ForecastAdaptorImpl extends BIAbstractDbAdaptor implements
		ForecastAdaptor {
	private final Logger log = Logger.getLogger(ForecastAdaptorImpl.class);

	private static Class<?> PKG = ForecastAdaptorImpl.class;

	private static final String[] YUN_DESC = new String[] { "上旬", "中旬", "下旬" };

	private static final String TEMPLATE_MONTH_PREDICT = "monthPredict.h";
	private static final String TEMPLATE_EXTEND_PREDICT = "extendPredict.h";
	private static final String TEMPLATE_MONTH_PREDICT_SCORE = "monthPredictScore.h";
	private static final String TEMPLATE_EXTEND_PREDICT_SCORE = "extendPredictScore.h";

	private static final String PROP_MONTH_PREDICT_FILE_ROOT_PATH = "custom.portal.monthPredict.file.rootPath";
	private static final String PROP_MONTH_PREDICT_FILE_CATEGORY = "custom.portal.monthPredict.file.category";

	@Override
	public String monthPredictUpdate(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {
			String currentMonth = (String) context.get("param");

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

			String currentMonth = children[0].getName().getBaseName();

			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			attrsMap.addVariable("menuId", context.get("id"));
			attrsMap.addVariable("monthes", children);
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

	private BrowseMeta getBrowse(String rootPathProp, String categoryProp,
			String workDir) throws BIException, FileSystemException {
		String category = PropertyUtils
				.getProperty(PROP_MONTH_PREDICT_FILE_CATEGORY);

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

			String currentXun = (String) context.get("param");
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
			attrsMap.addVariable("yun", getExtendPredictKey(current));
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
}
