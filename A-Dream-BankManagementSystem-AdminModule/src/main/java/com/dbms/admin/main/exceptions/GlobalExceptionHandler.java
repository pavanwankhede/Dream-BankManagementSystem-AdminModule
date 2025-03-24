package com.dbms.admin.main.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.dbms.admin.main.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return new ResponseEntity<>(
            "Error: This request method is not allowed. Please check the API documentation.",
            HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(EmployeeNotFoundException e) {
        System.out.println("Exception caught: " + e.getMessage()); 
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
}
}