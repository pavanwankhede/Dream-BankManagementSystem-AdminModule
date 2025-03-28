package com.dbms.admin.main.controller;

import java.io.IOException;
import java.lang.invoke.WrongMethodTypeException;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.WrongClassException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbms.admin.main.dto.UsernamePasswordUpdate;
import com.dbms.admin.main.model.Employee;
import com.dbms.admin.main.serviceinterface.ServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/admin_module")
@RequiredArgsConstructor // Automatically injects dependencies
public class AdminController {
	
	
	@Autowired
	private ObjectMapper mapper;
	
	private static final Logger log= LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private ServiceInterface serviceInterface;
	
	 @PostMapping("/addEmployees")
	    public ResponseEntity<?> addEmployeeData(
	            @Valid @RequestPart("empData") String empDataJson, 
	            @RequestPart("passport") MultipartFile passport) {
	                
		 
	        try {
	            log.info("Received request to save employee data.");
                
  // Uses mapper.readValue(empDataJson, Employee.class) to convert the JSON string into an Employee object.
	            Employee empData = mapper.readValue(empDataJson, Employee.class);
	            
	            // Save employee data
	            Employee savedEmployee = serviceInterface.saveEmployeeData(empData, passport);
	            
	            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
	        
	        } catch (IOException e) {
	            log.error("Error processing employee data JSON", e);
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid employee data format.");
	        } 
	    }
	 
	 
	 
	 @GetMapping("/getAllEmployeeData")
	 public ResponseEntity<List<Employee>> getAllEmployee(){
		 log.info("Received request to fetch all employees.");
		 List<Employee> listEmployee= serviceInterface.getAllEmployees();
		 log.info("Successfully fetched {} employees.", listEmployee.size());
		 return new ResponseEntity<List<Employee>>(listEmployee,HttpStatus.OK);
		 }
	 
	 
	 
	  @GetMapping("/loginCheck/{userName}/{password}")
	 public ResponseEntity<?> loginCheck(@PathVariable("userName") String userName,@PathVariable("password") String password)
	 {
		  log.info("Checking login for user: {}", userName);
		  if(userName.equals("admin")&& password.equals("admin"))
		  {
			 List<Employee> listEmployees= serviceInterface.getAllEmployees();
			 return new ResponseEntity<List<Employee>>(listEmployees,HttpStatus.OK);
		  }else {
			Employee employee = serviceInterface.getByUnameAndPassword(userName, password);
	        if (employee != null) {
	            return ResponseEntity.ok(employee); // 200 OK with single employee
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	        }
		 
	 }
    }
	 
	  
	 @GetMapping("/get_Employee/{employeeId}")
	 public ResponseEntity<Employee> getSingleEmployee(@PathVariable("employeeId")int id){
		 Employee employee=serviceInterface.getSingleEmployee(id);
		 return new ResponseEntity<Employee>(employee,HttpStatus.OK);
	 }
	 
	 @PutMapping("/updateEmployeeData/{employeeId}")
	 public ResponseEntity<?> updateEmployeeData(
	         @Valid @RequestPart("empData") String empDataJson, 
	         @RequestPart("passport") MultipartFile passport,
	         @PathVariable("employeeId") int id) {

	     try {
	         log.info("Received request to update Employee with ID: {}", id);

	         // Convert the empDataJson to Employee object
	         Employee empData = mapper.readValue(empDataJson, Employee.class);

	         // Call service to update employee data
	         Employee savedEmployee = serviceInterface.updateEmployeeData(empData, passport, id);

	         log.info("Successfully updated Employee with ID: {}", id);
	         return new ResponseEntity<>(savedEmployee, HttpStatus.OK);

	     } catch (IOException e) {
	         log.error("Error processing employee data JSON", e);
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid employee data format.");
	     } 
	    }
	 
	 @DeleteMapping("/deleteById/{employeeId}")
		public ResponseEntity<String> deleteEnquiryByID(@PathVariable("employeeId") int id){
			
			log.info("Received request to delete Employee with ID: {}", id);
			
			  boolean delete= serviceInterface.deleteEmployeeByID(id);
			  if(delete)
			  {
				  log.info("Successfully deleted Employee with ID: {}", id);
				  return new ResponseEntity<>("Delete Employee SuccessFully ✅",HttpStatus.OK);
			  }else
			  {
				  log.error("Error deleting Employee with ID: {}", id);
					return new ResponseEntity<String>("Employee Not Found",HttpStatus.NOT_FOUND);
			  }
			
 }
	 @PutMapping("/updateUsernamePassword/{employeeId}")
	 public ResponseEntity<String> updateEmployeeUsernamePassword(
	         @PathVariable int employeeId, 
	         @RequestBody UsernamePasswordUpdate request) {

	     log.info("Received request to update Username Password for Employee ID: {}", employeeId);

	     String response = serviceInterface.updateEmployeeUsernamePassword(employeeId, request);

	     if ("Success".equals(response)) {
	         return ResponseEntity.ok("Username and password successfully changed.");
	     } else {
	         return ResponseEntity.badRequest().body(response); // Returns specific error message
	     }

}
}
	 

