package com.plywet.platform.bi.component.components.base;

import com.plywet.platform.bi.component.components.SimpleComponentMeta;
import com.plywet.platform.bi.component.utils.HTML;

/**
 * 图例设置
 * 
 *@author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public class LegendMeta extends SimpleComponentMeta {

	@Override
	public String[] getAttributesName() {
		return HTML.LEGEND_ATTRS;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BASE_LEGEND;
	}

}
