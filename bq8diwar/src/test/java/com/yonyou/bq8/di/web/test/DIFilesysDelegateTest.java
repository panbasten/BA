package com.yonyou.bq8.di.web.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.delegates.vo.FilesysType;
import com.yonyou.bq8.di.web.service.DIFileSystemDelegate;

public class DIFilesysDelegateTest extends WebBaseTestcase {
	@Test
	public void testGetFilesysTypes() {
		DIFileSystemDelegate filesysService = (DIFileSystemDelegate)ctx.getBean("di.service.filesystemService");
		Assert.assertNotNull(filesysService);
		
		List<FilesysType> filesysTypes = null;
		try {
			filesysTypes = filesysService.getFilesysTypes();
		} catch (DIException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		Assert.assertNotNull(filesysTypes);
	}
}
