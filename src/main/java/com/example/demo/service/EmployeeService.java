package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	public void addNewEmployees(Employee employee) {
		employeeRepository.save(employee);
	}
	public void saveEmployees(Employee employee,Employee newEmployee) {
		if(employee!=null) {
			employee.setFirstName(newEmployee.getFirstName());
			employee.setLastName(newEmployee.getLastName());
			employee.setEmail(newEmployee.getEmail());
			employeeRepository.save(employee);
		}
	}
	public Employee getEmployeeById(long id) {
		Employee employee = employeeRepository.getEmployeeById(id);
		if(employee != null) return employee;
		else return null;
	}
	public void deleteEmployee(long id) {
		Employee employee = employeeRepository.getEmployeeById(id);
		if(employee != null) {
			employeeRepository.deleteEmployee(id);
		}
	}
}
