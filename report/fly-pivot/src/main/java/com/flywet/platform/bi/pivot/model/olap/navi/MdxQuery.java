package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;
import com.tonbeller.wcf.format.FormatException;

/**
 * Lets the user view and modify the mdx
 * 
 * @author av
 */
public interface MdxQuery extends Extension {
	/**
	 * name of the Extension for lookup
	 */
	public static final String ID = "mdxQuery";

	/**
	 * returns the current (valid) mdx query
	 * 
	 * @return String
	 */
	String getMdxQuery();

	/**
	 * sets the mdx from user input
	 * 
	 * @throws com.tonbeller.wcf.format.FormatException
	 *             if the syntax is invalid. The internal mdx is not updated in
	 *             this case
	 * @param mdxQuery
	 *            the query to set
	 */
	void setMdxQuery(String mdxQuery) throws FormatException;
}
