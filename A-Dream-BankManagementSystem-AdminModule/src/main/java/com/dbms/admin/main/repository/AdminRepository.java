package com.dbms.admin.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbms.admin.main.model.Employee;

@Repository
public interface AdminRepository extends JpaRepository<Employee, Integer> {

}
