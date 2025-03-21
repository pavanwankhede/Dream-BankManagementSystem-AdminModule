package com.dbms.admin.main.serviceImpl;

import java.io.IOException;
import java.util.List;

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


		@Override
		public List<Employee> getAllEmployees() {
			log.info("Fetching all employees for admin user.");
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
	}


	

	


