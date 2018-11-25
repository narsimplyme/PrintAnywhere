package com.capstone.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Token {
	
	private String tokenId;
	private String ttl;
	private String userId;
	
	public Token(String tokenId, String ttl, String userId) {
		super();
		this.tokenId = tokenId;
		this.ttl = ttl;
		this.userId = userId;
	}

	public Token() {
		super();
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getTtl() {
		return ttl;
	}

	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public boolean isToken() {
		if(this.tokenId == null)
			return false;
		if(this.tokenId.length() < 0)
			return false;
		else
			return true;
	}
	
	public boolean isTTL() {
		if(this.ttl == null)
			return false;
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Date d1;
		Date d2;
		try {
			d1 = f.parse(this.ttl);
			d2 = f.parse(f.format(date));
			long diff = d2.getTime() - d1.getTime();
			long min = diff / (1000*60);
			if(min > 30)
				return false;
			else
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
		
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Token [tokenId=");
		builder.append(tokenId);
		builder.append(", ttl=");
		builder.append(ttl);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}

}
