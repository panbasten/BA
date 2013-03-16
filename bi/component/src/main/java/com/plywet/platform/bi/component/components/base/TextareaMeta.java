package com.plywet.platform.bi.component.components.base;

import com.plywet.platform.bi.component.components.SimpleValidateComponentMeta;
import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;

/**
 * 文本域功能
 * 
 *@author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public class TextareaMeta extends SimpleValidateComponentMeta implements
		ComponentMetaInterface {

	@Override
	public String[] getAttributesName() {
		return HTML.INPUT_TEXTAREA_ATTRS;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BASE_TEXTAREA;
	}

}
