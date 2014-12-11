package com.luopeng.http;

public class SynCityProRequest {

	
	public String sysProvince(){
		return HttpRequest.sendGet("http://www.weather.com.cn/data/city3jdata/china.html");
	}
}
