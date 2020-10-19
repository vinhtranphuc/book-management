package com.book.payload;

import java.util.Date;
import java.util.Map;

public class Response {
	
	private int status;
	public Date timestamp;
	public Object data;
	public String message;
	public Map<String,Object> messages;
	private String accessToken;
	
	public Response() {
		this.timestamp = new Date();
	}
	
	public Response(Object data) {
		this.timestamp = new Date();
		this.data = data;
	}
	
	public Response(Object data, String message) {
		this.timestamp = new Date();
		this.data = data;
		this.message = message;
	}
	
	public Response(Object data, Map<String,Object> messages) {
		this.timestamp = new Date();
		this.data = data;
		this.messages = messages;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}