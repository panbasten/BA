package com.flywet.platform.bi.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.pentaho.di.core.plugins.JobEntryPluginType;
import org.pentaho.di.core.plugins.PluginInterface;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.intf.BIFunctionTypeAdaptor;
import com.flywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.flywet.platform.bi.delegates.vo.FunctionType;
import com.flywet.platform.bi.services.intf.BIFunctionDelegates;

/**
 * 元数据服务层接口实现类
 * 
 * @author Peter Pan
 * 
 */
@Service("bi.service.funcServices")
public class BIFunctionServices implements BIFunctionDelegates {

	private final Logger log = Logger.getLogger(BIFunctionServices.class);

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

}
