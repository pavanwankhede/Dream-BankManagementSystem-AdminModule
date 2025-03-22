package com.dbms.admin.main.serviceImpl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbms.admin.main.model.Employee;
import com.dbms.admin.main.repository.AdminRepository;
import com.dbms.admin.main.serviceinterface.ServiceInterface;

@Service
public class AdminServiceImpl implements ServiceInterface{
	
	private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired 
	private AdminRepository adminRepository;


		@Override
	    public Employee saveEmployeeData(Employee empData, MultipartFile passport) {
	        try {
	        	//Checks if the passport file is not null and not empty.
	            if (passport != null && !passport.isEmpty()) {
	            	
	            	// If a passport photo is provided, it reads the file as a byte array
	                empData.setPassportPhoto(passport.getBytes());
	            }
	            return adminRepository.save(empData);
	        
	        } catch (IOException e) {
	            log.error("Error while processing passport photo", e);
	            throw new RuntimeException("Failed to process passport photo", e);
	        }
	    }
	}


	

	


