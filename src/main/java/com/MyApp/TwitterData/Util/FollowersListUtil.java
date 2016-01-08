package com.MyApp.TwitterData.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;






import com.MyApp.TwitterData.Model.companyModel;
import com.MyApp.TwitterData.Model.followerModel;

/**
 * 處理twitter api 返回的json
 * 
 * @author Love
 *
 */
public class FollowersListUtil {
	/**
	 * followers/list 返回的json 提取每一个 followers 放到 set中 *
	 * 
	 * @param json
	 *            followers/list' json
	 * @return Follower set
	 */
	
	
	public List<followerModel> getFollowers(String json){
		
		JSONObject jo = new JSONObject(json);
		List<followerModel> list = new ArrayList<followerModel>();
		int len = jo.getJSONArray("users").length();
		for (int i = 0; i < len; i++) {
			followerModel follower = new followerModel();
			follower.setUserId(jo.getJSONArray("users").getJSONObject(i).getLong("id"));
			follower.setScreen_name(jo.getJSONArray("users").getJSONObject(i).getString("screen_name"));;
			follower.setLocation((jo.getJSONArray("users").getJSONObject(i).isNull("location"))?null:jo.getJSONArray("users").getJSONObject(i).getString("location"));
			follower.setDescription((jo.getJSONArray("users").getJSONObject(i).isNull("description"))?null:jo.getJSONArray("users").getJSONObject(i).getString("description"));
			follower.setUrl((jo.getJSONArray("users").getJSONObject(i).isNull("url"))?null:jo.getJSONArray("users").getJSONObject(i).getString("url"));
			follower.setFollowers_count(jo.getJSONArray("users").getJSONObject(i).getLong("followers_count"));;
			follower.setFriends_count(jo.getJSONArray("users").getJSONObject(i).getLong("friends_count"));
			follower.setFavourites_count(jo.getJSONArray("users").getJSONObject(i).getLong("favourites_count"));
			follower.setVerified(jo.getJSONArray("users").getJSONObject(i).getBoolean("verified"));
			follower.setStatuses_count(jo.getJSONArray("users").getJSONObject(i).getLong("statuses_count"));
			
			list.add(follower);
		}
		
		return list;
		
	}
	
	
	
	
	
	
	/**
	 * 获取screen_name的字符串
	 * @param json	查询学校的字符串
	 * @return
	 */
	public String getName(String json) {
		if(json.equals("[]")){
			return null;
		}
		json = "{search:" + json + "}";
		JSONObject jo = new JSONObject(json);
		String screenName = (jo.getJSONArray("search").getJSONObject(0).isNull("name"))?null:jo.getJSONArray("search").getJSONObject(0).getString("name");
		return screenName;
	}

	public companyModel getCompany(String json,String companyName) {
		json = "{search:" + json + "}";
		companyModel companyModel = new companyModel();
		JSONObject jo = new JSONObject(json);
		
		companyModel.setCompanyName(companyName);
		
		companyModel.setScreenName(jo.getJSONArray("search").getJSONObject(0).getString("screen_name"));
		companyModel.setTwitter_id(jo.getJSONArray("search").getJSONObject(0).getLong("id"));
		companyModel.setLocation((jo.getJSONArray("search").getJSONObject(0).isNull("location"))?null:jo.getJSONArray("search").getJSONObject(0).getString("location"));
		companyModel.setDescription((jo.getJSONArray("search").getJSONObject(0).isNull("description"))?null:jo.getJSONArray("search").getJSONObject(0).getString("description"));
		companyModel.setUrl((jo.getJSONArray("search").getJSONObject(0).isNull("url"))?null:jo.getJSONArray("search").getJSONObject(0).getString("url"));
		companyModel.setFollowers_count(jo.getJSONArray("search").getJSONObject(0).getLong("followers_count"));
		companyModel.setFriends_count(jo.getJSONArray("search").getJSONObject(0).getLong("friends_count"));
		companyModel.setListed_count(jo.getJSONArray("search").getJSONObject(0).getLong("listed_count"));
		companyModel.setCreate_at((jo.getJSONArray("search").getJSONObject(0).isNull("created_at"))?null:jo.getJSONArray("search").getJSONObject(0).getString("created_at"));
		companyModel.setFavourites_count(jo.getJSONArray("search").getJSONObject(0).getLong("favourites_count"));
		companyModel.setVerified(jo.getJSONArray("search").getJSONObject(0).getBoolean("verified"));
		companyModel.setStatuses_count(jo.getJSONArray("search").getJSONObject(0).getLong("statuses_count"));
		return companyModel;
	}
	
	
	
	
	
	/**
	 * followers/list 返回的json 提取当前
	 * 
	 * @param json
	 *            followers/list' json
	 * @return Cursor
	 */
	public long getCursor(String json) {
		JSONObject jo = new JSONObject(json);
		return jo.getLong("next_cursor");
	}

	/**
	 * API Rate Limits *
	 * 
	 * @param json
	 *            application/rate_limit_status 返回json
	 * @return 需要等待的 毫秒数
	 */
	public long GetWaitSearchTime(String json) {
		JSONObject jobj = new JSONObject(json);
		if (jobj != null) {
			try {
				if (!jobj.isNull("resources")) {
					JSONObject resourcesobj = jobj.getJSONObject("resources");
					JSONObject statusobj = resourcesobj.getJSONObject("users");
					JSONObject apilimit = statusobj
							.getJSONObject("/users/search");
					int numremhits = apilimit.getInt("remaining");
					System.out.println("剩余次数 " + numremhits);
					if (numremhits <= 1) {
						long cuuenttime = System.currentTimeMillis() / 1000;
						long resettime = apilimit.getInt("reset");
						resettime = (resettime - cuuenttime) * 1000;
						if (resettime < 0) {
							resettime = 0 - resettime;
						}
						System.out.println("开始等待 " + resettime + "ms");
						return resettime;
					}
				}
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
		}
		return 0;
	}

	public long GetWaitTime(String json) {
		JSONObject jobj = new JSONObject(json);
		if (jobj != null) {
			try {
				if (!jobj.isNull("resources")) {
					JSONObject resourcesobj = jobj.getJSONObject("resources");
					JSONObject statusobj = resourcesobj
							.getJSONObject("followers");
					JSONObject apilimit = statusobj
							.getJSONObject("/followers/list");
					int numremhits = apilimit.getInt("remaining");
					System.out.println("剩余次数 " + numremhits);
					if (numremhits <= 1) {
						long cuuenttime = System.currentTimeMillis() / 1000;
						long resettime = apilimit.getInt("reset");
						resettime = (resettime - cuuenttime) * 1000;
						if (resettime < 0) {
							resettime = 0 - resettime;
						}
						System.out.println("开始等待 " + (resettime+50000) + "ms");
						return (resettime+50000);
					}
				}
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
		}
		return 0;
	}

}
