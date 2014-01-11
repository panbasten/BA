package com.flywet.platform.bi.pivot.model.region;

import org.json.simple.JSONObject;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.IRegionData;
import com.flywet.platform.bi.pivot.model.IRegionObject;
import com.flywet.platform.bi.pivot.model.factory.PivotRegionDataFactory;
import com.tonbeller.wcf.controller.RequestContext;

public class TableRegion implements IRegionObject {

	public static final String REGION_OBJECT_NAME = "TableRegion";

	private IRegionData regionData;

	public IRegionData getRegionData() {
		return regionData;
	}

	public void setRegionData(IRegionData regionData) {
		this.regionData = regionData;
	}

	public static TableRegion instance(Node node) {
		TableRegion tr = new TableRegion();
		tr.regionData = PivotRegionDataFactory.resolver(node);
		return tr;
	}

	@Override
	public void init() throws BIException {
		if (regionData != null) {
			regionData.init();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();
		jo.put(PROP_NAME_REGION_OBJECT_TYPE, getTypeName());
		if (regionData != null) {
			jo.put(PROP_NAME_REGION_DATA, regionData.renderJo(context));
		}
		return jo;
	}

	@Override
	public String getTypeName() {
		return REGION_OBJECT_NAME;
	}

}
