package com.yonyou.bq8.di.el;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.VariableResolver;

/**
 * 
 * <p>
 * This is the JSTL-specific implementation of VariableResolver. It looks up
 * variable references in the PageContext, and also recognizes references to
 * implicit objects.
 * 
 * @author PeterPan
 * 
 **/

public class VariableResolverImpl implements VariableResolver {
	// -------------------------------------
	// Member variables
	// -------------------------------------

	private PageContext mCtx;

	// -------------------------------------
	/**
	 * 
	 * Constructor
	 **/
	public VariableResolverImpl(PageContext pCtx) {
		mCtx = pCtx;
	}

	// -------------------------------------
	/**
	 * 
	 * Resolves the specified variable within the given context. Returns null if
	 * the variable is not found.
	 **/
	public Object resolveVariable(String pName) throws ELException {
		// Check for implicit objects
		if ("pageContext".equals(pName)) {
			return mCtx;
		} else if ("pageScope".equals(pName)) {
			return ImplicitObjects.getImplicitObjects(mCtx).getPageScopeMap();
		} else if ("requestScope".equals(pName)) {
			return ImplicitObjects.getImplicitObjects(mCtx)
					.getRequestScopeMap();
		} else if ("sessionScope".equals(pName)) {
			return ImplicitObjects.getImplicitObjects(mCtx)
					.getSessionScopeMap();
		} else if ("applicationScope".equals(pName)) {
			return ImplicitObjects.getImplicitObjects(mCtx)
					.getApplicationScopeMap();
		} else if ("param".equals(pName)) {
			return ImplicitObjects.getImplicitObjects(mCtx).getParamMap();
		} else if ("paramValues".equals(pName)) {
			return ImplicitObjects.getImplicitObjects(mCtx).getParamsMap();
		} else if ("header".equals(pName)) {
			return ImplicitObjects.getImplicitObjects(mCtx).getHeaderMap();
		} else if ("headerValues".equals(pName)) {
			return ImplicitObjects.getImplicitObjects(mCtx).getHeadersMap();
		} else if ("initParam".equals(pName)) {
			return ImplicitObjects.getImplicitObjects(mCtx).getInitParamMap();
		} else if ("cookie".equals(pName)) {
			return ImplicitObjects.getImplicitObjects(mCtx).getCookieMap();
		}

		// Otherwise, just look it up in the page context
		else {
			return mCtx.findAttribute(pName);
		}
	}

	// -------------------------------------
}
