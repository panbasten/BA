package com.flywet.platform.bi.di.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.plugins.JobEntryPluginType;
import org.pentaho.di.core.plugins.PluginInterface;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryElementMetaInterface;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.pools.RepPool;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.enums.BIDirectoryCategory;
import com.flywet.platform.bi.di.service.intf.BIDIDelegates;
import com.flywet.platform.bi.services.impl.AbstractDirectoryServices;

@Service("bi.service.diService")
public class BIDIServices extends AbstractDirectoryServices implements
		BIDIDelegates {

	private final Logger log = Logger.getLogger(BIDIServices.class);

	private static final String ICON_PATH = "resources/images/entities/";

	@Override
	public void getSubDirectoryObject(Long id, BrowseMeta browse,
			BIDirectoryCategory cate) throws BIException {
		getSubDirctoryObjectByType(Utils.CATEGORY_DI_TRANS, id, browse);
		getSubDirctoryObjectByType(Utils.CATEGORY_DI_JOB, id, browse);
	}

	private void getSubDirctoryObjectByType(String category, Long id,
			BrowseMeta browse) throws BIException {
		Repository rep = null;
		try {
			rep = RepPool.instance().borrowRep();

			List<RepositoryElementMetaInterface> repObjects = null;
			if (Utils.CATEGORY_DI_TRANS.equals(category)) {
				repObjects = rep.getTransformationObjects(new LongObjectId(id),
						false);
			} else if (Utils.CATEGORY_DI_JOB.equals(category)) {
				repObjects = rep.getJobObjects(new LongObjectId(id), false);
			}
			if (repObjects == null) {
				return;
			}
			for (RepositoryElementMetaInterface sub : repObjects) {
				BrowseNodeMeta node = subDirectoryObject(sub.getObjectId()
						.getId(), category, "ui-" + category + "-icon", sub
						.getName());
				browse.addContent(node);
			}

		} catch (KettleException e) {
			log.error("创建目录下面的子对象页面出现错误。");
			throw new BIException("创建目录下面的子对象页面出现错误。");
		} finally {
			RepPool.instance().returnRep(rep);
		}

	}

	@Override
	public void getPlugins(String category, BrowseMeta browseMeta)
			throws BIJSONException {
		getPlugins(category, browseMeta, null);
	}

	@Override
	public void getPlugins(String category, BrowseMeta browseMeta,
			String nodeClass) throws BIJSONException {
		PluginRegistry registry = PluginRegistry.getInstance();
		final List<PluginInterface> basesteps = registry
				.getPlugins(StepPluginType.class);

		for (int i = 0; i < basesteps.size(); i++) {
			if (basesteps.get(i).getCategory().equalsIgnoreCase(category)) {
				BrowseNodeMeta plugin = new BrowseNodeMeta();
				plugin.setId(basesteps.get(i).getIds()[0]);
				plugin.setCategory(category);
				if (nodeClass != null) {
					plugin.addClass(nodeClass);
				}
				plugin.addAttribute(HTML.ATTR_TYPE, Utils.DOM_LEAF);
				plugin.addAttribute(HTML.ATTR_ICON, ICON_PATH
						+ basesteps.get(i).getImageFile());
				plugin.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, basesteps
						.get(i).getName());
				plugin.addAttribute(HTML.ATTR_TITLE, basesteps.get(i)
						.getDescription());
				browseMeta.addContent(plugin);
			}
		}
	}

	@Override
	public List<String> getPluginNavigator(String category) {

		PluginRegistry registry = PluginRegistry.getInstance();

		if (Utils.CATEGORY_DI_TRANS.equals(category)) {
			return registry.getCategories(StepPluginType.class);
		} else if (Utils.CATEGORY_DI_JOB.equals(category)) {
			return registry.getCategories(JobEntryPluginType.class);
		}

		return null;
	}
}
