package com.dbms.admin.main.serviceinterface;

import org.springframework.web.multipart.MultipartFile;

import com.dbms.admin.main.model.Employee;

import jakarta.validation.Valid;

public interface ServiceInterface {

	Employee saveEmployeeData(Employee empData, MultipartFile passport);


 


}
