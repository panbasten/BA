package com.plywet.platform.bi.component.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTMLWriter {

	private List<HTMLElement> els = new ArrayList<HTMLElement>();

	private HTMLElement current;

	public static HTMLWriter instance() {
		return new HTMLWriter();
	}

	public static void writeAttributeFromMap(HTMLWriter writer,
			Map<String, Object> attrsMap, String[] attrsName) {
		if (attrsName != null) {
			for (String n : attrsName) {
				if (attrsMap.containsKey(n)) {
					writer.writeAttribute(n, attrsMap.get(n));
				}
			}
		}
	}

	public void startElement(String tag) {
		if (current == null) {
			current = new HTMLElement(tag);
			els.add(current);
		} else {
			HTMLElement tmp = new HTMLElement(tag, current);
			current.addSubEls(tmp);
			current = tmp;
		}
	}

	public void endElement(String tag) {
		if (current != null) {
			current = current.getPEl();
		}
	}

	public void writeAttribute(String attrName, Object attrValue) {
		if (current != null) {
			current.addTagAttributes(attrName, attrValue);
		}
	}

	public void writeText(String text) {
		if (current == null) {
			current = new HTMLElement("");
			els.add(current);
		}
		current.addText(text);
	}

	public String toString() {
		String w = "";
		for (HTMLElement el : els) {
			w += el.toString();
		}
		return w;
	}

	/**
	 * HTML元素
	 * 
	 * @author PeterPan
	 * 
	 */
	class HTMLElement {
		private String tag;
		private Map<String, Object> tagAttributes;
		private String text;
		private HTMLElement pEl;
		private List<HTMLElement> subEls;

		public HTMLElement(String tag) {
			this.tag = tag;
		}

		public HTMLElement(String tag, HTMLElement pEl) {
			this.tag = tag;
			this.pEl = pEl;
		}

		public void addText(String text) {
			if (this.text == null) {
				this.text = text;
			} else {
				this.text += text;
			}
		}

		public String toString() {
			String s = "";
			if (this.tag != null && !"".equals(this.tag)) {
				s += "<";
				s += this.tag;
				s += getAttributesString();
				s += ">";
			}
			if (subEls != null) {
				for (HTMLElement e : subEls) {
					s += e.toString();
				}
			}
			if (this.text != null) {
				s += this.text;
			}
			if (this.tag != null && !"".equals(this.tag)) {
				s += "</";
				s += this.tag;
				s += ">";
			}
			return s;
		}

		public String getTag() {
			return tag;
		}

		public HTMLElement getPEl() {
			return pEl;
		}

		public String getAttributesString() {
			String a = "";
			if (this.tagAttributes != null) {
				for (Map.Entry<String, Object> e : this.tagAttributes
						.entrySet()) {
					a += " ";
					a += e.getKey();
					a += "=";
					if (e.getValue() instanceof String
							|| e.getValue() instanceof Boolean
							|| e.getValue() == null) {
						a += "\"";
						a += String.valueOf(e.getValue());
						a += "\"";
					} else {
						a += String.valueOf(e.getValue());
					}
				}
			}
			return a;
		}

		public Map<String, Object> getTagAttributes() {
			return tagAttributes;
		}

		public void addTagAttributes(String attrName, Object attrValue) {
			if (this.tagAttributes == null)
				this.tagAttributes = new HashMap<String, Object>();
			this.tagAttributes.put(attrName, attrValue);
		}

		public List<HTMLElement> getSubEls() {
			return subEls;
		}

		public void addSubEls(HTMLElement subEls) {
			if (this.subEls == null)
				this.subEls = new ArrayList<HTMLElement>();
			this.subEls.add(subEls);
		}

	}
}
