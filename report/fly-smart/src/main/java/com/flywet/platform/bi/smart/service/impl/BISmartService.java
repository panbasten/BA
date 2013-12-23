package com.flywet.platform.bi.smart.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flywet.platform.bi.base.enums.BIReportCategory;
import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.component.components.browse.BrowseNodeMeta;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.enums.BIDirectoryCategory;
import com.flywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.flywet.platform.bi.services.impl.AbstractDirectoryServices;
import com.flywet.platform.bi.smart.cache.SmartCache;
import com.flywet.platform.bi.smart.dao.intf.BISmartAdaptor;
import com.flywet.platform.bi.smart.model.BISmartMeta;
import com.flywet.platform.bi.smart.service.intf.BISmartDelegates;

@Service("bi.service.smartService")
public class BISmartService extends AbstractDirectoryServices implements
		BISmartDelegates {

	@Override
	public BISmartMeta getSmartObject(Long id) throws BIException {

		BISmartMeta sm = SmartCache.get(id);

		if (sm == null) {
			BISmartAdaptor adaptor = BIAdaptorFactory
					.createAdaptor(BISmartAdaptor.class);

			Object[] smart = adaptor.getSmartObject(String.valueOf(id));

			if (smart != null) {
				sm = new BISmartMeta(smart);
				SmartCache.put(id, sm);
			}

		}

		return sm;
	}

	@Override
	public void getSubDirectoryObject(Long id, BrowseMeta browse,
			BIDirectoryCategory cate) throws BIException {
		// 获得该子目录下面的报表对象
		BISmartAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BISmartAdaptor.class);
		List<Object[]> rows = adaptor
				.getSubDirectoryObjects(String.valueOf(id));

		if (rows == null) {
			return;
		}

		for (Object[] r : rows) {
			BrowseNodeMeta node = new BrowseNodeMeta();
			String smId = String.valueOf(r[0]);
			String category = BIReportCategory.getCategoryById(
					((Long) r[1]).intValue()).getCategory();
			node.setId(smId);
			node.setCategory(category);
			node.addAttribute(BrowseNodeMeta.ATTR_DISPLAY_NAME, String
					.valueOf(r[2]));
			String style = "ui-" + category + "-icon";

			node.addAttribute(BrowseNodeMeta.ATTR_ICON_STYLE, style);
			node.addAttribute(HTML.ATTR_TYPE, Utils.DOM_LEAF);
			node.addAttribute(HTML.ATTR_SRC, category + "/open/" + smId);
			node.addEvent("mouseup", "Flywet.browse.showOperationForFile");
			node.addEvent("dblclick", "Flywet.browse.openFile");
			browse.addContent(node);
		}
	}

}
