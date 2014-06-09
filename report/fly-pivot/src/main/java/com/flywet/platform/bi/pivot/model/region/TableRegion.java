package com.flywet.platform.bi.pivot.model.region;

import org.json.simple.JSONObject;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.exception.BIPivotException;
import com.flywet.platform.bi.pivot.model.IRegionData;
import com.flywet.platform.bi.pivot.model.IRegionObject;
import com.flywet.platform.bi.pivot.model.factory.PivotRegionDataFactory;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * 表格区域
 * 
 * @author PeterPan
 * 
 */
public class TableRegion implements IRegionObject {

	public static final String REGION_OBJECT_NAME = "Table";

	private IRegionData regionData;

	public IRegionData getRegionData() {
		return regionData;
	}

	public void setRegionData(IRegionData regionData) {
		this.regionData = regionData;
	}

	public static TableRegion instance(Node node) throws BIException {
		TableRegion tr = new TableRegion();
		tr.regionData = PivotRegionDataFactory.resolver(node);
		return tr;
	}

	public static String persistent(TableRegion tr) {
		// TODO
		StringBuffer sb = new StringBuffer();

		return sb.toString();
	}

	@Override
	public void init(RequestContext context) throws BIException {
		try {
			if (regionData != null) {
				regionData.init(context);
			}
		} catch (Exception e) {
			throw new BIPivotException("初始化透视报表区域数据出现错误.", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		try {
			JSONObject jo = new JSONObject();
			jo.put(PROP_NAME_REGION_OBJECT_TYPE, getTypeName());
			if (regionData != null) {
				jo.put(PROP_NAME_REGION_DATA, regionData.renderJo(context));
			}
			return jo;
		} catch (Exception e) {
			throw new BIPivotException("渲染表格报表区域出现错误.", e);
		}
	}

	@Override
	public String getTypeName() {
		return REGION_OBJECT_NAME;
	}

	@Override
	public Object findByName(String name) throws BIException {
		Object rtn;
		if (regionData != null) {
			rtn = regionData.findByName(name);
			if (rtn != null)
				return rtn;
		}
		return null;
	}

}
