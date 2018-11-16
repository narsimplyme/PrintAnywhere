package com.capstone.dto;

public class File {
	
	int fileId;
	String fileHash;
	String fileName;
	String fileType;
	String fileDate;
	String fileSize;
	String userId;
	
	public File() {
		super();
	}
	
	public File(int fileId, String fileHash, String fileName, String fileType, String fileDate, String fileSize, String userId) {
		super();
		this.fileId = fileId;
		this.fileHash = fileHash;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileDate = fileDate;
		this.fileSize = fileSize;
		this.userId = userId;
	}
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getFileHash() {
		return fileHash;
	}
	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
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
		builder.append("File [fileId=");
		builder.append(fileId);
		builder.append(", fileHash=");
		builder.append(fileHash);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", fileType=");
		builder.append(fileType);
		builder.append(", fileDate=");
		builder.append(fileDate);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}

}
