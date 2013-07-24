package com.flywet.platform.bi.component.components.base;

import com.flywet.platform.bi.component.components.SimpleComponentMeta;
import com.flywet.platform.bi.component.utils.HTML;

/**
 * 间距设置功能
 * 
 *@author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public class SpanMeta extends SimpleComponentMeta {

	@Override
	public String[] getAttributesName() {
		return HTML.COMMON;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BASE_SPAN;
	}

}
