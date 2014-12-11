package com.luopeng.dao;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUserDao {
	ApplicationContext context = null;

	@Before
	public void initContext(){
		this.context = new ClassPathXmlApplicationContext("/WEB-INF/spring/app-*.xml");

	}
	
}
