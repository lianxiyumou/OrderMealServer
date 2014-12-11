package com.luopeng.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.luopeng.model.Place;

@Component
public interface PlaceDao {

	public void insert(Place place);
	
	public List<Place> selectAll();
	
	public void insertbatch(List<Place> places);
	
	public void updatebatch(List<Place> places);
	
	public List<Place> selectNear(Map<String,Object> params);
	
}
