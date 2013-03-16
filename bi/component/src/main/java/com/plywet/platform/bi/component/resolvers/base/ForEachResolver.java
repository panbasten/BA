package com.plywet.platform.bi.component.resolvers.base;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;

import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.HTMLWriter;
import com.plywet.platform.bi.core.exception.BIPageException;

public class ForEachResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	public static final String ATTRIBUTE_ITEMS = "items";
	public static final String ATTRIBUTE_VAR = "var";
	public static final String ATTRIBUTE_INDEX_VAR = "indexVar";
	public static final String ATTRIBUTE_SIZE_VAR = "sizeVar";

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		String varStr = HTML.getTagAttribute(node, ATTRIBUTE_VAR, attrs);
		String indexVarStr = HTML.getTagAttribute(node, ATTRIBUTE_INDEX_VAR,
				attrs);
		String sizeVarStr = HTML.getTagAttribute(node, ATTRIBUTE_SIZE_VAR,
				attrs);
		Collection<?> items = (Collection<?>) HTML.getTagAttributeObject(node,
				ATTRIBUTE_ITEMS, attrs);
		if (items != null && items.size() > 0) {
			if (sizeVarStr != null) {
				attrs.addVariable(sizeVarStr, items.size());
			}
			int index = 0;
			for (Iterator<?> itet = items.iterator(); itet.hasNext();) {
				attrs.addVariable(varStr, itet.next());
				if (indexVarStr != null) {
					attrs.addVariable(indexVarStr, index);
				}
				index++;
				super.renderSub(node, html, script, attrs, fileUrl);
			}
		}
	}

}
