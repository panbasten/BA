package com.yonyou.bq8.di.component.resolvers.base;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;

import com.yonyou.bq8.di.component.core.ComponentResolverInterface;
import com.yonyou.bq8.di.component.resolvers.BaseComponentResolver;
import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.component.utils.HTMLWriter;
import com.yonyou.bq8.di.core.exception.DIPageException;

public class ForEachResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	public static final String ATTRIBUTE_ITEMS = "items";
	public static final String ATTRIBUTE_VAR = "var";
	public static final String ATTRIBUTE_INDEX_VAR = "indexVar";
	public static final String ATTRIBUTE_SIZE_VAR = "sizeVar";

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
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
