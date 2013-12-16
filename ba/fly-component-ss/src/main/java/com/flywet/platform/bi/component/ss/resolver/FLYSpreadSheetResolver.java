package com.flywet.platform.bi.component.ss.resolver;

import java.util.List;

import org.pentaho.di.core.Const;
import org.w3c.dom.Node;

import com.flywet.platform.bi.component.anno.FLYComponent;
import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.flywet.platform.bi.component.ss.model.SpreadSheetMeta;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.core.exception.BIPageException;

@FLYComponent(fileName = "fly-spreadsheet.xml")
public class FLYSpreadSheetResolver extends BaseComponentResolver implements
		ComponentResolverInterface {
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			SpreadSheetMeta ss = (SpreadSheetMeta) HTML.getTagAttributeObject(
					node, HTML.ATTR_VALUE, attrs);
			if (ss == null) {
				ss = new SpreadSheetMeta();
			}
			String target = HTML.getTagAttribute(node, HTML.ATTR_TARGET, attrs);
			if (Const.isEmpty(target)) {
				target = HTML.getId(node, attrs);
				html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
				html.writeAttribute(HTML.ATTR_ID, target);
				html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
			}

			ss.setId(target);
			script.add("Flywet.cw(\"SpreadSheet\", \"" + target + "_var\", "
					+ ss.getFormJo().toJSONString() + ");");
		} catch (Exception e) {
			throw new BIPageException(e);
		}

	}
}
