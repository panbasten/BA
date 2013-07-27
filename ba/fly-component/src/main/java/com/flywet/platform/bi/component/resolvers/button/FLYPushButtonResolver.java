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

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			String type = HTML.getTagAttribute(node, HTML.ATTR_TYPE, attrs);
			if (BUTTON_TYPE_SEPARATOR.equalsIgnoreCase(type)) {
				renderSeparatorType(node, html, script, attrs);
			} else {
				renderButtonType(node, html, script, attrs);
			}
		} catch (BIException e) {
			throw new BIPageException(e);
		}
	}

	private void renderSeparatorType(Node node, HTMLWriter html,
			List<String> script, FLYVariableResolver attrs) {
		html.startElement(HTML.COMPONENT_TYPE_BASE_SPAN);

		HTML.writeStyleClassAttribute(node, html, attrs, HTML.SEPARATOR_CLASS);
		HTML.writeStyleAttribute(node, html, attrs);

		if (HTML.containsTagName(node, HTML.ATTR_ID))
			html.writeAttribute(HTML.ATTR_ID, HTML.getTagAttribute(node,
					HTML.ATTR_ID, attrs));

		html.startElement(HTML.COMPONENT_TYPE_BASE_SPAN);
		html.writeAttribute(HTML.ATTR_CLASS, HTML.SEPARATOR_ICON_CLASS);

		html.endElement(HTML.COMPONENT_TYPE_BASE_SPAN);
		html.endElement(HTML.COMPONENT_TYPE_BASE_SPAN);
	}

	private String resolveStyleClass(Node node, FLYVariableResolver attrs,
			boolean isMenu) {
		String icon = HTML.getTagAttribute(node, HTML.ATTR_ICON_CLASS, attrs);
		String label = HTML.getTagAttribute(node, HTML.ATTR_LABEL, attrs);
		String state = HTML.getTagAttribute(node, HTML.ATTR_STATE, attrs);
		String userClass = HTML.getTagAttribute(node, HTML.ATTR_CLASS, attrs);
		String styleClass = "";

		if (isMenu) {
			if (label != null && icon == null) {
				styleClass = HTML.BUTTON_MENU_TEXT_ONLY_BUTTON_CLASS;
			} else if (label != null && icon != null) {
				styleClass = "right".equalsIgnoreCase(HTML.getTagAttribute(
						node, HTML.ATTR_ICON_POS, attrs)) ? HTML.BUTTON_MENU_TEXT_ICON_RIGHT_BUTTON_CLASS
						: HTML.BUTTON_MENU_TEXT_ICON_LEFT_BUTTON_CLASS;
			} else if (label == null && icon != null) {
				styleClass = HTML.BUTTON_MENU_ICON_ONLY_BUTTON_CLASS;
			}
		} else {
			if (label != null && icon == null) {
				styleClass = HTML.BUTTON_TEXT_ONLY_BUTTON_CLASS;
			} else if (label != null && icon != null) {
				styleClass = "right".equalsIgnoreCase(HTML.getTagAttribute(
						node, HTML.ATTR_ICON_POS, attrs)) ? HTML.BUTTON_TEXT_ICON_RIGHT_BUTTON_CLASS
						: HTML.BUTTON_TEXT_ICON_LEFT_BUTTON_CLASS;
			} else if (label == null && icon != null) {
				styleClass = HTML.BUTTON_ICON_ONLY_BUTTON_CLASS;
			}
		}

		if (HTML.ATTR_STATE_DISABLED.equalsIgnoreCase(state)) {
			styleClass = styleClass + " " + "ui-state-disabled";
		} else if (HTML.ATTR_STATE_ACTIVE.equalsIgnoreCase(state)) {
			styleClass = styleClass + " " + "ui-state-active";
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
				item.setText(HTML.getTagAttribute(subNode, HTML.ATTR_TEXT, attrs))
						.setIconCls(
								HTML.getTagAttribute(subNode,
										HTML.ATTR_ICON_CLASS, attrs))
						.setOnClick(
								HTML.getTagAttribute(subNode, HTML.ATTR_ON_CLICK,
										attrs));
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

		html.writeAttribute(HTML.ATTR_CLASS, resolveStyleClass(node, attrs,
				isMenu));
		html.writeAttribute(HTML.ATTR_STYLE, HTML.getStyle(node, attrs));
		
		if (isMenu) {
			html.writeAttribute(ATTR_MENU_ID, menuId);
		}

		// mouseOver
		String mouseOverEvent = "$(this).addClass('ui-state-hover');"
				+ Utils.NVL(HTML.getTagAttribute(node, HTML.ATTR_ON_MOUSE_OVER,
						attrs), "");
		html.writeAttribute(HTML.ATTR_ON_MOUSE_OVER, mouseOverEvent);

		// mouseOut
		String mouseOutEvent = "$(this).removeClass('ui-state-hover');"
				+ Utils.NVL(HTML.getTagAttribute(node, HTML.ATTR_ON_MOUSE_OUT,
						attrs), "");
		html.writeAttribute(HTML.ATTR_ON_MOUSE_OUT, mouseOutEvent);

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
				HTML.ATTR_TYPE, HTML.ATTR_ON_MOUSE_OVER,
				HTML.ATTR_ON_MOUSE_OUT, HTML.ATTR_ON_CLICK, HTML.ATTR_STATE,
				HTML.ATTR_ICON_CLASS, HTML.ATTR_ICON_POS, HTML.ATTR_LABEL },
				html, attrs);

		// icon
		String icon = HTML.getTagAttribute(node, HTML.ATTR_ICON_CLASS, attrs);
		if (icon != null) {
			String defaultIconClass = "right".equalsIgnoreCase(HTML
					.getTagAttribute(node, HTML.ATTR_ICON_POS, attrs)) ? HTML.BUTTON_RIGHT_ICON_CLASS
					: HTML.BUTTON_LEFT_ICON_CLASS;
			String iconClass = defaultIconClass + " " + icon;

			html.startElement(HTML.COMPONENT_TYPE_BASE_SPAN);
			html.writeAttribute(HTML.ATTR_CLASS, iconClass);
			html.endElement(HTML.COMPONENT_TYPE_BASE_SPAN);
		}

		// text
		html.startElement(HTML.COMPONENT_TYPE_BASE_SPAN);
		html.writeAttribute(HTML.ATTR_CLASS, HTML.BUTTON_TEXT_CLASS);

		String label = HTML.getTagAttribute(node, HTML.ATTR_LABEL, attrs);
		if (label != null) {
			html.writeText(label);
		} else {
			html.writeText("ui-text");
		}
		html.endElement(HTML.COMPONENT_TYPE_BASE_SPAN);

		// 下拉菜单
		if (isMenu) {
			html.startElement(HTML.COMPONENT_TYPE_BASE_SPAN);
			html.writeAttribute(HTML.ATTR_CLASS, HTML.BUTTON_MENU_ICON_CLASS);
			html.endElement(HTML.COMPONENT_TYPE_BASE_SPAN);

			script.add("Flywet.cw(\"Menu\",\"" + menuId + "_var\","
					+ menuItems.getFormJo() + ");");
		}

		html.endElement(HTML.COMPONENT_TYPE_BASE_BUTTON);
	}

}
