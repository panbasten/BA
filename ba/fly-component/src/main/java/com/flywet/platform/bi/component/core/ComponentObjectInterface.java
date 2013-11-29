package com.flywet.platform.bi.component.core;

import java.io.Serializable;

import org.json.simple.JSONObject;

public interface ComponentObjectInterface extends Serializable, Cloneable {
	public JSONObject toJSONObject();
}
