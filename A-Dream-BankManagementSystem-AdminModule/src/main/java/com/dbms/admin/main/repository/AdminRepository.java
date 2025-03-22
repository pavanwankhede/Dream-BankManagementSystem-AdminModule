package com.dbms.admin.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbms.admin.main.model.Employee;

@Repository
public interface AdminRepository extends JpaRepository<Employee, Integer> {
	
	
	@Query("SELECT e FROM Employee e WHERE e.userName = :userName AND e.password = :password")
	public Employee findByUserNameAndPassword(String userName, String password);

}
