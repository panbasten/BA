package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;
import com.flywet.platform.bi.pivot.model.olap.model.Expression;
import com.flywet.platform.bi.pivot.model.olap.model.Hierarchy;
import com.flywet.platform.bi.pivot.model.olap.model.Member;
import com.flywet.platform.bi.pivot.model.olap.model.PropertyExpr;

/**
 * erzeugt ein berechnetes Element
 * 
 * @author av
 */
public interface CalcMember extends Extension {
	public static final String ID = "calcMember";

	/**
	 * creates a new Measure
	 * 
	 * @param name
	 *            name of the new measure
	 * @param expr
	 *            calculation of new measure
	 * @param format
	 *            null or format_string property
	 * @return
	 * @throws InvalidExpressionException
	 */
	Member createMeasure(String name, Expression expr, Expression format,
			PropertyExpr[] propex) throws InvalidExpressionException;

	Member createMember(String name, Expression expr, Hierarchy hier,
			Member parent) throws InvalidExpressionException;

}
