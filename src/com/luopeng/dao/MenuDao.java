package com.luopeng.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.luopeng.model.Menu;

public interface MenuDao {

	public List<Menu> selectAll();
	
	public int insert(Menu menu);
	
	public List<Menu> selectMenuByDate(Date date);
	
	public List<Map<String,Object>> selectMenuFood(Map<String,Object> menu);
	
	public void insertFood2Menu(Map<String,Object> foodMenu);
	
	public void deleteFood2Menu(Map<String,Object> foodMenu);
	
	
	
}
