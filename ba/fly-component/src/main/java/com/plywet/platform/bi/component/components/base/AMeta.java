package com.plywet.platform.bi.component.components.base;

import com.plywet.platform.bi.component.components.SimpleComponentMeta;
import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;

/**
 * 基本数据类
 * 
 * @author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public class AMeta extends SimpleComponentMeta implements
		ComponentMetaInterface {

	@Override
	public String[] getAttributesName() {
		return HTML.LINK_ATTRS;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BASE_A;
	}

}
