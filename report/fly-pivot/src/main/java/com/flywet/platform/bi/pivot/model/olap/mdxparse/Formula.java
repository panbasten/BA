package com.flywet.platform.bi.pivot.model.olap.mdxparse;

import com.flywet.platform.bi.pivot.model.util.StringUtil;

/**
 * Formula representing a WITH MEMBER ... or a WITH SET ...
 * 
 */
public class Formula implements Exp {

	boolean isMember;
	String[] names;
	Exp exp;
	MemberProperty[] memberProperties;

	/** Construct formula specifying a set. */
	Formula(String[] names, Exp exp) {
		this(false, names, exp, new MemberProperty[0]);
	}

	/** Construct a formula specifying a member. */
	Formula(String[] names, Exp exp, MemberProperty[] memberProperties) {
		this(true, names, exp, memberProperties);
	}

	private Formula(boolean isMember, String[] names, Exp exp,
			MemberProperty[] memberProperties) {
		this.isMember = isMember;
		this.names = names;
		this.exp = exp;
		this.memberProperties = memberProperties;
	}

	/**
	 * Returns the isMember.
	 * 
	 * @return boolean
	 */
	public boolean isMember() {
		return isMember;
	}

	/**
	 * return the unique name
	 * 
	 * @return String
	 */
	public String getUniqeName() {
		String str = "";
		for (int i = 0; i < names.length; i++) {
			if (i > 0)
				str += ".";
			str += StringUtil.bracketsAround(names[i]);
		}

		return str;
	}

	/**
	 * return the first name (Dimension/Hierarchy)
	 * 
	 * @return String
	 */
	public String getFirstName() {
		return names[0];
	}

	/**
	 * return the Last name (eg. member name)
	 * 
	 * @return String
	 */
	public String getLastName() {
		int n = names.length - 1;
		return names[n];
	}

	/**
	 * format to MDX
	 */
	public String toMdx() {
		StringBuffer sb = new StringBuffer();
		if (isMember) {
			sb.append("MEMBER ");
		} else {
			sb.append("SET ");

		}

		for (int i = 0; i < names.length; i++) {
			if (i > 0)
				sb.append('.');
			sb.append(StringUtil.bracketsAround(names[i]));
		}

		sb.append(" AS '");
		sb.append(exp.toMdx());
		sb.append('\'');

		for (int i = 0; i < memberProperties.length; i++) {
			sb.append(',');
			sb.append(memberProperties[i].toMdx());
		}

		return sb.toString();
	}

	/**
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		MemberProperty[] cloneMemberProperties = new MemberProperty[memberProperties.length];
		for (int i = 0; i < cloneMemberProperties.length; i++) {
			cloneMemberProperties[i] = (MemberProperty) memberProperties[i]
					.clone();
		}
		return new Formula(isMember, names, (Exp) exp.clone(),
				cloneMemberProperties);

	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.mdxparse.Exp#accept
	 */
	public void accept(ExpVisitor visitor) {
		visitor.visitFormula(this);
	}

	/**
	 * @return Exp for formula
	 */
	public Exp getExp() {
		return exp;
	}

} // End Formula
