package com.plywet.platform.bi.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.delegates.enums.BIFileSystemCategory;
import com.plywet.platform.bi.delegates.intf.BIFilesysTypeAdaptor;
import com.plywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.plywet.platform.bi.delegates.vo.FilesysDirectory;
import com.plywet.platform.bi.delegates.vo.FilesysType;
import com.plywet.platform.bi.web.service.BIFileSystemDelegate;

@Service("bi.service.filesystemService")
public class BIFileSystemService implements BIFileSystemDelegate {
	private final Logger log = Logger.getLogger(BIFileSystemService.class);

	@Override
	public List<FilesysType> getFilesysTypes() throws BIException {
		BIFilesysTypeAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIFilesysTypeAdaptor.class);

		List<Object[]> rs = adaptor.getAllFilesysType();

		if (rs == null || rs.isEmpty()) {
			return Collections.emptyList();
		}

		List<FilesysType> filesysTypes = new ArrayList<FilesysType>();
		for (Object[] obj : rs) {
			FilesysType filesysType = new FilesysType();
			filesysType.setId(Long.parseLong(obj[0].toString()));
			filesysType.setCode(obj[1].toString());
			filesysType.setDesc(obj[2].toString());
			filesysTypes.add(filesysType);
		}

		return filesysTypes;
	}

	@Override
	public List<FilesysDirectory> getFilesysRoots(BIFileSystemCategory cate)
			throws BIException {
		return cate.getFsAdaptor().getRootDirectories();
	}

	@Override
	public FilesysDirectory getFilesysObject(String category, long rootId)
			throws BIException {
		try {

			BIFileSystemCategory cate = BIFileSystemCategory
					.getCategoryByCode(category);
			FilesysDirectory fd = cate.getFsAdaptor().getRootDirectoryById(
					rootId);

			return fd;
		} catch (Exception e) {
			log.error("获得文件系统对象失败。", e);
			throw new BIException("获得文件系统对象失败。");
		}
	}

	@Override
	public void removeFilesysObject(String category, String id)
			throws BIException {
		try {
			BIFileSystemCategory cate = BIFileSystemCategory
					.getCategoryByCode(category);
			cate.getFsAdaptor().deleteRootDirectory(Long.valueOf(id));
		} catch (Exception e) {
			log.error("删除文件系统对象失败。", e);
			throw new BIException("删除文件系统对象失败。");
		}
	}

	@Override
	public void updateFilesysObject(String category, FilesysDirectory dir)
			throws BIException {
		try {
			BIFileSystemCategory cate = BIFileSystemCategory
					.getCategoryByCode(category);
			cate.getFsAdaptor().updateRootDirectory(dir);
		} catch (Exception e) {
			log.error("更新文件系统对象失败。", e);
			throw new BIException("更新文件系统对象失败。");
		}
	}

	@Override
	public void createFilesysObject(String category, FilesysDirectory dir)
			throws BIException {
		try {
			BIFileSystemCategory cate = BIFileSystemCategory
					.getCategoryByCode(category);
			dir.setFsType(Integer.valueOf(cate.getId()).longValue());
			cate.getFsAdaptor().addRootDirectory(dir);
		} catch (Exception e) {
			log.error("新增文件系统对象失败。", e);
			throw new BIException("新增文件系统对象失败。");
		}
	}

	@Override
	public FileObject composeVfsObject(String category, String workDir,
			long rootId) throws BIException {
		try {
			FileSystemManager fsManager = VFS.getManager();
			FileSystemOptions opts = new FileSystemOptions();

			BIFileSystemCategory cate = BIFileSystemCategory
					.getCategoryByCode(category);
			FilesysDirectory fd = cate.getFsAdaptor().getRootDirectoryById(
					rootId);

			String vfsPath = cate.getVfsPath(workDir, fd);

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
	public String getRootDesc(String category, long rootId) throws BIException {
		String rootDirName = null;

		FilesysDirectory fd = BIFileSystemCategory.getCategoryByCode(category)
				.getFsAdaptor().getRootDirectoryById(rootId);

		return fd.getDesc();
	}

}
