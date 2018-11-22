package com.capstone.util;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.capstone.dto.File;

public class Response {
	
	boolean success = true;
	String message = "";
	String errors = "";
	JSONObject data = new JSONObject();
	
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

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
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
		builder.append("]");
		return builder.toString();
	}
	
	
}
