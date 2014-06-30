package com.flywet.platform.bi.pivot.model.dataformat;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.tonbeller.wcf.controller.RequestContext;

public class DataBarDataFormat implements IDataFormat {

	public static final String DATA_FORMAT_TYPE = "dataBar";

	private String name;

	public static DataBarDataFormat instance(Node node) throws BIException {
		try {
			DataBarDataFormat dbdf = new DataBarDataFormat();

			dbdf.name = Const.NVL(XMLHandler.getTagAttribute(node, "name"),
					null);

			return dbdf;

		} catch (Exception e) {
			throw new BIException("实例化数据条数据格式出现错误：DataBarDataFormat");
		}

	}

	@Override
	public void init(RequestContext context) throws BIException {

	}

	@Override
	public Object findByName(String name) throws BIException {
		if (name.equals(this.name)) {
			return this;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();
		jo.put("type", DATA_FORMAT_TYPE);
		jo.put("name", getName());
		return jo;
	}

	@Override
	public String getName() {
		return name;
	}

}
