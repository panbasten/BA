package com.plywet.platform.bi.component.components.base;

import com.plywet.platform.bi.component.components.SimpleComponentMeta;
import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;

/**
 * BrMeta
 * 
 *@author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public class BrMeta extends SimpleComponentMeta implements
		ComponentMetaInterface {

	@Override
	public String[] getAttributesName() {
		return HTML.COMMON;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BASE_BR;
	}

}
