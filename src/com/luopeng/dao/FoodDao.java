package com.luopeng.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.luopeng.model.City;
import com.luopeng.model.Food;

@Component
public interface FoodDao {

	public void updatebatch(List<Food> list);
	
	public List<Food> selectAll();	
	
	
}
