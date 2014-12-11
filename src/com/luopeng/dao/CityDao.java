package com.luopeng.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.luopeng.model.City;

@Component
public interface CityDao {

	public void updatebatch(List<City> City);
	
	public List<City> selectAll();	
	
	public List<City> selectSynCities();
	
}
