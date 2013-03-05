package com.yonyou.bq8.di.delegates.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryBase;

import com.yonyou.bq8.di.delegates.anno.DIDelegate;
import com.yonyou.bq8.di.delegates.intf.DIHostAdaptor;
import com.yonyou.bq8.di.delegates.model.AbstractDbAdaptor;
import com.yonyou.bq8.di.delegates.vo.DIHost;
import com.yonyou.bq8.di.exceptions.DIKettleException;

@DIDelegate(type = "db")
public class DIHostAdaptorImpl extends AbstractDbAdaptor implements
		DIHostAdaptor {
	private final Logger logger = Logger.getLogger(DIHostAdaptorImpl.class);

	private final String QUERY_HOST = "SELECT "
			+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY
			+ "," + KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_PATH
			+ "," + KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_DESCRIPTION
			+ "," + KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_NOTES
			+ " FROM " + KettleDatabaseRepositoryBase.TABLE_R_FILESYS_DIRECTORY;

	@Override
	public List<Object[]> getAllHosts() throws DIKettleException {
		try {
			return getRows(QUERY_HOST);
		} catch (KettleException e) {
			throw new DIKettleException(e);
		}
	}

	@Override
	public Object[] getHostById(long id) throws DIKettleException {
		try {
			String sql = QUERY_HOST + " WHERE "
					+ KettleDatabaseRepositoryBase.FIELD_HOST_ID_HOST + " = "
					+ id;
			return getOneRow(sql);
		} catch (KettleException e) {
			throw new DIKettleException(e);
		}
	}

	@Override
	public List<Object[]> getHostsByType(int type) throws DIKettleException {
		try {
			String sql = QUERY_HOST
					+ " WHERE "
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_FS_TYPE
					+ " = " + type;
			return getRows(sql);
		} catch (KettleException e) {
			throw new DIKettleException(e);
		}
	}

	@Override
	public void delete(long id) throws DIKettleException {
		try {
			String sql = "DELETE FROM "
					+ KettleDatabaseRepositoryBase.TABLE_R_HOST + " WHERE "
					+ KettleDatabaseRepositoryBase.FIELD_HOST_ID_HOST + " = "
					+ id;
			execSql(sql);
		} catch (KettleException e) {
			throw new DIKettleException(e);
		}
	}

	@Override
	public void addHost(DIHost host) throws DIKettleException {
		try {
			String sql = "INSERT INTO "
					+ KettleDatabaseRepositoryBase.TABLE_R_HOST
					+ " VALUES(?,'?','?','?',?,'?','?','?','?','?')";
			List<String> params = new ArrayList<String>();
			params.add(String.valueOf(this.getNextBatchId(
					KettleDatabaseRepositoryBase.TABLE_R_HOST,
					KettleDatabaseRepositoryBase.FIELD_HOST_ID_HOST)));
			params.add(host.getCode());
			params.add(host.getDesc());
			params.add(host.getIp());
			params.add(String.valueOf(host.getPort()));
			params.add(host.getUsername());
			params.add(host.getPassword());
			params.add(host.getType());
			params.add(host.getNotes());
			params.add(host.getMode());

			sql = replaceParam(sql, params);
			execSql(sql);
		} catch (KettleException e) {
			logger.error("add host exception:", e);
			throw new DIKettleException(e);
		}
	}

	@Override
	public void updateHost(DIHost host) throws DIKettleException {
		try {
			String sql = "UPDATE " + KettleDatabaseRepositoryBase.TABLE_R_HOST
					+ " SET " + KettleDatabaseRepositoryBase.FIELD_HOST_CODE
					+ " = '?', "
					+ KettleDatabaseRepositoryBase.FIELD_HOST_DESCRIPTION
					+ "= '?', " + KettleDatabaseRepositoryBase.FIELD_HOST_IP
					+ "='?', " + KettleDatabaseRepositoryBase.FIELD_HOST_PORT
					+ " = ?, "
					+ KettleDatabaseRepositoryBase.FIELD_HOST_USERNAME
					+ "= '?',"
					+ KettleDatabaseRepositoryBase.FIELD_HOST_PASSWORD
					+ "='?'," + KettleDatabaseRepositoryBase.FIELD_HOST_TYPE
					+ "='?'," + KettleDatabaseRepositoryBase.FIELD_HOST_NOTES
					+ "='?'," + KettleDatabaseRepositoryBase.FIELD_HOST_MODE
					+ "='?'" + " WHERE "
					+ KettleDatabaseRepositoryBase.FIELD_HOST_ID_HOST + "=?";
			List<String> params = new ArrayList<String>();
			params.add(host.getCode());
			params.add(host.getDesc());
			params.add(host.getIp());
			params.add(String.valueOf(host.getPort()));
			params.add(host.getUsername());
			params.add(host.getPassword());
			params.add(host.getType());
			params.add(host.getNotes());
			params.add(host.getMode());
			params.add(String.valueOf(host.getId()));

			sql = replaceParam(sql, params);
			execSql(sql);
		} catch (KettleException e) {
			throw new DIKettleException(e);
		}

	}

}
