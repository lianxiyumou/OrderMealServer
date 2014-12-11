package com.luopeng.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luopeng.dao.CityDao;
import com.luopeng.dao.ProvinceDao;
import com.luopeng.http.HttpRequest;
import com.luopeng.model.City;
import com.luopeng.model.Province;
import com.luopeng.parse.ParseCity;
import com.luopeng.parse.ParseProvince;


@Controller
@RequestMapping(value="/city")
public class CityController {

	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private CityDao cityDao;
	
	public void synProvince(){
		String result = HttpRequest.sendGet("http://www.weather.com.cn/data/city3jdata/china.html");
		System.out.println("result: "+result);
		List<Province> list = ParseProvince.str2List(result);
		provinceDao.updatebatch(list);
		synCity(list);
	}
	
	public void synCity(List<Province> list){
		for(Province p : list){
			String url = "http://www.weather.com.cn/data/city3jdata/provshi/"+p.getCode()+".html";
			String result = HttpRequest.sendGet(url);
			List<City> cities = ParseCity.str2List(result, p.getCode());
			cityDao.updatebatch(cities);
		}
	}
	
}
