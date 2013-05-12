package com.plywet.platform.bi.web.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pentaho.di.core.undo.TransAction;
import org.w3c.dom.Document;

import edu.emory.mathcs.backport.java.util.Collections;

public class TemplateCache {

	private static Map<String, TemplateCacheObject> DASHBOARD_TEMPLATES = Collections
			.synchronizedMap(new HashMap<String, TemplateCacheObject>());

	public static void put(String id, Document doc) {
		DASHBOARD_TEMPLATES.put(id, new TemplateCacheObject(doc));
	}

	public static Document get(String id) {
		TemplateCacheObject obj = DASHBOARD_TEMPLATES.get(id);
		if (obj != null) {
			return obj.getDoc();
		}
		return null;
	}
}

class TemplateCacheObject {
	// TODO
	private String user;
	
	private Document doc;

	protected List<TemplateAction> undo;

	TemplateCacheObject(Document doc) {
		this.doc = doc;
	}

	public Document getDoc() {
		return doc;
	}

	public String getUser() {
		return user;
	}

}