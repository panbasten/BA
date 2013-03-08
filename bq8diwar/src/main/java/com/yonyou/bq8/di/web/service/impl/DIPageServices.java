package com.yonyou.bq8.di.web.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.plugins.JobEntryPluginType;
import org.pentaho.di.core.plugins.PluginInterface;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositoryElementInterface;
import org.pentaho.di.repository.RepositoryElementMetaInterface;
import org.pentaho.di.trans.TransMeta;
import org.springframework.stereotype.Service;

import com.yonyou.bq8.di.component.components.breadCrumb.BreadCrumbMeta;
import com.yonyou.bq8.di.component.components.breadCrumb.BreadCrumbNodeMeta;
import com.yonyou.bq8.di.component.components.browse.BrowseMeta;
import com.yonyou.bq8.di.component.components.browse.BrowseNodeMeta;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.core.exception.DIJSONException;
import com.yonyou.bq8.di.core.utils.Utils;
import com.yonyou.bq8.di.delegates.DIEnvironmentDelegate;
import com.yonyou.bq8.di.delegates.intf.DIFunctionTypeAdaptor;
import com.yonyou.bq8.di.delegates.utils.DIAdaptorFactory;
import com.yonyou.bq8.di.delegates.vo.FunctionType;
import com.yonyou.bq8.di.exceptions.DIKettleException;
import com.yonyou.bq8.di.web.service.AbstractDirectoryServices;
import com.yonyou.bq8.di.web.service.DIPageDelegates;
import com.yonyou.bq8.di.web.service.TransOrJobMetaCache;

/**
 * 元数据服务层接口实现类
 * 
 * @author Peter Pan
 * 
 */
@Service("di.service.pageServices")
public class DIPageServices extends AbstractDirectoryServices implements
		DIPageDelegates {

	private final Logger log = Logger.getLogger(DIPageServices.class);

	private static final String ICON_PATH = "resources/images/entities/";

	@Override
	public BreadCrumbMeta getParentDirectories(String repository, Long id)
			throws DIException {
		return parentDirectories(repository, id, "转换/作业", "/transjob/dir/");
	}

	@Override
	public void getSubDirectory(String repository, Long id, BrowseMeta browse)
			throws DIException {
		subDirectory(repository, id, browse, "transjob/dir/");
	}

	@Override
	public void getSubDirectoryObject(String repository, String category,
			Long id, BrowseMeta browse) throws DIException {
		Repository rep = null;
		try {
			rep = DIEnvironmentDelegate.instance().borrowRep(repository, null);

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
				node.addAttribute(HTML.ATTR_SRC, "transjob/" + category + "/"
						+ sub.getObjectId().getId());
				node.addEvent("mouseup", "YonYou.browse.showOperationForFile");
				node.addEvent("dblclick", "YonYou.browse.openFile");
				browse.addContent(node);
			}

		} catch (KettleException e) {
			log.error("创建目录下面的子对象页面出现错误。");
			throw new DIException("创建目录下面的子对象页面出现错误。");
		} finally {
			DIEnvironmentDelegate.instance().returnRep(repository, rep);
		}

	}

	@Override
	public List<FunctionType> getNavigatorsLevelOne(String repository)
			throws DIException {
		DIFunctionTypeAdaptor functionTypeDelegate = DIAdaptorFactory
				.createAdaptor(DIFunctionTypeAdaptor.class);
		List<FunctionType> functionTypes = functionTypeDelegate
				.getFunctionByParent(0L);
		return functionTypes;
	}

	@Override
	public List<FunctionType> getFunctionsByParent(String repository,
			long parentId) throws DIException {
		DIFunctionTypeAdaptor functionTypeDelegate = DIAdaptorFactory
				.createAdaptor(DIFunctionTypeAdaptor.class);
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
			throws DIJSONException {
		getPlugins(category, browseMeta, null);
	}

	@Override
	public void getPlugins(String category, BrowseMeta browseMeta,
			String nodeClass) throws DIJSONException {
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

	/**
	 * 保存资源库对象，包括：RepositoryObjectType枚举类型中的
	 * 
	 * @param repository
	 * @param repositoryElement
	 * @throws DIKettleException
	 */
	@Override
	public void save(String repository,
			RepositoryElementInterface repositoryElement)
			throws DIKettleException {
		Repository rep = null;
		try {
			rep = DIEnvironmentDelegate.instance().borrowRep(repository, null);
			rep.save(repositoryElement, null, null);
		} catch (Exception ex) {
			log.error("通过ID获得转换出现异常", ex);
		} finally {
			DIEnvironmentDelegate.instance().returnRep(repository, rep);
		}
	}

	/**
	 * 通过ID获得转换
	 * 
	 * @param id
	 * @throws DIKettleException
	 */
	@Override
	public TransMeta loadTransformation(String repository, Long id)
			throws DIKettleException {
		TransMeta transMeta = TransOrJobMetaCache.getTrans(repository, id);
		if (transMeta == null) {
			Repository rep = null;
			try {
				rep = DIEnvironmentDelegate.instance().borrowRep(repository,
						null);
				transMeta = rep.loadTransformation(new LongObjectId(id), null);
				TransOrJobMetaCache.putTrans(repository, id, transMeta);
			} catch (Exception ex) {
				log.error("通过ID获得转换出现异常", ex);
			} finally {
				DIEnvironmentDelegate.instance().returnRep(repository, rep);
			}
		}
		return transMeta;
	}

	/**
	 * 通过ID清除缓存
	 * 
	 * @param id
	 * @throws DIKettleException
	 */
	@Override
	public TransMeta clearCacheTransformation(String repository, Long id) {
		return TransOrJobMetaCache.clearTrans(repository, id);
	}

	/**
	 * 更新转换
	 * 
	 * @param repository
	 * @param id
	 * @param transMeta
	 */
	@Override
	public void updateCacheTransformation(String repository, Long id,
			TransMeta transMeta) {
		TransOrJobMetaCache.putTrans(repository, id, transMeta);
	}

}
