package com.flywet.platform.bi.pivot.model.olap.mdxparse;

import com.flywet.platform.bi.pivot.model.olap.model.Dimension;
import com.flywet.platform.bi.pivot.model.olap.model.Hierarchy;
import com.flywet.platform.bi.pivot.model.olap.model.Level;
import com.flywet.platform.bi.pivot.model.olap.model.Member;

/**
 * Visitor for MDX parse expressions
 */
public interface ExpVisitor {

	void visitCompoundId(CompoundId visio);

	void visitFormula(Formula visio);

	void visitFunCall(FunCall visio);

	void visitLiteral(Literal visio);

	void visitMemberProperty(MemberProperty visio);

	void visitParsedQuery(ParsedQuery visio);

	void visitQueryAxis(QueryAxis visio);

	void visitDimension(Dimension visio);

	void visitHierarchy(Hierarchy visio);

	void visitLevel(Level visio);

	void visitMember(Member visio);

} // ExpVisitor
