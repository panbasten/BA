package com.yonyou.bq8.di.web.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.pentaho.di.repository.IUser;

import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.delegates.vo.Role;
import com.yonyou.bq8.di.delegates.vo.User;
import com.yonyou.bq8.di.web.service.DIUserDelegate;

public class DIUserDelegateTest extends WebBaseTestcase {
	@Test
	public void testDelUser() throws DIException {
		DIUserDelegate userDelegate = (DIUserDelegate)ctx.getBean("di.service.userService");
		Assert.assertNotNull(userDelegate);
		userDelegate.delUser(3);
	}
	
	@Test
	public void testAddUser() throws DIException {
		User userInfo = new User();
		userInfo.setDesc("MY测试用户001");
		userInfo.setEnabled("Y");
		userInfo.setLogin("han");
		userInfo.setPassword("123456");
		userInfo.setName("张三三");
		
		DIUserDelegate userDelegate = (DIUserDelegate)ctx.getBean("di.service.userService");
		Assert.assertNotNull(userDelegate);
		
		userDelegate.saveUser(userInfo);
	}
	
	@Test
	public void testGetAllUsers() throws DIException {
		DIUserDelegate userDelegate = (DIUserDelegate)ctx.getBean("di.service.userService");
		Assert.assertNotNull(userDelegate);
		
		List<User> users = userDelegate.getAllUser();
		Assert.assertNotNull(users);
		for (User user : users) {
			System.out.println(user.getLogin());
		}
	}
	
	@Test
	public void testGetUser() throws DIException {
		DIUserDelegate userDelegate = (DIUserDelegate)ctx.getBean("di.service.userService");
		Assert.assertNotNull(userDelegate);
		
		User user = userDelegate.getUserById(3L);
		Assert.assertNotNull(user);
		System.out.println("fetch user by id-3:"+user.getLogin());
	}
	
	@Test
	public void relateToRole() throws DIException {
		DIUserDelegate userDelegate = (DIUserDelegate)ctx.getBean("di.service.userService");
		Assert.assertNotNull(userDelegate);
		
		List<Long> roleIds = new ArrayList<Long>();
		roleIds.add(1L);
		roleIds.add(2L);
		roleIds.add(3L);
		userDelegate.relateToRole(3, roleIds);
	}
	
	@Test
	public void testGetRelateRoles() throws DIException {
		DIUserDelegate userDelegate = (DIUserDelegate)ctx.getBean("di.service.userService");
		Assert.assertNotNull(userDelegate);
		
		List<Role> roles = userDelegate.getRelateRoles(3L);
		Assert.assertNotNull(roles);
		for (Role role : roles) {
			System.out.println(role.getDesc());
		}
	}
}
