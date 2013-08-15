package com.flywet.platform.bi.component.components.selectMenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OptionsData {

	private List<String[]> options = new ArrayList<String[]>();

	private OptionsData() {

	}

	public static final OptionsData instance() {
		return new OptionsData();
	}

	public static final OptionsData instance(String[] labels) {
		OptionsData od = new OptionsData();
		for (int i = 0; i < labels.length; i++) {
			od.addOption(String.valueOf(i), labels[i]);
		}
		return od;
	}

	public static final OptionsData instance(Map<String, String> opts) {
		OptionsData od = new OptionsData();
		if (opts != null) {
			for (Iterator<String> iter = opts.keySet().iterator(); iter
					.hasNext();) {
				String key = iter.next();
				od.addOption(key, opts.get(key));
			}
		}
		return od;
	}

	public List<String[]> getOptions() {
		return options;
	}

	public void addOption(String val, String label) {
		options.add(new String[] { val, label });
	}
}
