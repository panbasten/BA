package com.plywet.platform.bi.component.vo;

import org.w3c.dom.Node;

public class ComponentSlot {
	
	private ComponentSlot(Node node) {

	}

	public static ComponentSlot instance(Node node) throws Exception {
		return new ComponentSlot(node);
	}
}
