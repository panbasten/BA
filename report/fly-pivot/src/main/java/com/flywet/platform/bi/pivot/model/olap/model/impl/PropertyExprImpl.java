package com.flywet.platform.bi.pivot.model.olap.model.impl;

import java.util.ArrayList;
import java.util.List;

import com.flywet.platform.bi.pivot.model.olap.model.Expression;
import com.flywet.platform.bi.pivot.model.olap.model.FunCallExpr;
import com.flywet.platform.bi.pivot.model.olap.model.PropertyExpr;
import com.flywet.platform.bi.pivot.model.olap.model.StringExpr;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;
import com.flywet.platform.bi.pivot.model.olap.model.VisitorSupportSloppy;

/**
 * PropertyExpr implementation
 */
public class PropertyExprImpl implements PropertyExpr {

	private Expression valueExpr;
	private String name;

	/**
	 * c'tor
	 * 
	 * @param name
	 */
	public PropertyExprImpl(String name, Expression valueExpr) {
		this.name = name;
		this.valueExpr = valueExpr;
	}

	/**
	 * @return the value expression
	 * @see com.flywet.platform.bi.pivot.model.olap.model.PropertyExpr#getValueExpr()
	 */
	public Expression getValueExpr() {
		return valueExpr;
	}

	/**
	 * @return the property name
	 * @see com.flywet.platform.bi.pivot.model.olap.model.PropertyExpr#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * walk expression tree and find possible choices
	 * 
	 * @see com.flywet.platform.bi.pivot.model.olap.model.PropertyExpr#getChoices()
	 */
	public String[] getChoices() {

		final List choices = new ArrayList();

		this.accept(new VisitorSupportSloppy() {
			// ParameterExpr not supported
			public void visitStringExpr(StringExpr v) {
				choices.add(v.getValue());
			}

			public void visitFunCallExpr(FunCallExpr v) {
				Expression[] args = v.getArgs();
				for (int i = 0; i < args.length; i++) {
					args[i].accept(this);
				}
			}

			public void visitPropertyExpr(PropertyExpr v) {
				Expression exp = v.getValueExpr();
				exp.accept(this);
			}

		});

		return (String[]) choices.toArray(new String[0]);
	}

	/**
	 * visitor implementation
	 * 
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Visitable#accept
	 */
	public void accept(Visitor visitor) {
		visitor.visitPropertyExpr(this);
	}

} // PropertyExpr
