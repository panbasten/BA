package com.flywet.platform.bi.delegates.vo;

public class Authorization {
	/**
	 * 角色ID
	 */
	private long rid;

	/**
	 * 授权对象ID
	 */
	private long oid;

	/**
	 * 授权对象类型（Function, PortalMenu, Button）
	 */
	private long otype;

	/**
	 * 权限许可类型
	 */
	private long permission;

	public long getRid() {
		return rid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getOtype() {
		return otype;
	}

	public void setOtype(long otype) {
		this.otype = otype;
	}

	public long getPermission() {
		return permission;
	}

	public void setPermission(long permission) {
		this.permission = permission;
	}
}
