package com.plywet.platform.bi.delegates.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryBase;

import com.plywet.platform.bi.delegates.exceptions.BIKettleException;
import com.plywet.platform.bi.delegates.model.BIAbstractDbAdaptor;
import com.plywet.platform.bi.delegates.vo.FilesysDirectory;

public abstract class BIAbstractFsAdaptor extends BIAbstractDbAdaptor {

	private final Logger logger = Logger.getLogger(BIAbstractFsAdaptor.class);

	public void addRootDirectory(FilesysDirectory directory)
			throws BIKettleException {
		try {
			String sql = "INSERT INTO "
					+ KettleDatabaseRepositoryBase.TABLE_R_FILESYS_DIRECTORY
					+ " VALUES(?,?,?,?)";

			execSql(
					sql,
					new Object[] {
							this
									.getNextBatchId(
											KettleDatabaseRepositoryBase.TABLE_R_FILESYS_DIRECTORY,
											KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY),
							directory.getPath(), directory.getDesc(),
							directory.getNotes() });
		} catch (KettleException e) {
			logger.error("add local directory exception:", e);
			throw new BIKettleException(e);
		}
	}

	public void deleteRootDirectory(long id) throws BIKettleException {
		try {
			String sql = "DELETE FROM "
					+ KettleDatabaseRepositoryBase.TABLE_R_FILESYS_DIRECTORY
					+ " WHERE "
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY
					+ " = " + id;
			execSql(sql);
		} catch (KettleException e) {
			throw new BIKettleException(e);
		}
	}

	public FilesysDirectory getRootDirectoryById(long id)
			throws BIKettleException {
		try {
			String sql = QUERY_FS_DIRECTORY
					+ " WHERE "
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY
					+ " = " + id;
			Object[] rs = getOneRow(sql);
			if (rs == null) {
				return null;
			}
			return createFilesysDirectory(rs);

		} catch (KettleException e) {
			throw new BIKettleException(e);
		}
	}

	public void updateRootDirectory(FilesysDirectory directory)
			throws BIKettleException {
		try {
			String sql = "UPDATE "
					+ KettleDatabaseRepositoryBase.TABLE_R_FILESYS_DIRECTORY
					+ " SET "
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_PATH
					+ "=?, "
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_DESCRIPTION
					+ "=?, "
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_NOTES
					+ "=? "
					+ " WHERE "
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY
					+ "=?";
			execSql(sql, new Object[] { directory.getPath(),
					directory.getDesc(), directory.getNotes(),
					directory.getId() });
		} catch (KettleException e) {
			throw new BIKettleException(e);
		}
	}

	public final String QUERY_FS_DIRECTORY = "SELECT "
			+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY
			+ "," + KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_PATH
			+ ","
			+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_DESCRIPTION
			+ "," + KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_NOTES
			+ " FROM " + KettleDatabaseRepositoryBase.TABLE_R_FILESYS_DIRECTORY;

	protected List<FilesysDirectory> getRootDirectoriesByType(int type)
			throws BIKettleException {
		try {
			String sql = QUERY_FS_DIRECTORY
					+ " WHERE "
					+ KettleDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_FS_TYPE
					+ " = " + type;
			List<Object[]> rs = getRows(sql);
			if (rs == null || rs.isEmpty()) {
				return Collections.emptyList();
			}

			List<FilesysDirectory> filesysDirectories = new ArrayList<FilesysDirectory>();
			for (Object[] obj : rs) {
				FilesysDirectory filesysDirectory = createFilesysDirectory(obj);
				filesysDirectories.add(filesysDirectory);
			}
			return filesysDirectories;
		} catch (KettleException e) {
			throw new BIKettleException(e);
		}
	}

	protected FilesysDirectory createFilesysDirectory(Object[] obj) {
		FilesysDirectory filesysDirectory = new FilesysDirectory();
		filesysDirectory.setId(Long.parseLong(obj[0].toString()));
		filesysDirectory.setPath(obj[1].toString());
		filesysDirectory.setDesc(obj[2].toString());
		filesysDirectory.setNotes(obj[3].toString());
		return filesysDirectory;
	}
}
