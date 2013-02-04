package com.yonyou.bq8.di.component.resolvers.base;

import java.io.File;
import java.util.List;

import org.w3c.dom.Node;

import com.yonyou.bq8.di.component.core.ComponentResolverInterface;
import com.yonyou.bq8.di.component.resolvers.BaseComponentResolver;
import com.yonyou.bq8.di.component.utils.BQPageTemplateUtils;
import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.component.utils.HTMLWriter;
import com.yonyou.bq8.di.component.utils.PageTemplateInterpolator;
import com.yonyou.bq8.di.core.exception.DIPageException;

public class IncludeResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
		String src = HTML.getTagAttribute(node, HTML.ATTR_SRC, attrs);
		src = BQPageTemplateUtils.reletivePathCal(
				new File(fileUrl).getParent(), src);
		Object[] includeText = PageTemplateInterpolator.interpolate(src,
				script, attrs);
		html.writeText((String) includeText[0]);
	}
}
