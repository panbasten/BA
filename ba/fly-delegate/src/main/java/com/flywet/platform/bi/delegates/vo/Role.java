package com.flywet.platform.bi.delegates.vo;

import java.util.List;

public class Role {
	private long rid;
	private String roleName;
	private String desc;

	// 采用手工加载方式
	private List<Authorization> auths;

	public Role() {

	}

	public Role(long rid, String roleName, String desc) {
		this.rid = rid;
		this.roleName = roleName;
		this.desc = desc;
	}

	public long getRid() {
		return rid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Authorization> getAuths() {
		return auths;
	}

	public void setAuths(List<Authorization> auths) {
		this.auths = auths;
	}
}
