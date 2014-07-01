package com.flywet.platform.bi.component.resolvers.button;

import java.util.List;

import org.pentaho.di.core.Const;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.flywet.platform.bi.component.components.menu.MenuItemMeta;
import com.flywet.platform.bi.component.components.menu.MenuItemsMeta;
import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.exception.BIPageException;
import com.flywet.platform.bi.core.utils.Utils;

public class FLYPushButtonResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	public static final String BUTTON_TYPE_BUTTON = "button";
	public static final String BUTTON_TYPE_SEPARATOR = "separator";

	public static final String ATTR_MENU_ID = "menuId";
	public static final String ATTR_BTN_STYLE = "btnStyle";
	public static final String ATTR_BTN_SIZE = "btnSize";

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			renderButtonType(node, html, script, attrs);
		} catch (BIException e) {
			throw new BIPageException(e);
		}
	}

	private String resolveStyleClass(Node node, FLYVariableResolver attrs,
			boolean isMenu) throws BIPageException {

		String state = HTML.getTagAttribute(node, HTML.ATTR_STATE, attrs);
		String btnStyle = Const.NVL(
				HTML.getTagAttribute(node, ATTR_BTN_STYLE, attrs), "default");
		String userClass = HTML.getTagAttribute(node, HTML.ATTR_CLASS, attrs);
		String styleClass = "";

		if (HTML.ATTR_STATE_DISABLED.equalsIgnoreCase(state)) {
			styleClass = styleClass + " disabled";
		} else if (HTML.ATTR_STATE_ACTIVE.equalsIgnoreCase(state)) {
			styleClass = styleClass + " active";
		}

		styleClass = styleClass + " btn btn-" + btnStyle;

		String btnSize = HTML.getTagAttribute(node, ATTR_BTN_SIZE, attrs);
		if ("large".equalsIgnoreCase(btnSize)) {
			styleClass = styleClass + " btn btn-lg";
		} else if ("small".equalsIgnoreCase(btnSize)) {
			styleClass = styleClass + " btn btn-sm";
		} else if ("mini".equalsIgnoreCase(btnSize)) {
			styleClass = styleClass + " btn btn-xs";
		}

		if (userClass != null) {
			styleClass = styleClass + " " + userClass;
		}

		return styleClass;

	}

	private void renderButtonType(Node node, HTMLWriter html,
			List<String> script, FLYVariableResolver attrs)
			throws BIPageException, BIJSONException {
		// 获取menuItem
		NodeList nodeList = node.getChildNodes();
		MenuItemsMeta menuItems = new MenuItemsMeta();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			if (HTML.COMPONENT_TYPE_MENU_ITEM.equalsIgnoreCase(subNode
					.getNodeName())) {
				MenuItemMeta item = new MenuItemMeta();
				item.setText(
						HTML.getTagAttribute(subNode, HTML.ATTR_TEXT, attrs))
						.setIconCls(
								HTML.getTagAttribute(subNode,
										HTML.ATTR_ICON_CLASS, attrs))
						.setOnClick(
								HTML.getTagAttribute(subNode,
										HTML.ATTR_ON_CLICK, attrs));
				menuItems.addContent(item);
			}
		}

		String menuId = "";
		boolean isMenu = (menuItems.size() > 0);
		if (isMenu) {
			menuId = "menu-" + HTML.getId(node, attrs);
			menuItems.setId(menuId);
		}

		html.startElement(HTML.COMPONENT_TYPE_BASE_BUTTON);
		html.writeAttribute(HTML.ATTR_TYPE, BUTTON_TYPE_BUTTON);

		String state = HTML.getTagAttribute(node, HTML.ATTR_STATE, attrs);
		if (HTML.ATTR_STATE_DISABLED.equalsIgnoreCase(state)) {
			html.writeAttribute(HTML.ATTR_DISABLED, HTML.ATTR_DISABLED);
		}

		html.writeAttribute(HTML.ATTR_CLASS,
				resolveStyleClass(node, attrs, isMenu));
		html.writeAttribute(HTML.ATTR_STYLE, HTML.getStyle(node, attrs));

		if (isMenu) {
			html.writeAttribute(ATTR_MENU_ID, menuId);
		}

		// click
		if (isMenu) {
			String clickEvent = "Flywet.PushButton.showMenu('"
					+ menuId
					+ "',this);"
					+ Utils.NVL(HTML.getTagAttribute(node, HTML.ATTR_ON_CLICK,
							attrs), "");
			html.writeAttribute(HTML.ATTR_ON_CLICK, clickEvent);
		} else {
			String clickEvent = HTML.getTagAttribute(node, HTML.ATTR_ON_CLICK,
					attrs);
			if (!Const.isEmpty(clickEvent)) {
				html.writeAttribute(HTML.ATTR_ON_CLICK, clickEvent);
			}
		}

		HTML.writeAttributes(node.getAttributes(), new String[] {
				HTML.ATTR_ON_CLICK, HTML.ATTR_STATE, HTML.ATTR_ICON_CLASS,
				HTML.ATTR_ICON_ALIGN, HTML.ATTR_LABEL }, html, attrs);

		// icon
		String iconCls = HTML
				.getTagAttribute(node, HTML.ATTR_ICON_CLASS, attrs);
		String label = HTML.getTagAttribute(node, HTML.ATTR_LABEL, attrs);

		String content = "";
		if (label != null) {
			content = label;
		}

		if (iconCls != null) {
			if ("right".equalsIgnoreCase(HTML.getTagAttribute(node,
					HTML.ATTR_ICON_ALIGN, attrs))) {
				content = content + "<span class='glyphicon " + iconCls
						+ "'></span>";
			} else {
				content = "<span class='glyphicon " + iconCls + "'></span>"
						+ content;
			}
		}

		html.writeText(content);

		// 下拉菜单
		if (isMenu) {
			// script.add("Flywet.cw(\"Menu\",\"" + menuId + "_var\","
			// + menuItems.getFormJo() + ");");
		}

		html.endElement(HTML.COMPONENT_TYPE_BASE_BUTTON);
	}
}
