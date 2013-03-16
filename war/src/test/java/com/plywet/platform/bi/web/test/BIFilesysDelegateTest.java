package com.plywet.platform.bi.web.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.delegates.vo.FilesysType;
import com.plywet.platform.bi.web.service.BIFileSystemDelegate;

public class BIFilesysDelegateTest extends WebBaseTestcase {
	@Test
	public void testGetFilesysTypes() {
		BIFileSystemDelegate filesysService = (BIFileSystemDelegate) ctx
				.getBean("bi.service.filesystemService");
		Assert.assertNotNull(filesysService);

		List<FilesysType> filesysTypes = null;
		try {
			filesysTypes = filesysService.getFilesysTypes();
		} catch (BIException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		Assert.assertNotNull(filesysTypes);
	}
}
