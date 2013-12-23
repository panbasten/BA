package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * default implementation of Visitor. Throws an UnsupportedOperationException
 * for each method.
 * 
 * @author av
 */
public abstract class VisitorSupportStrict implements Visitor {

	public void visitAxis(Axis v) {
		throw new UnsupportedOperationException();
	}

	public void visitCell(Cell v) {
		throw new UnsupportedOperationException();
	}

	public void visitDimension(Dimension v) {
		throw new UnsupportedOperationException();
	}

	public void visitHierarchy(Hierarchy v) {
		throw new UnsupportedOperationException();
	}

	public void visitLevel(Level v) {
		throw new UnsupportedOperationException();
	}

	public void visitMember(Member v) {
		throw new UnsupportedOperationException();
	}

	public void visitPosition(Position v) {
		throw new UnsupportedOperationException();
	}

	public void visitProperty(Property v) {
		throw new UnsupportedOperationException();
	}

	public void visitResult(Result v) {
		throw new UnsupportedOperationException();
	}

	public void visitMemberPropertyMeta(MemberPropertyMeta v) {
		throw new UnsupportedOperationException();
	}

	public void visitBooleanExpr(BooleanExpr v) {
		throw new UnsupportedOperationException();
	}

	public void visitIntegerExpr(IntegerExpr v) {
		throw new UnsupportedOperationException();
	}

	public void visitDoubleExpr(DoubleExpr v) {
		throw new UnsupportedOperationException();
	}

	public void visitStringExpr(StringExpr v) {
		throw new UnsupportedOperationException();
	}

	public void visitFunCallExpr(FunCallExpr v) {
		throw new UnsupportedOperationException();
	}

	public void visitParameterExpr(ParameterExpr v) {
		throw new UnsupportedOperationException();
	}

	public void visitPropertyExpr(PropertyExpr v) {
		throw new UnsupportedOperationException();
	}

	public void visitEmptyMember(EmptyMember v) {
		throw new UnsupportedOperationException();
	}
}
