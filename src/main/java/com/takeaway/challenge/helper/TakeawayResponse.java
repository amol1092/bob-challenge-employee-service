package com.takeaway.challenge.helper;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class TakeawayResponse {

	private final String message;
	
	private final HttpStatus code;
	
	private final boolean isSuccess;
	
	private final Object content;
	
	private TakeawayResponse(String message, HttpStatus code, boolean isSuccess, Object content) {
		this.message = message;
		this.code = code;
		this.isSuccess = isSuccess;
		this.content = content;
	}
	
	public static TakeawayResponse success(String message, HttpStatus code, Object content) {
		return new TakeawayResponse(message, code, true, content);
	}
	
	public static TakeawayResponse failure(String message, HttpStatus code) {
		return new TakeawayResponse(message, code, false, null);
	}
}
