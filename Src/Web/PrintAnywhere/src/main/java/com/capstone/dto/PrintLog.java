package com.capstone.dto;

public class PrintLog {
	
	private int printId;
	private String clientId;
	private String printCount;
	private String fileId;
	private String userId;
	private int printStartPoint;
	private int printEndPoint;
	
	public PrintLog() {
		super();
	}
	
	public PrintLog(int printId, String clientId, String printCount, String fileId, String userId, int printStartPoint, int printEndPoint) {
		super();
		this.printId = printId;
		this.clientId = clientId;
		this.printCount = printCount;
		this.fileId = fileId;
		this.userId = userId;
		this.printStartPoint = printStartPoint;
		this.printEndPoint = printEndPoint;
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
	public int getPayStartPoint() {
		return printStartPoint;
	}

	public void setPrintStartPoint(int printStartPoint) {
		this.printStartPoint = printStartPoint;
	}

	public int getPrintEndPoint() {
		return printEndPoint;
	}

	public void setPrintEndPoint(int printEndPoint) {
		this.printEndPoint = printEndPoint;
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
