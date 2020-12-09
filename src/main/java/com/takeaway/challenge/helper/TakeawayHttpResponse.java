package com.takeaway.challenge.helper;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class TakeawayHttpResponse {

	private final String message;
	
	private final HttpStatus code;
	
	private final boolean isSuccess;
	
	private final Object content;
	
	private TakeawayHttpResponse(String message, HttpStatus code, boolean isSuccess, Object content) {
		this.message = message;
		this.code = code;
		this.isSuccess = isSuccess;
		this.content = content;
	}
	
	public static TakeawayHttpResponse success(String message, HttpStatus code, Object content) {
		return new TakeawayHttpResponse(message, code, true, content);
	}
	
	public static TakeawayHttpResponse failure(String message, HttpStatus code) {
		return new TakeawayHttpResponse(message, code, false, null);
	}
}
