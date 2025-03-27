package com.dbms.admin.main.serviceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbms.admin.main.dto.UsernamePasswordUpdate;
import com.dbms.admin.main.employeeCredentials.UsernamePasswordGenerator;
import com.dbms.admin.main.exceptions.EmployeeNotFoundException;

import com.dbms.admin.main.exceptions.ValidationException;
import com.dbms.admin.main.model.Employee;
import com.dbms.admin.main.repository.AdminRepository;
import com.dbms.admin.main.serviceinterface.EmailDetails;
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
	
	@Autowired
    private EmailDetails emailDetails;
	
	 @Override
	    public Employee saveEmployeeData(Employee empData, MultipartFile passport) {
	        try {
	            validateUser(empData);

	            // Generate UserName Password
	            empData.setUserName(UsernamePasswordGenerator.generateUsername(empData.getFirstName()));
	            empData.setPassword(UsernamePasswordGenerator.generatePassword(empData.getFirstName()));

	            // Save passport photo if provided
	            if (passport != null && !passport.isEmpty()) {
	                empData.setPassportPhoto(passport.getBytes());
	            }

	            // Save employee and send email
	            Employee savedEmployee = adminRepository.save(empData);
	            emailDetails.sendEmail(empData.getEmailId(), empData.getUserName(), empData.getPassword());

	            return savedEmployee;
	        } catch (IOException e) {
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
		        log.info("Existing employee found: {}", employee.getEmployeeId());


		        // Update passport if provided
		        if (passport != null && !passport.isEmpty()) {
		            try {
		                employee.setPassportPhoto(passport.getBytes());  // Assuming passport photo is saved as a byte array
		                log.info("Updating passport for Employee ID: {}", id);
		            } catch (IOException e) {
		                log.error("Error processing passport file", e);
		                throw new RuntimeException("Failed to process passport file.");
		            }
		        }

		        // Save the updated employee data
		        Employee updatedEmployee = adminRepository.save(employee);
		        log.info("Successfully updated Employee with ID: {}", id);
		        return updatedEmployee;
		    } else {
		        log.warn("Employee with ID {} not found", id);
		        throw new RuntimeException("Employee not found.");
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
		 

		 @Override
		    public String updateEmployeeUsernamePassword(int id, UsernamePasswordUpdate request) {
		        Employee employee = adminRepository.findById(id)
		                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

		        // Verify old username
		        if (!request.getOldUsername().equals(employee.getUserName())) {
		            return "Old username does not match.";
		        }

		        // Verify old password
		        if (!request.getOldPassword().equals(employee.getPassword())) {
		            return "Old password does not match.";
		        }

		        // Update username and password
		        employee.setUserName(request.getNewUsername());
		        employee.setPassword(request.getNewPassword()); // Storing plain text (not recommended)

		        adminRepository.save(employee);
		        return "Success";
		    }
	
}


	

	


