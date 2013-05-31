package com.plywet.platform.bi.component.resolvers.base;

import java.util.List;

import org.w3c.dom.Node;

import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.HTMLWriter;
import com.plywet.platform.bi.core.exception.BIPageException;

public class TestResolver extends BaseComponentResolver implements
		ComponentResolverInterface {
	
	public static final String ATTRIBUTE_VAR = "var";
	
	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		String var = HTML.getTagAttribute(node, ATTRIBUTE_VAR, attrs);
		
		super.renderSub(node, html, script, attrs, fileUrl);
		
	}
}
