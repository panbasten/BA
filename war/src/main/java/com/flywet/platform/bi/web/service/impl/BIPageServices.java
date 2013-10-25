package com.flywet.platform.bi.web.service.impl;

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
import org.pentaho.di.repository.RepositoryElementMetaInterface;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.pools.RepPool;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.enums.BIDirectoryCategory;
import com.flywet.platform.bi.delegates.enums.BIReportCategory;
import com.flywet.platform.bi.delegates.intf.BIDomainAdaptor;
import com.flywet.platform.bi.delegates.intf.BIFunctionTypeAdaptor;
import com.flywet.platform.bi.delegates.intf.BIReportAdaptor;
import com.flywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.flywet.platform.bi.delegates.vo.FunctionType;
import com.flywet.platform.bi.services.intf.BIPageDelegates;

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

	@Override
	public BreadCrumbMeta getParentDirectories(Long id,
			BIDirectoryCategory category) throws BIException {
		return parentDirectories(id, category.getDescription(), "/"
				+ category.getId() + "/dir/", category);
	}

	@Override
	public void getSubDirectory(Long id, BrowseMeta browse,
			BIDirectoryCategory category) throws BIException {
		subDirectory(id, browse, "/" + category.getId() + "/dir/", category);
	}

	@Override
	public void getSubDirectoryObject(Long id, BrowseMeta browse,
			BIDirectoryCategory category) throws BIException {
		// DI
		if (category == BIDirectoryCategory.DI) {
			getSubDirectoryObjectForDI(id, browse);
		}
		// REPORT
		else if (category == BIDirectoryCategory.REPORT) {
			getSubDirectoryObjectForReport(id, browse);
		}
		// DOMAIN
		else if (category == BIDirectoryCategory.DOMAIN) {
			getSubDirectoryObjectForDomain(id, browse);
		}
	}

	private void getSubDirectoryObjectForDomain(Long id, BrowseMeta browse)
			throws BIException {
		// 获得该子目录下面的报表对象
		BIDomainAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIDomainAdaptor.class);
		List<Object[]> rows = adaptor
				.getSubDirectoryObjects(String.valueOf(id));

		if (rows == null) {
			return;
		}

		for (Object[] r : rows) {
			BrowseNodeMeta node = new BrowseNodeMeta();
			String domainId = String.valueOf(r[0]);
			String category = BIReportCategory.getCategoryById(
					((Long) r[1]).intValue()).getCategory();
			node.setId(domainId);
			node.setCategory(category);
			node.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, String
					.valueOf(r[3]));
			String style = "ui-" + category + "-icon";
			if (Utils.toBoolean(String.valueOf(r[2]), false)) {
				style += "-ref";
			}

			node.addAttribute(BrowseNodeMeta.ATTR_ICON_STYLE, style);
			node.addAttribute(HTML.ATTR_TYPE, Utils.DOM_LEAF);
			node.addAttribute(HTML.ATTR_SRC, category + "/open/" + domainId);
			node.addEvent("mouseup", "Flywet.browse.showOperationForFile");
			node.addEvent("dblclick", "Flywet.browse.openFile");
			browse.addContent(node);
		}
	}

	private void getSubDirectoryObjectForReport(Long id, BrowseMeta browse)
			throws BIException {
		// 获得该子目录下面的报表对象
		BIReportAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIReportAdaptor.class);
		List<Object[]> rows = adaptor
				.getSubDirectoryObjects(String.valueOf(id));

		if (rows == null) {
			return;
		}

		for (Object[] r : rows) {
			BrowseNodeMeta node = new BrowseNodeMeta();
			String reportId = String.valueOf(r[0]);
			String category = BIReportCategory.getCategoryById(
					((Long) r[1]).intValue()).getCategory();
			node.setId(reportId);
			node.setCategory(category);
			node.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, String
					.valueOf(r[3]));
			String style = "ui-" + category + "-icon";
			if (Utils.toBoolean(String.valueOf(r[2]), false)) {
				style += "-ref";
			}

			node.addAttribute(BrowseNodeMeta.ATTR_ICON_STYLE, style);
			node.addAttribute(HTML.ATTR_TYPE, Utils.DOM_LEAF);
			node.addAttribute(HTML.ATTR_SRC, category + "/open/" + reportId);
			node.addEvent("mouseup", "Flywet.browse.showOperationForFile");
			node.addEvent("dblclick", "Flywet.browse.openFile");
			browse.addContent(node);
		}
	}

	private void getSubDirectoryObjectForDI(Long id, BrowseMeta browse)
			throws BIException {
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
				node.addEvent("mouseup", "Flywet.browse.showOperationForFile");
				node.addEvent("dblclick", "Flywet.browse.openFile");
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
	public List<FunctionType> getNavigatorsLevelOne() throws BIException {
		BIFunctionTypeAdaptor functionTypeDelegate = BIAdaptorFactory
				.createAdaptor(BIFunctionTypeAdaptor.class);
		List<FunctionType> functionTypes = functionTypeDelegate
				.getFunctionByParent(0L);

		return functionTypes;
	}

	@Override
	public List<FunctionType> getFunctionsByParent(long parentId)
			throws BIException {
		BIFunctionTypeAdaptor functionTypeDelegate = BIAdaptorFactory
				.createAdaptor(BIFunctionTypeAdaptor.class);
		List<FunctionType> functionTypes = functionTypeDelegate
				.getFunctionByParent(parentId);

		for (FunctionType ft : functionTypes) {
			List<FunctionType> children = getFunctionsByParent(ft.getId());
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

		if (Utils.CATEGORY_DI_TRANS.equals(category)) {
			return registry.getCategories(StepPluginType.class);
		} else if (Utils.CATEGORY_DI_JOB.equals(category)) {
			return registry.getCategories(JobEntryPluginType.class);
		}

		return null;
	}

	@Override
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

	@Override
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

	@Override
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
