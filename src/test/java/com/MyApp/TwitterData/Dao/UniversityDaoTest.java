package com.MyApp.TwitterData.Dao;

import java.io.IOException;
import java.util.List;
import junit.framework.TestCase;

public class UniversityDaoTest extends TestCase {

    public UniversityDaoTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testListUniversity() throws IOException {
        /*
         * 读取文件，将500所大学存入mysql
         */
//        UniversityDao ud = UniversityDao.getInstance();
//        File file = new File("C:/Users/Love/Documents/500 University");
//        FileReader fr = new FileReader(file);
//        BufferedReader br = new BufferedReader(fr);
//        String line = "";
//        while ((line = br.readLine()) != null) {
//            University u = new University();
//            u.setWiki_name(line);
//            ud.updateUniversity(u);
//        }
    }
    public void testGetUniversityByTwitterName() throws IOException {
        
//        FollowersListUtil flu= new FollowersListUtil();
//        DbDAO db=new DbDAO();
//        //University u = ud.getUniversityByTwitterName("Harvard");     
//        University u =(University) db.query("from University u where u.twitter_name ='Harvard'").list().get(0);
//      
//         
//        File file=new File("D:/json");
//        FileReader fw =new FileReader(file);
//        BufferedReader bf =new BufferedReader(fw);
//        String json = bf.readLine();
//        bf.close();
//        
//        long cursor=flu.getCursor(json);
//        
//        System.out.println(cursor);//输出到屏幕
//        
//          
//        Set<Follower> set = flu.getFollower(json);  
//        
//        
//        long startTime = System.currentTimeMillis();        
//        db.mergeSet(set);
//        long endTime = System.currentTimeMillis();
//        
//        System.out.println("folower" + (endTime - startTime) + "ms");
//        
//       
//
//        u.getFollower().addAll(set);
//
//        startTime = System.currentTimeMillis();        
//        db.saveorupdata(u);
//        endTime = System.currentTimeMillis();
//        System.out.println("folower" + (endTime - startTime) + "ms");
    }
    
    public void testquery(){
//        DbDAO db = new DbDAO();
//        List<University> list = db.query("from University where id not in(1)").list();
//        for(University u:list){
//               if(null!=u.getTwitter_name()){
//                   System.out.println(u.getTwitter_name());
//               }
//        }
        
        
    }

}
