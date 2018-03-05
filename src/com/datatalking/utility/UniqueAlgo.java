package com.datatalking.utility;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;


public class UniqueAlgo {
	private static UniqueAlgo _instance=new UniqueAlgo();
	public static UniqueAlgo instance(){
		return _instance;
	}
	public static String getUUID(){
		return UUID.randomUUID().toString().toLowerCase();
	}
	public static String getGUID(){
		return (_instance.new RandomGUID()).toString();
	}
	
	public static String getShortUUID(String name) {  
		return getShortUUID(UUID.fromString(name));  
	}  
	  
	public static String getShortUUID(byte[] bytes) {  
		return getShortUUID(UUID.nameUUIDFromBytes(bytes));  
	}  
	  
	public static String getShortUUID() {  
		return getShortUUID(UUID.randomUUID());  
	}  
	  
	
	
	private static String getShortUUID(UUID u) {  
		return UUIDtoString(u);  
	}  
	private static String UUIDtoString(UUID u) {  
		long mostSigBits = u.getMostSignificantBits();  
		long leastSigBits = u.getLeastSignificantBits();  
		return (digits(mostSigBits >> 32, 8) + digits(mostSigBits >> 16, 4)  
	                + digits(mostSigBits, 4) + digits(leastSigBits >> 48, 4) + digits(  
	                leastSigBits, 12));  
	}  
	private static String digits(long val, int digits) {  
		long hi = 1L << (digits * 4);  
		return Long.toString(hi | (val & (hi - 1)), 36).substring(1);  
	}  
	//------------
	private static SecureRandom mySecureRand; 
	private static Random myRand;
	private static String s_id;
	private static final int PAD_BELOW = 0x10;  
	private static final int TWO_BYTES = 0xFF;
	static{  
	      mySecureRand = new SecureRandom();  
	      long secureInitializer = mySecureRand.nextLong();  
	      myRand = new Random(secureInitializer);  
	      try {  
	         s_id = InetAddress.getLocalHost().toString();  
	      } catch (UnknownHostException e) {  
	         e.printStackTrace();  
	      } 
	} 
	public class RandomGUID {
		/**
		 * RandomGUID myGUID = new RandomGUID();  
		    System.out.println("Seeding String=" + myGUID.valueBeforeMD5);  
		    		Seeding String=tang-hasee/192.168.1.15:1442384156694:-3714628389708975471
		    System.out.println("rawGUID=" + myGUID.valueAfterMD5);  
		    		rawGUID=97345fc57e49784609817b347b1d7ceb
		    System.out.println("RandomGUID=" + myGUID.toString()); 
		    		RandomGUID=97345fc5-7e49-7846-0981-7b347b1d7ceb
		 * */
		public String valueBeforeMD5 = "";  
		public String valueAfterMD5 = "";  
		public RandomGUID() {  
		      getRandomGUID(false);  
		}
		public RandomGUID(boolean secure) {  
		      getRandomGUID(secure);  
		} 
		private void getRandomGUID(boolean secure) {  
		      MessageDigest md5 = null;  
		      StringBuffer sbValueBeforeMD5 = new StringBuffer(128);  
		  
		      try {  
		         md5 = MessageDigest.getInstance("MD5");  
		      } catch (NoSuchAlgorithmException e) {  
		         e.printStackTrace();
		      } 
		      try {  
		         long time = System.currentTimeMillis();  
		         long rand = 0;  
		  
		         if (secure) {  
		            rand = mySecureRand.nextLong();  
		         } else {  
		            rand = myRand.nextLong();  
		         }  
		         sbValueBeforeMD5.append(s_id);  
		         sbValueBeforeMD5.append(":");  
		         sbValueBeforeMD5.append(Long.toString(time));  
		         sbValueBeforeMD5.append(":");  
		         sbValueBeforeMD5.append(Long.toString(rand));  
		  
		         valueBeforeMD5 = sbValueBeforeMD5.toString();  
		         md5.update(valueBeforeMD5.getBytes());  
		  
		         byte[] array = md5.digest();  
		         StringBuffer sb = new StringBuffer(32);  
		         for (int j = 0; j < array.length; ++j) {  
		            int b = array[j] & TWO_BYTES;  
		            if (b < PAD_BELOW)  
		               sb.append('0');  
		            sb.append(Integer.toHexString(b));  
		         }  
		  
		         valueAfterMD5 = sb.toString();  
		  
		      } catch (Exception e) {  
		         e.printStackTrace();
		      }  
		   }
		@Override
		public String toString() {  
		      String raw = valueAfterMD5.toLowerCase(); //toUpperCase() 
		      StringBuffer sb = new StringBuffer(64);  
		      sb.append(raw.substring(0, 8));  
		      sb.append("-");  
		      sb.append(raw.substring(8, 12));  
		      sb.append("-");  
		      sb.append(raw.substring(12, 16));  
		      sb.append("-");  
		      sb.append(raw.substring(16, 20));  
		      sb.append("-");  
		      sb.append(raw.substring(20));  
		  
		      return sb.toString();  
		   }  
	}
}
