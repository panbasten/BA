package com.flywet.platform.bi.web.functions;

import java.util.List;

import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.component.components.selectMenu.OptionsData;
import com.flywet.platform.bi.component.utils.FLYFunctionMapper;

public class DIFunctions {

	private static Class<?> PKG = DIFunctions.class;

	private static String PREFIX = "di";

	private static OptionsData transStatusOptionsData = OptionsData
			.instance(new String[] {
					"",
					BaseMessages.getString(PKG,
							"Page.Trans.Transstatus.Draft.Label"),
					BaseMessages.getString(PKG,
							"Page.Trans.Transstatus.Production.Label") });

	public static List<String[]> transStatusOptions() {
		return transStatusOptionsData.getOptions();
	}

	public static void register() {
		if (!FLYFunctionMapper.singleton.contains(PREFIX)) {
			FLYFunctionMapper.singleton.register(PREFIX, DIFunctions.class);
		}
	}
}
