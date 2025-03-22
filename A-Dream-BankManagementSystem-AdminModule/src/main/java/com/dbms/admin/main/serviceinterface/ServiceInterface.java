package com.dbms.admin.main.serviceinterface;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dbms.admin.main.model.Employee;

import jakarta.validation.Valid;

public interface ServiceInterface {

	public Employee saveEmployeeData(Employee empData, MultipartFile passport);

	public List<Employee> getAllEmployees();

    public	Employee getByUnameAndPassword(String userName, String password);

   public Employee getSingleEmployee(int id);

public Employee updateEmployeeData(Employee empData, MultipartFile passport, int id);

public boolean deleteEmployeeByID(int id);


 


}
