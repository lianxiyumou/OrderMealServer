package com.luopeng.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.luopeng.util.StringUtil;

public class BaiduMapRequest {

	public static String sendRequest(String query,String region,int pageSize, int pageNum){
		String result = "";
		String url = "http://api.map.baidu.com/place/v2/search";
		Map<String,String> param = new HashMap<String,String>();
		try {
			param.put("q", URLEncoder.encode(query, "UTF-8"));
			param.put("region", URLEncoder.encode(region, "UTF-8"));
			param.put("scope", "2");
			param.put("filter", "industry_type:cater");
			param.put("output", "json");
			param.put("ak", "2aa6118989c675b2352eb1b39a1a16bd");
			param.put("page_size", String.valueOf(pageSize));
			param.put("page_num", String.valueOf(pageNum));
			result = HttpRequest.sendGet(url, param);
//			System.out.println("resultStr: "+result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	
		
	}
	
}
