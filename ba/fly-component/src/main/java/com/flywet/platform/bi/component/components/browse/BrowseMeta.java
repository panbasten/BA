package com.flywet.platform.bi.component.components.browse;

import com.flywet.platform.bi.component.components.ComplexComponentMeta;
import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;

public class BrowseMeta extends ComplexComponentMeta implements
		ComponentMetaInterface {

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BROWSE;
	}

}
