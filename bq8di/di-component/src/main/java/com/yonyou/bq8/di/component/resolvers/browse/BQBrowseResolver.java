package com.yonyou.bq8.di.component.resolvers.browse;

import java.util.List;

import org.pentaho.di.core.Const;
import org.w3c.dom.Node;

import com.yonyou.bq8.di.component.components.browse.BrowseMeta;
import com.yonyou.bq8.di.component.core.ComponentResolverInterface;
import com.yonyou.bq8.di.component.resolvers.BaseComponentResolver;
import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;
import com.yonyou.bq8.di.core.exception.DIPageException;

public class BQBrowseResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	public void renderScript(Node node, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
		try {
			BrowseMeta browse = (BrowseMeta) HTML.getTagAttributeObject(node,
					HTML.ATTR_VALUE, attrs);
			String target = HTML.getTagAttribute(node, HTML.ATTR_TARGET, attrs);
			if (!Const.isEmpty(target)) {
				browse.setId(target);
				script.add("YonYou.cw(\"BrowsePanel\", \"" + target + "_var\", "
						+ browse.getFormJo().toJSONString() + ");");
			}
		} catch (DIJSONException e) {
			throw new DIPageException(e);
		}

	}

}
