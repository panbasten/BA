package com.plywet.platform.bi.component.resolvers.base;

import java.util.List;

import org.drools.util.StringUtils;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.HTMLWriter;
import com.plywet.platform.bi.component.utils.PageTemplateResolver;
import com.plywet.platform.bi.core.exception.BIPageException;

public class CompositionResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	/**
	 * 对于构建下面的子元素，添加position:absolute属性
	 */
	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		NodeList nodeList = node.getChildNodes();

		String freeLayout = XMLUtils.getTagOrAttribute(node,
				HTML.ATTR_FREE_LAYOUT);

		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node subNode = nodeList.item(i);
				if (!XMLUtils.isTextNode(subNode)) {
					if (StringUtils.isEmpty(XMLUtils.getTagOrAttribute(subNode,
							HTML.ATTR_FREE_LAYOUT))) {
						if (!StringUtils.isEmpty(freeLayout)) {
							XMLUtils.setAttribute(subNode,
									HTML.ATTR_FREE_LAYOUT, freeLayout);
						}
					}

				}
				PageTemplateResolver.resolverNode(subNode, html, script, attrs,
						fileUrl);
			}
		}
		if (node.getNodeValue() != null) {
			html.writeText(Const.removeTAB(Const
					.removeCRLF(node.getNodeValue())));
		}
	}

}
