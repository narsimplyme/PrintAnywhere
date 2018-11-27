package com.capstone.dto;

public class Reserve {
	private int reserveId;
	private String userId;
	private int fileId;
	private int clientId;
	private String reserveTime;
	
	public Reserve() {
		super();
	}
	public Reserve(int reserveId, String userId, int fileId, int clientId, String reserveTime) {
		super();
		this.reserveId = reserveId;
		this.userId = userId;
		this.fileId = fileId;
		this.clientId = clientId;
		this.reserveTime = reserveTime;
	}
	
	public int getReserveId() {
		return reserveId;
	}
	public void setReserveId(int reserveId) {
		this.reserveId = reserveId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getReserveTime() {
		return reserveTime;
	}
	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	
}
