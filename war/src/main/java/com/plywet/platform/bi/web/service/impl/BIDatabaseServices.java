package com.plywet.platform.bi.web.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.Repository;
import org.springframework.stereotype.Service;

import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.delegates.BIEnvironmentDelegate;
import com.plywet.platform.bi.delegates.exceptions.BIKettleException;
import com.plywet.platform.bi.web.service.BIDatabaseDelegates;

@Service("bi.service.databaseServices")
public class BIDatabaseServices implements BIDatabaseDelegates {

	private final Logger log = Logger.getLogger(BIDatabaseServices.class);

	@Override
	public void getNavigatorsDatabase(String repository, BrowseMeta browseMeta)
			throws BIException {

		Repository rep = null;
		try {
			rep = BIEnvironmentDelegate.instance().borrowRep(repository, null);
			List<DatabaseMeta> databaseMetas = rep.getDatabases();
			for (DatabaseMeta d : databaseMetas) {
				BrowseNodeMeta node = new BrowseNodeMeta();
				node.setCategory(Utils.CATEGORY_DB);
				node.setId(d.getObjectId().getId());
				node
						.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, d
								.getName());
				node.addAttribute(BrowseNodeMeta.ATTR_ICON_STYLE, "ui-"
						+ Utils.CATEGORY_DB + "-icon");
				node.addAttribute(HTML.ATTR_TYPE, Utils.DOM_LEAF);
				node.addAttribute(HTML.ATTR_SRC, Utils.CATEGORY_DB + "/object/"
						+ d.getObjectId().getId());
				node.addEvent("mouseup", "Plywet.browse.showOperationForFile");
				node.addEvent("dblclick", "Plywet.browse.openFile");
				browseMeta.addContent(node);
			}
		} catch (Exception ex) {
			log.error("获得数据库对象出现异常", ex);
		} finally {
			BIEnvironmentDelegate.instance().returnRep(repository, rep);
		}

	}

	@Override
	public DatabaseMeta getDatabaseMeta(String repository, Long id)
			throws BIKettleException {
		Repository rep = null;
		try {
			rep = BIEnvironmentDelegate.instance().borrowRep(repository, null);
			return rep.loadDatabaseMeta(new LongObjectId(id), null);
		} catch (Exception ex) {
			log.error("获得数据库对象出现异常", ex);
		} finally {
			BIEnvironmentDelegate.instance().returnRep(repository, rep);
		}
		return null;
	}

}
