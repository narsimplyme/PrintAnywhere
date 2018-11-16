package com.capstone.dto;

public class Pay {
	
	int payId;
	String payDate;
	String payCash;
	String payType;
	String userId;
	
	public Pay() {
		super();
	}
	public Pay(int payId, String payDate, String payCash, String payType, String userId) {
		super();
		this.payId = payId;
		this.payDate = payDate;
		this.payCash = payCash;
		this.payType = payType;
		this.userId = userId;
	}
	
	public int getPayId() {
		return payId;
	}
	public void setPayId(int payId) {
		this.payId = payId;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getPayCash() {
		return payCash;
	}
	public void setPayCash(String payCash) {
		this.payCash = payCash;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
}
