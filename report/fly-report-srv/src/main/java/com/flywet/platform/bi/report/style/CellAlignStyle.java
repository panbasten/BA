package com.flywet.platform.bi.report.style;

import com.flywet.platform.bi.report.enums.AlignEnum;
import com.flywet.platform.bi.report.enums.VerticalEnum;

public class CellAlignStyle {

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

	private CellAlignStyle() {

	}

	public static CellAlignStyle getDefaultInstance() {
		CellAlignStyle cas = new CellAlignStyle();
		cas.setAlign(DefaultStyleConst.DEFAULT_ALIGN);
		cas.setVertical(DefaultStyleConst.DEFAULT_VERTICAL);
		cas.setIndentation(DefaultStyleConst.DEFAULT_INDENTATION);
		cas.setWrap(DefaultStyleConst.DEFAULT_WRAP);
		cas.setShrink(DefaultStyleConst.DEFAULT_SHRINK);
		return cas;
	}

	public static CellAlignStyle getInstance() {
		CellAlignStyle cas = new CellAlignStyle();
		return cas;
	}

	public AlignEnum getAlign() {
		return align;
	}

	public CellAlignStyle setAlign(AlignEnum align) {
		this.align = align;
		return this;
	}

	public VerticalEnum getVertical() {
		return vertical;
	}

	public CellAlignStyle setVertical(VerticalEnum vertical) {
		this.vertical = vertical;
		return this;
	}

	public float getIndentation() {
		return indentation;
	}

	public CellAlignStyle setIndentation(float indentation) {
		this.indentation = indentation;
		return this;
	}

	public boolean isWrap() {
		return wrap;
	}

	public CellAlignStyle setWrap(boolean wrap) {
		this.wrap = wrap;
		return this;
	}

	public boolean isShrink() {
		return shrink;
	}

	public CellAlignStyle setShrink(boolean shrink) {
		this.shrink = shrink;
		return this;
	}

}
