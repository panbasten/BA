package com.flywet.platform.bi.base.rest;

import java.util.List;

import org.json.simple.JSONObject;
import org.pentaho.di.core.exception.KettleException;
import org.w3c.dom.Document;

import com.flywet.platform.bi.base.model.TemplateMeta;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.exception.BIPageException;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.rest.EditorResourceInterface;

public abstract class AbstractReportResource implements EditorResourceInterface {

	@SuppressWarnings("unchecked")
	protected JSONObject getReportPageJson(String id, TemplateMeta templateMeta)
			throws BIJSONException, BIPageException, KettleException {
		Document doc = templateMeta.getDoc();
		Object[] domString = PageTemplateInterpolator.interpolate("report:"
				+ id, doc, FLYVariableResolver.instance());

		JSONObject jo = new JSONObject();
		JSONObject reportInfo = new JSONObject();
		reportInfo.put("id", id);
		jo.put("reportInfo", reportInfo);
		jo.put("dom", (String) domString[0]);
		jo.put("script", JSONUtils
				.convertToJSONArray((List<String>) domString[1]));
		jo.put("domStructure", templateMeta.getXML());

		return jo;
	}

	protected String getTemplateString(String cate) {
		return EDITOR_TEMPLATE_PREFIX + cate + ".h";
	}

}
