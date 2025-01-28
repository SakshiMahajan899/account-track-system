package com.rabobank.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for account transaction responses.
 */
@Data
@AllArgsConstructor
public class ResponseDto {
	private int code;
	private HttpStatus status;
	private String message;

}
