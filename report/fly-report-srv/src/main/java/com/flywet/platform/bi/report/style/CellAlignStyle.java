package com.flywet.platform.bi.report.style;

import java.util.Map;
import java.util.WeakHashMap;

import org.pentaho.di.core.Const;

import com.flywet.platform.bi.report.enums.AlignEnum;
import com.flywet.platform.bi.report.enums.VerticalEnum;
import com.flywet.platform.bi.report.utils.DefaultConst;
import com.flywet.platform.bi.report.utils.DefaultSetting;

public class CellAlignStyle implements ICellAlignStyle {

	// 水平对齐
	private AlignEnum align;

	// 垂直对齐
	private VerticalEnum vertical;

	// 缩进
	private float indentation;

	// 自动换行
	private boolean wrap;

	// 缩小字体填充
	private boolean shrink;

	// 缓存
	private static Map<String, ICellAlignStyle> CACHE = new WeakHashMap<String, ICellAlignStyle>();

	private String _uuid;

	private CellAlignStyle(AlignEnum align, VerticalEnum vertical,
			float indentation, boolean wrap, boolean shrink) {
		this.align = align;
		this.vertical = vertical;
		this.indentation = indentation;
		this.wrap = wrap;
		this.shrink = shrink;

		this._uuid = createUUID(align, vertical, indentation, wrap, shrink);
	}

	public static ICellAlignStyle getDefaultInstance() {
		return getInstance(null, null, DefaultConst.UNDEFINED_INT, null, null);
	}

	public static ICellAlignStyle getInstance(AlignEnum align,
			VerticalEnum vertical, float indentation, Boolean wrap,
			Boolean shrink) {
		align = Const.NVL(align, DefaultSetting.DEFAULT_ALIGN);
		vertical = Const.NVL(vertical, DefaultSetting.DEFAULT_VERTICAL);
		indentation = (indentation < 0) ? DefaultConst.UNDEFINED_INT
				: indentation;
		wrap = Const.NVL(wrap, DefaultSetting.DEFAULT_WRAP);
		shrink = Const.NVL(shrink, DefaultSetting.DEFAULT_SHRINK);

		String key = createUUID(align, vertical, indentation, wrap, shrink);
		ICellAlignStyle alignStyle = CACHE.get(key);
		if (alignStyle == null) {
			alignStyle = new CellAlignStyle(align, vertical, indentation, wrap,
					shrink);
			CACHE.put(key, alignStyle);
		}
		return alignStyle;
	}

	private static String createUUID(AlignEnum align, VerticalEnum vertical,
			Float indentation, Boolean wrap, Boolean shrink) {
		String result = "";
		result = result + align.getIndex() + ",";
		result = result + vertical.getIndex() + ",";
		result = result + indentation + ",";
		result = result + wrap + ",";
		result = result + shrink;
		return result;
	}

	@Override
	public String getUUID() {
		return _uuid;
	}

	@Override
	public AlignEnum getAlign() {
		return align;
	}

	@Override
	public VerticalEnum getVertical() {
		return vertical;
	}

	@Override
	public float getIndentation() {
		return indentation;
	}

	@Override
	public boolean isWrap() {
		return wrap;
	}

	@Override
	public boolean isShrink() {
		return shrink;
	}

}
