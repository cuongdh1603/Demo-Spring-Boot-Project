package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	@Query(value = "SELECT * FROM EMPLOYEES WHERE ID = :id",nativeQuery = true)
	Employee getEmployeeById(long id);
	@Query(value = "select * from employees where"
			+ " id = (select max(id) from employees)",nativeQuery = true)
	Employee getLastestEmployee();
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM EMPLOYEES WHERE ID = :id",nativeQuery = true)
	void deleteEmployee(long id);
	
}
