package com.flywet.platform.bi.web.utils;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectory;
import org.pentaho.di.repository.RepositoryDirectoryInterface;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.enums.BIDirectoryCategory;

public class DIUtils {

	private final static Logger log = Logger.getLogger(DIUtils.class);

	public static RepositoryDirectoryInterface getRootDirectory(
			BIDirectoryCategory category) {
		RepositoryDirectory root = new RepositoryDirectory();
		root.setObjectId(new LongObjectId(category.getRootId()));
		return root;
	}

	public static RepositoryDirectoryInterface getDirecotry(Repository rep,
			long id, BIDirectoryCategory category) throws BIException {
		try {

			RepositoryDirectoryInterface root = getRootDirectory(category);

			RepositoryDirectoryInterface rd = rep.loadRepositoryDirectoryTree(
					root).findDirectory(new LongObjectId(id));

			return rd;

		} catch (KettleException e) {
			log.error("创建父目录页面出现错误。");
			throw new BIException("创建父目录页面出现错误。");
		}
	}

}
