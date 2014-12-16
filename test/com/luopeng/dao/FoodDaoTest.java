package com.luopeng.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.luopeng.model.Food;

public class FoodDaoTest {

	private ApplicationContext context = null;
	private FoodDao foodDao;
	@Before
	public void initContext(){
		//System.out.println("initContext");
		this.context = new FileSystemXmlApplicationContext("WebContent/WEB-INF/spring/app-comm.xml","WebContent/WEB-INF/spring/app-ds.xml");
		//System.out.println("initContext over");
		foodDao = context.getBean(FoodDao.class);
	}		
	
	//@Test
	public void Testupdatebatch(){
		List<Food> list = new ArrayList<Food>();
		for(int i=1; i<10; i++){
			Food food = new Food();
			food.setId(i);
			food.setName("鸡腿菇炒肉"+i);
			food.setType(i%2);
			food.setState(2);
			list.add(food);
		}
		foodDao.updatebatch(list);
	}
	
	//@Test
	public void testSelectByType(){
		List<Food> list = foodDao.selectByType(1);
	}
	
	//@Test
	public void TestSelectAll(){
		List<Food> list = foodDao.selectAll();
		if(list != null ){
			for(Food food : list){
				System.out.println("id:"+food.getId()+" name:"+food.getName()+" , state:"+food.getType());
			}
		}
	}
	
	//@Test
	public void testDelete(){
		List<Integer> ids = new ArrayList<Integer>();
		for(int i=0; i<=9; i++){
			ids.add(i);
		}
		foodDao.deleteFoods(ids);
	}
	
	//@Test
	public void testSelectByMenu(){
		Calendar c = Calendar.getInstance();
		foodDao.selectMenuFood(c.getTime());
	}
	
	//@Test
	public void testSelectMenu(){
		Calendar c = Calendar.getInstance();
		foodDao.selectMenu(c.getTime());
	}
	
	//@Test
	public void testSelectFoodByDate(){
		Calendar c = Calendar.getInstance();
		List<Map<String,Object>> foods = foodDao.selectMenuFood(c.getTime());
		if(foods.isEmpty()){
			System.out.println("foods is empty");
		}else{
			System.out.println("foods is not empty");
		}
	}
	
	
}
