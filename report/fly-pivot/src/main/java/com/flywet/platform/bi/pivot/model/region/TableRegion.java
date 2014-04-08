package com.flywet.platform.bi.pivot.model.region;

import org.json.simple.JSONObject;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.exception.BIPivotException;
import com.flywet.platform.bi.pivot.model.IRegionData;
import com.flywet.platform.bi.pivot.model.IRegionObject;
import com.flywet.platform.bi.pivot.model.data.PivotData;
import com.flywet.platform.bi.pivot.model.factory.PivotRegionDataFactory;
import com.tonbeller.jpivot.table.TableComponent;
import com.tonbeller.jpivot.table.TableComponentFactory;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * 表格区域
 * 
 * @author PeterPan
 * 
 */
public class TableRegion implements IRegionObject {

	public static final String REGION_OBJECT_NAME = "TableRegion";

	private IRegionData regionData;

	// 透视报表表格组件
	private TableComponent tc;

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
	public void init(RequestContext context) throws BIException {
		try {
			if (regionData != null) {
				regionData.init();
			}
			if (PivotData.REGION_DATA_NAME.equals(regionData.getTypeName())) {
				PivotData pd = (PivotData) regionData;
				tc = TableComponentFactory.instance(
						"olap_" + pd.getCatalogId(), pd.getOm());
				tc.initialize(context);
			}
			// TODO 其他类型数据
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
				if (PivotData.REGION_DATA_NAME.equals(regionData.getTypeName())) {
					JSONObject regionDataJo = regionData.renderJo(context);
					regionDataJo.put(IRegionData.PROP_NAME_DATA, tc
							.renderJo(context));
					jo.put(PROP_NAME_REGION_DATA, regionDataJo);
				}
				// TODO 其他类型渲染
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

}
