package com.luopeng.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class OffFileControllerTest {
	private ApplicationContext context = null;
	private ChatController offController = null;
	
	@Before
	public void initContext(){
		//System.out.println("initContext");
		this.context = new FileSystemXmlApplicationContext("WebContent/WEB-INF/spring/app-comm.xml","WebContent/WEB-INF/spring/app-ds.xml");
		//System.out.println("initContext over");
		offController = context.getBean(ChatController.class);
	}
	
	@Test
	public void testUpload(){
		System.out.println("offController :"+offController);
	}
	
}
