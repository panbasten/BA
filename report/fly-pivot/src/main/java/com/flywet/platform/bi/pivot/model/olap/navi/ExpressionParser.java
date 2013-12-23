package com.flywet.platform.bi.pivot.model.olap.navi;

import com.flywet.platform.bi.pivot.model.core.Extension;
import com.flywet.platform.bi.pivot.model.olap.model.Dimension;
import com.flywet.platform.bi.pivot.model.olap.model.Expression;
import com.flywet.platform.bi.pivot.model.olap.model.Hierarchy;
import com.flywet.platform.bi.pivot.model.olap.model.Level;
import com.flywet.platform.bi.pivot.model.olap.model.Member;
import com.flywet.platform.bi.pivot.model.util.JPivotException;

/**
 * serialisiert expr. Wird zunaechst nur fuer unique names von Member,
 * Hierarchy, Dimension, Level benutzt.
 */

public interface ExpressionParser extends Extension {
	public static final String ID = "expressionParser";

	class InvalidSyntaxException extends JPivotException {
		public InvalidSyntaxException() {
		}

		public InvalidSyntaxException(String message) {
			super(message);
		}

		public InvalidSyntaxException(String message, Throwable cause) {
			super(message, cause);
		}

		public InvalidSyntaxException(Throwable cause) {
			super(cause);
		}
	}

	String unparse(Expression expr);

	Expression parse(String expr) throws InvalidSyntaxException;

	/**
	 * typespecific lookup because Mondrians unique names are not unique.
	 * [Measures] is the unique name for both, Hierarchy and Dimension
	 */
	Member lookupMember(String uniqueName) throws InvalidSyntaxException;

	/**
	 * typespecific lookup because Mondrians unique names are not unique.
	 * [Measures] is the unique name for both, Hierarchy and Dimension. much
	 * faster than lookupMember or parse(uname) because it does not have to look
	 * into the DB.
	 */
	Level lookupLevel(String uniqueName) throws InvalidSyntaxException;

	/**
	 * typespecific lookup because Mondrians unique names are not unique.
	 * [Measures] is the unique name for both, Hierarchy and Dimension much
	 * faster than lookupMember or parse(uname) because it does not have to look
	 * into the DB.
	 */
	Hierarchy lookupHierarchy(String uniqueName) throws InvalidSyntaxException;

	/**
	 * typespecific lookup because Mondrians unique names are not unique.
	 * [Measures] is the unique name for both, Hierarchy and Dimension much
	 * faster than lookupMember or parse(uname) because it does not have to look
	 * into the DB.
	 */
	Dimension lookupDimension(String uniqueName) throws InvalidSyntaxException;
}
