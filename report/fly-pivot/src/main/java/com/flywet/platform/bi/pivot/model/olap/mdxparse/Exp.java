package com.flywet.platform.bi.pivot.model.olap.mdxparse;

/**
 * Expression node for an MDX parse tree
 */
public interface Exp {

	public String toMdx();

	public Object clone();

	/**
	 * Exp is visitable
	 */
	public void accept(ExpVisitor visitor);

} // End Exp
