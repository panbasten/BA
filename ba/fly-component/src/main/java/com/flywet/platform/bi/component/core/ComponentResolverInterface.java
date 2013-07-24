package com.flywet.platform.bi.component.core;

import java.util.List;

import org.w3c.dom.Node;

import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.core.exception.BIPageException;

public interface ComponentResolverInterface {

	public void render(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException;

	public void renderStart(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException;

	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException;

	public void renderEnd(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException;

	public void renderScript(Node node, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException;

}
