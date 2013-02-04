package com.yonyou.bq8.di.component.components.flow;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.components.flow.util.FlowGraphHelper;
import com.yonyou.bq8.di.component.core.ComponentDataInterface;
import com.yonyou.bq8.di.core.exception.DIJSONException;

/**
 * 绘图元素
 * 
 * @author 潘巍（Peter Pan）
 * @since 2011-2-11 上午11:09:02
 */
public class FlowElementSet implements ComponentDataInterface {

	public static final String STEPS = "steps";

	public static final String HOPS = "hops";

	private List<FlowStep> vertexs;

	private List<FlowStep> steps;

	private List<FlowHop> hops;

	public List<FlowStep> getVertexs() {
		return vertexs;
	}

	public void setVertexs(List<FlowStep> vertexs) {
		this.vertexs = vertexs;
	}

	public void addVertex(FlowStep step) {
		addStep(step);
		if (this.vertexs == null)
			this.vertexs = new ArrayList<FlowStep>();
		this.vertexs.add(step);
	}

	public void addVertexById(String id) {
		if (this.vertexs == null)
			this.vertexs = new ArrayList<FlowStep>();
		FlowStep step = findStepById(id);
		if (step != null)
			this.vertexs.add(step);
	}

	public List<FlowStep> getSteps() {
		return steps;
	}

	public FlowStep getStep(int index) {
		return steps.get(index);
	}

	public FlowElementSet setSteps(List<FlowStep> steps) {
		this.steps = steps;
		return this;
	}

	public void addStep(FlowStep step) {
		if (step == null)
			return;
		if (this.steps == null) {
			this.steps = new ArrayList<FlowStep>();
		}
		this.steps.add(step);
	}

	public FlowStep findStepById(String id) {
		if (this.steps != null && id != null) {
			for (FlowStep step : this.steps) {
				if (id.equals(step.getId())) {
					return step;
				}
			}
		}
		return null;
	}

	public List<FlowHop> getHops() {
		return hops;
	}

	public FlowHop getHop(int index) {
		return hops.get(index);
	}

	public FlowElementSet setHops(List<FlowHop> hops) {
		this.hops = hops;
		return this;
	}

	public void addHop(FlowHop hop) {
		if (hop == null)
			return;
		if (this.hops == null) {
			this.hops = new ArrayList<FlowHop>();
		}
		this.hops.add(hop);
	}

	public void addHops(List<FlowHop> hops) {
		if (hops == null) {
			return;
		}
		if (this.hops == null) {
			this.hops = new ArrayList<FlowHop>();
		}
		this.hops.addAll(hops);
	}

	public FlowHop findHopById(String id) {
		if (this.hops != null) {
			for (FlowHop hop : this.hops) {
				if (id.equals(hop.getId())) {
					return hop;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormDataJo() throws DIJSONException  {
		try {
			JSONObject jo = new JSONObject();

			if (this.steps != null) {
				JSONArray ja = new JSONArray();
				for (FlowStep step : this.steps) {
					ja.add(step.getFormDataJo());
				}
				jo.put(STEPS, ja);
			}

			if (this.hops != null) {
				JSONArray ja = new JSONArray();
				for (FlowHop hop : this.hops) {
					ja.add(hop.getFormDataJo());
				}
				jo.put(HOPS, ja);
			}

			return jo;
		} catch (Exception e) {
			throw new DIJSONException("将该对象转换为json对象时出现错误.", e);
		}
	}

	@Override
	public void removeAll() {
		this.vertexs = null;

		if (this.hops != null) {
			for (FlowHop hop : this.hops) {
				hop.removeAll();
			}
		}

		if (this.steps != null) {
			for (FlowStep step : this.steps) {
				step.removeAll();
			}
		}

	}

	@Override
	public void construct(JSONObject json) throws DIJSONException {
		try {
			if (json.containsKey(STEPS)) {
				this.steps = new ArrayList<FlowStep>();
				JSONArray ja = (JSONArray) json.get(STEPS);
				JSONObject sjson;
				String type;
				for (int i = 0; i < ja.size(); i++) {
					sjson = (JSONObject) ja.get(i);
					type = (String) sjson.get(FlowStep.TYPE);
					FlowStep s = FlowGraphHelper.createStep(type);
					s.construct(sjson);
					this.steps.add(s);
				}
			}

			if (json.containsKey(HOPS)) {
				this.hops = new ArrayList<FlowHop>();
				JSONArray ja = (JSONArray) json.get(HOPS);
				JSONObject hjson;
				for (int i = 0; i < ja.size(); i++) {
					FlowHop h = FlowGraphHelper.createHop();
					hjson = (JSONObject) ja.get(i);
					h.construct(hjson);

					// 创建端点
					if (hjson.containsKey(FlowHop.FROM_EL_ID)) {
						String fromElId = (String) hjson
								.get(FlowHop.FROM_EL_ID);
						if (fromElId != null) {
							for (FlowStep s : this.steps) {
								if (fromElId.equals(s.getId())) {
									h.setFromEl(s);
									break;
								}
							}
						}
					}

					if (hjson.containsKey(FlowHop.TO_EL_ID)) {
						String toElId = (String) hjson.get(FlowHop.TO_EL_ID);
						if (toElId != null) {
							for (FlowStep s : this.steps) {
								if (toElId.equals(s.getId())) {
									h.setToEl(s);
									break;
								}
							}
						}
					}

					h.createId();

					this.hops.add(h);
				}
			}
		} catch (Exception e) {
			throw new DIJSONException("由json对象构造该对象时出现错误.", e);
		}
	}

}
