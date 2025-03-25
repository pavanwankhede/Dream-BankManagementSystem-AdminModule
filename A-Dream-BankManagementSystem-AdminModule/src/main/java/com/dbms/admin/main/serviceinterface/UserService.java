package com.dbms.admin.main.serviceinterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.dbms.admin.main.exceptions.ValidationException;
import com.dbms.admin.main.model.Employee;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service 
public class UserService {
	
	private Validator validator;

	 public void validateUser(Employee employee) {
	        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

	        if (!violations.isEmpty()) {
	            Map<String, String> errors = new HashMap<>();
	            for (ConstraintViolation<Employee> violation : violations) {
	                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
	            }
	            throw new ValidationException(errors); // Throw custom validation exception
	        }
	    }
}
