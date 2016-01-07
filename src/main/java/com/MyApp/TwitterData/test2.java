package com.MyApp.TwitterData;

import com.db.manager.TwitterDB;

public class test2 {
	
	
	public static void main(String[] args) {
		TwitterDB db = new TwitterDB();
		System.out.println(db.randomGetScreenName()[1]);
	}
}
