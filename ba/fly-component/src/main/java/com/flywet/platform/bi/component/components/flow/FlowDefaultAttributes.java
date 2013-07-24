package com.flywet.platform.bi.component.components.flow;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;

import com.flywet.platform.bi.component.components.flow.util.FlowGraphHelper;
import com.flywet.platform.bi.component.core.ComponentDataInterface;
import com.flywet.platform.bi.core.exception.BIJSONException;

public class FlowDefaultAttributes implements ComponentDataInterface {

	public static final String ON_INIT_STEP = "onInitStep";

	public static final String ON_INIT_HOP = "onInitHop";

	private FlowStep onInitStep;

	private FlowHop onInitHop;

	public FlowStep getOnInitStep() {
		return onInitStep;
	}

	public void setOnInitStep(FlowStep onInitStep) {
		this.onInitStep = onInitStep;
	}

	public FlowHop getOnInitHop() {
		return onInitHop;
	}

	public void setOnInitHop(FlowHop onInitHop) {
		this.onInitHop = onInitHop;
	}

	@Override
	public void removeAll() {
		if (this.onInitStep != null)
			this.onInitStep.removeAll();
		if (this.onInitHop != null)
			this.onInitHop.removeAll();
	}

	@Override
	public void construct(JSONObject json) throws BIJSONException {
		try {
			if (json.containsKey(ON_INIT_STEP)) {
				JSONObject sjson = (JSONObject) json.get(ON_INIT_STEP);
				if (this.onInitStep == null) {
					String type = (String) sjson.get(FlowStep.TYPE);
					if (Const.isEmpty(type)) {
						this.onInitStep = FlowGraphHelper.createDefaultStep();
					} else {
						this.onInitStep = FlowGraphHelper.createStep(type);
					}
				}
				this.onInitStep.construct(sjson);
			}

			if (json.containsKey(ON_INIT_HOP)) {
				if (this.onInitHop == null) {
					this.onInitHop = FlowGraphHelper.createHop();
				}
				this.onInitHop.construct((JSONObject) json.get(ON_INIT_HOP));
			}
		} catch (Exception e) {
			throw new BIJSONException("由json对象构造该对象时出现错误.", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormDataJo() throws BIJSONException {
		try {
			JSONObject jo = new JSONObject();
			if (this.onInitStep != null)
				jo.put(ON_INIT_STEP, this.onInitStep.getFormDataJo());
			if (this.onInitHop != null)
				jo.put(ON_INIT_HOP, this.onInitHop.getFormDataJo());
			return jo;
		} catch (Exception e) {
			throw new BIJSONException("将该对象转换为json对象时出现错误.", e);
		}
	}

}
