package com.db.manager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class deleteSameName {

	private static final String POOLNAME = "proxool.Twitter";

	// 查询未获得schoolname_facebook的学校
	public List<String> getEmptySchoolNameList() {
		DBServer dbServer = new DBServer(POOLNAME);
		List<String> list = new ArrayList<String>();
		try {
			String sql = "(select id,company_name,count(company_name) from t_company t2  group by company_name having count(company_name) > 1)";
			ResultSet rs = dbServer.select(sql);
			while (rs.next()) {
//				System.out.println(rs.getString("company_name"));
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
	
	public void deleteSchoolSameNameList(String name) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			String sql = "SELECT id from t_company where company_name='"+name+"'";
			System.out.println(sql);
			ResultSet rs = dbServer.select(sql);
			rs.next();
			while (rs.next()) {
				
				System.out.println(rs.getString("id"));
				delete(rs.getString("id"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();

		}
		
	}
	
	
	public void delete(String  id) {
		DBServer dbServer = new DBServer(POOLNAME);
		try {
			dbServer.delete("delete from t_company where id ="+id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbServer.close();
		}
	}
	public static void main(String[] args) {
		deleteSameName tesetsql =  new deleteSameName();
		List<String> list = tesetsql.getEmptySchoolNameList();
		System.out.println(111);
		for(String string : list){
			System.out.println(string);
			tesetsql.deleteSchoolSameNameList(string);
		}
	}
	
}
