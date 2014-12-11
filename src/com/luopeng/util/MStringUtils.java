package com.luopeng.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class MStringUtils extends StringUtils {

	/**
	 * 从形如loginfrom=qq&timestamp=1413442440054&appname=netchat
	 * 获得Map
	 * @param str: loginfrom=qq&timestamp=1413442440054&appname=netchat
	 * @param regex： &
	 * @return
	 */
	public static Map<String,String> getMapFromString(String str, String regex){
		String[] strArray = str.split(regex);
		int length = strArray.length;
		if(strArray == null || length == 0){
			return null;
		}
		Map<String,String> map = new HashMap<String,String>();
		for(int i=0; i<length; i++){
			String[] mapInfo = strArray[i].split("=");
			if(mapInfo != null && mapInfo.length == 2){
				map.put(mapInfo[0], mapInfo[1]);
			}
		}
		return map;
	}
	
}
