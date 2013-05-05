package com.plywet.platform.bi.delegates.intf;

import java.util.List;

import com.plywet.platform.bi.exceptions.BIKettleException;

public interface BIReportAdaptor {
	public List<Object[]> getSubDirectoryObjects(String dirId)
			throws BIKettleException;
}
