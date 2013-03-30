package com.plywet.platform.bi.component.vo;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.plywet.platform.bi.component.core.ComponentAttributeInterface;
import com.plywet.platform.bi.core.utils.PropertyUtils;
import com.plywet.platform.bi.core.utils.XmlUtils;

public class ComponentAttributeGroup extends BaseComponentAttribute implements
		ComponentAttributeInterface {

	private List<ComponentAttribute> attrs = new ArrayList<ComponentAttribute>();

	private ComponentAttributeGroup(Node node) throws Exception {
		this.name = XMLHandler.getTagAttribute(node, "name");

		this.description = PropertyUtils.getCodedTranslation(XmlUtils
				.getTagOrAttribute(node, "description"));

		this.tooltip = PropertyUtils.getCodedTranslation(XmlUtils
				.getTagOrAttribute(node, "tooltip"));

		NodeList children = node.getChildNodes();
		Node childnode;

		if (children != null) {
			for (int i = 0; i < children.getLength(); i++) {
				childnode = children.item(i);
				if (childnode.getNodeName().equalsIgnoreCase("attribute")) {
					addAttribute(childnode);
				}
			}
		}
	}

	public List<ComponentAttribute> getAttributes() {
		return attrs;
	}

	public void addAttribute(ComponentAttribute attr) {
		attrs.add(attr);
	}

	public void addAttribute(Node attributesNode) throws Exception {
		addAttribute(ComponentAttribute.instance(attributesNode));
	}

	public static ComponentAttributeGroup instance(Node node) throws Exception {
		return new ComponentAttributeGroup(node);
	}

}
