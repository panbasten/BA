package com.yonyou.bq8.di.delegates.intf;

import java.util.List;

import com.yonyou.bq8.di.delegates.vo.FilesysDirectory;
import com.yonyou.bq8.di.exceptions.DIKettleException;

public interface DIFsLocalAdaptor {
	public Object[] getLocalRootById(long id) throws DIKettleException;
	public void addFsDirectory(FilesysDirectory directory) throws DIKettleException;
	public void updateFsDirectory(FilesysDirectory directory) throws DIKettleException;
	public void deleteFsDirectory(long id) throws DIKettleException;
	public List<Object[]> getLocalRoots() throws DIKettleException;
}
