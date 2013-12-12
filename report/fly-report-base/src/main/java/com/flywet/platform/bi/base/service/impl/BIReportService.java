package com.flywet.platform.bi.base.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.base.dao.intf.BIReportAdaptor;
import com.flywet.platform.bi.base.service.intf.BIReportDelegates;
import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.enums.BIDirectoryCategory;
import com.flywet.platform.bi.delegates.enums.BIReportCategory;
import com.flywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.flywet.platform.bi.services.impl.AbstractDirectoryServices;

@Service("bi.service.reportService")
public class BIReportService extends AbstractDirectoryServices implements
		BIReportDelegates {
	private final Logger log = Logger.getLogger(BIReportService.class);

	@Override
	public Object[] getReportObject(Long id) throws BIException {
		BIReportAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIReportAdaptor.class);

		Object[] report = adaptor.getReportObject(String.valueOf(id));
		// 如果是引用，再次查询 TODO

		if (report != null) {
			Object[] rtn = new Object[] { report[0], report[2] };
			return rtn;
		}

		return null;
	}

	public void saveReportObject(Long id, String xml) {
		BIReportAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIReportAdaptor.class);
		// TODO 如果id不存在，插入一条记录

	}

	@Override
	public void getSubDirectoryObject(Long id, BrowseMeta browse,
			BIDirectoryCategory cate) throws BIException {
		getSubDirectoryObjectForReport(id, browse);
	}

	private void getSubDirectoryObjectForReport(Long id, BrowseMeta browse)
			throws BIException {
		// 获得该子目录下面的报表对象
		BIReportAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIReportAdaptor.class);
		List<Object[]> rows = adaptor
				.getSubDirectoryObjects(String.valueOf(id));

		if (rows == null) {
			return;
		}

		for (Object[] r : rows) {
			String reportId = String.valueOf(r[0]);
			String category = BIReportCategory.getCategoryById(
					((Long) r[1]).intValue()).getCategory();

			String style = "ui-" + category + "-icon";
			if (Utils.toBoolean(String.valueOf(r[2]), false)) {
				style += "-ref";
			}

			BrowseNodeMeta node = subDirectoryObject(reportId, category, style,
					String.valueOf(r[3]));
			browse.addContent(node);
		}
	}

}
