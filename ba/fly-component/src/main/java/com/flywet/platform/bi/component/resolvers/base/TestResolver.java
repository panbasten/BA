package com.flywet.platform.bi.component.resolvers.base;

import java.util.List;

import org.pentaho.reporting.libraries.base.util.StringUtils;
import org.w3c.dom.Node;

import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.core.exception.BIPageException;

public class TestResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	public static final String ATTRIBUTE_VAR = "var";

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		String var = HTML.getTagAttribute(node, ATTRIBUTE_VAR, attrs);
		if (StringUtils.toBoolean(var)) {
			super.renderSub(node, html, script, attrs, fileUrl);
		}

	}
}
