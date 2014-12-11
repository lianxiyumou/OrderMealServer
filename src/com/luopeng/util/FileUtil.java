package com.luopeng.util;

import org.apache.commons.lang.StringUtils;

public class FileUtil {

	public static String[] getFileNameAndType(String fileName){
		return StringUtils.split(fileName, ".", StringUtils.lastIndexOf(fileName, '.'));
	}
	
}
