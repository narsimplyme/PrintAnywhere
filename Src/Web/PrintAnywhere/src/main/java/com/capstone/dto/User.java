package com.capstone.dto;

public class User {
	
	String userId;
	String userPw;
	String userName;
	String userMail;
	String userNickname;
	String userPhoneNumber;
	String userPoint;
	
	public User() {
		super();
	}
	
	public User(String userId, String userPw, String userName, String userMail, String userNickname,
			String userPhoneNumber, String userPoint) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.userName = userName;
		this.userMail = userMail;
		this.userNickname = userNickname;
		this.userPhoneNumber = userPhoneNumber;
		this.userPoint = userPoint;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}
	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}
	public String getUserPoint() {
		return userPoint;
	}
	public void setUserPoint(String userPoint) {
		this.userPoint = userPoint;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [userId=");
		builder.append(userId);
		builder.append(", userPw=");
		builder.append(userPw);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", userMail=");
		builder.append(userMail);
		builder.append(", userNickname=");
		builder.append(userNickname);
		builder.append(", userPhoneNumber=");
		builder.append(userPhoneNumber);
		builder.append(", userPoint=");
		builder.append(userPoint);
		builder.append("]");
		return builder.toString();
	}
	

}
