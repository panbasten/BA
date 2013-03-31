package com.plywet.platform.bi.component.resolvers.layout;

import java.util.List;

import org.pentaho.pms.util.Const;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.HTMLWriter;
import com.plywet.platform.bi.core.exception.BIPageException;

public class FLYGridLayoutResolver extends BaseComponentResolver implements
		ComponentResolverInterface {
	
	public static final String ATTR_COLUMN = "column";

	public static final String ATTR_COLS = "cols";

	public static final String ATTR_ITEM_WIDTH = "itemWidth";

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
		String styleClass = HTML.getTagAttribute(node, HTML.ATTR_CLASS, attrs);
		styleClass = (styleClass != null) ? HTML.LAYOUT_CLASS + " "
				+ styleClass : HTML.LAYOUT_CLASS;
		html.writeAttribute(HTML.ATTR_CLASS, styleClass);

		String columnStr = HTML.getTagAttribute(node, ATTR_COLUMN, attrs);
		String itemWidthStr = HTML
				.getTagAttribute(node, ATTR_ITEM_WIDTH, attrs);
		int column = 1;
		if (columnStr != null) {
			column = Integer.valueOf(columnStr);
		}
		int[] itemWidth;
		if (itemWidthStr != null) {
			String[] itemWidthStrArr = itemWidthStr.split(",");
			itemWidth = new int[itemWidthStrArr.length];
			for (int i = 0; i < itemWidthStrArr.length; i++) {
				if (itemWidthStrArr[i].endsWith("%")) {
					itemWidth[i] = Integer.valueOf(itemWidthStrArr[i]
							.substring(0, itemWidthStrArr[i].length() - 1));
				} else {
					itemWidth[i] = Integer.valueOf(itemWidthStrArr[i]);
				}
			}
		} else {
			int per = (int) Math.floor(100 / column);
			itemWidth = new int[] { per };
		}

		renderShowWithStyle(node, html, attrs);

		HTML.getAttributesString(node.getAttributes(), new String[] {
				HTML.ATTR_CLASS, HTML.ATTR_STYLE, ATTR_COLUMN, ATTR_ITEM_WIDTH,
				ATTR_SHOW }, html, attrs);

		renderItems(node, html, script, attrs, fileUrl, column, itemWidth);

		html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
	}

	private void renderItems(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl, int column,
			int[] itemWidth) throws BIPageException {
		NodeList nodeList = node.getChildNodes();
		int index = 0;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			if (HTML.COMPONENT_TYPE_GRID_LAYOUT_ITEM.equalsIgnoreCase(subNode
					.getNodeName())) {
				int cols = 1, w = 0;
				String colsStr = HTML
						.getTagAttribute(subNode, ATTR_COLS, attrs);
				if (colsStr != null) {
					cols = Integer.valueOf(colsStr);
				}
				for (int c = 0; c < cols; c++) {
					w += getWidth(index, column, itemWidth);
					index++;
				}

				html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
				html.writeAttribute(HTML.ATTR_CLASS, HTML.LAYOUT_ITEM_CLASS);

				String style = Const.NVL(HTML.getTagAttribute(subNode,
						HTML.ATTR_STYLE, attrs), "");
				style = "width:" + w + "%;" + style;
				style = getShowStyle(subNode, html, attrs) + style;
				html.writeAttribute(HTML.ATTR_STYLE, style);

				HTML.getAttributesString(subNode.getAttributes(),
						new String[] { HTML.ATTR_CLASS, HTML.ATTR_STYLE,
								ATTR_SHOW, ATTR_COLS }, html, attrs);
				super.renderSub(subNode, html, script, attrs, fileUrl);
				html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
			}
		}
	}

	private int getWidth(int index, int column, int[] itemWidth) {
		int wIdx = index % column;
		wIdx = (wIdx > itemWidth.length - 1) ? (itemWidth.length - 1) : wIdx;
		return itemWidth[wIdx];
	}

}
