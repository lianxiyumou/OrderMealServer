package com.luopeng.controller;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.luopeng.dao.UserDao;
import com.luopeng.model.User;

public class ControllerTest {
	private ApplicationContext context = null;
	private TestController testController = null;
	
	@Before
	public void initContext(){
		//System.out.println("initContext");
		this.context = new FileSystemXmlApplicationContext("WebContent/WEB-INF/spring/app-comm.xml","WebContent/WEB-INF/spring/app-ds.xml");
		//System.out.println("initContext over");
		testController = context.getBean(TestController.class);
	}
	
	//@Test
	public void testGetUsers(){
		List<User> list = testController.getUsers();
		if(list != null){
			for(User user : list){
				System.out.println("username:"+user.getUsername()+" pwd"+user.getPassword());
			}
		}
	}
	
	@Test
	public void testFindAllPlace(){
		testController.findAllPlace();
	}
	
	
}
