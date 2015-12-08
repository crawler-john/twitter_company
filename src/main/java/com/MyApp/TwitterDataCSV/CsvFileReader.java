package com.MyApp.TwitterDataCSV;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.db.manager.TwitterDB;



/**
 * @author ashraf_sarhan
 *
 */
public class CsvFileReader {
	
	//CSV文件头
	private static final String [] FILE_HEADER = {"company","num1","num2"};
	
	/**
	 * 
	 * @param fileName文件名
	 * @return 读取的CSV文件的list
	 */
	public static List<DataLine> readCsvFile(String fileName) {
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		List<DataLine> userList = new ArrayList<DataLine>();
		//创建CSVFormat（header mapping）
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER);
        try {
            //初始化FileReader object
            fileReader = new FileReader(fileName);
            //初始化 CSVParser object
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            //CSV文件records
            List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
            // CSV
        	
            // 
            for (int i = 1; i < csvRecords.size(); i++) {
            	CSVRecord record = csvRecords.get(i);
            	//创建用户对象填入数据
            	//schoolName,Productivity,EducationQualit,FinancialSupport
            	DataLine company = new DataLine(record.get("company"), record.get("num1"),
            				record.get("num2"));
            	userList.add(company);	
			}
            // 遍历打印
//            for (DataLine user : userList) {
//				System.out.println(user.toString());
//			}
            
        } 
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvFileParser.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		TwitterDB db  = new TwitterDB();
		List<DataLine> companyData =  readCsvFile("CompanyRanking.csv");
		int i = 0;
		for(DataLine co:companyData){
			i++;
			System.out.println(i);
			System.out.println(co.getCompanyName());
			db.insertBasicInfo(i, co.getCompanyName(), null);
	
		}
	
	}

}