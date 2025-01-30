package com.rabobank.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for account transaction responses.
 */
@AllArgsConstructor
@Data
public class ResponseDto {
	
	private String message;
	private int code;
	private HttpStatus status;

}
