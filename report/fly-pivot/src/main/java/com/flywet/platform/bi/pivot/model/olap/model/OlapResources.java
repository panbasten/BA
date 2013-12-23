package com.flywet.platform.bi.pivot.model.olap.model;

import java.util.Locale;

import com.flywet.platform.bi.pivot.model.res.Resources;

public class OlapResources {

	private OlapResources() {
	}

	public static Resources instance(Locale locale) {
		return Resources.instance(locale, OlapResources.class);
	}

}
