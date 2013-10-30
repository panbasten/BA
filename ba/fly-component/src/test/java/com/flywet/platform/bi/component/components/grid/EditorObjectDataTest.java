package com.flywet.platform.bi.component.components.grid;

import org.pentaho.di.core.row.ValueMeta;

import junit.framework.TestCase;

import com.flywet.platform.bi.component.components.combo.ComboBoxMeta;
import com.flywet.platform.bi.component.vo.NameValuePair;
import com.flywet.platform.bi.core.exception.BIJSONException;

public class EditorObjectDataTest extends TestCase {

	public void testText() throws BIJSONException {
		EditorObjectData eod = new EditorObjectData();
		eod.initText();
		System.out.println("--------text-------");
		System.out.println(eod.getFormDataJo().toJSONString());
	}

	public void testCombobox() throws BIJSONException {
		EditorObjectData eod = new EditorObjectData();
		ComboBoxMeta cbm = new ComboBoxMeta();
		cbm.setLocalDataWithNameValuePair(NameValuePair.instance(ValueMeta
				.getTypes()));
		eod.initCombobox(cbm);
		System.out.println("--------combo box-------");
		System.out.println(eod.getFormDataJo().toJSONString());
	}
}
