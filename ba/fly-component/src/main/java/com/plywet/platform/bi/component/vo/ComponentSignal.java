package com.plywet.platform.bi.component.vo;

import org.w3c.dom.Node;

public class ComponentSignal {

	private ComponentSignal(Node node) {

	}

	public static ComponentSignal instance(Node node) throws Exception {
		return new ComponentSignal(node);
	}
}
