package com.yonyou.bq8.di.component.components.combo;

import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;

public class ComboTreeMeta extends ComboMeta implements ComponentMetaInterface {
	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_COMBO_TREE;
	}
}
