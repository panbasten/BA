package com.flywet.platform.bi.component.resolvers.input;

import java.util.List;

import org.w3c.dom.Node;

import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.core.exception.BIPageException;

public class FLYComboTreeResolver extends BaseComponentResolver implements
		ComponentResolverInterface {
	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			html.startElement(HTML.COMPONENT_TYPE_BASE_INPUT);
			
			
			html.endElement(HTML.COMPONENT_TYPE_BASE_INPUT);
		} catch (Exception e) {
			throw new BIPageException("ComboTree解析出现错误。");
		}
	}
}
