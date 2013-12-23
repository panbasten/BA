package com.flywet.platform.bi.pivot.model.olap.mdxparse;

/**
 * member property implementation
 */
public class MemberProperty implements Exp {

	private String name;
	private Exp exp;

	public MemberProperty(String name, Exp exp) {
		this.name = name;
		this.exp = exp;
	}

	/**
	 * @return The expression that makes up the value of the member property
	 */
	public Exp getExp() {
		return exp;
	}

	/**
	 * format to MDX
	 */
	public String toMdx() {
		String str = name;
		str += " = ";
		str += exp.toMdx();
		return str;
	}

	/**
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		return new MemberProperty(name, (Exp) exp.clone());
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.mdxparse.Exp#accept
	 */
	public void accept(ExpVisitor visitor) {
		visitor.visitMemberProperty(this);
	}

} // End MemberProperty
