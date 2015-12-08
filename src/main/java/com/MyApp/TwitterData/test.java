package com.MyApp.TwitterData;

import com.MyApp.TwitterData.Service.TwitterService;
import com.MyApp.TwitterData.Util.FollowersListUtil;

public class test {
	public static void main(String[] args) {
		TwitterService ts = new TwitterService();

		FollowersListUtil flu = new FollowersListUtil();
	
		try {
			Thread.sleep(flu.GetWaitSearchTime(ts.getRateLimitStatus()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		String json = ts.getSearchList("Sony_Music_Entertainment");
		
		System.out.println(flu.getCompany(json,"Associated Press"));
		System.out.println(json);

	}

}
