package com.rabobank.dto;

import org.springframework.http.HttpStatus;

/**
 * DTO for account transaction responses.
 */

public class ResponseDto {
	private int code;
	private HttpStatus status;
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int errorCode) {
		this.code = errorCode;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseDto(String message, int errorCode, HttpStatus status) {
		this.message = message;
		this.code = errorCode;
		this.status = status;
	}

}
