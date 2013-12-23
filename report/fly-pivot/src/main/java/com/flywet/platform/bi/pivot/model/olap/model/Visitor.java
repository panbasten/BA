package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * Created on 29.10.2002
 * 
 * @author av
 */
public interface Visitor {
	void visitAxis(Axis v);

	void visitCell(Cell v);

	void visitDimension(Dimension v);

	void visitHierarchy(Hierarchy v);

	void visitLevel(Level v);

	void visitMember(Member v);

	void visitPosition(Position v);

	void visitProperty(Property v);

	void visitResult(Result v);

	void visitMemberPropertyMeta(MemberPropertyMeta v);

	void visitBooleanExpr(BooleanExpr v);

	void visitIntegerExpr(IntegerExpr v);

	void visitDoubleExpr(DoubleExpr v);

	void visitStringExpr(StringExpr v);

	void visitFunCallExpr(FunCallExpr v);

	void visitParameterExpr(ParameterExpr v);

	void visitPropertyExpr(PropertyExpr v);

	void visitEmptyMember(EmptyMember v);

}
