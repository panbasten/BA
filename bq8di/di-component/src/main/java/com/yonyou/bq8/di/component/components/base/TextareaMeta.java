package com.yonyou.bq8.di.component.components.base;

import com.yonyou.bq8.di.component.components.SimpleValidateComponentMeta;
import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;

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
