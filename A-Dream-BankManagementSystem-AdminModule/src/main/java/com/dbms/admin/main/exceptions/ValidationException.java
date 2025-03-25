package com.dbms.admin.main.exceptions;

import java.util.Map;

import lombok.Data;

@Data
public class ValidationException extends RuntimeException {

	private final Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        super("Validation failed");
        this.errors = errors;
    }
   
}
