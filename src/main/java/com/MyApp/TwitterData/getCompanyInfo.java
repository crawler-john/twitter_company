package com.MyApp.TwitterData;

import java.util.List;

import com.MyApp.TwitterData.Model.companyModel;
import com.MyApp.TwitterData.Service.TwitterService;
import com.MyApp.TwitterData.Util.FollowersListUtil;
import com.db.manager.TwitterDB;

public class getCompanyInfo {
	public static void main(String[] args) {
		TwitterService ts = new TwitterService();
		TwitterDB db = new TwitterDB();
		FollowersListUtil flu = new FollowersListUtil();
		String id = args[0];
		
		List<String> emptyCompanyName = db.getEmptyCompanyNameList(id);

		for (String s : emptyCompanyName) {
			System.out.println(s);

			try {
				Thread.sleep(flu.GetWaitSearchTime(ts.getRateLimitStatus()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			String json = ts.getSearchList(s.replaceAll(" ", "_").replaceAll(
					"&", "_"));
			try {
				// System.out.println("flu.getName(json)"+flu.getName(json)+"\ns"+s+"");
				// System.out.println("flu.getName(json).toLowerCase()"+flu.getName(json).toLowerCase()+"\ns.toLowerCase()"+s.toLowerCase()+"");
				if ((flu.getName(json).toLowerCase().equals(s.toLowerCase()))
						|| flu.getName(json).toLowerCase()
								.contains(s.toLowerCase())
						|| s.toLowerCase().contains(
								flu.getName(json).toLowerCase())) {

					companyModel companyModel = new companyModel();
					companyModel = flu.getCompany(json, s);
					db.updateCompanyBasic(companyModel);
					db.updateInfoState(s, "2");

				} 
				else {
					List<String> list = db.getRedirectNameList(s);
					for (String ss : list) {
						System.out.println(s + "           " +ss);

						try {
							Thread.sleep(flu.GetWaitSearchTime(ts
									.getRateLimitStatus()));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						json = ts.getSearchList(ss.replaceAll(" ", "_").replaceAll(
								"&", "_"));
						if ((flu.getName(json).toLowerCase().equals(ss.toLowerCase()))
						|| flu.getName(json).toLowerCase()
								.contains(ss.toLowerCase())
						|| ss.toLowerCase().contains(
								flu.getName(json).toLowerCase())) {

							companyModel companyModel = new companyModel();
							companyModel = flu.getCompany(json, s);
							db.updateCompanyBasic(companyModel);
							db.updateInfoState(s, "2");
							break;

						}
					}
				}
			} catch (Exception e) {
				continue;
			}

		
		}

	}

}
