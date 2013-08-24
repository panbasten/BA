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

	/**
	 * 实例化字符串数组，key是数组顺序，从0开始
	 * 
	 * @param labels
	 * @return
	 */
	public static final OptionsData instance(String[] labels) {
		OptionsData od = new OptionsData();
		for (int i = 0; i < labels.length; i++) {
			od.addOption(String.valueOf(i), labels[i]);
		}
		return od;
	}

	/**
	 * 实例化简单字符串数组，key和value相同
	 * 
	 * @param labels
	 * @return
	 */
	public static final OptionsData instanceSimpleStrings(String[] labels) {
		OptionsData od = new OptionsData();
		for (int i = 0; i < labels.length; i++) {
			od.addOption(labels[i], labels[i]);
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
