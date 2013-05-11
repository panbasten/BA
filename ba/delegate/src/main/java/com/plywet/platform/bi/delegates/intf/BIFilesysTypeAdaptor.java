package com.plywet.platform.bi.delegates.intf;

import java.util.List;

import com.plywet.platform.bi.delegates.exceptions.BIKettleException;
import com.plywet.platform.bi.delegates.model.BIAdaptorInterface;

public interface BIFilesysTypeAdaptor extends BIAdaptorInterface {
	public List<Object[]> getAllFilesysType() throws BIKettleException;
}
