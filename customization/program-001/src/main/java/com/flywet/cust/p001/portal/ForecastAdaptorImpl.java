package com.flywet.cust.p001.portal;

import com.flywet.platform.bi.component.web.ActionMessage;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;

public class ForecastAdaptorImpl extends BIAbstractDbAdaptor implements
		ForecastAdaptor {

	@Override
	public String test() throws BIJSONException {
		return ActionMessage.instance().failure("打开Portal的菜单出现错误。")
				.toJSONString();
	}

}
