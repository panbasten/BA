package com.yonyou.bq8.di.component.resolvers.layout;

import java.util.List;

import org.pentaho.pms.util.Const;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yonyou.bq8.di.component.core.ComponentResolverInterface;
import com.yonyou.bq8.di.component.resolvers.BaseComponentResolver;
import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.component.utils.HTMLWriter;
import com.yonyou.bq8.di.core.exception.DIPageException;

public class BQLayoutResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	public static final String ATTR_COLUMN = "column";

	public static final String ATTR_COLS = "cols";

	public static final String ATTR_ITEM_WIDTH = "itemWidth";

	public static final String LAYOUT_STYLE_CLASS = "ui-layout-div ui-helper-clearfix";

	public static final String LAYOUT_ITEM_STYLE_CLASS = "ui-layout-float ui-helper-clearfix";

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
		html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
		String styleClass = HTML.getTagAttribute(node, HTML.ATTR_CLASS, attrs);
		styleClass = (styleClass != null) ? LAYOUT_STYLE_CLASS + " "
				+ styleClass : LAYOUT_STYLE_CLASS;
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
			BQVariableResolver attrs, String fileUrl, int column,
			int[] itemWidth) throws DIPageException {
		NodeList nodeList = node.getChildNodes();
		int index = 0;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			if (HTML.COMPONENT_TYPE_LAYOUT_ITEM.equalsIgnoreCase(subNode
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
				html.writeAttribute(HTML.ATTR_CLASS, LAYOUT_ITEM_STYLE_CLASS);

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
