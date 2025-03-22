package com.dbms.admin.main.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.dbms.admin.main.model.Employee;
import com.dbms.admin.main.serviceinterface.ServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/admin_module")
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
	            Employee savedEmployee = serviceInterface.saveEmployeeData(empData, passport);
	            
	            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
	        
	        } catch (IOException e) {
	            log.error("Error processing employee data JSON", e);
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid employee data format.");
	        } 
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
}


