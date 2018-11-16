package com.capstone.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

public class AuthToken {
	private String token;
	
	public static String getToken(String seed) {
		String SHA = "";
		try {
	        Date date = new Date();
			String planText = seed + date.getTime();
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(planText.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			SHA = null; 
		}
	return SHA;		
	}
}
