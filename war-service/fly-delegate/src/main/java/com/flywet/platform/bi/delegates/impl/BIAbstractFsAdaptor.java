package com.flywet.platform.bi.delegates.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;

import com.flywet.platform.bi.core.db.BIDatabaseRepositoryBase;
import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;
import com.flywet.platform.bi.delegates.vo.FilesysDirectory;

public abstract class BIAbstractFsAdaptor extends BIAbstractDbAdaptor {

	private final Logger logger = Logger.getLogger(BIAbstractFsAdaptor.class);

	public void addRootDirectory(FilesysDirectory directory)
			throws BIKettleException {
		try {
			String sql = "INSERT INTO "
					+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_FILESYS_DIRECTORY)
					+ " VALUES(?,?,?,?,?)";

			execSql(
					sql,
					new Object[] {
							this
									.getNextBatchId(
											BIDatabaseRepositoryBase.TABLE_BI_FILESYS_DIRECTORY,
											BIDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY),
							directory.getFsType(), directory.getPath(),
							directory.getDesc(), directory.getNotes() });
		} catch (KettleException e) {
			logger.error("add local directory exception:", e);
			throw new BIKettleException(e);
		}
	}

	public void deleteRootDirectory(long id) throws BIKettleException {
		try {
			String sql = "DELETE FROM "
					+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_FILESYS_DIRECTORY)
					+ " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY)
					+ " = " + id;
			execSql(sql);
		} catch (KettleException e) {
			throw new BIKettleException(e);
		}
	}

	public FilesysDirectory getRootDirectoryById(long id)
			throws BIKettleException {
		try {
			String sql = getQueryFsDirectory()
					+ " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY)
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
					+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_FILESYS_DIRECTORY)
					+ " SET "
					+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_PATH)
					+ "=?, "
					+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_DESCRIPTION)
					+ "=?, "
					+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_NOTES)
					+ "=? "
					+ " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY)
					+ "=?";
			execSql(sql, new Object[] { directory.getPath(),
					directory.getDesc(), directory.getNotes(),
					directory.getId() });
		} catch (KettleException e) {
			throw new BIKettleException(e);
		}
	}

	private String getQueryFsDirectory() {
		return "SELECT "
				+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY)
				+ ","
				+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_PATH)
				+ ","
				+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_DESCRIPTION)
				+ ","
				+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_NOTES)
				+ " FROM "
				+ quoteTable(BIDatabaseRepositoryBase.TABLE_BI_FILESYS_DIRECTORY);
	}

	protected List<FilesysDirectory> getRootDirectoriesByType(int type)
			throws BIKettleException {
		try {
			String sql = getQueryFsDirectory()
					+ " WHERE "
					+ quote(BIDatabaseRepositoryBase.FIELD_FILESYS_DIRECTORY_FS_TYPE)
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
		FilesysDirectory filesysDirectory = FilesysDirectory.instance().setId(
				(Long) obj[0]).setPath((String) obj[1])
				.setDesc((String) obj[2]).setNotes((String) obj[3]);
		return filesysDirectory;
	}
}
