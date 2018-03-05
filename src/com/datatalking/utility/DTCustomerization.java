package com.datatalking.utility;

import com.datatalking.exception.AppException;
//import org.codehaus.jackson.map.ObjectMapper;

public class DTCustomerization {
	public static final int version=0;
	public static final int subversion=1;
	public static final String builddate="20150917";
	public static final String author="TANG Dalong";
	public static final String desc[]={""};
	public static String Versions(){
		return String.format("version:%d.%d.%s Created by %s",version,subversion,builddate,author);
	}
	public static int Version(){
		return version;
	}
	public static int SubVersion(){
		return subversion;
	}
	public static String BuildDate(){
		return builddate;
	}
	public static String Author(){
		return author;
	}
	
	public static void main(String args[]) throws AppException{
//		System.out.println(DTCustomerization.Versions());
//		System.out.println(DataConvert.is("123"));
		
		test t1=new test();
		t1.ID="1";
		t1.Name="name1";
		String json="{\"ID\":\"1\",\"Name\":\"name1\"}";
		System.out.println(DataConvert.toJson(t1));
		test t0=DataConvert.fromJson(json, test.class);
		System.out.println(t0.toString());
		
		System.out.println(json=DataConvert.toXML(t1));
		System.out.println(DataConvert.fromXML(json, test.class).toString());
	}
}
