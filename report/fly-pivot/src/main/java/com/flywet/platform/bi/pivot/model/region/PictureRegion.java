package com.flywet.platform.bi.pivot.model.region;

import org.json.simple.JSONObject;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.exception.BIPivotException;
import com.flywet.platform.bi.pivot.model.IRegionData;
import com.flywet.platform.bi.pivot.model.IRegionObject;
import com.flywet.platform.bi.pivot.model.data.PictureData;
import com.tonbeller.wcf.controller.RequestContext;

public class PictureRegion implements IRegionObject {

	public static final String REGION_OBJECT_NAME = "Picture";

	private IRegionData regionData;

	public IRegionData getRegionData() {
		return regionData;
	}

	public void setRegionData(IRegionData regionData) {
		this.regionData = regionData;
	}

	public static PictureRegion instance(Node node) throws BIException {
		PictureRegion pr = new PictureRegion();
		pr.regionData = PictureData.instance(node);
		return pr;
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
			throw new BIPivotException("渲染图片区域出现错误.", e);
		}
	}

	@Override
	public void init(RequestContext context) throws BIException {
		try {
			if (regionData != null) {
				regionData.init(context);
			}
		} catch (Exception e) {
			throw new BIPivotException("初始化图片区域数据出现错误.", e);
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
