package com.flywet.platform.bi.component.components.base;

import java.util.ArrayList;
import java.util.List;

import com.flywet.platform.bi.component.components.SimpleValidateComponentMeta;
import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;

/**
 * 列表框功能
 * 
 * @author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public class SelectMeta extends SimpleValidateComponentMeta implements
		ComponentMetaInterface {

	private List<OptionObject> options;

	@Override
	public String[] getAttributesName() {
		return HTML.SELECT_ATTRS;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BASE_SELECT;
	}

	public SelectMeta addOption(String value, String text, boolean selected) {
		if (options == null) {
			options = new ArrayList<OptionObject>();
		}
		OptionObject o = new OptionObject(value, text);
		o.setSelected(selected);
		options.add(o);
		return this;
	}

}
