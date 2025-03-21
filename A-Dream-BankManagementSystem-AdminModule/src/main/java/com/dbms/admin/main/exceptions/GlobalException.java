package com.dbms.admin.main.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dbms.admin.main.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(ResourceNotSavedException.class)
	public ResponseEntity<ErrorResponseDTO> handleResourceNotSavedException(ResourceNotSavedException e) {
	    ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());
	    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
