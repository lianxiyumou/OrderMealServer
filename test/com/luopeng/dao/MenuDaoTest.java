package com.luopeng.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MenuDaoTest {

	private ApplicationContext context = null;
	private MenuDao menuDao;
	@Before
	public void initContext(){
		//System.out.println("initContext");
		this.context = new FileSystemXmlApplicationContext("WebContent/WEB-INF/spring/app-comm.xml","WebContent/WEB-INF/spring/app-ds.xml");
		//System.out.println("initContext over");
		menuDao = context.getBean(MenuDao.class);
	}		
	
	@Test
	public void testSelectMenu(){
		menuDao.selectAll();
	}
	
}
