/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * Copyright (C) 2003-2004 TONBELLER AG.
 * All Rights Reserved.
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 *
 * 
 */
package com.tonbeller.jpivot.table;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Element;

import com.tonbeller.jpivot.olap.model.Axis;
import com.tonbeller.jpivot.table.span.Span;

/**
 * @author av
 */
public class ColumnAxisBuilderImpl extends AxisBuilderSupport implements
		ColumnAxisBuilder {

	public ColumnAxisBuilderImpl() {
		super(new SpanBuilderImpl("column-heading", "heading-heading"));
		setMemberIndent(false);
		setShowParentMembers(false);
		setHierarchyHeader(HIERARCHY_HEADER);
		setMemberSpan(HIERARCHY_THEN_POSITION_SPAN);
		setHeaderSpan(HIERARCHY_THEN_POSITION_SPAN);
	}

	public void buildRow(Element parent, int rowIndex) {
		final int N = spanCalc.getPositionCount();
		for (int i = 0; i < N; i++) {
			boolean even = (i % 2 == 0);
			Span span = spanCalc.getSpan(i, rowIndex);
			if (span.isSignificant()) {
				int colspan = span.getPositionSpan();
				int rowspan = span.getHierarchySpan();
				buildHeading(parent, span, rowspan, colspan, even);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void buildRowJo(JSONArray parent, int rowIndex) {
		final int N = spanCalc.getPositionCount();
		for (int i = 0; i < N; i++) {
			boolean even = (i % 2 == 0);
			Span span = spanCalc.getSpan(i, rowIndex);
			if (span.isSignificant()) {
				int colspan = span.getPositionSpan();
				int rowspan = span.getHierarchySpan();
				Object[] b = buildHeadingJo(span, rowspan, colspan, even);
				JSONObject cell = (JSONObject) b[1];
				cell.put("_TAG", (String) b[0]);
				parent.add(cell);
			}
		}
	}

	public int getColumnCount() {
		return spanCalc.getPositionCount();
	}

	public int getRowCount() {
		return spanCalc.getHierarchyCount();
	}

	protected Axis getAxis() {
		return table.getColumnAxis();
	}
}
