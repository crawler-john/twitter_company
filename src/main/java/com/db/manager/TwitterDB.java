package com.db.manager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.MyApp.TwitterData.Model.companyModel;
import com.MyApp.TwitterData.Model.followerModel;

public class TwitterDB {

	private static final String POOLNAME = "proxool.Twitter";

	// 查询未获得screen_name的学校
	public List<String> getEmptyCompanyNameList(String id) {
		DBServer dbServer = new DBServer(POOLNAME);
		List<String> list = new ArrayList<String>();
		try {
			String sql = "SELECT company_name from t_company where state is null and id > "
					+ id;

			ResultSet rs = dbServer.select(sql);
			while (rs.next()) {
				list.add(rs.getString("company_name"));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();

		}
		return list;

	}

	/**
	 * 
	 * @return
	 */
	public String[] randomGetScreenName() {
		DBServer dbServer = new DBServer(POOLNAME);
		String strings[] = { "", "" };
		try {
			String sql = "select * from t_company where state = '2' order by rand() limit 1";
			ResultSet rs = dbServer.select(sql);
			while (rs.next()) {
				strings[0] = rs.getString("id");
				strings[1] = rs.getString("screen_name");
				return strings;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		return null;
	}

	public void updateCompanyBasic(companyModel company) {
		DBServer dbServer = new DBServer(POOLNAME);
		long before = System.currentTimeMillis();

		try {
			HashMap<Integer, Object> params = new HashMap<Integer, Object>();
			int i = 1;
			params.put(i++, company.getScreenName());
			params.put(i++, company.getTwitter_id());
			params.put(i++, company.getLocation());
			params.put(i++, company.getDescription());
			params.put(i++, company.getUrl());
			params.put(i++, company.getFollowers_count());
			params.put(i++, company.getFriends_count());
			params.put(i++, company.getListed_count());
			params.put(i++, company.getCreate_at());
			params.put(i++, company.getFavourites_count());
			params.put(i++, company.isVerified());
			params.put(i++, company.getStatuses_count());
			String columns = "screen_name,twitter_id,location,description,url,followers_count,friends_count,listed_count,create_at,favourites_count,verified,statuses_count";
			String condition = "where company_name = '"
					+ company.getCompanyName() + "'";
			dbServer.update("t_company", columns, condition, params);
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}

	// 修改状态的值
	public void updateInfoState(String companyName, String state) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {

			String sql = "update t_company set state='" + state
					+ "' where company_name = '" + companyName + "'";
			dbServer.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}

	public void updateState(String screenName, String state) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {

			String sql = "update t_company set state='" + state
					+ "' where screen_name = '" + screenName + "'";
			dbServer.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}

	public void insertBasicInfo(int id, String companyName,
			String companyName_Twitter) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			HashMap<Integer, Object> params = new HashMap<Integer, Object>();
			int i = 1;
			params.put(i++, id);
			params.put(i++, companyName);
			params.put(i++, companyName_Twitter);
			dbServer.insert("t_company", "id,company_name,screen_name", params);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}

	public void insertFollowerInfo(String id, List<followerModel> followerList) {
		for (followerModel follower : followerList) {
			
			try {
				this.insertCompanyFollower(id, follower.getUserId());
			} catch (Exception e1) {
				System.out.println(id+"   "+follower.getUserId());
			}	
			try {
				
				this.insertFollowerInfo(id, follower);
			} catch (Exception e) {
				continue;
			}
		}
	}

	private void insertFollowerInfo(String id, followerModel follower)
			throws Exception {
		DBServer dbServer = new DBServer(POOLNAME);


		try {
			HashMap<Integer, Object> params = new HashMap<Integer, Object>();
			int i = 1;
			params.put(i++, follower.getUserId());
			params.put(i++, follower.getScreen_name());
			params.put(i++, follower.getLocation());
			params.put(i++, follower.getDescription());
			params.put(i++, follower.getUrl());
			params.put(i++, follower.getFollowers_count());
			params.put(i++, follower.getFriends_count());
			params.put(i++, follower.getFavourites_count());
			params.put(i++, follower.isVerified());
			params.put(i++, follower.getStatuses_count());
			dbServer.insert(
					"t_followers",
					"userId,screen_name,location,description,url,followers_count,friends_count,favourites_count,verified,statuses_count",
					params);

		} catch (Exception e) {
			System.out.println("已经差如果这个人了" + follower.getUserId());
		} finally {
			dbServer.close();
		}
	}

	private void insertCompanyFollower(String twitter_id, long user_id) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			HashMap<Integer, Object> params = new HashMap<Integer, Object>();
			int i = 1;
			params.put(i++, twitter_id);
			params.put(i++, user_id);

			dbServer.insert("t_company_followers", "companyId,userId", params);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}

	public List<String> getRedirectNameList(String name) {
	
		List<String> list = this.getRedirectList(this.selectRdFromList(this
				.selectPageId(name)));
		return list;
	}

	// 获取其在page表中的位置
	private String selectPageId(String Name) {
		DBServer dbServer = new DBServer(POOLNAME);
		String result = "";
		try {
			String sql = "SELECT page_id from page where page_title='" + Name
					+ "'";
			ResultSet rs = dbServer.select(sql);
			while (rs.next()) {
				result = result + rs.getString("page_id") + ",";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}

	// 获取其在page表中重定向的名字的列表
	private String selectRdFromList(String rd_to) {
		if (rd_to == null && rd_to == "") {
			System.out.println("rd_to 为空");
			return null;
		}
		DBServer dbServer = new DBServer(POOLNAME);
		String result = "";
		String sql = "";
		try {
			if (rd_to.contains(",")) {
				sql = "SELECT rd_from from redirect where rd_to in (" + rd_to
						+ ")";
			} else {
				sql = "SELECT rd_from from redirect where rd_to = " + rd_to;
			}

			ResultSet rs = dbServer.select(sql);
			while (rs.next()) {
				result = result + rs.getString("rd_from") + ",";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}

	private List<String> getRedirectList(String rd_from) {

		DBServer dbServer = new DBServer(POOLNAME);
		List<String> list = new ArrayList<String>();
		String sql = "";
		if (rd_from == null && rd_from == "") {
			System.out.println("rd_from 为空");
			return list;
		}
		try {
			if (rd_from.contains(",")) {
				sql = "SELECT page_title from page where page_id IN ("
						+ rd_from + ")";
			} else {
				sql = "SELECT page_title from page where page_id = " + rd_from;

			}
			ResultSet rs = dbServer.select(sql);
			while (rs.next()) {
				list.add(rs.getString("page_title"));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();

		}
		return list;

	}
}
