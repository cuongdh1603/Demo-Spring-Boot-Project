package com.example.demo.service;

import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	public Employee addNewEmployees(Employee employee) {
		employeeRepository.save(employee);
		return employeeRepository.getLastestEmployee();
	}
	public void saveEmployees(Employee employee,Employee newEmployee) {
		if(employee!=null) {
			employee.setFirstName(newEmployee.getFirstName());
			employee.setLastName(newEmployee.getLastName());
			employee.setEmail(newEmployee.getEmail());
			employee.setPhoto(newEmployee.getPhoto());
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
	public void saveImage(MultipartFile imageFile) throws Exception{
		String folder = "/photo/";
		byte[] bytes = imageFile.getBytes();
		Paths.get(folder + imageFile.getOriginalFilename());
		
	}
}
