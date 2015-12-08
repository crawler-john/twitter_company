package com.MyApp.TwitterData;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.MyApp.TwitterData.Model.followerModel;
import com.MyApp.TwitterData.Service.TwitterService;
import com.MyApp.TwitterData.Util.FollowersListUtil;
import com.db.manager.TwitterDB;

public class getFollowersInfo {
	
	public static void main(String[] args) throws Exception {
		Logger logger = Logger.getLogger(getFollowersInfo.class.getName());
		TwitterService ts = new TwitterService();
		TwitterDB db = new TwitterDB();
		FollowersListUtil flu = new FollowersListUtil();
		
		String jsonstr = null;
		Long time_a = (long) 0;
		String screenNamelist[] = {};
		
		while(null!=(screenNamelist=db.randomGetScreenName())){
			String id = screenNamelist[0];
			String screenName = screenNamelist[1];
			db.updateState(screenName, "1");
			
			//开始存储
			System.out.println(screenName);
			
			long cursor = -1;
			while (0 != cursor) {
				time_a = System.currentTimeMillis();
					
				jsonstr = ts.getFollowersList(screenName, cursor);
			
				System.out.println("getFollowersList耗时 : "
						+ (System.currentTimeMillis() - time_a) / 1000f
						+ " 秒 ");
				
				List<followerModel> list=new ArrayList<followerModel>();
				time_a = System.currentTimeMillis();
				try {
					cursor = flu.getCursor(jsonstr);
					logger.info(screenName + " + " + cursor);
					list = flu.getFollowers(jsonstr);
					
					db.insertFollowerInfo(id, list);
					

					
				} catch (Exception e) {
					logger.error("没有读取到json " + screenName + " + "
							+ cursor + e);
					throw(e);
				}
				System.out.println("存储耗时 : "
						+ (System.currentTimeMillis() - time_a) / 1000f
						+ " 秒 ");
				time_a = System.currentTimeMillis();
				long rate = 0;
				rate = flu.GetWaitTime(ts.getRateLimitStatus());
				System.out.println("GetWaitTime耗时 : "
						+ (System.currentTimeMillis() - time_a) / 1000f
						+ " 秒 ");
				Thread.sleep(rate);
			}
			
			db.updateState(screenName, "0");
		}
			
		
	}
}
