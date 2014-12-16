package com.luopeng.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.luopeng.model.Menu;

public class MenuDaoTest {
	private ApplicationContext context = null;
	
	private MenuDao menuDao;
	
	@Before
	public void init(){
		this.context = new FileSystemXmlApplicationContext("WebContent/WEB-INF/spring/app-comm.xml","WebContent/WEB-INF/spring/app-ds.xml");
		menuDao = context.getBean(MenuDao.class);
	}
	
	//@Test
	public void testSelectAll(){
		menuDao.selectAll();
	}
	
	//@Test
	public void testInsert(){
		Menu menu = new Menu();
		menu.setTitle("温馨套餐2");
		menu.setCreateDate(Calendar.getInstance().getTime());
		int id = menuDao.insert(menu);
		System.out.println("testInsert id:"+id+" menuId:"+menu.getId());
	}
	
	@Test
	public void testInsertFood2Menu(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("menuId", 1);
		params.put("foodId", 2);
		params.put("createDdate", Calendar.getInstance().getTime());
		List<Map<String,Object>> menu = menuDao.selectMenuFood(params);
		if(menu.isEmpty()){
			menuDao.insertFood2Menu(params);
		}else{
			System.out.println("already insert");
		}
	}	
	
}
