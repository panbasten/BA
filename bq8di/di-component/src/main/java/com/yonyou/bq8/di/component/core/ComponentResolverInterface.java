package com.yonyou.bq8.di.component.core;

import java.util.List;

import org.w3c.dom.Node;

import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.HTMLWriter;
import com.yonyou.bq8.di.core.exception.DIPageException;

public interface ComponentResolverInterface {

	public void render(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException;

	public void renderStart(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException;

	public void renderSub(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException;

	public void renderEnd(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException;

	public void renderScript(Node node, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException;

}
