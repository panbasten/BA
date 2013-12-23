package com.flywet.platform.bi.pivot.model.olap.navi;

import java.util.Map;

import com.flywet.platform.bi.pivot.model.core.Extension;
import com.flywet.platform.bi.pivot.model.olap.model.Expression;

/**
 * sets a parameter of the MDX Query
 * 
 * @author av
 */
public interface SetParameter extends Extension {
	public static final String ID = "setParameter";

	/**
	 * sets a parameter
	 * 
	 * @param paramName
	 *            name of the parameter in the MDX query
	 * @param expr
	 *            the value of the parameter, e.g. a member
	 */
	void setParameter(String paramName, Expression expr);

	/**
	 * for scripting.
	 * 
	 * @return Map containing parameter names (= keys) and strings to display
	 *         value (= value)
	 */
	public Map getDisplayValues();

	/**
	 * return the names of all defined parameters in this query
	 * 
	 * @return the names of all defined parameters in this query
	 */
	public String[] getParameterNames();
}
