package com.rabobank.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for account transaction responses.
 */

public class ResponseDto {
	private int errorCode;
    private HttpStatus status;
	private String message;
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
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

	public ResponseDto(String message,int errorCode, HttpStatus status)
 {
		this.message = message;
		this.errorCode=errorCode;
		this.status=status;
	}

}
