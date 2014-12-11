package com.luopeng.model;

import java.util.HashMap;

public class ResponseModel extends HashMap<String,Object> {

	public ResponseModel(){
		this.put("error", 0);
		this.put("msg", "");
	}
	
}
