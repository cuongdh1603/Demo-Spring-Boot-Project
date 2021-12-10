package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@Controller
@RequestMapping
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@GetMapping("/index")
	public String viewHomePage(Model model) {
		model.addAttribute("listEmployee",employeeService.getAllEmployees());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return "index";
//		return findPaginated(1, model);
	}
	@GetMapping("/add_new")
	public String addNewEmployees(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee",employee);
		return "new_employee";
	}
	@PostMapping("/save_employee")
	public String saveEmployees(@ModelAttribute("employee") Employee employee) {
		employeeService.addNewEmployees(employee);
		return "redirect:/index";
	}
	@PostMapping("update_employee/{id}")
	public String updateEmployees(@PathVariable("id") long id,@ModelAttribute("employee") Employee updateEmployee) {
		Employee employee = employeeService.getEmployeeById(id);
		employeeService.saveEmployees(employee, updateEmployee);
		return "redirect:/index";
	}
	@GetMapping("/show_update/{id}")
	public String updateEmployees(Model model,@PathVariable("id") long id) {
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee",employee);
		return "update";
	}
	@GetMapping("/delete/{id}")
	public String deleteEmployees(@PathVariable("id") long id) {
		employeeService.deleteEmployee(id);
		return "redirect:/index";
	}
}
