package com.flywet.platform.bi.component.components.breadCrumb;

import com.flywet.platform.bi.component.components.ComplexComponentMeta;
import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;

/**
 * 面包屑JSON实体
 * 
 * @author PeterPan
 * 
 */
public class BreadCrumbMeta extends ComplexComponentMeta implements
		ComponentMetaInterface {
	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BREAD_CRUMB;
	}
}
