package com.flywet.platform.bi.component.components.messageBox;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.components.BaseComponentMeta;
import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIJSONException;

/**
 * 消息功能
 * 
 * @author panwei
 * @version 1.0
 * @since 2009/06/22
 */
public class MessageBoxMeta extends BaseComponentMeta implements
		ComponentMetaInterface {
	public static final String MESSAGEBOX_TYPE_CONFIRM = "confirm";
	public static final String MESSAGEBOX_TYPE_PROMPT = "prompt";
	public static final String MESSAGEBOX_TYPE_MULTIPROMPT = "multiprompt";
	public static final String MESSAGEBOX_TYPE_YNCDIALOG = "ync";
	public static final String MESSAGEBOX_TYPE_ALERT = "alert";
	public static final String MESSAGEBOX_TYPE_CUSTOM = "custom";

	public static final String MESSAGEBOX_ICON_ERROR = "error";
	public static final String MESSAGEBOX_ICON_INFO = "info";
	public static final String MESSAGEBOX_ICON_QUESTION = "question";
	public static final String MESSAGEBOX_ICON_WARNING = "warning";

	/**
	 * 类型
	 */
	private String type = MESSAGEBOX_TYPE_ALERT;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 题目
	 */
	private String title;

	/**
	 * 消息
	 */
	private String message;

	/**
	 * 按钮调用方法
	 */
	private String fn;

	private int oraTop = -1, oraLeft = -1, oraWidth = -1, oraHeight = -1;

	/**
	 * 消息框获得
	 * 
	 * @param message
	 */
	public MessageBoxMeta(String message) {
		this.message = message;
	}

	/**
	 * 得到json
	 * 
	 * @return
	 * @throws BIJSONException
	 */
	@Override
	public JSONObject getFormJo() throws BIJSONException {
		super.addAttribute(HTML.ATTR_TYPE, this.type);
		if (StringUtils.isNotEmpty(this.icon))
			super.addAttribute(HTML.ATTR_ICON_CLASS, this.icon);
		if (StringUtils.isNotEmpty(this.title))
			super.addAttribute(HTML.ATTR_TITLE, this.title);
		if (StringUtils.isNotEmpty(this.message))
			super.addAttribute("message", this.message);
		if (StringUtils.isNotEmpty(this.fn))
			super.addAttribute("fn", this.fn);

		if (this.oraTop != -1)
			super.addAttribute("oraTop", this.oraTop);
		if (this.oraLeft != -1)
			super.addAttribute("oraLeft", this.oraLeft);
		if (this.oraWidth != -1)
			super.addAttribute("oraWidth", this.oraWidth);
		if (this.oraHeight != -1)
			super.addAttribute("oraHeight", this.oraHeight);

		return super.getFormJo();
	}

	@Override
	public void removeAll() {
		super.removeAll();
		this.type = MESSAGEBOX_TYPE_ALERT;
		this.icon = null;
		this.title = null;
		this.message = null;
		this.fn = null;
		this.oraTop = -1;
		this.oraLeft = -1;
		this.oraWidth = -1;
		this.oraHeight = -1;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getOraTop() {
		return oraTop;
	}

	public void setOraTop(int oraTop) {
		this.oraTop = oraTop;
	}

	public int getOraLeft() {
		return oraLeft;
	}

	public void setOraLeft(int oraLeft) {
		this.oraLeft = oraLeft;
	}

	public int getOraWidth() {
		return oraWidth;
	}

	public void setOraWidth(int oraWidth) {
		this.oraWidth = oraWidth;
	}

	public int getOraHeight() {
		return oraHeight;
	}

	public void setOraHeight(int oraHeight) {
		this.oraHeight = oraHeight;
	}

	public String getFn() {
		return fn;
	}

	public void setFn(String fn) {
		this.fn = fn;
	}

	@Override
	public String[] getAttributesName() {
		return null;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_MESSAGEBOX;
	}

}
