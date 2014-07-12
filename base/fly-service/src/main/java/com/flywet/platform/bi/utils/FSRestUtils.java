package com.flywet.platform.bi.utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.FileType;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.log4j.Logger;
import org.pentaho.di.core.Const;

import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.DateUtils;
import com.flywet.platform.bi.core.utils.FileUtils;
import com.flywet.platform.bi.core.utils.PropertyUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.enums.BIFileSystemCategory;

/**
 * 文件系统展现的工具类
 * 
 * @author PeterPan
 * 
 */
public class FSRestUtils {

	private final static Logger log = Logger.getLogger(FSRestUtils.class);

	private static Class<?> PKG = FSRestUtils.class;

	public static BrowseMeta getBrowse(String rootPathProp,
			String categoryProp, String workDir) throws BIException,
			FileSystemException {
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

				node.addExtendAttribute("lastTime", getLastModifiedTime(child));

				node.addEvent("dblclick", "Flywet.PortalAction.downloadFile");
			}
			browse.addContent(node);
		}

		browse.addClass("fly-browsepanel");

		return browse;
	}

	public static String getLastModifiedTime(FileObject file)
			throws FileSystemException {
		if (file == null || file.getContent() == null) {
			return "";
		}
		return DateUtils
				.formatDate(new Date(file.getContent().getLastModifiedTime()),
						DateUtils.GENERALIZED_DATE_TIME_FORMAT);
	}

	public static FileObject getFileObject(String categoryProp, String workDir,
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

	public static FileObject getFileObject(String categoryProp, String vfsPath)
			throws BIException {
		try {
			String category = PropertyUtils.getProperty(categoryProp);

			FileSystemManager fsManager = VFS.getManager();
			FileSystemOptions opts = new FileSystemOptions();

			if (BIFileSystemCategory.FILESYS_TYPE_SFTP.getCategory().equals(
					category)) {
				SftpFileSystemConfigBuilder builder = SftpFileSystemConfigBuilder
						.getInstance();
				builder.setStrictHostKeyChecking(opts, "no");
				builder.setUserDirIsRoot(opts, false);

				builder.getKnownHosts(opts);
				builder.setUserDirIsRoot(opts, false);
			}

			return fsManager.resolveFile(vfsPath, opts);
		} catch (Exception e) {
			log.error("get vfs fileobject exception", e);
			throw new BIException("读取文件系统" + vfsPath + "失败");
		}
	}

	static class FileObjectComparator implements Comparator<FileObject> {

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

	static class FileObjectDescComparator implements Comparator<FileObject> {

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
