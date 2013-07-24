package com.flywet.platform.bi.component.components.base;

import com.flywet.platform.bi.component.components.SimpleValidateComponentMeta;
import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;

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
