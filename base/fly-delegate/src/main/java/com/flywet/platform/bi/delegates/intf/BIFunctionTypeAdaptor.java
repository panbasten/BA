package com.flywet.platform.bi.delegates.intf;

import java.util.List;

import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.delegates.model.BIAdaptorInterface;
import com.flywet.platform.bi.delegates.vo.FunctionType;

public interface BIFunctionTypeAdaptor extends BIAdaptorInterface {
	public List<FunctionType> getFunctionByParent(long id)
			throws BIKettleException;
}
