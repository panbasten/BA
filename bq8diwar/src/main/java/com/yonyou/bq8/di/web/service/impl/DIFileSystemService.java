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
import com.yonyou.bq8.di.core.model.FilesystemCategory;
import com.yonyou.bq8.di.core.utils.BQFileUtils;
import com.yonyou.bq8.di.delegates.intf.DIFilesysTypeAdaptor;
import com.yonyou.bq8.di.delegates.intf.DIFsLocalAdaptor;
import com.yonyou.bq8.di.delegates.intf.DIHostAdaptor;
import com.yonyou.bq8.di.delegates.utils.DIAdaptorFactory;
import com.yonyou.bq8.di.delegates.vo.DIHost;
import com.yonyou.bq8.di.delegates.vo.FilesysDirectory;
import com.yonyou.bq8.di.delegates.vo.FilesysType;
import com.yonyou.bq8.di.web.service.DIFileSystemDelegate;

@Service("di.service.filesystemService")
public class DIFileSystemService implements DIFileSystemDelegate {
	private final Logger log = Logger.getLogger(DIFileSystemService.class);
	
	@Override
	public List<DIHost> getAllHost() throws DIException{
		DIHostAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIHostAdaptor.class);
		
		List<Object[]> hostObjs = adaptor.getAllHosts();
		if (hostObjs == null) {
			return Collections.emptyList();
		}
		
		List<DIHost> hosts = new ArrayList<DIHost>();
		for (Object[] hostObj : hostObjs) {
			DIHost host = populateHost(hostObj);
			hosts.add(host);
		}
		
		return hosts;
	}

	@Override
	public DIHost getFtphostById(long id) throws DIException{
		DIHostAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIHostAdaptor.class);
		Object[] hostObj = adaptor.getHostById(id);
		if (hostObj == null) {
			return null;
		}
		
		DIHost host = populateHost(hostObj);
		return host;
	}

	private DIHost populateHost(Object[] hostObj) {
		DIHost host = new DIHost();
		host.setId(Long.parseLong(hostObj[0].toString()));
		host.setCode(String.valueOf(hostObj[1]));
		host.setDesc(hostObj[2].toString());
		host.setIp(hostObj[3].toString());
		host.setPort(Integer.parseInt(hostObj[4].toString()));
		host.setUsername(hostObj[5].toString());
		host.setPassword(hostObj[6].toString());
		host.setType(hostObj[7].toString());
		host.setNotes(hostObj[8].toString());
		host.setMode(hostObj[9].toString());
		return host;
	}
	
	@Override
	public List<FilesysType> getFilesysTypes() throws DIException {
		DIFilesysTypeAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIFilesysTypeAdaptor.class);
		
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
	public List<FilesysDirectory> getLocalRoots() throws DIException {
		DIFsLocalAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIFsLocalAdaptor.class);
		
		List<Object[]> rs = adaptor.getLocalRoots();
		
		if (rs == null || rs.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<FilesysDirectory> filesysDirectories = new ArrayList<FilesysDirectory>();
		for (Object[] obj : rs) {
			FilesysDirectory filesysDirectory = new FilesysDirectory();
			filesysDirectory.setId(Long.parseLong(obj[0].toString()));
			filesysDirectory.setPath(obj[1].toString());
			filesysDirectory.setDesc(obj[2].toString());
			filesysDirectory.setNotes(obj[3].toString());
			filesysDirectories.add(filesysDirectory);
		}
		
		return filesysDirectories;
	}

	@Override
	public FilesysDirectory getLocalRootById(long id) throws DIException {
		DIFsLocalAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIFsLocalAdaptor.class);
		
		Object[] rs = adaptor.getLocalRootById(id);
		
		if (rs == null) {
			return null;
		}
		
		FilesysDirectory filesysDirectory = new FilesysDirectory();
		filesysDirectory.setId(Long.parseLong(rs[0].toString()));
		filesysDirectory.setPath(rs[1].toString());
		filesysDirectory.setDesc(rs[2].toString());
		filesysDirectory.setNotes(rs[3].toString());
		
		return filesysDirectory;
	}

	@Override
	public  FileObject composeVfsObject(String category,String workDir,long rootId) throws DIException {
		try {
			FileSystemManager fsManager = VFS.getManager();
			FileSystemOptions opts = new FileSystemOptions();
			 
			String vfsPath = null;
			
			if (FilesystemCategory.FILESYS_TYPE_LOCAL.getCategory().equals(category)) {
				FilesysDirectory filesysDirectory = this.getLocalRootById(rootId);
				String rootDir = filesysDirectory.getPath();
				vfsPath = BQFileUtils.dirAppend(rootDir, workDir);
			} else if (FilesystemCategory.FILESYS_TYPE_FTP.getCategory().equals(category)) {
				DIHost host = this.getFtphostById(rootId);
				vfsPath = "ftp://" + host.getUsername() + ":" + host.getPassword() + "@"+host.getIp()
						+ ":" + host.getPort() + workDir;
			} else if (FilesystemCategory.FILESYS_TYPE_SFTP.getCategory().equals(category)) {
				DIHost host = this.getFtphostById(rootId);
				vfsPath = "sftp://" + host.getUsername() + ":" + host.getPassword() + "@"+host.getIp()
						+ ":" + host.getPort() + workDir;
				SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			}
			
			FileObject fileObj = fsManager.resolveFile(vfsPath,opts);
			return fileObj;
		} catch (FileSystemException e) {
			log.error("get vfs fileobject exception", e);
			throw new DIException("读取文件系统" + workDir + "失败");
		}
		
	}
	

	@Override
	public List<DIHost> getHostByCategory(String category) throws DIException {
		DIHostAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIHostAdaptor.class);
		
		List<Object[]> hostObjs = adaptor.getHostsByType(category);
		if (hostObjs == null) {
			return Collections.emptyList();
		}
		
		List<DIHost> hosts = new ArrayList<DIHost>();
		for (Object[] hostObj : hostObjs) {
			DIHost host = populateHost(hostObj);
			hosts.add(host);
		}
		
		return hosts;
	}

	@Override
	public String getRootDesc(String category, long rootId) throws DIException {
		String rootDirName = null;
		
		if (FilesystemCategory.FILESYS_TYPE_LOCAL.getCategory().equals(category)) {
			FilesysDirectory filesysDirectory = this.getLocalRootById(rootId);
			rootDirName = filesysDirectory.getDesc();
		} else {
			DIHost host = this.getFtphostById(rootId);
			rootDirName = host.getDesc();
		} 
		return rootDirName;
	}

	@Override
	public void addHost(DIHost host) throws DIException {
		DIHostAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIHostAdaptor.class);
		adaptor.addHost(host);
	}

	@Override
	public void delHost(long id) throws DIException {
		DIHostAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIHostAdaptor.class);
		adaptor.delete(id);
	}

	@Override
	public void updateHost(DIHost host) throws DIException {
		DIHostAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIHostAdaptor.class);
		adaptor.updateHost(host);
	}

	@Override
	public void addLocalRoot(FilesysDirectory directory)
			throws DIException {
		DIFsLocalAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIFsLocalAdaptor.class);
		adaptor.addFsDirectory(directory);
	}

	@Override
	public void deleteLocalRoot(long id) throws DIException {
		DIFsLocalAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIFsLocalAdaptor.class);
		adaptor.deleteFsDirectory(id);
	}

	@Override
	public void updateLocalRoot(FilesysDirectory directory)
			throws DIException {
		DIFsLocalAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIFsLocalAdaptor.class);
		adaptor.updateFsDirectory(directory);
	}

}
