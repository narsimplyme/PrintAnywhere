package com.capstone.util;

import java.util.ArrayList;

import com.capstone.dto.File;

public class Response {
	
	boolean success = true;
	String message = "";
	String errors = "";
	String token = "";
	Object data = new Object();
	ArrayList<File> fileArray = new ArrayList<>();
	
	public Response() {
		super();
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	public ArrayList<File> getFileArray() {
		return fileArray;
	}

	public void setFileArray(ArrayList<File> fileArray) {
		this.fileArray = fileArray;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Response [success=");
		builder.append(success);
		builder.append(", message=");
		builder.append(message);
		builder.append(", errors=");
		builder.append(errors);
		builder.append(", data=");
		builder.append(token);
		builder.append("]");
		return builder.toString();
	}
	
	
}
