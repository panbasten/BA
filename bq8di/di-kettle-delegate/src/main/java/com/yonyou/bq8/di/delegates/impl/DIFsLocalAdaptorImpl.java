package com.yonyou.bq8.di.delegates.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryBase;

import com.yonyou.bq8.di.delegates.anno.DIDelegate;
import com.yonyou.bq8.di.delegates.intf.DIFsLocalAdaptor;
import com.yonyou.bq8.di.delegates.model.AbstractDbAdaptor;
import com.yonyou.bq8.di.delegates.vo.FilesysDirectory;
import com.yonyou.bq8.di.exceptions.DIKettleException;

@DIDelegate(type="db")
public class DIFsLocalAdaptorImpl extends AbstractDbAdaptor implements DIFsLocalAdaptor {
	private final Logger logger = Logger.getLogger(DIFsLocalAdaptorImpl.class);
	
	@Override
	public void addFsDirectory(FilesysDirectory directory) throws DIKettleException {
		try {
			String sql = "INSERT INTO "
				+ KettleDatabaseRepositoryBase.TABLE_R_FILESYS_DIRECTORY
				+ " VALUES(?,'?','?','?')";
			List<String> params = new ArrayList<String>();
			params.add(String.valueOf(this.getNextBatchId(KettleDatabaseRepositoryBase.TABLE_R_FILESYS_DIRECTORY,KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY)));
			params.add(directory.getPath());
			params.add(directory.getDesc());
			params.add(directory.getNotes());
			
			sql = replaceParam(sql, params);
			execSql(sql);
		} catch (KettleException e) {
			logger.error("add local directory exception:", e);
			throw new DIKettleException(e);
		}
	}

	@Override
	public void deleteFsDirectory(long id) throws DIKettleException {
		try {
			String sql = "DELETE FROM "
				+ KettleDatabaseRepositoryBase.TABLE_R_FILESYS_DIRECTORY 
				+ " WHERE " + KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY + " = " + id;
			execSql(sql);
		} catch (KettleException e) {
			throw new DIKettleException(e);
		}
	}

	@Override
	public Object[] getLocalRootById(long id) throws DIKettleException {
		try {
			String sql = "SELECT "
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_PATH
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_DESCRIPTION
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_NOTES
					+ " FROM "
					+ KettleDatabaseRepositoryBase.TABLE_R_FILESYS_DIRECTORY
					+ " WHERE "
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY + " = " + id;
			return getOneRow(sql);
		} catch (KettleException e) {
			throw new DIKettleException(e);
		}
	}

	@Override
	public void updateFsDirectory(FilesysDirectory directory) throws DIKettleException {
		try {
			String sql = "UPDATE "
				+ KettleDatabaseRepositoryBase.TABLE_R_FILESYS_DIRECTORY
				+ " SET " + KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_PATH + "= '?', " 
				+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_DESCRIPTION + "='?', "
				+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_NOTES + " = '?' "
				+ " WHERE " 
				+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY + "=?" ;
			List<String> params = new ArrayList<String>();
			params.add(directory.getPath());
			params.add(directory.getDesc());
			params.add(directory.getNotes());
			params.add(String.valueOf(directory.getId()));
			
			sql = replaceParam(sql, params);
			execSql(sql);
		} catch (KettleException e) {
			throw new DIKettleException(e);
		}
	}
	
	@Override
	public List<Object[]> getLocalRoots() throws DIKettleException {
		try {
			String sql = "SELECT "
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_PATH
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_DESCRIPTION
					+ ","
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_NOTES
					+ " FROM "
					+ KettleDatabaseRepositoryBase.TABLE_R_FILESYS_DIRECTORY
					+ " WHERE "
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_FS_TYPE 
					+ " = 1";
			return getRows(sql);
		} catch (KettleException e) {
			throw new DIKettleException(e);
		}
	}

}
