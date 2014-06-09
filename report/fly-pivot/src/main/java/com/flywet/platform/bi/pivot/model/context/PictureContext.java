package com.flywet.platform.bi.pivot.model.context;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.FileUtils;
import com.tonbeller.wcf.controller.RequestContext;

public class PictureContext implements IPictureContext {

	private byte[] data;

	private String name;

	private String ext;

	public static final String CONTEXT_TAG_NAME = "Picture";

	public static PictureContext instance(Node node) throws BIException {
		try {
			PictureContext pc = new PictureContext();

			pc.name = Const.NVL(XMLHandler.getTagAttribute(node, "name"), null);
			pc.ext = Const.NVL(XMLHandler.getTagAttribute(node, "ext"), null);

			String dataStr = XMLHandler.getNodeValue(node);
			if (!Const.isEmpty(dataStr)) {
				pc.data = FileUtils.decodeBASE64(Const.removeTAB(Const
						.removeCRLF(dataStr)));
			}

			return pc;
		} catch (Exception e) {
			throw new BIException("实例化上下文出现错误：PictureContext");
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

	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		return null;
	}

	@Override
	public byte[] getData() {
		return data;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getExt() {
		return ext;
	}

}
