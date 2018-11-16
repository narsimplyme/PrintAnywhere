package com.capstone.dto;

public class PrintLog {
	
	int printId;
	String clientId;
	String printCount;
	String fileId;
	String userId;
	
	public PrintLog() {
		super();
	}
	
	public PrintLog(int printId, String clientId, String printCount, String fileId, String userId) {
		super();
		this.printId = printId;
		this.clientId = clientId;
		this.printCount = printCount;
		this.fileId = fileId;
		this.userId = userId;
	}
	
	public int getPrintId() {
		return printId;
	}
	public void setPrintId(int printId) {
		this.printId = printId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getPrintCount() {
		return printCount;
	}
	public void setPrintCount(String printCount) {
		this.printCount = printCount;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PrintLog [printId=");
		builder.append(printId);
		builder.append(", clientId=");
		builder.append(clientId);
		builder.append(", printCount=");
		builder.append(printCount);
		builder.append(", fileId=");
		builder.append(fileId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}

}
