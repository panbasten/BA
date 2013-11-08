package com.flywet.platform.bi.di.model;

import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.pms.util.Const;

public class CheckResultObject {

	private String stepName;

	private int type;

	private String typeText;

	private String text;

	private CheckResultObject(CheckResultInterface remark) {
		StepMeta stepMeta = (StepMeta) remark.getSourceInfo();
		this.stepName = (stepMeta != null) ? stepMeta.getName() : "<全局>";
		this.type = remark.getType();
		this.typeText = remark.getType() + " - " + remark.getTypeDesc();
		this.text = Const.NVL(remark.getText(), "");
	}

	public static CheckResultObject instance(CheckResultInterface remark) {
		return new CheckResultObject(remark);
	}

	public String getStepName() {
		return stepName;
	}

	public String getTypeText() {
		return typeText;
	}

	public String getText() {
		return text;
	}

	public int getType() {
		return type;
	}

}
