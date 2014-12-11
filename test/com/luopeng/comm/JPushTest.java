package com.luopeng.comm;

import org.junit.Test;

public class JPushTest {

	//@Test
	public void testPushNotice(){
		JPush.getInstance().pushNotice("server push2");
	}
	
	@Test
	public void testSendNotificationAll(){
		JPush.getInstance().sendNotificationAll("server notifaction");
	}
	
}
