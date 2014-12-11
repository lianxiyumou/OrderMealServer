package com.luopeng.dao;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.luopeng.model.User;

public class UserDaoTest {
	private ApplicationContext context = null;
	private UserDao userDao;
	@Before
	public void initContext(){
		//System.out.println("initContext");
		this.context = new FileSystemXmlApplicationContext("WebContent/WEB-INF/spring/app-comm.xml","WebContent/WEB-INF/spring/app-ds.xml");
		//System.out.println("initContext over");
		userDao = context.getBean(UserDao.class);
	}
	
	//@Test
	public void testInsert(){
		System.out.println("wokao");
		User user = new User();
		user.setAge(24);
		user.setEmail("990740109@qq.com");
		user.setPassword("123456");
		user.setSex("m");
		user.setUsername("luopeng3");
		System.out.println("userDao:"+userDao);
		int userId = userDao.insert(user);
	    System.out.println("userId:"+user.getId());
	}
	
	//@Test
	public void testFindByUserName(){
		User user = userDao.findByUserName("luopeng");
		if(user != null){
			System.out.println("userName:"+user.getUsername()+" pwd:"+user.getPassword());
		}
	}
	
	//@Test
	public void testSelectAll(){
		List<User> list = userDao.selectAll();
		if(list != null){
			for(User user : list){
				System.out.println("userName:"+user.getUsername()+" pwd:"+user.getPassword());
			}
		}
	}
	
	//@Test
	public void testDelete(){
		userDao.delete("luopeng");
	}
	
	//@Test
	public void testSelectByMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("username", "luopeng");
//		List<User> list = userDao.selectByMap(map);
//		if(list != null){
//			for(User user : list){
//				System.out.println("userName:"+user.getUsername()+" pwd:"+user.getPassword());
//			}
//		}
		List<Map<String,Object>> list = userDao.selectByMap(map);
		if(list != null){
			for(Map<String,Object> obj : list){
				Set<String> keys = obj.keySet();
				for(String key : keys){
					System.out.println(key+":"+obj.get(key)+" ");
				}
			}
		}
	}
	
	@Test
	public void testSelectUserNameAndPwd(){
		Map<String,String> params = new HashMap<String,String>();
		
		User user = new User();
		user.setUsername("lianxi");
		user.setPassword("23456");
		userDao.selectUserNameAndPwd(user);
		System.out.println("id:"+user.getId());
	}
	
	
}
