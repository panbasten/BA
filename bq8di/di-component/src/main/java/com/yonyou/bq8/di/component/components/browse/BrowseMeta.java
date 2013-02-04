package com.yonyou.bq8.di.component.components.browse;

import com.yonyou.bq8.di.component.components.ComplexComponentMeta;
import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;

public class BrowseMeta extends ComplexComponentMeta implements
		ComponentMetaInterface {

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BROWSE;
	}

}
