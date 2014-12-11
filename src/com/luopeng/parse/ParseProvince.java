package com.luopeng.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.luopeng.model.Province;

public class ParseProvince {

	public static List<Province> str2List(String str){
		Gson gson = new Gson();
		JsonObject jobj = gson.fromJson(str, JsonObject.class);
		System.out.println("Parse:"+ jobj);
		Set<Entry<String, JsonElement>> entries =  jobj.entrySet();
		List<Province> list = new ArrayList<Province>();
		for(Entry<String, JsonElement> entry : entries){
			entry.getKey();
			Province province = new Province();
			province.setCode(Integer.valueOf(entry.getKey()));
			province.setName(entry.getValue().getAsString());
			list.add(province);
		}
		return list;
	}
	
}
