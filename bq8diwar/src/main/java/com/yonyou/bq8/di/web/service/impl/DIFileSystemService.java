package com.yonyou.bq8.di.web.service.impl;

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

import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.delegates.intf.DIFilesysTypeAdaptor;
import com.yonyou.bq8.di.delegates.utils.DIAdaptorFactory;
import com.yonyou.bq8.di.delegates.utils.DIFileSystemCategory;
import com.yonyou.bq8.di.delegates.vo.FilesysDirectory;
import com.yonyou.bq8.di.delegates.vo.FilesysType;
import com.yonyou.bq8.di.web.service.DIFileSystemDelegate;

@Service("di.service.filesystemService")
public class DIFileSystemService implements DIFileSystemDelegate {
	private final Logger log = Logger.getLogger(DIFileSystemService.class);

	@Override
	public List<FilesysType> getFilesysTypes() throws DIException {
		DIFilesysTypeAdaptor adaptor = DIAdaptorFactory
				.createAdaptor(DIFilesysTypeAdaptor.class);

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
	public List<FilesysDirectory> getFilesysRoots(DIFileSystemCategory cate)
			throws DIException {
		return cate.getFsAdaptor().getRootDirectories();
	}

	@Override
	public FileObject composeVfsObject(String category, String workDir,
			long rootId) throws DIException {
		try {
			FileSystemManager fsManager = VFS.getManager();
			FileSystemOptions opts = new FileSystemOptions();

			DIFileSystemCategory cate = DIFileSystemCategory
					.getCategoryByCode(category);
			FilesysDirectory fd = cate.getFsAdaptor().getRootDirectoryById(
					rootId);

			String vfsPath = cate.getVfsPath(workDir, fd);

			if (DIFileSystemCategory.FILESYS_TYPE_SFTP.getCategory().equals(
					category)) {
				SftpFileSystemConfigBuilder.getInstance()
						.setStrictHostKeyChecking(opts, "no");
			}

			FileObject fileObj = fsManager.resolveFile(vfsPath, opts);
			return fileObj;
		} catch (FileSystemException e) {
			log.error("get vfs fileobject exception", e);
			throw new DIException("读取文件系统" + workDir + "失败");
		}

	}

	@Override
	public String getRootDesc(String category, long rootId) throws DIException {
		String rootDirName = null;

		FilesysDirectory fd = DIFileSystemCategory.getCategoryByCode(category)
				.getFsAdaptor().getRootDirectoryById(rootId);

		return fd.getDesc();
	}

}
