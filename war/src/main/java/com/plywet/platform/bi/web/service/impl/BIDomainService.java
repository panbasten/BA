package com.plywet.platform.bi.web.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.delegates.intf.BIDomainAdaptor;
import com.plywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.plywet.platform.bi.web.service.BIDomainDelegates;

@Service("bi.service.domainService")
public class BIDomainService implements BIDomainDelegates {
	private final Logger log = Logger.getLogger(BIDomainService.class);

	@Override
	public Object[] getDomainObject(Long id) throws BIException {
		BIDomainAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIDomainAdaptor.class);

		Object[] domain = adaptor.getDomainObject(String.valueOf(id));
		// 如果是引用，再次查询 TODO

		if (domain != null) {
			Object[] rtn = new Object[] { domain[0], domain[2] };
			return rtn;
		}

		return null;
	}
}
