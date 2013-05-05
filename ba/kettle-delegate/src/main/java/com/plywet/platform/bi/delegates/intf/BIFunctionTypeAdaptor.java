package com.plywet.platform.bi.delegates.intf;

import java.util.List;

import com.plywet.platform.bi.delegates.model.BIAdaptorInterface;
import com.plywet.platform.bi.delegates.vo.FunctionType;
import com.plywet.platform.bi.exceptions.BIKettleException;

public interface BIFunctionTypeAdaptor extends BIAdaptorInterface {
	public List<FunctionType> getFunctionByParent(long id)
			throws BIKettleException;
}
