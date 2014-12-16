package com.luopeng.parse;

import org.junit.Test;

public class ParseFoodTest {

	@Test
	public void testStr2List(){
		String str = "[{'name':1,'type':1},{'name':2,'type':2}]";
		ParseFood.str2FoodList(str);
	}
	
}
