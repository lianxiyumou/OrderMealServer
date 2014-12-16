package com.luopeng.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.luopeng.model.City;
import com.luopeng.model.Food;

@Component
public interface FoodDao {

	public void updatebatch(List<Food> list);
	
	public void insertbatch(List<Food> list);
	
	public List<Food> selectByType(int foodType);
	
	public List<Food> selectAll();	
	
	public void deleteFoods(List<Integer> ids);
	
	public List<Map<String,Object>> selectMenuFood(Date date);
	
	public void selectMenu(Date date);
	
	public void selectFoodReason(int id);
	
	public void insert(Food food);
	
}
