package com.flywet.platform.bi.services.impl;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectory;
import org.pentaho.di.repository.RepositoryDirectoryInterface;

import com.flywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.flywet.platform.bi.component.components.breadCrumb.BreadCrumbNodeMeta;
import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.pools.RepPool;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.enums.BIDirectoryCategory;

public abstract class AbstractDirectoryServices {

	private final static Logger log = Logger
			.getLogger(AbstractDirectoryServices.class);

	public RepositoryDirectoryInterface getRootDirectory(
			BIDirectoryCategory category) {
		RepositoryDirectory root = new RepositoryDirectory();
		root.setObjectId(new LongObjectId(category.getRootId()));
		return root;
	}

	public RepositoryDirectoryInterface getDirecotry(long id,
			BIDirectoryCategory category) throws BIException {
		Repository rep = null;
		try {
			rep = RepPool.instance().borrowRep();

			RepositoryDirectoryInterface root = getRootDirectory(category);

			RepositoryDirectoryInterface rd = rep.loadRepositoryDirectoryTree(
					root).findDirectory(new LongObjectId(id));

			return rd;

		} catch (KettleException e) {
			log.error("创建父目录页面出现错误。");
			throw new BIException("创建父目录页面出现错误。");
		} finally {
			RepPool.instance().returnRep(rep);
		}
	}

	protected BreadCrumbMeta parentDirectories(Long id, String tital,
			String prefixPath, BIDirectoryCategory category) throws BIException {
		Repository rep = null;
		try {
			BreadCrumbMeta bce = new BreadCrumbMeta();
			rep = RepPool.instance().borrowRep();
			RepositoryDirectoryInterface root = getRootDirectory(category);
			RepositoryDirectoryInterface rd = rep.loadRepositoryDirectoryTree(
					root).findDirectory(new LongObjectId(id));
			bce.addEvent("click", "Flywet.browse.changeDir");
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
			RepPool.instance().returnRep(rep);
		}
	}

	/**
	 * 实例化目录子对象
	 * 
	 * @param id
	 * @param cate
	 * @param style
	 * @param displayName
	 * @return
	 * @throws BIJSONException
	 */
	protected BrowseNodeMeta subDirectoryObject(String id, String cate,
			String style, String displayName) throws BIJSONException {
		BrowseNodeMeta node = new BrowseNodeMeta();
		node.setId(id);
		node.setCategory(cate);
		node.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, displayName);
		node.addAttribute(BrowseNodeMeta.ATTR_ICON_STYLE, style);
		node.addAttribute(HTML.ATTR_TYPE, Utils.DOM_LEAF);
		node.addAttribute(HTML.ATTR_SRC, cate + "/" + id + "/open");
		node.addEvent("mouseup", "Flywet.browse.showOperationForFile");
		node.addEvent("dblclick", "Flywet.browse.openFile");
		return node;
	}

	/**
	 * 获得目录对象
	 * 
	 * @param id
	 * @param browse
	 * @param prefixPath
	 * @param category
	 * @throws BIException
	 */
	protected void subDirectory(Long id, BrowseMeta browse, String prefixPath,
			BIDirectoryCategory category) throws BIException {
		Repository rep = null;
		try {
			rep = RepPool.instance().borrowRep();
			RepositoryDirectoryInterface root = getRootDirectory(category);
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

				node.addEvent("mouseup", "Flywet.browse.showOperationForDir");
				node.addEvent("dblclick", "Flywet.browse.changeDir");
				browse.addContent(node);
			}

		} catch (KettleException e) {
			log.error("创建子目录页面出现错误。");
			throw new BIException("创建子目录页面出现错误。");
		} finally {
			RepPool.instance().returnRep(rep);
		}

	}

	public BreadCrumbMeta getParentDirectories(Long id,
			BIDirectoryCategory category) throws BIException {
		return parentDirectories(id, category.getDescription(), "/"
				+ category.getId() + "/dir/", category);
	}

	public void getSubDirectory(Long id, BrowseMeta browse,
			BIDirectoryCategory category) throws BIException {
		subDirectory(id, browse, "/" + category.getId() + "/dir/", category);
	}

	public void removeDirectoryObject(long dirId, BIDirectoryCategory category)
			throws BIException {
		Repository rep = null;
		try {
			rep = RepPool.instance().borrowRep();
			RepositoryDirectoryInterface dir = this.getDirecotry(dirId,
					category);
			if (dir.getChildren() != null && dir.getChildren().size() > 0) {
				throw new BIException("该目录非空，请先清空内部元素！");
			}
			rep.deleteRepositoryDirectory(dir);
		} catch (BIException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("保存目录出现错误。");
			throw new BIException("保存目录现错误。");
		} finally {
			RepPool.instance().returnRep(rep);
		}
	}

	public void newDirectoryObject(long parentDirId, String name,
			BIDirectoryCategory category) throws BIException {
		Repository rep = null;
		try {
			rep = RepPool.instance().borrowRep();
			RepositoryDirectoryInterface dir = this.getDirecotry(parentDirId,
					category);
			if (dir.findChild(name) != null) {
				throw new BIException("目录名称重复！");
			}
			rep.createRepositoryDirectory(dir, name);
		} catch (BIException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("保存目录出现错误。");
			throw new BIException("保存目录现错误。");
		} finally {
			RepPool.instance().returnRep(rep);
		}
	}

	public void editDirectoryObject(long parentDirId, long dirId, String name,
			BIDirectoryCategory category) throws BIException {
		Repository rep = null;
		try {
			rep = RepPool.instance().borrowRep();
			RepositoryDirectoryInterface pdir = this.getDirecotry(parentDirId,
					category);
			RepositoryDirectoryInterface dir = this.getDirecotry(dirId,
					category);
			if (!dir.getName().equals(name)) {
				if (pdir.findChild(name) != null) {
					throw new BIException("目录名称重复！");
				}
				rep.renameRepositoryDirectory(new LongObjectId(dirId), pdir,
						name);
			}
		} catch (BIException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("保存目录出现错误。");
			throw new BIException("保存目录现错误。");
		} finally {
			RepPool.instance().returnRep(rep);
		}
	}

}
