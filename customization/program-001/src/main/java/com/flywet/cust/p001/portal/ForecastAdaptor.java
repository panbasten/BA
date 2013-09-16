package com.flywet.cust.p001.portal;

import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.delegates.model.BIAdaptorInterface;

public interface ForecastAdaptor extends BIAdaptorInterface {

	public String test() throws BIJSONException;

}
