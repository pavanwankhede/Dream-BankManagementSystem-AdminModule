package com.dbms.admin.main.controller;

import java.time.LocalDate;
import java.time.LocalTime;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.admin.main.model.Employee;
import com.dbms.admin.main.serviceinterface.ServiceInterface;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/admin_module")
public class AdminController {
	
	private static final Logger log= LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private ServiceInterface serviceInterface;
	
	@PostMapping("/addEmployees")
	public ResponseEntity<Employee> addEmployeeData(@Valid @RequestBody Employee empData) {
	  
	    log.info("Received request to save empData: {}", empData);

	    Employee employee = serviceInterface.saveEmployeeData(empData);
	    return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}
	

}
