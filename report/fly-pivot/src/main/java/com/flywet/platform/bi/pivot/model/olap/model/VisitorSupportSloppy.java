package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * default implementation of Visitor. Does nothing
 * 
 * @author av
 */
public abstract class VisitorSupportSloppy implements Visitor {

	public VisitorSupportSloppy() {
		super();
	}

	public void visitAxis(Axis v) {
	}

	public void visitCell(Cell v) {
	}

	public void visitDimension(Dimension v) {
	}

	public void visitHierarchy(Hierarchy v) {
	}

	public void visitLevel(Level v) {
	}

	public void visitMember(Member v) {
	}

	public void visitPosition(Position v) {
	}

	public void visitProperty(Property v) {
	}

	public void visitResult(Result v) {
	}

	public void visitMemberPropertyMeta(MemberPropertyMeta v) {
	}

	public void visitBooleanExpr(BooleanExpr v) {
	}

	public void visitIntegerExpr(IntegerExpr v) {
	}

	public void visitDoubleExpr(DoubleExpr v) {
	}

	public void visitStringExpr(StringExpr v) {
	}

	public void visitFunCallExpr(FunCallExpr v) {
	}

	public void visitParameterExpr(ParameterExpr v) {
	}

	public void visitPropertyExpr(PropertyExpr v) {
	}

	public void visitEmptyMember(EmptyMember v) {
	}

}
