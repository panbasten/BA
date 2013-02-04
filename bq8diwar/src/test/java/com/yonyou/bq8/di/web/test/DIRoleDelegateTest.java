package com.yonyou.bq8.di.web.test;

import java.util.List;

import org.junit.Test;
import org.pentaho.di.core.util.Assert;

import com.yonyou.bq8.di.delegates.vo.Role;
import com.yonyou.bq8.di.exceptions.DIKettleException;
import com.yonyou.bq8.di.web.service.DIRoleDelegate;

public class DIRoleDelegateTest extends WebBaseTestcase {
	@Test
	public void testDelRole() throws DIKettleException {
		DIRoleDelegate roleDelegate = (DIRoleDelegate)ctx.getBean("di.service.roleService");
		roleDelegate.delRole(1L);
		Role role = roleDelegate.getRoleById(1L);
		Assert.assertNull(role);
	}
	
	@Test
	public void saveRole() throws DIKettleException {
		DIRoleDelegate roleDelegate = (DIRoleDelegate)ctx.getBean("di.service.roleService");
		Role role = new Role();
		role.setRoleName("管理员");
		role.setDesc("什么都管");
		roleDelegate.saveRole(role);
	}
	@Test
	public void testGetAllRoles() throws DIKettleException {
		DIRoleDelegate roleDelegate = (DIRoleDelegate)ctx.getBean("di.service.roleService");
		List<Role> roles = roleDelegate.getAllRoles();
		for (Role role : roles) {
			System.out.println("get all:" + role.getRoleName());
		}
	}
	
	@Test
	public void testGetRole() throws DIKettleException {
		DIRoleDelegate roleDelegate = (DIRoleDelegate)ctx.getBean("di.service.roleService");
		Role role = roleDelegate.getRoleById(1L);
		Assert.assertNotNull(role);
		System.out.println(role.getRid() + "," + role.getRoleName());
	}
}
