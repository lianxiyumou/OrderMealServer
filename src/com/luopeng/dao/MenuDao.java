package com.luopeng.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.luopeng.model.Menu;

@Component
public interface MenuDao {

	public List<Menu> selectAll();
	
}
