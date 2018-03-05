package com.datatalking.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.zip.CRC32;

public class RandomCode {
	public static final char[] CODES= {
		'0','1','2','3','4','5','6','7','8','9','0',
		'a','b','c','d','e','f','g','h','i','j','k','l','m','n','p','q','r','s','t','u','v','w','x','y','z',
		'A','B','C','D','E','F','G','H','I','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'
	};
	public static final char[] codes= {
		'0','1','2','3','4','5','6','7','8','9','0',
		'a','b','c','d','e','f','g','h','i','j','k','l','m','n','p','q','r','s','t','u','v','w','x','y','z'
	};
	private static CRC32 crc32 = new CRC32();
	private static final boolean isCheckSum=true;
	private static final boolean caseSensitive=false;
	public static boolean debug=true;
	public static synchronized String getRandomCode(int len,boolean isChecksum,boolean caseSensitive){
		int _len=isChecksum?(len-1):len;
		if(_len<1)
			return "";
		char[] _code=caseSensitive?CODES:codes;
		Random random = new Random(); 
		StringBuilder sb=new StringBuilder();
		
		for(int i=0;i<_len;i++)
			sb.append(_code[random.nextInt(_code.length)]);
		if(!isChecksum)
			return sb.toString();
		crc32.reset();
		crc32.update(sb.toString().getBytes());
		sb.append(_code[(int) (crc32.getValue()%_code.length)]);
		return sb.toString();
	}
	public static synchronized String getRandomCode(int len){
		return getRandomCode(len,isCheckSum,caseSensitive);
	}
	
	private static final SimpleDateFormat sdf=new SimpleDateFormat("MMdd");
	public static synchronized String getRandomCode(){
		return sdf.format(new Date(System.currentTimeMillis()));
	}
	
	public static synchronized boolean checkRandomCode(String code,boolean caseSensitive){
		if(code==null||code.length()<2)
			return false;
		if(debug)
			return getRandomCode().compareTo(code)==0?true:false;
		int len=code.length();
		char c=code.charAt(len-1);
		String _temp=code.substring(0,len-1);
		crc32.reset();
		crc32.update(_temp.getBytes());
		char[] _code=caseSensitive?CODES:codes;
		char _c=_code[(int) (crc32.getValue()%_code.length)];
		if(_c==c)
			return true;
		return false;
	}
	public static synchronized boolean checkRandomCode(String code){
		return checkRandomCode(code,caseSensitive);
	}
	
	
	
	
	public static void main(String args[]){
		String temp=null;
		for(int i=0;i<4;i++)
			System.out.printf("%s - >%s\n",temp=getRandomCode(),checkRandomCode(temp));
	}
}
