package com.plywet.platform.bi.web.service;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectory;
import org.pentaho.di.repository.RepositoryDirectoryInterface;

import com.plywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.plywet.platform.bi.component.components.breadCrumb.BreadCrumbNodeMeta;
import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.delegates.BIEnvironmentDelegate;

public abstract class AbstractDirectoryServices {

	private final Logger log = Logger
			.getLogger(AbstractDirectoryServices.class);

	protected BreadCrumbMeta parentDirectories(String repository, Long rootId,
			Long id, String tital, String prefixPath) throws BIException {
		Repository rep = null;
		try {
			BreadCrumbMeta bce = new BreadCrumbMeta();
			rep = BIEnvironmentDelegate.instance().borrowRep(repository, null);
			RepositoryDirectory root = new RepositoryDirectory();
			root.setObjectId(new LongObjectId(rootId));
			RepositoryDirectoryInterface rd = rep.loadRepositoryDirectoryTree(
					root).findDirectory(new LongObjectId(id));
			bce.addEvent("click", "Plywet.browse.changeDir");
			String name;
			while (true) {
				name = rd.getName();
				if (rd.isRoot()) {
					name = tital;
				}
				BreadCrumbNodeMeta node = new BreadCrumbNodeMeta(rd
						.getObjectId().getId(), name, prefixPath
						+ rd.getObjectId().getId(), "");

				bce.addContentFirse(node);

				if (rd.getParent() == null) {
					break;
				} else {
					rd = rd.getParent();
				}
			}
			return bce;

		} catch (KettleException e) {
			log.error("创建父目录页面出现错误。");
			throw new BIException("创建父目录页面出现错误。");
		} finally {
			BIEnvironmentDelegate.instance().returnRep(repository, rep);
		}
	}

	protected void subDirectory(String repository, Long rootId, Long id,
			BrowseMeta browse, String prefixPath) throws BIException {
		Repository rep = null;
		try {
			rep = BIEnvironmentDelegate.instance().borrowRep(repository, null);
			RepositoryDirectory root = new RepositoryDirectory();
			root.setObjectId(new LongObjectId(rootId));
			RepositoryDirectoryInterface rd = rep.loadRepositoryDirectoryTree(
					root).findDirectory(new LongObjectId(id));
			for (RepositoryDirectoryInterface subrd : rd.getChildren()) {
				BrowseNodeMeta node = new BrowseNodeMeta();
				node.setId(subrd.getObjectId().getId());
				node.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, subrd
						.getName());
				node.addAttribute(HTML.ATTR_TYPE, Utils.DOM_NODE);
				node.addAttribute(HTML.ATTR_SRC, prefixPath
						+ subrd.getObjectId().getId());

				node.addEvent("mouseup", "Plywet.browse.showOperationForDir");
				node.addEvent("dblclick", "Plywet.browse.changeDir");
				browse.addContent(node);
			}

		} catch (KettleException e) {
			log.error("创建子目录页面出现错误。");
			throw new BIException("创建子目录页面出现错误。");
		} finally {
			BIEnvironmentDelegate.instance().returnRep(repository, rep);
		}

	}

}
