package com.flywet.platform.bi.pivot.service.impl;

import org.pentaho.di.core.database.DatabaseMeta;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.utils.BIWebUtils;
import com.flywet.platform.bi.pivot.service.intf.BIPivotDelegates;
import com.flywet.platform.bi.pivot.utils.MondrianOlapHelper;
import com.flywet.platform.bi.services.intf.BIDatabaseDelegates;

@Service("bi.service.pivotService")
public class BIPivotService implements BIPivotDelegates {

	/**
	 * 执行一个Mdx语句
	 */
	@Override
	public void queryMdx() throws BIException {
		String mdx = "select"
				+ "{[Measures].[Unit Sales], [Measures].[Store Cost], [Measures].[Store Sales]} on columns,"
				+ "{([Promotion Media].[All Media], [Product].[All Products])} ON rows"
				+ "from Sales" + "where ([Time].[1997])";
		BIDatabaseDelegates dbDelegates = BIWebUtils
				.getBean("bi.service.databaseServices");
		DatabaseMeta dbMeta = dbDelegates.getDatabaseMeta(5L);
		MondrianOlapHelper helper = MondrianOlapHelper.instance(dbMeta, null,
				mdx);
		
		helper.openConnection();
		helper.query();
		helper.close();
	}

}
