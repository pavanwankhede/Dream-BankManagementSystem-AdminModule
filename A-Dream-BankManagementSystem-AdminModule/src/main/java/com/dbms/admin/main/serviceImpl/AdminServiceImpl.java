package com.dbms.admin.main.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.admin.main.exceptions.ResourceNotSavedException;
import com.dbms.admin.main.model.Employee;
import com.dbms.admin.main.repository.AdminRepository;
import com.dbms.admin.main.serviceinterface.ServiceInterface;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Service
public class AdminServiceImpl implements ServiceInterface{
	
	private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired 
	private AdminRepository adminRepository;

	@Override
	public Employee saveEmployeeData(@Valid Employee empData) {
		
		 try {
		        log.info("Processing new employee: {}", empData);

		        // Save employee data
		        Employee savedEmployee = adminRepository.save(empData);

		        log.info("Employee saved successfully: {}", savedEmployee);
		        return savedEmployee;

		 
		    } catch (ConstraintViolationException e) {
		        log.error("Validation error: {}", e.getMessage(), e);
		        throw new ResourceNotSavedException("Invalid employee data. Please check the required fields.");
	    }
	}
	}


