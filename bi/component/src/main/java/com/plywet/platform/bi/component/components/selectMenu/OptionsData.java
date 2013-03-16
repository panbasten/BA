package com.plywet.platform.bi.component.components.selectMenu;

import java.util.ArrayList;
import java.util.List;

public class OptionsData {

	private List<String[]> options = new ArrayList<String[]>();

	public List<String[]> getOptions() {
		return options;
	}

	public void addOption(String val, String label) {
		options.add(new String[] { val, label });
	}
}
