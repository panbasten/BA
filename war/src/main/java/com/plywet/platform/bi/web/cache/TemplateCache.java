package com.plywet.platform.bi.web.cache;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import edu.emory.mathcs.backport.java.util.Collections;

public class TemplateCache {

	private static Map<String, Document> DASHBOARD_TEMPLATES = Collections
			.synchronizedMap(new HashMap<String, Document>());
	
	public static void put(){
		
	}
}
