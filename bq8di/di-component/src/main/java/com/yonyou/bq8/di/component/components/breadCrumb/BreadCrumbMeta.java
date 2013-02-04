package com.yonyou.bq8.di.component.components.breadCrumb;

import com.yonyou.bq8.di.component.components.ComplexComponentMeta;
import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;

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
