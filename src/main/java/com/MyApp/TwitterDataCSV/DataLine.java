package com.MyApp.TwitterDataCSV;

public class DataLine {
	private String companyName;
	private String num1;
	private String num2;
	public DataLine(String companyName,String num1,String num2){
		this.companyName = companyName;
		this.num1 = num1;
		this.num2 = num2;
		
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getNum1() {
		return num1;
	}

	public void setNum1(String num1) {
		this.num1 = num1;
	}

	public String getNum2() {
		return num2;
	}

	public void setNum2(String num2) {
		this.num2 = num2;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("companyName : ").append(this.getCompanyName());
		sb.append(", NUM1 : ").append(this.getNum1());
		sb.append(", NUM2 : ").append(this.getNum2());
		return sb.toString();
		
		
	}
	
	
}
