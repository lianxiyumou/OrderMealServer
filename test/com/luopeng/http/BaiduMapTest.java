package com.luopeng.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.luopeng.dao.PlaceDao;
import com.luopeng.model.Place;
import com.luopeng.util.GsonUtil;

public class BaiduMapTest {

	private ApplicationContext context = null;
	private PlaceDao placeDao;	
	
	@Before
	public void initContext(){
		//System.out.println("initContext");
		this.context = new FileSystemXmlApplicationContext("WebContent/WEB-INF/spring/app-comm.xml","WebContent/WEB-INF/spring/app-ds.xml");
		//System.out.println("initContext over");
		placeDao = context.getBean(PlaceDao.class);
	}	
	
}
