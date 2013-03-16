package com.plywet.platform.bi.component.components.combo;

import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;

public class ComboTreeMeta extends ComboMeta implements ComponentMetaInterface {
	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_COMBO_TREE;
	}
}
