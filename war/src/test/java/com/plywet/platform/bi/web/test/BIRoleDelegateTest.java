package com.plywet.platform.bi.web.test;

import java.util.List;

import org.junit.Test;
import org.pentaho.di.core.util.Assert;

import com.plywet.platform.bi.delegates.exceptions.BIKettleException;
import com.plywet.platform.bi.delegates.vo.Role;
import com.plywet.platform.bi.web.service.BIRoleDelegate;

public class BIRoleDelegateTest extends WebBaseTestcase {
	@Test
	public void testDelRole() throws BIKettleException {
		BIRoleDelegate roleDelegate = (BIRoleDelegate) ctx
				.getBean("bi.service.roleService");
		roleDelegate.delRole(1L);
		Role role = roleDelegate.getRoleById(1L);
		Assert.assertNull(role);
	}

	@Test
	public void saveRole() throws BIKettleException {
		BIRoleDelegate roleDelegate = (BIRoleDelegate) ctx
				.getBean("bi.service.roleService");
		Role role = new Role();
		role.setRoleName("管理员");
		role.setDesc("什么都管");
		roleDelegate.saveRole(role);
	}

	@Test
	public void testGetAllRoles() throws BIKettleException {
		BIRoleDelegate roleDelegate = (BIRoleDelegate) ctx
				.getBean("bi.service.roleService");
		List<Role> roles = roleDelegate.getAllRoles();
		for (Role role : roles) {
			System.out.println("get all:" + role.getRoleName());
		}
	}

	@Test
	public void testGetRole() throws BIKettleException {
		BIRoleDelegate roleDelegate = (BIRoleDelegate) ctx
				.getBean("bi.service.roleService");
		Role role = roleDelegate.getRoleById(1L);
		Assert.assertNotNull(role);
		System.out.println(role.getRid() + "," + role.getRoleName());
	}
}
