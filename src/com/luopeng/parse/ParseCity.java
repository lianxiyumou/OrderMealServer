package com.luopeng.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.luopeng.model.City;

public class ParseCity {

	public static List<City> str2List(String str,int pCode){
		Gson gson = new Gson();
		JsonObject jobj = gson.fromJson(str, JsonObject.class);
		Set<Entry<String, JsonElement>> entries =  jobj.entrySet();
		List<City> list = new ArrayList<City>();
		for(Entry<String, JsonElement> entry : entries){
			entry.getKey();
			City city = new City();
			city.setCode(Integer.valueOf(entry.getKey()));
			city.setName(entry.getValue().getAsString());
			city.setpCode(pCode);
			list.add(city);
		}
		return list;
	}	
	
}
