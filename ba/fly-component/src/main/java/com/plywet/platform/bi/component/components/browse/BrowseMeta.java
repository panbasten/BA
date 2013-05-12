package com.plywet.platform.bi.component.components.browse;

import com.plywet.platform.bi.component.components.ComplexComponentMeta;
import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;

public class BrowseMeta extends ComplexComponentMeta implements
		ComponentMetaInterface {

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BROWSE;
	}

}
