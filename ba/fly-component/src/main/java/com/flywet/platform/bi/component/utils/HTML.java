package com.flywet.platform.bi.component.utils;

import java.util.HashMap;
import java.util.Map;

import org.pentaho.di.core.util.UUIDUtil;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.pms.util.Const;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIConvertException;
import com.flywet.platform.bi.core.exception.BIPageException;
import com.flywet.platform.bi.core.utils.ArrayUtils;
import com.flywet.platform.bi.core.utils.Utils;

public class HTML {

	public static final int DEFAULT_GRID_ROW_NUMBER = 5;

	public static final String COMPONENT_TYPE_FLY_PREFIX = "fly:";

	public static final String COMPONENT_TYPE_COMPLEX = "base:complex";
	public static final String COMPONENT_TYPE_NULL = "base:null";
	public static final String COMPONENT_TYPE_BASE_A = "a";
	public static final String COMPONENT_TYPE_BASE_BUTTON = "button";
	public static final String COMPONENT_TYPE_BASE_INPUT = "input";
	public static final String COMPONENT_TYPE_BASE_SELECT = "select";
	public static final String COMPONENT_TYPE_BASE_OPTION = "option";
	public static final String COMPONENT_TYPE_BASE_TEXTAREA = "textarea";
	public static final String COMPONENT_TYPE_BASE_BR = "br";
	public static final String COMPONENT_TYPE_BASE_DIV = "div";
	public static final String COMPONENT_TYPE_BASE_UL = "ul";
	public static final String COMPONENT_TYPE_BASE_LI = "li";
	public static final String COMPONENT_TYPE_BASE_FORM = "form";
	public static final String COMPONENT_TYPE_BASE_LABEL = "label";
	public static final String COMPONENT_TYPE_BASE_FIELDSET = "fieldset";
	public static final String COMPONENT_TYPE_BASE_LEGEND = "legend";
	public static final String COMPONENT_TYPE_BASE_P = "p";
	public static final String COMPONENT_TYPE_BASE_FONT = "font";
	public static final String COMPONENT_TYPE_BASE_SPAN = "span";

	public static final String COMPONENT_CATEGORY_BASE = "Base";
	public static final String COMPONENT_CATEGORY_SPACERS = "Spacers";
	public static final String COMPONENT_CATEGORY_BUTTONS = "Buttons";
	public static final String COMPONENT_CATEGORY_INPUT = "Input";
	public static final String COMPONENT_CATEGORY_DISPLAY = "Display";
	public static final String COMPONENT_CATEGORY_ITEM = "Item";
	public static final String COMPONENT_CATEGORY_CONTAINERS = "Containers";
	public static final String COMPONENT_CATEGORY_LAYOUT = "Layout";

	public static final String COMPONENT_TYPE_FOR_EACH = COMPONENT_TYPE_FLY_PREFIX
			+ "foreach";
	public static final String COMPONENT_TYPE_INCLUDE = COMPONENT_TYPE_FLY_PREFIX
			+ "include";
	public static final String COMPONENT_TYPE_COMPOSITION = COMPONENT_TYPE_FLY_PREFIX
			+ "composition";;

	public static final String COMPONENT_TYPE_GRID_LAYOUT = COMPONENT_TYPE_FLY_PREFIX
			+ "GridLayout";
	public static final String COMPONENT_TYPE_GRID_LAYOUT_ITEM = COMPONENT_TYPE_FLY_PREFIX
			+ "GridLayoutItem";
	public static final String COMPONENT_TYPE_INPUT_TEXT = COMPONENT_TYPE_FLY_PREFIX
			+ "InputText";
	public static final String COMPONENT_TYPE_SELECT_MENU = COMPONENT_TYPE_FLY_PREFIX
			+ "SelectMenu";
	public static final String COMPONENT_TYPE_OPTION = COMPONENT_TYPE_FLY_PREFIX
			+ "Option";
	public static final String COMPONENT_TYPE_OPTIONS = COMPONENT_TYPE_FLY_PREFIX
			+ "Options";
	public static final String COMPONENT_TYPE_LABEL_OBJECT = COMPONENT_TYPE_FLY_PREFIX
			+ "LabelObject";
	public static final String COMPONENT_TYPE_BUTTON = COMPONENT_TYPE_FLY_PREFIX
			+ "Button";
	public static final String COMPONENT_TYPE_TAB_VIEW = COMPONENT_TYPE_FLY_PREFIX
			+ "TabView";
	public static final String COMPONENT_TYPE_TAB = COMPONENT_TYPE_FLY_PREFIX
			+ "Tab";
	public static final String COMPONENT_TYPE_MESSAGEBOX = COMPONENT_TYPE_FLY_PREFIX
			+ "Messagebox";
	public static final String COMPONENT_TYPE_FLOWCHART = COMPONENT_TYPE_FLY_PREFIX
			+ "Flowchart";
	public static final String COMPONENT_TYPE_TREE = COMPONENT_TYPE_FLY_PREFIX
			+ "Tree";
	public static final String COMPONENT_TYPE_TREE_NODE = COMPONENT_TYPE_FLY_PREFIX
			+ "TreeNode";
	public static final String COMPONENT_TYPE_BREAD_CRUMB = COMPONENT_TYPE_FLY_PREFIX
			+ "BreadCrumb";
	public static final String COMPONENT_TYPE_BREAD_CRUMB_NODE = COMPONENT_TYPE_FLY_PREFIX
			+ "BreadCrumbNode";
	public static final String COMPONENT_TYPE_BROWSE = COMPONENT_TYPE_FLY_PREFIX
			+ "Browse";
	public static final String COMPONENT_TYPE_BROWSE_NODE = COMPONENT_TYPE_FLY_PREFIX
			+ "BrowseNode";
	public static final String COMPONENT_TYPE_SPREADSHEET = COMPONENT_TYPE_FLY_PREFIX
			+ "SpreadSheet";
	public static final String COMPONENT_TYPE_PANEL = COMPONENT_TYPE_FLY_PREFIX
			+ "Panel";

	public static final String COMPONENT_TYPE_DATA_GRID = COMPONENT_TYPE_FLY_PREFIX
			+ "DataGrid";
	public static final String COMPONENT_TYPE_VALIDATE_BOX = COMPONENT_TYPE_FLY_PREFIX
			+ "ValidateBox";
	public static final String COMPONENT_TYPE_NUMBER_BOX = COMPONENT_TYPE_FLY_PREFIX
			+ "NumberBox";
	public static final String COMPONENT_TYPE_DATE_BOX = COMPONENT_TYPE_FLY_PREFIX
			+ "DateBox";
	public static final String COMPONENT_TYPE_COMBO = COMPONENT_TYPE_FLY_PREFIX
			+ "Combo";
	public static final String COMPONENT_TYPE_COMBO_BOX = COMPONENT_TYPE_FLY_PREFIX
			+ "ComboBox";
	public static final String COMPONENT_TYPE_COMBO_GRID = COMPONENT_TYPE_FLY_PREFIX
			+ "ComboGrid";
	public static final String COMPONENT_TYPE_COMBO_TREE = COMPONENT_TYPE_FLY_PREFIX
			+ "ComboTree";

	public static final String COMPONENT_TYPE_MENU_ITEMS = COMPONENT_TYPE_FLY_PREFIX
			+ "MenuItems";
	public static final String COMPONENT_TYPE_MENU_ITEM = COMPONENT_TYPE_FLY_PREFIX
			+ "MenuItem";

	public static final String[] SINGLE_COMPONENT = new String[] { COMPONENT_TYPE_BASE_BR };

	public static final String TAG_ATTRITUDES = "attrs";
	public static final String TAG_CONTENT = "items";
	public static final String TAG_HTML = "html";
	public static final String TAG_DATA = "data";
	public static final String TAG_INIT_SCRIPT = "initScript";
	public static final String TAG_COMPLETE_SCRIPT = "completeScript";
	public static final String TAG_COMPONENT_TYPE = "componentType";
	public static final String TAG_VALIDATE = "validate";
	public static final String TAG_WEIGHT_VAR = "weightVar";

	public static final String ATTR_EVENTS = "events";
	public static final String ATTR_ON_CLICK = "onclick";
	public static final String ATTR_ON_BLUR = "onblur";
	public static final String ATTR_ON_FOCUS = "onfocus";
	public static final String ATTR_ON_CHANGE = "onchange";
	public static final String ATTR_ON_SELECT = "onselect";
	public static final String ATTR_ON_DBLCLICK = "ondblclick";
	public static final String ATTR_ON_KEY_DOWN = "onkeydown";
	public static final String ATTR_ON_KEY_PRESS = "onkeypress";
	public static final String ATTR_ON_KEY_UP = "onkeyup";
	public static final String ATTR_ON_MOUSE_DOWN = "onmousedown";
	public static final String ATTR_ON_MOUSE_MOVE = "onmousemove";
	public static final String ATTR_ON_MOUSE_OUT = "onmouseout";
	public static final String ATTR_ON_MOUSE_OVER = "onmouseover";
	public static final String ATTR_ON_MOUSE_UP = "onmouseup";
	public static final String ATTR_ON_DRAG = "ondrag";
	public static final String ATTR_ID = "id";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_CLASS = "class";
	public static final String ATTR_CLS = "cls";
	public static final String ATTR_VALUE = "value";
	public static final String ATTR_STYLE = "style";
	public static final String ATTR_WIDTH = "width";
	public static final String ATTR_HEIGHT = "height";
	public static final String ATTR_TITLE = "title";
	public static final String ATTR_TEXT = "text";
	public static final String ATTR_TYPE = "type";
	public static final String ATTR_SELECTED = "selected";
	public static final String ATTR_BUTTON_TYPE_BUTTON = "button";
	public static final String ATTR_BUTTON_TYPE_RESET = "reset";
	public static final String ATTR_BUTTON_TYPE_SUBMIT = "submit";
	public static final String ATTR_INPUT_TYPE_BUTTON = "button";
	public static final String ATTR_INPUT_TYPE_CHECKBOX = "checkbox";
	public static final String ATTR_INPUT_TYPE_FILE = "file";
	public static final String ATTR_INPUT_TYPE_HIDDEN = "hidden";
	public static final String ATTR_INPUT_TYPE_IMAGE = "image";
	public static final String ATTR_INPUT_TYPE_PASSWORD = "password";
	public static final String ATTR_INPUT_TYPE_RADIO = "radio";
	public static final String ATTR_INPUT_TYPE_RESET = "reset";
	public static final String ATTR_INPUT_TYPE_SUBMIT = "submit";
	public static final String ATTR_INPUT_TYPE_TEXT = "text";
	public static final String ATTR_MAX_LENGTH = "maxlength";
	public static final String ATTR_SRC = "src";
	public static final String ATTR_ACCEPT = "accept";
	public static final String ATTR_ACTION = "action";
	public static final String ATTR_METHOD = "method";
	public static final String ATTR_METHOD_GET = "get";
	public static final String ATTR_METHOD_POST = "post";
	public static final String ATTR_TARGET = "target";
	public static final String ATTR_TARGET_BLANK = "_blank";
	public static final String ATTR_TARGET_PARENT = "_parent";
	public static final String ATTR_TARGET_SELF = "_self";
	public static final String ATTR_TARGET_TOP = "_top";
	public static final String ATTR_SIZE = "size";
	public static final String ATTR_ALIGN = "align";
	public static final String ATTR_ALIGN_TOP = "top";
	public static final String ATTR_ALIGN_BOTTOM = "bottom";
	public static final String ATTR_ALIGN_LEFT = "left";
	public static final String ATTR_ALIGN_RIGHT = "right";
	public static final String ATTR_COLOR = "color";
	public static final String ATTR_HREF = "href";
	public static final String ATTR_LABEL = "label";
	public static final String ATTR_ICON = "icon";
	public static final String ATTR_ICON_CLASS = "iconCls";
	public static final String ATTR_ICON_POS = "iconPos";
	public static final String ATTR_COLS = "cols";
	public static final String ATTR_ROWS = "rows";
	public static final String ATTR_FOR = "for";
	public static final String ATTR_BUDDY = "buddy";
	public static final String ATTR_DISABLED = "disabled";
	public static final String ATTR_READONLY = "readonly";
	public static final String ATTR_STATE = "state";
	public static final String ATTR_STATE_DEFAULT = "default";
	public static final String ATTR_STATE_DISABLED = "disabled";
	public static final String ATTR_STATE_READONLY = "readonly";
	public static final String ATTR_STATE_ACTIVE = "active";

	public static final String ATTR_VALIDATE = "validate";

	public static final String ATTR_FONT = "font";
	public static final String ATTR_FONT_FAMILY = "fontFamily";
	public static final String ATTR_FONT_SIZE = "fontSize";
	public static final String ATTR_FONT_WEIGHT = "fontWeight";
	public static final String ATTR_FONT_ITALIC = "fontItalic";
	public static final String ATTR_FONT_UNDER = "fontUnder";
	public static final String ATTR_FONT_STRIKE = "fontStrike";

	public static final String ATTR_GEOMETRY = "geometry";
	public static final String ATTR_X = "x";
	public static final String ATTR_Y = "y";
	public static final String ATTR_FREE_LAYOUT = "freeLayout";

	public static final String ATTR_OVERFLOW = "overflow";
	public static final String ATTR_OVERFLOW_X = "overflow-x";
	public static final String ATTR_OVERFLOW_Y = "overflow-y";

	public static final String ATTR_MARGIN_GROUP = "marginGroup";
	public static final String ATTR_MARGIN = "margin";
	public static final String ATTR_MARGIN_TOP = "marginTop";
	public static final String ATTR_MARGIN_BOTTOM = "marginBottom";
	public static final String ATTR_MARGIN_LEFT = "marginLeft";
	public static final String ATTR_MARGIN_RIGHT = "marginRight";

	public static final String ATTR_PADDING_GROUP = "paddingGroup";
	public static final String ATTR_PADDING = "padding";
	public static final String ATTR_PADDING_TOP = "paddingTop";
	public static final String ATTR_PADDING_BOTTOM = "paddingBottom";
	public static final String ATTR_PADDING_LEFT = "paddingLeft";
	public static final String ATTR_PADDING_RIGHT = "paddingRight";

	public static final String ATTR_BORDER_GROUP = "borderGroup";
	public static final String ATTR_BORDER = "border";
	public static final String ATTR_BORDER_WIDTH = "borderWidth";
	public static final String ATTR_BORDER_COLOR = "borderColor";
	public static final String ATTR_BORDER_STYLE = "borderStyle";
	public static final String ATTR_BORDER_TOP = "borderTop";
	public static final String ATTR_BORDER_TOP_WIDTH = "borderTopWidth";
	public static final String ATTR_BORDER_TOP_COLOR = "borderTopColor";
	public static final String ATTR_BORDER_TOP_STYLE = "borderTopStyle";
	public static final String ATTR_BORDER_BOTTOM = "borderBottom";
	public static final String ATTR_BORDER_BOTTOM_WIDTH = "borderBottomWidth";
	public static final String ATTR_BORDER_BOTTOM_COLOR = "borderBottomColor";
	public static final String ATTR_BORDER_BOTTOM_STYLE = "borderBottomStyle";
	public static final String ATTR_BORDER_LEFT = "borderLeft";
	public static final String ATTR_BORDER_LEFT_WIDTH = "borderLeftWidth";
	public static final String ATTR_BORDER_LEFT_COLOR = "borderLeftColor";
	public static final String ATTR_BORDER_LEFT_STYLE = "borderLeftStyle";
	public static final String ATTR_BORDER_RIGHT = "borderRight";
	public static final String ATTR_BORDER_RIGHT_WIDTH = "borderRightWidth";
	public static final String ATTR_BORDER_RIGHT_COLOR = "borderRightColor";
	public static final String ATTR_BORDER_RIGHT_STYLE = "borderRightStyle";

	public static final String ATTR_SHOW = "show";

	public static final String DATA_GRID_SUFFIX = ":rows";

	public static String[] CLICK_EVENT = { ATTR_ON_CLICK };

	public static String[] BLUR_FOCUS_EVENTS = { ATTR_ON_BLUR, ATTR_ON_FOCUS };

	public static String[] CHANGE_SELECT_EVENTS = { ATTR_ON_CHANGE,
			ATTR_ON_SELECT };

	public static String[] COMMON = { ATTR_ID, ATTR_NAME, ATTR_STYLE,
			ATTR_CLASS, ATTR_STATE };

	public static String[] COMMON_EVENTS = { ATTR_ON_CLICK, ATTR_ON_DBLCLICK,
			ATTR_ON_KEY_DOWN, ATTR_ON_KEY_PRESS, ATTR_ON_KEY_UP,
			ATTR_ON_MOUSE_DOWN, ATTR_ON_MOUSE_MOVE, ATTR_ON_MOUSE_OUT,
			ATTR_ON_MOUSE_OVER, ATTR_ON_MOUSE_UP };

	public static String[] IMG_ATTRS_WITHOUT_EVENTS = { ATTR_WIDTH,
			ATTR_HEIGHT, ATTR_TITLE };

	public static String[] LINK_ATTRS_WITHOUT_EVENTS = { ATTR_DISABLED,
			ATTR_HREF, ATTR_TITLE, ATTR_TYPE };

	public static String[] BUTTON_ATTRS_WITHOUT_EVENTS = { ATTR_LABEL,
			ATTR_TITLE, ATTR_TYPE, ATTR_VALUE };

	public static String[] MEDIA_ATTRS = { ATTR_HEIGHT, ATTR_WIDTH };

	public static String[] INPUT_TEXT_ATTRS_WITHOUT_EVENTS = { ATTR_DISABLED,
			ATTR_READONLY, ATTR_MAX_LENGTH, ATTR_SIZE, ATTR_TITLE, ATTR_SRC,
			ATTR_ACCEPT, ATTR_TYPE, ATTR_VALUE };

	public static String[] SELECT_ATTRS_WITHOUT_EVENTS = { ATTR_DISABLED,
			ATTR_READONLY, ATTR_TITLE, ATTR_VALUE };

	public static String[] TEXTAREA_ATTRS = { ATTR_DISABLED, ATTR_READONLY,
			ATTR_COLS, ATTR_ROWS, ATTR_SIZE, ATTR_TITLE, ATTR_VALUE };

	public static String[] LINK_EVENTS = ArrayUtils.concat(COMMON_EVENTS,
			BLUR_FOCUS_EVENTS);

	public static String[] BUTTON_EVENTS = ArrayUtils.concat(LINK_EVENTS,
			CHANGE_SELECT_EVENTS);

	public static String[] IMG_ATTRS = ArrayUtils.concat(
			IMG_ATTRS_WITHOUT_EVENTS, COMMON_EVENTS, COMMON);

	public static String[] LINK_ATTRS = ArrayUtils.concat(
			LINK_ATTRS_WITHOUT_EVENTS, LINK_EVENTS, COMMON,
			new String[] { ATTR_VALUE });

	public static String[] BUTTON_ATTRS = ArrayUtils.concat(
			BUTTON_ATTRS_WITHOUT_EVENTS, BUTTON_EVENTS, COMMON,
			new String[] { ATTR_VALUE });

	public static String[] FONT_ATTRS = ArrayUtils.concat(COMMON,
			COMMON_EVENTS, new String[] { ATTR_TITLE, ATTR_SIZE, ATTR_COLOR,
					ATTR_VALUE });

	public static String[] LABEL_ATTRS = ArrayUtils.concat(COMMON,
			COMMON_EVENTS, new String[] { ATTR_TITLE, ATTR_FOR, ATTR_VALUE });

	public static String[] LEGEND_ATTRS = ArrayUtils.concat(COMMON,
			COMMON_EVENTS, new String[] { ATTR_TITLE, ATTR_ALIGN, ATTR_VALUE });

	public static String[] FORM_ATTRS = ArrayUtils.concat(COMMON,
			COMMON_EVENTS,
			new String[] { ATTR_ACTION, ATTR_METHOD, ATTR_TARGET });

	public static final String[] INPUT_TEXT_ATTRS = ArrayUtils.concat(
			INPUT_TEXT_ATTRS_WITHOUT_EVENTS, COMMON_EVENTS, COMMON,
			CHANGE_SELECT_EVENTS, BLUR_FOCUS_EVENTS);

	public static final String[] INPUT_TEXTAREA_ATTRS = ArrayUtils.concat(
			TEXTAREA_ATTRS, COMMON_EVENTS, COMMON, CHANGE_SELECT_EVENTS,
			BLUR_FOCUS_EVENTS);

	public static final String[] SELECT_ATTRS = ArrayUtils.concat(
			SELECT_ATTRS_WITHOUT_EVENTS, COMMON_EVENTS, COMMON,
			CHANGE_SELECT_EVENTS, BLUR_FOCUS_EVENTS);

	public static final String[] STYLE_ATTRS = { ATTR_STYLE, ATTR_CLASS,
			ATTR_FONT_FAMILY, ATTR_FONT_SIZE, ATTR_FONT_WEIGHT,
			ATTR_FONT_ITALIC, ATTR_X, ATTR_Y, ATTR_WIDTH, ATTR_HEIGHT,
			ATTR_MARGIN, ATTR_MARGIN_TOP, ATTR_MARGIN_BOTTOM, ATTR_MARGIN_LEFT,
			ATTR_MARGIN_RIGHT, ATTR_PADDING, ATTR_PADDING_TOP,
			ATTR_PADDING_BOTTOM, ATTR_PADDING_LEFT, ATTR_PADDING_RIGHT,
			ATTR_BORDER, ATTR_BORDER_WIDTH, ATTR_BORDER_COLOR,
			ATTR_BORDER_STYLE, ATTR_BORDER_TOP, ATTR_BORDER_TOP_WIDTH,
			ATTR_BORDER_TOP_COLOR, ATTR_BORDER_TOP_STYLE, ATTR_BORDER_BOTTOM,
			ATTR_BORDER_BOTTOM_WIDTH, ATTR_BORDER_BOTTOM_COLOR,
			ATTR_BORDER_BOTTOM_STYLE, ATTR_BORDER_LEFT, ATTR_BORDER_LEFT_WIDTH,
			ATTR_BORDER_LEFT_COLOR, ATTR_BORDER_LEFT_STYLE, ATTR_BORDER_RIGHT,
			ATTR_BORDER_RIGHT_WIDTH, ATTR_BORDER_RIGHT_COLOR,
			ATTR_BORDER_RIGHT_STYLE, ATTR_SHOW };

	public static final Map<String, Boolean> STYLE_ATTRS_MAP = new HashMap<String, Boolean>();
	static {
		for (String d : STYLE_ATTRS) {
			STYLE_ATTRS_MAP.put(d, true);
		}
	};

	public final static String BUTTON_TEXT_ONLY_BUTTON_CLASS = "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only";
	public final static String BUTTON_ICON_ONLY_BUTTON_CLASS = "ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only";
	public final static String BUTTON_TEXT_ICON_LEFT_BUTTON_CLASS = "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-left";
	public final static String BUTTON_TEXT_ICON_RIGHT_BUTTON_CLASS = "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-right";

	public final static String BUTTON_MENU_TEXT_ONLY_BUTTON_CLASS = "ui-button ui-button-menu ui-widget ui-state-default ui-corner-all ui-button-menu-text-only";
	public final static String BUTTON_MENU_ICON_ONLY_BUTTON_CLASS = "ui-button ui-button-menu ui-widget ui-state-default ui-corner-all ui-button-menu-icon-only";
	public final static String BUTTON_MENU_TEXT_ICON_LEFT_BUTTON_CLASS = "ui-button ui-button-menu ui-widget ui-state-default ui-corner-all ui-button-menu-text-icon-left";
	public final static String BUTTON_MENU_TEXT_ICON_RIGHT_BUTTON_CLASS = "ui-button ui-button-menu ui-widget ui-state-default ui-corner-all ui-button-menu-text-icon-right";

	public final static String BUTTON_LEFT_ICON_CLASS = "ui-button-icon-left ui-icon";
	public final static String BUTTON_RIGHT_ICON_CLASS = "ui-button-icon-right ui-icon";
	public final static String BUTTON_MENU_ICON_CLASS = "ui-button-menu-icon ui-icon ui-icon-triangle-1-s";
	public final static String BUTTON_TEXT_CLASS = "ui-button-text";
	public final static String BUTTON_TEXT_ONLY_BUTTON_FLAT_CLASS = "ui-button ui-widget ui-state-default ui-button-text-only";

	public final static String SEPARATOR_DEFAULT_CLASS = "ui-separator ui-state-default ui-corner-all";
	public final static String SEPARATOR_CLASS = "ui-separator";
	public final static String SEPARATOR_ICON_CLASS = "ui-icon ui-icon-no-hover ui-icon-grip-dotted-vertical-narrow";

	public static final String REQUIRED_CLASS = "ui-required-tag";

	public static final String LAYOUT_CLASS = "ui-helper-clearfix";

	public static final String LAYOUT_ITEM_CLASS = "ui-layout-float ui-helper-clearfix";

	public static final String LAYOUT_SINGLE_CLASS = "ui-label-single ui-helper-clearfix";

	public static final String STATE_DISABLED_CLASS = "ui-state-disabled";

	public static Map<String, Object> getAttributesMap(NamedNodeMap nnm,
			FLYVariableResolver attrs) throws BIPageException {
		return getAttributesMap(nnm, null, attrs);
	}

	public static Map<String, Object> getAttributesMap(NamedNodeMap nnm,
			String[] dis, FLYVariableResolver attrs) throws BIPageException {
		try {
			Map<String, Boolean> disMap = null;
			if (dis != null) {
				disMap = new HashMap<String, Boolean>();
				for (String d : dis) {
					disMap.put(d, true);
				}
			}

			if (nnm != null) {
				Map<String, Object> rtn = new HashMap<String, Object>();
				for (int i = 0; i < nnm.getLength(); i++) {
					Node n = nnm.item(i);
					if (!marchAttribute(disMap, n.getNodeName())) {
						String val = PageTemplateInterpolator
								.interpolateExpressions(n.getNodeValue(), attrs);
						rtn.put(n.getNodeName(), Utils.autoConvert(val));
					}
				}
				return rtn;
			}
		} catch (BIConvertException e) {
			throw new BIPageException(e);
		}
		return null;
	}

	public static void writeAttributes(NamedNodeMap nnm, String[] dis,
			HTMLWriter html, FLYVariableResolver attrs) throws BIPageException {
		Map<String, Boolean> disMap = null;
		if (dis != null) {
			disMap = new HashMap<String, Boolean>();
			for (String d : dis) {
				disMap.put(d, true);
			}
		}

		if (nnm != null) {
			for (int i = 0; i < nnm.getLength(); i++) {
				Node n = nnm.item(i);
				if (!marchAttribute(disMap, n.getNodeName())) {
					html.writeAttribute(n.getNodeName(),
							PageTemplateInterpolator.interpolateExpressions(n
									.getNodeValue(), attrs));
				}
			}
		}
	}

	public static void writeAttributes(NamedNodeMap nnm, HTMLWriter html,
			FLYVariableResolver attrs) throws BIPageException {
		writeAttributes(nnm, null, html, attrs);
	}

	private static boolean marchAttribute(Map<String, Boolean> disMap,
			String nodeName) {
		if (STYLE_ATTRS_MAP.containsKey(nodeName)) {
			return true;
		}
		if (disMap == null) {
			return false;
		}
		return disMap.containsKey(nodeName);

	}

	public static String getId(Node node, FLYVariableResolver attrs) {
		String id = HTML.getTagAttribute(node, HTML.ATTR_ID, attrs);
		if (Const.isEmpty(id)) {
			id = "id_" + UUIDUtil.getUUIDAsString();
		}
		return id;
	}

	/**
	 * 获得标签的属性值
	 * 
	 * @param node
	 * @param attribute
	 * @return
	 */
	public static String getTagAttribute(Node node, String attribute) {
		return XMLHandler.getTagAttribute(node, attribute);
	}

	public static String getTagAttribute(Node node, String attribute,
			FLYVariableResolver attrs) {
		String attr = null;
		if (attrs == null) {
			attr = XMLHandler.getTagAttribute(node, attribute);
		} else {
			attr = XMLHandler.getTagAttribute(node, attribute);
			if (attr != null) {
				attr = attr.trim();
				attr = PageTemplateInterpolator.evaluate(attr, attrs);
			}
		}

		return attr;
	}

	public static Object getTagAttributeObject(Node node, String attribute,
			FLYVariableResolver attrs) {
		if (attrs == null) {
			return XMLHandler.getTagAttribute(node, attribute);
		} else {
			String tagString = XMLHandler.getTagAttribute(node, attribute);
			if (Const.isEmpty(tagString)) {
				return null;
			}
			return PageTemplateInterpolator.evaluateObject(tagString, attrs);
		}
	}

	public static boolean containsTagName(Node node, String attribute) {
		return XMLHandler.containsTagName(node, attribute);
	}

	private static String addStyleString(Node node, FLYVariableResolver attrs,
			String attrName, String styleName) {
		String v = getTagAttribute(node, attrName, attrs);

		if (!Utils.isEmpty(v)) {
			return styleName + ":" + v + ";";
		}

		return "";
	}

	private static String addStyleInteger(Node node, FLYVariableResolver attrs,
			String attrName, String styleName) {
		String v = getTagAttribute(node, attrName, attrs);

		if (!Utils.isEmpty(v)) {
			return styleName + ":" + v + "px;";
		}

		return "";
	}

	private static String addStyle(Node node, FLYVariableResolver attrs,
			String attrName, String styleName) {
		String v = getTagAttribute(node, attrName, attrs);

		if (!Utils.isEmpty(v)) {
			if (Utils.isNumber(v)) {
				return styleName + ":" + v + "px;";
			} else {
				return styleName + ":" + v + ";";
			}
		}

		return "";
	}

	public static String getFontStyle(Node node, FLYVariableResolver attrs) {
		String style = "";
		if (PageTemplateResolverType.containComponentAttribute(node
				.getNodeName(), ATTR_FONT)) {
			style += addStyleString(node, attrs, ATTR_FONT_FAMILY,
					"font-family");

			style += addStyle(node, attrs, ATTR_FONT_SIZE, "font-size");

			style += addStyleString(node, attrs, ATTR_FONT_WEIGHT,
					"font-weight");

			if (Utils.toBoolean(getTagAttribute(node, ATTR_FONT_ITALIC, attrs),
					false)) {
				style += "font-style:italic;";
			}
		}
		return style;
	}

	public static String getGeometryStyle(Node node, FLYVariableResolver attrs) {
		String style = "";
		if (PageTemplateResolverType.containComponentAttribute(node
				.getNodeName(), ATTR_GEOMETRY)) {
			// 如果拥有freeLayout
			String v = getTagAttribute(node, ATTR_FREE_LAYOUT, attrs);
			if (Utils.toBoolean(v, false)) {
				style += "position:absolute;";
				style += addStyleInteger(node, attrs, ATTR_X, "left");
				style += addStyleInteger(node, attrs, ATTR_Y, "top");
			}
			style += addStyle(node, attrs, ATTR_WIDTH, "width");
			style += addStyle(node, attrs, ATTR_HEIGHT, "height");

		}
		return style;
	}

	public static String getMarginStyle(Node node, FLYVariableResolver attrs) {
		String style = "";

		if (PageTemplateResolverType.containComponentAttribute(node
				.getNodeName(), ATTR_MARGIN_GROUP)) {

			style += addStyle(node, attrs, ATTR_MARGIN, "margin");

			style += addStyleInteger(node, attrs, ATTR_MARGIN_TOP, "margin-top");
			style += addStyleInteger(node, attrs, ATTR_MARGIN_BOTTOM,
					"margin-bottom");
			style += addStyleInteger(node, attrs, ATTR_MARGIN_LEFT,
					"margin-left");
			style += addStyleInteger(node, attrs, ATTR_MARGIN_RIGHT,
					"margin-right");

		}

		return style;
	}

	public static String getPaddingStyle(Node node, FLYVariableResolver attrs) {
		String style = "";

		if (PageTemplateResolverType.containComponentAttribute(node
				.getNodeName(), ATTR_PADDING_GROUP)) {

			style += addStyle(node, attrs, ATTR_PADDING, "padding");

			style += addStyleInteger(node, attrs, ATTR_PADDING_TOP,
					"padding-top");
			style += addStyleInteger(node, attrs, ATTR_PADDING_BOTTOM,
					"padding-bottom");
			style += addStyleInteger(node, attrs, ATTR_PADDING_LEFT,
					"padding-left");
			style += addStyleInteger(node, attrs, ATTR_PADDING_RIGHT,
					"padding-right");
		}

		return style;
	}

	public static String getBorderStyle(Node node, FLYVariableResolver attrs) {
		String style = "";

		if (PageTemplateResolverType.containComponentAttribute(node
				.getNodeName(), ATTR_BORDER_GROUP)) {
			style += addStyleString(node, attrs, ATTR_BORDER, "border");
			style += addStyleInteger(node, attrs, ATTR_BORDER_WIDTH,
					"border-width");
			style += addStyleString(node, attrs, ATTR_BORDER_COLOR,
					"border-color");
			style += addStyleString(node, attrs, ATTR_BORDER_STYLE,
					"border-style");

			style += addStyleString(node, attrs, ATTR_BORDER_TOP, "border-top");
			style += addStyleInteger(node, attrs, ATTR_BORDER_TOP_WIDTH,
					"border-top-width");
			style += addStyleString(node, attrs, ATTR_BORDER_TOP_COLOR,
					"border-top-color");
			style += addStyleString(node, attrs, ATTR_BORDER_TOP_STYLE,
					"border-top-style");

			style += addStyleString(node, attrs, ATTR_BORDER_BOTTOM,
					"border-bottom");
			style += addStyleInteger(node, attrs, ATTR_BORDER_BOTTOM_WIDTH,
					"border-bottom-width");
			style += addStyleString(node, attrs, ATTR_BORDER_BOTTOM_COLOR,
					"border-bottom-color");
			style += addStyleString(node, attrs, ATTR_BORDER_BOTTOM_STYLE,
					"border-bottom-style");

			style += addStyleString(node, attrs, ATTR_BORDER_LEFT,
					"border-left");
			style += addStyleInteger(node, attrs, ATTR_BORDER_LEFT_WIDTH,
					"border-left-width");
			style += addStyleString(node, attrs, ATTR_BORDER_LEFT_COLOR,
					"border-left-color");
			style += addStyleString(node, attrs, ATTR_BORDER_LEFT_STYLE,
					"border-left-style");

			style += addStyleString(node, attrs, ATTR_BORDER_RIGHT,
					"border-right");
			style += addStyleInteger(node, attrs, ATTR_BORDER_RIGHT_WIDTH,
					"border-right-width");
			style += addStyleString(node, attrs, ATTR_BORDER_RIGHT_COLOR,
					"border-right-color");
			style += addStyleString(node, attrs, ATTR_BORDER_RIGHT_STYLE,
					"border-right-style");

		}

		return style;
	}

	public static String getShowStyle(Node node, FLYVariableResolver attrs) {
		String show = getTagAttribute(node, ATTR_SHOW, attrs);
		if (!Utils.isEmpty(show)) {
			if (Utils.toBoolean(show, true)) {
				return "display:block;";
			} else {
				return "display:none;";
			}
		}
		return "";
	}

	public static String getStyle(Node node, FLYVariableResolver attrs) {
		String style = Utils.NVL(getTagAttribute(node, ATTR_STYLE, attrs), "");

		style += getFontStyle(node, attrs);
		style += getGeometryStyle(node, attrs);
		style += getMarginStyle(node, attrs);
		style += getPaddingStyle(node, attrs);
		style += getBorderStyle(node, attrs);
		style += getShowStyle(node, attrs);

		return style;
	}

	public static void writeStyleAttribute(Node node, HTMLWriter html,
			FLYVariableResolver attrs) {
		String style = getStyle(node, attrs);
		if (!Utils.isEmpty(style)) {
			html.writeAttribute(ATTR_STYLE, style);
		}

	}

	public static void writeStyleAttribute(Node node, HTMLWriter html,
			FLYVariableResolver attrs, String otherStyle) {
		String style = getStyle(node, attrs);
		if (!Utils.isEmpty(otherStyle)) {
			style += PageTemplateInterpolator.evaluate(otherStyle, attrs);
		}
		if (!Utils.isEmpty(style)) {
			html.writeAttribute(ATTR_STYLE, style);
		}

	}

	public static void writeStyleClassAttribute(Node node, HTMLWriter html,
			FLYVariableResolver attrs, String def) {
		String styleClass = HTML.getTagAttribute(node, HTML.ATTR_CLASS, attrs);

		styleClass = (!Utils.isEmpty(styleClass)) ? def + " " + styleClass
				: def;
		if (!Utils.isEmpty(styleClass)) {
			html.writeAttribute(HTML.ATTR_CLASS, styleClass);
		}
	}
}
