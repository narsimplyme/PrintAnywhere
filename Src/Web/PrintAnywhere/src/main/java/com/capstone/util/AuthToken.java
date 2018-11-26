package com.capstone.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import com.capstone.dto.Token;

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
	
	public static Response isOk(Token token) {
		Response res = new Response();
		if(token == null) {
			res.setSuccess(false);
			res.setErrors(Constants.ERROR_CODE_7);
			res.setMessage(Constants.MSG_CODE_110);
		}else {
			if(!token.isToken()) {
				res.setSuccess(false);
				res.setErrors(Constants.ERROR_CODE_5);
				res.setMessage(Constants.MSG_CODE_107);
			}else {
				if(!token.isTTL()) {
					res.setSuccess(false);
					res.setErrors(Constants.ERROR_CODE_5);
					res.setMessage(Constants.MSG_CODE_108);
				}else {
					res.setSuccess(true);
					res.setMessage(Constants.MSG_CODE_200);
				}
			}
		}
		return res;
		
	}
}
