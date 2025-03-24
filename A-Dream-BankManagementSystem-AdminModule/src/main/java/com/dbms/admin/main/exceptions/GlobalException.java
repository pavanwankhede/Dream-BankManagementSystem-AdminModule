package com.dbms.admin.main.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dbms.admin.main.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
		ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());
	    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
<<<<<<< HEAD
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
       
		 return new ResponseEntity<>("Error: This request method is not allowed. Please check the API documentation.",HttpStatus.METHOD_NOT_ALLOWED);
                
    }
	
	  @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {

	        return new ResponseEntity<>("Employee not found with ID:",HttpStatus.NOT_FOUND);
	    }
=======
	
 
>>>>>>> branch 'master' of https://github.com/pavanwankhede/Dream-BankManagementSystem-AdminModule.git
}
