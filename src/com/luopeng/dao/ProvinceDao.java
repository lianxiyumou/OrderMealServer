package com.luopeng.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.luopeng.model.Place;
import com.luopeng.model.Province;

@Component
public interface ProvinceDao {

	public void updatebatch(List<Province> province);
	
	public List<Province> selectAll();
	
}
