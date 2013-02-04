package com.yonyou.bq8.di.web.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.Repository;
import org.springframework.stereotype.Service;

import com.yonyou.bq8.di.component.components.browse.BrowseMeta;
import com.yonyou.bq8.di.component.components.browse.BrowseNodeMeta;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.core.utils.Utils;
import com.yonyou.bq8.di.delegates.DIEnvironmentDelegate;
import com.yonyou.bq8.di.exceptions.DIKettleException;
import com.yonyou.bq8.di.web.service.DIDatabaseDelegates;

@Service("di.service.databaseServices")
public class DIDatabaseServices implements DIDatabaseDelegates {

	private final Logger log = Logger.getLogger(DIDatabaseServices.class);

	@Override
	public void getNavigatorsDatabase(String repository, BrowseMeta browseMeta)
			throws DIException {

		Repository rep = null;
		try {
			rep = DIEnvironmentDelegate.instance().borrowRep(repository, null);
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
				node.addEvent("mouseup", "YonYou.browse.showOperationForFile");
				node.addEvent("dblclick", "YonYou.browse.openFile");
				browseMeta.addContent(node);
			}
		} catch (Exception ex) {
			log.error("获得数据库对象出现异常", ex);
		} finally {
			DIEnvironmentDelegate.instance().returnRep(repository, rep);
		}

	}

	@Override
	public DatabaseMeta getDatabaseMeta(String repository, Long id)
			throws DIKettleException {
		Repository rep = null;
		try {
			rep = DIEnvironmentDelegate.instance().borrowRep(repository, null);
			return rep.loadDatabaseMeta(new LongObjectId(id), null);
		} catch (Exception ex) {
			log.error("获得数据库对象出现异常", ex);
		} finally {
			DIEnvironmentDelegate.instance().returnRep(repository, rep);
		}
		return null;
	}

}
