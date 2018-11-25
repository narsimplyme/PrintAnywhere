package com.capstone.dto;

public class Pay {
	
	private int payId;
	private String payDate;
	private int payCash;
	private String payType;
	private String userId;
	private int payStartPoint;
	private int payEndPoint;
	
	public Pay() {
		super();
	}
	public Pay(int payId, String payDate, int payCash, String payType, String userId,
			int payStartPoint, int payEndPoint) {
		super();
		this.payId = payId;
		this.payDate = payDate;
		this.payCash = payCash;
		this.payType = payType;
		this.userId = userId;
		this.payStartPoint = payStartPoint;
		this.payEndPoint = payEndPoint;
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
	public int getPayCash() {
		return payCash;
	}
	public void setPayCash(int payCash) {
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
	public int getPayStartPoint() {
		return payStartPoint;
	}
	public void setPayStartPoint(int payStartPoint) {
		this.payStartPoint = payStartPoint;
	}
	public int getPayEndPoint() {
		return payEndPoint;
	}
	public void setPayEndPoint(int payEndPoint) {
		this.payEndPoint = payEndPoint;
	}

	
}
