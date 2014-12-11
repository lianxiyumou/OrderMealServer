package com.luopeng.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SysPlaceControllerTest {

	private ApplicationContext context = null;
	private SynPlaceController synPlaceController = null;
	
	@Before
	public void initContext(){
		//System.out.println("initContext");
		this.context = new FileSystemXmlApplicationContext("WebContent/WEB-INF/spring/app-comm.xml","WebContent/WEB-INF/spring/app-ds.xml");
		//System.out.println("initContext over");
		synPlaceController = context.getBean(SynPlaceController.class);
	}	
	
	@Test
	public void testSysnFromBaidu(){
		synPlaceController.sysnFromBaidu();
	}
	
}
