package com.MyApp.TwitterData;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.MyApp.TwitterData.Model.followerModel;
import com.MyApp.TwitterData.Service.TwitterService;
import com.MyApp.TwitterData.Util.FollowersListUtil;
import com.db.manager.TwitterDB;
/**
 * 
 * @author 89596
 *args[0]	 screenName
 *args[1]	 id
 *args[2]    cursor
 */
public class CopyOfgetFollowersInfo {
	
	public static void main(String[] args) throws Exception {
		Logger logger = Logger
				.getLogger(CopyOfgetFollowersInfo.class.getName());
		TwitterService ts = new TwitterService();
		TwitterDB db = new TwitterDB();
		FollowersListUtil flu = new FollowersListUtil();
		String jsonstr = null;
		String[] strs=null;
		
		while(null != (strs=db.randomGetScreenName())){
			
			Long time_a = (long) 0;
			String screenName = strs[1];
			String id = strs[0];
			long cursor = (long)-1;
			//开始存储
			System.out.println(strs[0]+" "+ strs[1]);
			db.updateState(screenName, "1");
			while (0 != cursor) {
				time_a = System.currentTimeMillis();
				long rate = 0;
				rate = flu.GetWaitTime(ts.getRateLimitStatus());
				System.out.println("GetWaitTime耗时 : "
						+ (System.currentTimeMillis() - time_a) / 1000f + " 秒 ");
				Thread.sleep(rate);
				
				
				time_a = System.currentTimeMillis();
				jsonstr = ts.getFollowersList(screenName, cursor);
				System.out.println("getFollowersList耗时 : "
						+ (System.currentTimeMillis() - time_a) / 1000f + " 秒 ");
				List<followerModel> list = new ArrayList<followerModel>();
				time_a = System.currentTimeMillis();
				
				
				try {
					cursor = flu.getCursor(jsonstr);
					logger.info(screenName + " + " + cursor);
					list = flu.getFollowers(jsonstr);
					db.insertFollowerInfo(id, list);

				} catch (Exception e) {
					logger.error("没有读取到json " + screenName + " + " + cursor + e);
					throw (e);
				}
				System.out.println("存储耗时 : "
						+ (System.currentTimeMillis() - time_a) / 1000f + " 秒 ");
				
			}

			db.updateState(screenName, "0");
		}	
	}

}
