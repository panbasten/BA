package com.yonyou.bq8.di.component.resolvers.label;

import java.util.List;

import org.pentaho.pms.util.Const;
import org.w3c.dom.Node;

import com.yonyou.bq8.di.component.core.ComponentResolverInterface;
import com.yonyou.bq8.di.component.resolvers.BaseComponentResolver;
import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.component.utils.HTMLWriter;
import com.yonyou.bq8.di.core.exception.DIPageException;

public class BQLabelResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	public static final String ATTR_LABEL_DIV = "labelDiv";
	public static final String ATTR_LABEL_WIDTH = "labelWidth";

	public static final String ATTR_LABEL_TYPE_LABEL = "label";
	public static final String ATTR_LABEL_TYPE_LABEL_SINGLE = "labelSingle";
	public static final String ATTR_LABEL_TYPE_FIELDSET = "fieldset";

	public static final int STYLE_WIDTH_LABEL = 35;

	public static final String LABLE_STYLE_CLASS = "ui-label-default ui-helper-clearfix";
	public static final String LABLE_SINGLE_STYLE_CLASS = "ui-label-single ui-helper-clearfix";
	public static final String LABLE_SINGLE_OBJECT_STYLE_CLASS = "ui-label-single-object ui-helper-clearfix";
	public static final String LABLE_FIELDSET_STYLE_CLASS = "ui-label-fieldset";

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
		String typeStr = HTML.getTagAttribute(node, HTML.ATTR_TYPE, attrs);

		if (ATTR_LABEL_TYPE_FIELDSET.equalsIgnoreCase(typeStr)) {
			renderFieldset(node, html, script, attrs, fileUrl);
		} else if (ATTR_LABEL_TYPE_LABEL_SINGLE.equalsIgnoreCase(typeStr)) {
			renderLableSingle(node, html, script, attrs, fileUrl);
		} else {
			renderLabel(node, html, script, attrs, fileUrl);
		}
	}

	private void renderFieldset(Node node, HTMLWriter html,
			List<String> script, BQVariableResolver attrs, String fileUrl)
			throws DIPageException {
		html.startElement(HTML.COMPONENT_TYPE_BASE_FIELDSET);
		html.writeAttribute(HTML.ATTR_CLASS, LABLE_FIELDSET_STYLE_CLASS);
		renderShow(node, html, attrs);
		html.startElement(HTML.COMPONENT_TYPE_BASE_LEGEND);
		html.writeText(Const.NVL(HTML.getTagAttribute(node, HTML.ATTR_TITLE,
				attrs), ""));
		html.endElement(HTML.COMPONENT_TYPE_BASE_LEGEND);
		super.renderSub(node, html, script, attrs, fileUrl);
		html.endElement(HTML.COMPONENT_TYPE_BASE_FIELDSET);
	}

	private void renderLableSingle(Node node, HTMLWriter html,
			List<String> script, BQVariableResolver attrs, String fileUrl)
			throws DIPageException {
		html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
		html.writeAttribute(HTML.ATTR_CLASS, LABLE_SINGLE_STYLE_CLASS);
		renderShow(node, html, attrs);
		html.startElement(HTML.COMPONENT_TYPE_BASE_LABEL);
		String forStr = HTML.getTagAttribute(node, HTML.ATTR_FOR, attrs);
		if (forStr != null) {
			html.writeAttribute(HTML.ATTR_FOR, forStr);
		}
		html.writeText(Const.NVL(HTML.getTagAttribute(node, HTML.ATTR_TITLE,
				attrs), ""));
		html.endElement(HTML.COMPONENT_TYPE_BASE_LABEL);
		html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);

		html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
		html.writeAttribute(HTML.ATTR_CLASS, LABLE_SINGLE_OBJECT_STYLE_CLASS);
		renderShow(node, html, attrs);
		super.renderSub(node, html, script, attrs, fileUrl);
		html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
	}

	private void renderLabel(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
		html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
		html.writeAttribute(HTML.ATTR_CLASS, LABLE_STYLE_CLASS);
		String labelDivStr = HTML.getTagAttribute(node, ATTR_LABEL_DIV, attrs);
		int labelDiv = 1;
		if (labelDivStr != null) {
			labelDiv = Integer.valueOf(labelDivStr);
		}
		// 
		float labelWidth = STYLE_WIDTH_LABEL;
		String labelWidthStr = HTML.getTagAttribute(node, ATTR_LABEL_WIDTH,
				attrs);
		if (labelWidthStr != null) {
			labelWidth = (labelWidthStr.endsWith("%")) ? Float
					.valueOf(labelWidthStr.substring(0,
							labelWidthStr.length() - 1)) : Float
					.valueOf(labelWidthStr);
		}
		labelWidth = labelWidth / labelDiv;
		float objectWidth = 100 - labelWidth;
		html.writeAttribute(HTML.ATTR_STYLE, "width:" + labelWidth + "%;"
				+ getShowStyle(node, html, attrs));
		html.startElement(HTML.COMPONENT_TYPE_BASE_LABEL);
		String forStr = HTML.getTagAttribute(node, HTML.ATTR_FOR, attrs);
		if (forStr != null) {
			html.writeAttribute(HTML.ATTR_FOR, forStr);
		}
		html.writeText(Const.NVL(HTML.getTagAttribute(node, HTML.ATTR_TITLE,
				attrs), ""));
		html.endElement(HTML.COMPONENT_TYPE_BASE_LABEL);
		html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);

		html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
		html.writeAttribute(HTML.ATTR_CLASS, LABLE_STYLE_CLASS);
		html.writeAttribute(HTML.ATTR_STYLE, "width:" + objectWidth + "%;"
				+ getShowStyle(node, html, attrs));
		super.renderSub(node, html, script, attrs, fileUrl);
		html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
	}

}
