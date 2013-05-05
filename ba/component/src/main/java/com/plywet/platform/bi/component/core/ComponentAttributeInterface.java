package com.plywet.platform.bi.component.core;

import org.json.simple.JSONObject;

public interface ComponentAttributeInterface {

	public String getName();

	public void setName(String name);

	public String getDescription();

	public void setDescription(String description);

	public String getTooltip();

	public void setTooltip(String tooltip);

	public JSONObject toJSONObject();

}
