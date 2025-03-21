package com.dbms.admin.main.serviceinterface;

import com.dbms.admin.main.model.Employee;

import jakarta.validation.Valid;

public interface ServiceInterface {

 public	Employee saveEmployeeData(@Valid Employee empData);

}
