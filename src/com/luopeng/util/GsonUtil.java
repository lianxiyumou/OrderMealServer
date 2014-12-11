package com.luopeng.util;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.luopeng.model.Place;

public class GsonUtil {

	public static String obj2string(Object obj){
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	public static Place jobj2Place(JsonObject obj){
		Place place = new Place();
		if(obj.has("name")){
			place.setName(obj.get("name").getAsString());
		}
		if(obj.has("location")){
			JsonObject location = obj.get("location").getAsJsonObject();
			if(location !=null ){
				if(location.has("lat")){
					place.setLat(location.get("lat").getAsDouble());
				}
				if(location.has("lng")){
					place.setLng(location.get("lng").getAsDouble());
				}						
			}
		}
		if(obj.has("address")){
			place.setAddress(obj.get("address").getAsString());
		}
		if(obj.has("street_id")){
			place.setStreeId(obj.get("street_id").getAsString());
		}
		if(obj.has("telephone")){
			place.setTelephone(obj.get("telephone").getAsString());
		}
		if(obj.has("uid")){
			place.setUid(obj.get("uid").getAsString());
		}
		return place;
	}
	
	
	public static List<Place> jarray2Places(JsonArray list){
		if(list == null || list.isJsonNull() || list.size() == 0){
			return null;
		}
		List<Place> places = new ArrayList<Place>();
		Iterator<JsonElement>  iter = list.iterator();
		while(iter.hasNext()){
			JsonElement el = iter.next();
			if(el.isJsonObject()){
				JsonObject obj = el.getAsJsonObject();
				Place place = GsonUtil.jobj2Place(obj);
				places.add(place);
			}
		}
		return places;
	}
	
	
}
