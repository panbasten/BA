package com.flywet.platform.bi.component.components.flow.step;

import com.flywet.platform.bi.component.components.flow.FlowStep;

public enum SysStepType {

	// 图片
	PICTRUE(PictureStep.TYPE_NAME) {

		@Override
		public FlowStep createStep() {
			return new PictureStep();
		}

	},

	// 图形
	FIGURE(FigureStep.TYPE_NAME) {

		@Override
		public FlowStep createStep() {
			return new FigureStep();
		}

	};

	private String name;

	SysStepType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract FlowStep createStep();

	public static SysStepType get(String name) {
		for (SysStepType state : SysStepType.values()) {
			if (state.getName().equalsIgnoreCase(name)) {
				return state;
			}
		}
		return PICTRUE;
	}

}
