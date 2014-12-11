package com.luopeng.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.luopeng.dao.PlaceDao;
import com.luopeng.http.BaiduMapRequest;
import com.luopeng.model.Place;
import com.luopeng.util.GsonUtil;

@Controller
@RequestMapping(value="/synplace")
public class SynPlaceController {
	
	@Autowired
	private PlaceDao placeDao;
	@Autowired
	private CityController cityController;
	
	private List<String> cities = new ArrayList<String>();
	

	
	public void sysnFromBaidu(){
		
		int pageSize = 10;
		int pageNum = 1;
		boolean flag = true;
		JsonElement results;
		Map<String,JsonElement> res = null;
		try{
			while(flag){
				String result = BaiduMapRequest.sendRequest("高尔夫球场","深圳",pageSize,pageNum);
				Gson gson = new Gson();
				res = gson.fromJson(result, new TypeToken<Map<String,JsonElement>>(){}.getType());
				res.get("status");
				res.get("message");
				System.out.println("total: "+res.get("total"));
				results = res.get("results");
				System.out.println("result: "+results.toString());
				if(results.isJsonArray()){
					JsonArray list = results.getAsJsonArray();
					List<Place> places = GsonUtil.jarray2Places(list);
					if(places!=null && !places.isEmpty())
						placeDao.updatebatch(places);
					else
						return;
					pageNum++;
					System.out.println("sysnFromBaidu pageNum:"+pageNum);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
//			System.out.println("res:"+res);
		}
	}
	
}
