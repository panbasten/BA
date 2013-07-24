package com.flywet.platform.bi.component.components.combo;

import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;

public class ComboTreeMeta extends ComboMeta implements ComponentMetaInterface {
	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_COMBO_TREE;
	}
}
