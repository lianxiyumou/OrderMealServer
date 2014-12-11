package com.luopeng.comm;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

public class JPush {
	
	private static JPush jpush = new JPush();
	
	private JPushClient jpushClient = null;
	
	private static final String MASTER_SECRET = "ef1c1eb1d24f57b508c0576d";
	private static final String APP_KEY = "b138982e4411baaffd79b106";
	
	private JPush(){
		jpushClient = new JPushClient(MASTER_SECRET,APP_KEY);
	}
	
	public static JPush getInstance(){
		if(jpush == null){
			jpush = new JPush();
		}
		return jpush;
	}
	
	public void pushNotice(String msgContent){
		try {
			jpushClient.sendMessageAll(msgContent);
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendNotificationAll(String alert){
		try {
			jpushClient.sendNotificationAll(alert);
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
