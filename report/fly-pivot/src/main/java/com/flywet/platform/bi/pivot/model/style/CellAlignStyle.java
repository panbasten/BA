package com.flywet.platform.bi.pivot.model.style;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.pivot.model.ICacheable;
import com.flywet.platform.bi.pivot.model.IPivotReport;
import com.flywet.platform.bi.pivot.model.def.DefaultConst;
import com.flywet.platform.bi.pivot.model.def.DefaultSetting;
import com.flywet.platform.bi.pivot.model.enums.AlignEnum;
import com.flywet.platform.bi.pivot.model.enums.VerticalEnum;
import com.tonbeller.wcf.controller.RequestContext;

public class CellAlignStyle implements ICacheable, IPivotReport {

	private static final long serialVersionUID = -492985424176818682L;

	private static final String PROP_NAME_ALIGN = "align";
	private static final String PROP_NAME_VALIGN = "valign";
	private static final String PROP_NAME_INDENT = "indent";
	private static final String PROP_NAME_WRAP = "wrap";
	private static final String PROP_NAME_EXPAND = "expand";
	private static final String PROP_NAME_SHRINK = "shrink";

	// 水平对齐
	private final AlignEnum align;

	// 垂直对齐
	private final VerticalEnum vertical;

	// 缩进
	private final Float indentation;

	// 自动换行
	private final Boolean wrap;

	// 自动扩展(当wrap属性同时为true时起效)
	private final Boolean expand;

	// 缩小字体填充
	private final Boolean shrink;

	// 缓存
	private static Map<String, SoftReference<CellAlignStyle>> CACHE = new ConcurrentHashMap<String, SoftReference<CellAlignStyle>>();

	private final String _uuid;

	private CellAlignStyle(AlignEnum align, VerticalEnum vertical,
			Float indentation, Boolean wrap, Boolean expand, Boolean shrink) {
		this.align = align;
		this.vertical = vertical;
		this.indentation = indentation;
		this.wrap = wrap;
		this.expand = expand;
		this.shrink = shrink;

		this._uuid = createUUID(align, vertical, indentation, wrap, expand,
				shrink);
	}

	public static CellAlignStyle instance(Node node) {

		Integer alignInt = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_ALIGN), null);
		AlignEnum align = null;
		if (alignInt != null) {
			align = AlignEnum.get(alignInt.shortValue());
		}

		Integer verticalInt = Utils.toInt(
				XMLHandler.getTagAttribute(node, PROP_NAME_VALIGN), null);
		VerticalEnum vertical = null;
		if (verticalInt != null) {
			vertical = VerticalEnum.get(verticalInt.shortValue());
		}

		Float indentation = Utils.toFloat(
				XMLHandler.getTagAttribute(node, PROP_NAME_INDENT), null);

		Boolean wrap = Utils.toBoolean(
				XMLHandler.getTagAttribute(node, PROP_NAME_WRAP), null);

		Boolean expand = Utils.toBoolean(
				XMLHandler.getTagAttribute(node, PROP_NAME_EXPAND), null);

		Boolean shrink = Utils.toBoolean(
				XMLHandler.getTagAttribute(node, PROP_NAME_SHRINK), null);

		return getInstance(align, vertical, indentation, wrap, expand, shrink);

	}

	public static CellAlignStyle getDefaultInstance() {
		return getInstance(DefaultSetting.DEFAULT_ALIGN,
				DefaultSetting.DEFAULT_VERTICAL, DefaultConst.UNDEFINED_FLOAT,
				DefaultSetting.DEFAULT_WRAP, DefaultSetting.DEFAULT_EXTEND,
				DefaultSetting.DEFAULT_SHRINK);
	}

	public static CellAlignStyle getInstance(AlignEnum align,
			VerticalEnum vertical, Float indentation, Boolean wrap,
			Boolean expand, Boolean shrink) {
		if (align == null && vertical == null && indentation == null
				&& wrap == null && expand == null && shrink == null) {
			return null;
		}

		String key = createUUID(align, vertical, indentation, wrap, expand,
				shrink);
		CellAlignStyle alignStyle = matchCache(key);
		if (alignStyle == null) {
			alignStyle = new CellAlignStyle(align, vertical, indentation, wrap,
					expand, shrink);
			putCache(key, alignStyle);
		}
		return alignStyle;
	}

	private static CellAlignStyle matchCache(String key) {
		SoftReference<CellAlignStyle> ref = CACHE.get(key);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	private static void putCache(String key, CellAlignStyle cas) {
		CACHE.put(key, new SoftReference<CellAlignStyle>(cas));
	}

	public static void clearCache() {
		CACHE.clear();
	}

	private static String createUUID(AlignEnum align, VerticalEnum vertical,
			Float indentation, Boolean wrap, Boolean expand, Boolean shrink) {
		String result = "";
		result = result + ((align != null) ? align.getIndex() : "") + ",";
		result = result + ((vertical != null) ? vertical.getIndex() : "") + ",";
		result = result + Const.NVL(indentation, "") + ",";
		result = result + Const.NVL(wrap, "") + ",";
		result = result + Const.NVL(expand, "") + ",";
		result = result + Const.NVL(shrink, "");
		return result;
	}

	@Override
	public String getUUID() {
		return _uuid;
	}

	public AlignEnum getAlign() {
		return align;
	}

	public VerticalEnum getVertical() {
		return vertical;
	}

	public Float getIndentation() {
		return indentation;
	}

	public Boolean isWrap() {
		return wrap;
	}

	public Boolean isExpand() {
		return expand;
	}

	public Boolean isShrink() {
		return shrink;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (align != null) {
			jo.put(PROP_NAME_ALIGN, align.getIndex());
		}
		if (vertical != null) {
			jo.put(PROP_NAME_VALIGN, vertical.getIndex());
		}
		if (indentation != null) {
			jo.put(PROP_NAME_INDENT, indentation);
		}
		if (wrap != null) {
			jo.put(PROP_NAME_WRAP, wrap);
		}
		if (expand != null) {
			jo.put(PROP_NAME_EXPAND, expand);
		}
		if (shrink != null) {
			jo.put(PROP_NAME_SHRINK, shrink);
		}

		return jo;
	}

	@Override
	public void init(RequestContext context) throws BIException {

	}

	@Override
	public Object findByName(String name) throws BIException {
		return null;
	}
}
