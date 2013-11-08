package com.flywet.platform.bi.delegates.vo;

import com.flywet.platform.bi.delegates.enums.AuthorizationObjectCategory;

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
	private AuthorizationObjectCategory otype;
	private Long otypeL;// 在未指定的授权类型时用于保存

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

	public AuthorizationObjectCategory getOtype() {
		return otype;
	}

	public Long getOtypeLong() {
		if (otype == null) {
			return this.otypeL;
		}
		return Integer.valueOf(otype.getId()).longValue();
	}

	public void setOtype(long otype) {
		this.otype = AuthorizationObjectCategory.getCategoryById(Long.valueOf(
				otype).intValue());
		if (this.otype == null) {
			this.otypeL = otype;
		}
	}

	public long getPermission() {
		return permission;
	}

	public void setPermission(long permission) {
		this.permission = permission;
	}

}
