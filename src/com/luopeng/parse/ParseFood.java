package com.luopeng.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.luopeng.model.Food;

public class ParseFood {

	public static List<Food> str2FoodList(String str){
		List<Food> list = new ArrayList<Food>();
		try{
			Gson gson = new Gson();
			JsonArray jArray = gson.fromJson(str, JsonArray.class);
			for(int i=0; i< jArray.size(); i++){
				JsonObject jobj = jArray.get(i).getAsJsonObject();
				Food food = new Food();
				if(jobj.has("name"))
					food.setName(jobj.get("name").getAsString());
				if(jobj.has("state"))
					food.setState(jobj.get("state").getAsInt());
				if(jobj.has("type"))
					food.setType(jobj.get("type").getAsInt());
				list.add(food);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
}
