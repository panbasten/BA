package com.flywet.platform.bi.web.functions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;

import com.flywet.platform.bi.component.components.grid.GridDataObject;
import com.flywet.platform.bi.component.components.selectMenu.OptionsData;
import com.flywet.platform.bi.component.utils.FLYFunctionMapper;
import com.flywet.platform.bi.component.utils.HTML;

public class DIFunctions {

	private static Class<?> PKG = DIFunctions.class;

	private static String PREFIX = "di";

	// valueTypes
	private static final String OPTIONS_KEY_VALUE_TYPES = "valueTypes";
	private static OptionsData valueTypesOptionsData = OptionsData
			.instanceSimpleStrings(ValueMeta.getTypes());

	// transStatus
	private static final String OPTIONS_KEY_TRANS_STATUS = "transStatus";
	private static OptionsData transStatusOptionsData = OptionsData
			.instance(new String[] {
					BaseMessages.getString(PKG, "Page.Option.Empty.Label"),
					BaseMessages.getString(PKG,
							"Page.Trans.Transstatus.Draft.Label"),
					BaseMessages.getString(PKG,
							"Page.Trans.Transstatus.Production.Label") });

	private static Map<String, OptionsData> options = new ConcurrentHashMap<String, OptionsData>();
	private static AtomicBoolean initCache = new AtomicBoolean(false);

	static {
		if (!initCache.get()) {
			options.put(OPTIONS_KEY_VALUE_TYPES, valueTypesOptionsData);
			options.put(OPTIONS_KEY_TRANS_STATUS, transStatusOptionsData);
			initCache.set(true);
		}
	}

	/**
	 * 获得指定选项
	 * 
	 * @param key
	 * @return
	 */
	public static List<String[]> getOptions(String key) {
		return options.get(key).getOptions();
	}

	/**
	 * 获得所有数据库连接的选项
	 * 
	 * @param transMeta
	 * @return
	 */
	public static List<String[]> allDatabaseOptions(TransMeta transMeta) {
		OptionsData od = OptionsData.instance();
		DatabaseMeta dm;

		for (int i = 0; i < transMeta.nrDatabases(); i++) {
			dm = transMeta.getDatabase(i);
			od.addOption(dm.getObjectId().getId(), dm.getName());
		}

		return od.getOptions();
	}

	/**
	 * 创建数据表格数据集
	 * 
	 * @param keys
	 * @param values
	 * @return
	 */
	public static GridDataObject createDGDataSet(String[] keys,
			Object[][] values) {
		GridDataObject gd = GridDataObject.instance().setMinRows(
				HTML.DEFAULT_GRID_ROW_NUMBER);
		gd.putObjects(keys, values).transpose();
		return gd;
	}

	public static void register() {
		if (!FLYFunctionMapper.singleton.contains(PREFIX)) {
			FLYFunctionMapper.singleton.register(PREFIX, DIFunctions.class);
		}
	}
}
