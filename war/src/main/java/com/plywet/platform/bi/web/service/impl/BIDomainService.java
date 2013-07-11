package com.plywet.platform.bi.web.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.plywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.delegates.enums.BIReportCategory;
import com.plywet.platform.bi.delegates.intf.BIDomainAdaptor;
import com.plywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.plywet.platform.bi.web.service.BIDomainDelegates;

@Service("bi.service.domainService")
public class BIDomainService extends AbstractDirectoryServices implements
		BIDomainDelegates {
	private final Logger log = Logger.getLogger(BIDomainService.class);

	public final static String FILE_PATH_PREFIX = "/domain/dir/";

	public final static long DIRECTORY_ROOT_ID_DOMAIN = 2L;

	@Override
	public Long getRootDirectoryId() {
		return DIRECTORY_ROOT_ID_DOMAIN;
	}

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

	@Override
	public BreadCrumbMeta getParentDirectories(String repository, Long id)
			throws BIException {
		return parentDirectories(repository, id, "模型", FILE_PATH_PREFIX);
	}

	@Override
	public void getSubDirectory(String repository, Long id, BrowseMeta browse)
			throws BIException {
		subDirectory(repository, id, browse, FILE_PATH_PREFIX);
	}

	@Override
	public void getSubDirectoryObject(String repository, Long id,
			BrowseMeta browse) throws BIException {
		// 获得该子目录下面的报表对象
		BIDomainAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIDomainAdaptor.class);
		List<Object[]> rows = adaptor
				.getSubDirectoryObjects(String.valueOf(id));

		if (rows == null) {
			return;
		}

		for (Object[] r : rows) {
			BrowseNodeMeta node = new BrowseNodeMeta();
			String reportId = String.valueOf(r[0]);
			String category = BIReportCategory.getCategoryById(
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
			node.addAttribute(HTML.ATTR_SRC, category + "/open/" + reportId);
			node.addEvent("mouseup", "Plywet.browse.showOperationForFile");
			node.addEvent("dblclick", "Plywet.browse.openFile");
			browse.addContent(node);
		}
	}
}
