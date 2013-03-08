package com.yonyou.bq8.di.web.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.bq8.di.component.components.breadCrumb.BreadCrumbMeta;
import com.yonyou.bq8.di.component.components.browse.BrowseMeta;
import com.yonyou.bq8.di.component.components.browse.BrowseNodeMeta;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.core.utils.Utils;
import com.yonyou.bq8.di.delegates.intf.DIReportAdaptor;
import com.yonyou.bq8.di.delegates.utils.DIAdaptorFactory;
import com.yonyou.bq8.di.delegates.utils.DIReportCategory;
import com.yonyou.bq8.di.web.service.AbstractDirectoryServices;
import com.yonyou.bq8.di.web.service.DIReportDelegates;

@Service("di.service.reportService")
public class DIReportService extends AbstractDirectoryServices implements
		DIReportDelegates {
	private final Logger log = Logger.getLogger(DIFileSystemService.class);

	public final static String FILE_PATH_PREFIX = "/report/dir/";

	public final static long DIRECTORY_ROOT_ID_REPORT = 1L;

	@Override
	public BreadCrumbMeta getParentDirectories(String repository, Long id)
			throws DIException {
		return parentDirectories(repository, DIRECTORY_ROOT_ID_REPORT, id,
				"报表", FILE_PATH_PREFIX);
	}

	@Override
	public void getSubDirectory(String repository, Long id, BrowseMeta browse)
			throws DIException {
		subDirectory(repository, DIRECTORY_ROOT_ID_REPORT, id, browse,
				FILE_PATH_PREFIX);
	}

	@Override
	public void getSubDirectoryObject(String repository, Long id,
			BrowseMeta browse) throws DIException {
		// 获得该子目录下面的报表对象
		DIReportAdaptor adaptor = DIAdaptorFactory
				.createAdaptor(DIReportAdaptor.class);
		List<Object[]> rows = adaptor
				.getSubDirectoryObjects(String.valueOf(id));

		if (rows == null) {
			return;
		}

		for (Object[] r : rows) {
			BrowseNodeMeta node = new BrowseNodeMeta();
			String reportId = String.valueOf(r[0]);
			String category = DIReportCategory.getCategoryById(
					((Long) r[1]).intValue()).getCategory();
			node.setId(reportId);
			node.setCategory(category);
			node.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, String
					.valueOf(r[3]));
			String style = "ui-" + category + "-icon";
			if (Utils.toBoolean(String.valueOf(r[2]), false)) {
				style += "-ref";
			}

			node.addAttribute(BrowseNodeMeta.ATTR_ICON_STYLE, style);
			node.addAttribute(HTML.ATTR_TYPE, Utils.DOM_LEAF);
			node.addAttribute(HTML.ATTR_SRC, "report/" + category + "/"
					+ reportId);
			node.addEvent("mouseup", "YonYou.browse.showOperationForFile");
			node.addEvent("dblclick", "YonYou.browse.openFile");
			browse.addContent(node);
		}
	}

}
