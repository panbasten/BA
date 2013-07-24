package com.flywet.platform.bi.delegates.intf;

import java.util.List;

import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.model.BIAdaptorInterface;

public interface BIFilesysTypeAdaptor extends BIAdaptorInterface {
	public List<Object[]> getAllFilesysType() throws BIKettleException;
}
