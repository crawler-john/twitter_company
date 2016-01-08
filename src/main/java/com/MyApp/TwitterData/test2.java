package com.MyApp.TwitterData;

import com.MyApp.TwitterData.Service.TwitterService;
import com.db.manager.TwitterDB;

public class test2 {
	
	
	public static void main(String[] args) {
		TwitterService ts = new TwitterService();
		System.out.println(ts.getTimeLine("statoilasa"));
	}
}
