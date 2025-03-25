package com.dbms.admin.main.serviceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbms.admin.main.exceptions.EmployeeNotFoundException;
import com.dbms.admin.main.exceptions.ValidationException;
import com.dbms.admin.main.model.Employee;
import com.dbms.admin.main.repository.AdminRepository;
import com.dbms.admin.main.serviceinterface.ServiceInterface;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class AdminServiceImpl implements ServiceInterface{
	
	private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	
	@Autowired 
	private AdminRepository adminRepository;

	@Autowired
	private Validator validator;
 
		@Override
	    public Employee saveEmployeeData(Employee empData, MultipartFile passport) {
			 try {
		            // Validate Employee Data
		            validateUser(empData);
		            
		            // Checks if the passport file is not null and not empty.
		            if (passport != null && !passport.isEmpty()) {
		                // If a passport photo is provided, read the file as a byte array
		                empData.setPassportPhoto(passport.getBytes());
		            }
		            
		            return adminRepository.save(empData);
		        } catch (IOException e) {
		            log.error("Error while processing passport photo", e);
		            throw new RuntimeException("Failed to process passport photo", e);
		        }
		    }
		    
		    private void validateUser(Employee employee) {
		        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
		        
		        if (!violations.isEmpty()) {
		            Map<String, String> errors = new HashMap<>();
		            for (ConstraintViolation<Employee> violation : violations) {
		                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
		            }
		            throw new ValidationException(errors); // Throw custom validation exception
		        }
	    }


		@Override
		public List<Employee> getAllEmployees() {
			log.info("Fetching all employees for  user.");
			return adminRepository.findAll();
		}


		@Override
		public Employee getByUnameAndPassword(String userName, String password) {
			
			log.info("Checking login for user: {}", userName);
	        Employee employee = adminRepository.findByUserNameAndPassword(userName, password);
	        if (employee != null) {
	        	
	            log.info("Login successful for user: {}", userName);
	        } else {
	            log.warn("Invalid login attempt for user: {}", userName);
	        }
	        return employee;
		}
        


		@Override
		public Employee updateEmployeeData(Employee empData, MultipartFile passport, int id) {
			log.info("Received request to update Employee with ID: {}", id);
		    Optional<Employee> optionalEmployee = adminRepository.findById(id);
		    
		    if (optionalEmployee.isPresent()) {
		        Employee employee = optionalEmployee.get();
		        
		        // Update passport if provided
		        log.info("Existing employee found: {}", employee);
		        
		        if (passport != null && !passport.isEmpty()) {
		            try {
		            	
		            	// Assuming the passport is stored as a byte array
		            	employee.setPassportPhoto(passport.getBytes());
		            	log.info("Updating passport for Employee ID: {}", id);
		            	
		            } catch (IOException e) {
		                log.error("Error processing passport file", e);
		                throw new RuntimeException("Failed to process passport file.");
		            }
		        }
		        
		        log.info("Successfully updated Employee with ID: {}", id);
		        
		        // Save the updated employee
		        return adminRepository.save(employee);
		       
		    } else {
		    	
		    	log.error("Employee with ID {} not found", id);
		    	
		    	// Handle the case where employee is not found
		        throw new RuntimeException("Employee not found with id: " + id);  
		    }
		}


		@Override
		public boolean deleteEmployeeByID(int id) {
			 
			if(adminRepository.existsById(id))
			{
				adminRepository.deleteById(id);
				log.info("Successfully deleted Employee with ID: {}", id);
				return true;
			}
			log.error("Employee with ID {} not found. Deletion failed.", id);
			return false;
		}


		 public Employee getSingleEmployee(int id) {
			 return adminRepository.findById(id)
				        .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found."));
				}

	
}


	

	


