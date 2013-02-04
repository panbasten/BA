package com.yonyou.bq8.di.delegates.intf;

import java.util.List;

import com.yonyou.bq8.di.delegates.model.DIAdaptorInterface;
import com.yonyou.bq8.di.delegates.vo.FunctionType;
import com.yonyou.bq8.di.exceptions.DIKettleException;

public interface DIFunctionTypeAdaptor extends DIAdaptorInterface {
	public List<FunctionType> getFunctionByParent(long id) throws DIKettleException;
}
