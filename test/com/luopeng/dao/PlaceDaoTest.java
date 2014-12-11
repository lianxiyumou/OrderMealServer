package com.luopeng.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SimpleTimeZone;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.luopeng.comm.PageHelper;
import com.luopeng.model.Place;

public class PlaceDaoTest {

	private ApplicationContext context = null;
	private PlaceDao placeDao;
	@Before
	public void initContext(){
		//System.out.println("initContext");
		this.context = new FileSystemXmlApplicationContext("WebContent/WEB-INF/spring/app-comm.xml","WebContent/WEB-INF/spring/app-ds.xml");
		//System.out.println("initContext over");
		placeDao = context.getBean(PlaceDao.class);
	}	
	
	
	//@Test 
	public void testInsert(){
		double lat;
		double lng;
		System.out.println("start insert:"+getTime());
		for(int i=0; i<100000; i++){//2000000
			Place place = new Place();
			lat = (RandomUtils.nextInt(9000000*2)-9000000)/100000.0;
			lng = (RandomUtils.nextInt(1800000*2)-1800000)/100000.0;
			place.setLat(lat);
			place.setLng(lng);
			placeDao.insert(place);
		}
		System.out.println("over insert:"+getTime());
	}
	
	private String getTime(){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		return sd.format(c.getTime());
	}
	
	
	//@Test
	public void testInsertbatch(){
		List<Place> places = new ArrayList<Place>();
		double lat;
		double lng;		
		System.out.println("start insertbatch:"+getTime());
		for(int j=0; j<6; j++){
			for(int i=0; i<200000; i++){//2000000
				Place place = new Place();
				lat = (RandomUtils.nextInt(9000000*2)-9000000)/100000.0;
				lng = (RandomUtils.nextInt(1800000*2)-1800000)/100000.0;
				place.setLat(lat);
				place.setLng(lng);
				places.add(place);
			}		
			System.out.println("start insert");
			placeDao.insertbatch(places);
		}
		System.out.println("over insertbatch:"+getTime());
	}
	
	//@Test
	public void test(){
		System.out.println("test");
	}
	
	//@Test
	public void testSelectNear(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lat", 56.14262);
		params.put("lng", 37.605853);
//		params.put("dist", 1250.0);
		System.out.println("start selectNear:"+getTime());
		PageHelper.startPage(1, 10);
		placeDao.selectNear(params);
		System.out.println("over selectNear:"+getTime());
	}
	
	//@Test
	public void testSelectAll(){
		List<Place> places = placeDao.selectAll();
		for(Place place : places){
			System.out.println("name: "+place.getName()+" lat:"+place.getLat()+" lng:"+place.getLng());
		}
	}
	
	
	
}
