package com.capstone.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class BookMark {
	
	private String userId;
	private int clientId;
	
	public BookMark(String userId, int clientId) {
		super();
		this.userId = userId;
		this.clientId = clientId;
	}
	public BookMark() {
		super();
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	
}
