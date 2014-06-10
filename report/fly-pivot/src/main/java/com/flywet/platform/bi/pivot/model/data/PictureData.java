package com.flywet.platform.bi.pivot.model.data;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.FileUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.pivot.exception.BIPivotException;
import com.flywet.platform.bi.pivot.model.IRegionData;
import com.flywet.platform.bi.pivot.model.context.IPictureContext;
import com.flywet.platform.bi.pivot.model.enums.PicturePositionEnum;
import com.tonbeller.wcf.controller.RequestContext;

public class PictureData implements IRegionData, IPictureContext {
	public static final String REGION_DATA_NAME = "Picturn";

	public static final String PROP_NAME_NAME = "name";
	public static final String PROP_NAME_EXT = "ext";
	public static final String PROP_NAME_DATA = "data";
	public static final String PROP_NAME_POSITION = "position";
	public static final String PROP_NAME_WIDTH = "width";
	public static final String PROP_NAME_HEIGHT = "height";

	private String name;

	private String ext;

	private byte[] data;

	private String dataRes;

	private Integer width;

	private Integer height;

	private PicturePositionEnum pos;

	public static PictureData instance(Node node) throws BIException {
		try {
			PictureData pd = new PictureData();

			pd.name = Const.NVL(
					XMLHandler.getTagAttribute(node, PROP_NAME_NAME), null);

			pd.ext = Const.NVL(XMLHandler.getTagAttribute(node, PROP_NAME_EXT),
					null);

			Integer posInt = Utils.toInt(
					XMLHandler.getTagAttribute(node, PROP_NAME_POSITION), null);
			if (posInt != null) {
				pd.pos = PicturePositionEnum.get(posInt.shortValue());
			}

			pd.width = Utils.toInt(
					XMLHandler.getTagAttribute(node, PROP_NAME_WIDTH), null);
			pd.height = Utils.toInt(
					XMLHandler.getTagAttribute(node, PROP_NAME_HEIGHT), null);

			Node dataNode = XMLHandler.getSubNode(node, PROP_NAME_DATA);
			if (dataNode != null) {
				pd.dataRes = Const.NVL(
						XMLHandler.getTagAttribute(dataNode, PROP_NAME_RES),
						null);

				String dataStr = XMLHandler.getNodeValue(dataNode);
				if (!Const.isEmpty(dataStr)) {
					pd.data = FileUtils.decodeBASE64(Const.removeTAB(Const
							.removeCRLF(dataStr)));
				}
			}
			return pd;

		} catch (Exception e) {
			throw new BIException("实例化图片组件出现错误：PicturnData");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		try {
			JSONObject jo = new JSONObject();

			if (name != null) {
				jo.put(PROP_NAME_NAME, name);
			}

			if (pos != null) {
				jo.put(PROP_NAME_POSITION, pos.getIndex());
			}

			if (width != null) {
				jo.put(PROP_NAME_WIDTH, width);
			}

			if (height != null) {
				jo.put(PROP_NAME_HEIGHT, height);
			}

			if (dataRes != null) {
				jo.put(PROP_NAME_RES, dataRes);
			}

			return jo;
		} catch (Exception e) {
			throw new BIPivotException("渲染图片区域数据出现错误.", e);
		}
	}

	@Override
	public void init(RequestContext context) throws BIException {

	}

	@Override
	public void flush(RequestContext context) throws BIException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTypeName() {
		return REGION_DATA_NAME;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getDataRes() {
		return dataRes;
	}

	public void setDataRes(String dataRes) {
		this.dataRes = dataRes;
	}

	public PicturePositionEnum getPos() {
		return pos;
	}

	public void setPos(PicturePositionEnum pos) {
		this.pos = pos;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Override
	public String getExt() {
		return ext;
	}

	@Override
	public Object findByName(String name) throws BIException {
		if (name.equals(this.name)) {
			return this;
		}
		return null;
	}

}
