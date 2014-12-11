package com.luopeng.syn.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.luopeng.controller.CityController;
import com.luopeng.dao.PlaceDao;
import com.luopeng.dao.ProvinceDao;

public class SysCityControllerTest {

	
	private ApplicationContext context = null;
	private CityController synCityController;	
	
	@Before
	public void initContext(){
		//System.out.println("initContext");
		this.context = new FileSystemXmlApplicationContext("WebContent/WEB-INF/spring/app-comm.xml","WebContent/WEB-INF/spring/app-ds.xml");
		//System.out.println("initContext over");
		synCityController = context.getBean(CityController.class);
		ProvinceDao provinceDao = context.getBean(ProvinceDao.class);
		System.out.println("provinceDao:"+provinceDao);
	}		
	
	@Test
	public void testSynProvince(){
		synCityController.synProvince();
	}
	
}
