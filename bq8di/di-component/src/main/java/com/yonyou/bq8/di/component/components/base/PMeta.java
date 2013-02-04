package com.yonyou.bq8.di.component.components.base;

import com.yonyou.bq8.di.component.components.SimpleComponentMeta;
import com.yonyou.bq8.di.component.utils.HTML;

/**
 * PMeta
 * 
 *@author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public class PMeta extends SimpleComponentMeta {

	@Override
	public String[] getAttributesName() {
		return HTML.COMMON;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BASE_P;
	}

}
