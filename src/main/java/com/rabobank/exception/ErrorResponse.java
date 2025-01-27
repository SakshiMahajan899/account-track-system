package com.rabobank.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	private String code;
	private HttpStatus status;
	private String message;

	public ErrorResponse(String errorCode, HttpStatus status, String message) {
		this.code = errorCode;
		this.status = status;
		this.message = message;
	}

	public String getErrorCode() {
		return code;
	}

	public void setErrorCode(String errorCode) {
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
}
