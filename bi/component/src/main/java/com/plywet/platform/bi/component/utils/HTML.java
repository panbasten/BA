package com.plywet.platform.bi.component.utils;

import java.util.HashMap;
import java.util.Map;

import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.plywet.platform.bi.core.exception.BIConvertException;
import com.plywet.platform.bi.core.exception.BIPageException;
import com.plywet.platform.bi.core.utils.ArrayUtils;
import com.plywet.platform.bi.core.utils.Utils;

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

	public static final String COMPONENT_TYPE_FOR_EACH = COMPONENT_TYPE_FLY_PREFIX
			+ "foreach";
	public static final String COMPONENT_TYPE_INCLUDE = COMPONENT_TYPE_FLY_PREFIX
			+ "include";
	public static final String COMPONENT_TYPE_COMPOSITION = COMPONENT_TYPE_FLY_PREFIX
			+ "composition";;

	public static final String COMPONENT_TYPE_LAYOUT = COMPONENT_TYPE_FLY_PREFIX
			+ "Layout";
	public static final String COMPONENT_TYPE_LAYOUT_ITEM = COMPONENT_TYPE_FLY_PREFIX
			+ "LayoutItem";
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
	public static final String COMPONENT_TYPE_PANEL = COMPONENT_TYPE_FLY_PREFIX
			+ "Panel";

	public static final String COMPONENT_TYPE_GRID = COMPONENT_TYPE_FLY_PREFIX
			+ "Grid";
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
	public static final String ATTR_ALT = "alt";
	public static final String ATTR_VALUE = "value";
	public static final String ATTR_STYLE = "style";
	public static final String ATTR_WIDTH = "width";
	public static final String ATTR_HEIGHT = "height";
	public static final String ATTR_TITLE = "title";
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
	public static final String ATTR_ICON_POS = "iconPos";
	public static final String ATTR_COLS = "cols";
	public static final String ATTR_ROWS = "rows";
	public static final String ATTR_FOR = "for";
	public static final String ATTR_DISABLED = "disabled";
	public static final String ATTR_READONLY = "readonly";
	public static final String ATTR_STATE = "state";
	public static final String ATTR_STATE_DEFAULT = "default";
	public static final String ATTR_STATE_DISABLED = "disabled";
	public static final String ATTR_STATE_READONLY = "readonly";
	public static final String ATTR_STATE_ACTIVE = "active";
	public static final String ATTR_STATE_NOT_DISPLAY = "notDisplay";

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

	public static String[] IMG_ATTRS_WITHOUT_EVENTS = { ATTR_ALT, ATTR_WIDTH,
			ATTR_HEIGHT, ATTR_TITLE };

	public static String[] LINK_ATTRS_WITHOUT_EVENTS = { ATTR_DISABLED,
			ATTR_HREF, ATTR_TITLE, ATTR_TYPE };

	public static String[] BUTTON_ATTRS_WITHOUT_EVENTS = { ATTR_ALT,
			ATTR_LABEL, ATTR_TITLE, ATTR_TYPE, ATTR_VALUE };

	public static String[] MEDIA_ATTRS = { ATTR_HEIGHT, ATTR_WIDTH };

	public static String[] INPUT_TEXT_ATTRS_WITHOUT_EVENTS = { ATTR_DISABLED,
			ATTR_READONLY, ATTR_ALT, ATTR_MAX_LENGTH, ATTR_SIZE, ATTR_TITLE,
			ATTR_SRC, ATTR_ACCEPT, ATTR_TYPE, ATTR_VALUE };

	public static String[] SELECT_ATTRS_WITHOUT_EVENTS = { ATTR_DISABLED,
			ATTR_READONLY, ATTR_TITLE, ATTR_VALUE };

	public static String[] TEXTAREA_ATTRS = { ATTR_DISABLED, ATTR_READONLY,
			ATTR_COLS, ATTR_ROWS, ATTR_ALT, ATTR_SIZE, ATTR_TITLE, ATTR_VALUE };

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

	public static String[] LEGEND_ATTRS = ArrayUtils.concat(HTML.COMMON,
			COMMON_EVENTS, new String[] { HTML.ATTR_TITLE, HTML.ATTR_ALIGN,
					ATTR_VALUE });

	public static String[] FORM_ATTRS = ArrayUtils.concat(HTML.COMMON,
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

	public final static String BUTTON_TEXT_ONLY_BUTTON_CLASS = "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only";
	public final static String BUTTON_ICON_ONLY_BUTTON_CLASS = "ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only";
	public final static String BUTTON_TEXT_ICON_LEFT_BUTTON_CLASS = "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-left";
	public final static String BUTTON_TEXT_ICON_RIGHT_BUTTON_CLASS = "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-right";
	public final static String BUTTON_LEFT_ICON_CLASS = "ui-button-icon-left ui-icon";
	public final static String BUTTON_RIGHT_ICON_CLASS = "ui-button-icon-right ui-icon";
	public final static String BUTTON_TEXT_CLASS = "ui-button-text";
	public final static String BUTTON_TEXT_ONLY_BUTTON_FLAT_CLASS = "ui-button ui-widget ui-state-default ui-button-text-only";

	public final static String SEPARATOR_DEFAULT_CLASS = "ui-separator ui-state-default ui-corner-all";
	public final static String SEPARATOR_CLASS = "ui-separator";
	public final static String SEPARATOR_ICON_CLASS = "ui-icon ui-icon-grip-dotted-vertical-narrow";

	public static final String REQUIRED_CLASS = "ui-required-tag";

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

	public static void getAttributesString(NamedNodeMap nnm, String[] dis,
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

	public static void getAttributesString(NamedNodeMap nnm, HTMLWriter html,
			FLYVariableResolver attrs) throws BIPageException {
		getAttributesString(nnm, null, html, attrs);
	}

	private static boolean marchAttribute(Map<String, Boolean> disMap,
			String nodeName) {
		if (disMap == null) {
			return false;
		}
		return disMap.containsKey(nodeName);

	}

	public static String getTagAttribute(Node node, String attribute) {
		return XMLHandler.getTagAttribute(node, attribute);
	}

	public static String getTagAttribute(Node node, String attribute,
			FLYVariableResolver attrs) {
		if (attrs == null) {
			return XMLHandler.getTagAttribute(node, attribute);
		} else {
			String attr = XMLHandler.getTagAttribute(node, attribute);
			if (attr != null) {
				if ("".equals(attr.trim())) {
					return "";
				}
				return PageTemplateInterpolator.evaluate(attr, attrs);
			}
		}
		return null;
	}

	public static Object getTagAttributeObject(Node node, String attribute,
			FLYVariableResolver attrs) {
		if (attrs == null) {
			return XMLHandler.getTagAttribute(node, attribute);
		} else {
			return PageTemplateInterpolator.evaluateObject(XMLHandler
					.getTagAttribute(node, attribute), attrs);
		}
	}

	public static boolean containsTagName(Node node, String attribute) {
		return XMLHandler.containsTagName(node, attribute);
	}
}
