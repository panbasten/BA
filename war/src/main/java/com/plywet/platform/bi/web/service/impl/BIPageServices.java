package com.plywet.platform.bi.web.service.impl;

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

import com.plywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.exception.BIJSONException;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.delegates.BIEnvironmentDelegate;
import com.plywet.platform.bi.delegates.intf.BIFunctionTypeAdaptor;
import com.plywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.plywet.platform.bi.delegates.vo.FunctionType;
import com.plywet.platform.bi.web.service.BIPageDelegates;

/**
 * 元数据服务层接口实现类
 * 
 * @author Peter Pan
 * 
 */
@Service("bi.service.pageServices")
public class BIPageServices extends AbstractDirectoryServices implements
		BIPageDelegates {

	private final Logger log = Logger.getLogger(BIPageServices.class);

	private static final String ICON_PATH = "resources/images/entities/";

	public final static String FILE_PATH_PREFIX = "/transjob/dir/";

	public final static long DIRECTORY_ROOT_ID = 0L;

	@Override
	public BreadCrumbMeta getParentDirectories(String repository, Long id)
			throws BIException {
		return parentDirectories(repository, DIRECTORY_ROOT_ID, id, "转换/作业",
				FILE_PATH_PREFIX);
	}

	@Override
	public void getSubDirectory(String repository, Long id, BrowseMeta browse)
			throws BIException {
		subDirectory(repository, DIRECTORY_ROOT_ID, id, browse,
				FILE_PATH_PREFIX);
	}

	@Override
	public void getSubDirectoryObject(String repository, Long id,
			BrowseMeta browse) throws BIException {
		getSubDirctoryObjectByType(repository, Utils.CATEGORY_TRANS, id, browse);
		getSubDirctoryObjectByType(repository, Utils.CATEGORY_JOB, id, browse);
	}

	private void getSubDirctoryObjectByType(String repository, String category,
			Long id, BrowseMeta browse) throws BIException {
		Repository rep = null;
		try {
			rep = BIEnvironmentDelegate.instance().borrowRep(repository, null);

			List<RepositoryElementMetaInterface> repObjects = null;
			if (Utils.CATEGORY_TRANS.equals(category)) {
				repObjects = rep.getTransformationObjects(new LongObjectId(id),
						false);
			} else if (Utils.CATEGORY_JOB.equals(category)) {
				repObjects = rep.getJobObjects(new LongObjectId(id), false);
			}
			if (repObjects == null) {
				return;
			}
			for (RepositoryElementMetaInterface sub : repObjects) {
				BrowseNodeMeta node = new BrowseNodeMeta();
				node.setCategory(category);
				node.setId(sub.getObjectId().getId());
				node.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, sub
						.getName());
				node.addAttribute(BrowseNodeMeta.ATTR_ICON_STYLE, "ui-"
						+ category + "-icon");
				node.addAttribute(HTML.ATTR_TYPE, Utils.DOM_LEAF);
				node.addAttribute(HTML.ATTR_SRC, category + "/"
						+ sub.getObjectId().getId());
				node.addEvent("mouseup", "Plywet.browse.showOperationForFile");
				node.addEvent("dblclick", "Plywet.browse.openFile");
				browse.addContent(node);
			}

		} catch (KettleException e) {
			log.error("创建目录下面的子对象页面出现错误。");
			throw new BIException("创建目录下面的子对象页面出现错误。");
		} finally {
			BIEnvironmentDelegate.instance().returnRep(repository, rep);
		}

	}

	@Override
	public List<FunctionType> getNavigatorsLevelOne(String repository)
			throws BIException {
		BIFunctionTypeAdaptor functionTypeDelegate = BIAdaptorFactory
				.createAdaptor(BIFunctionTypeAdaptor.class);
		List<FunctionType> functionTypes = functionTypeDelegate
				.getFunctionByParent(0L);

		return functionTypes;
	}

	@Override
	public List<FunctionType> getFunctionsByParent(String repository,
			long parentId) throws BIException {
		BIFunctionTypeAdaptor functionTypeDelegate = BIAdaptorFactory
				.createAdaptor(BIFunctionTypeAdaptor.class);
		List<FunctionType> functionTypes = functionTypeDelegate
				.getFunctionByParent(parentId);

		for (FunctionType ft : functionTypes) {
			List<FunctionType> children = getFunctionsByParent(repository, ft
					.getId());
			ft.setChildren(children);
		}
		return functionTypes;
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

		if (Utils.CATEGORY_TRANS.equals(category)) {
			return registry.getCategories(StepPluginType.class);
		} else if (Utils.CATEGORY_JOB.equals(category)) {
			return registry.getCategories(JobEntryPluginType.class);
		}

		return null;
	}

}
