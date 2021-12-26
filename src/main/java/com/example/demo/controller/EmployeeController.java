package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;



@Controller
@RequestMapping
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@GetMapping({"/index","/"})
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
	public String saveEmployees(@ModelAttribute("employee") Employee newEmployee,
			@RequestParam("image") MultipartFile multipartFile) throws Exception {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		newEmployee.setPhoto(fileName);
		if(fileName.equals("")) newEmployee.setPhoto(null);
		Employee employee = employeeService.addNewEmployees(newEmployee);
		if(employee.getPhoto() != null) {
			String uploadDir = "./src/main/resources/static/image/" + employee.getId();
			Path uploadPath = Paths.get(uploadDir);
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			InputStream inputStream = multipartFile.getInputStream();
			Path filePath = uploadPath.resolve(fileName);
			System.out.println(filePath.toFile().getAbsolutePath());
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			
		}
		return "redirect:/index";
	}
	@PostMapping("update_employee/{id}")
	public String updateEmployees(@PathVariable("id") long id,
			@ModelAttribute("employee") Employee updateEmployee,
			@RequestParam("image") MultipartFile multipartFile) throws Exception {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		updateEmployee.setPhoto(fileName);
		if(fileName.equals("")) updateEmployee.setPhoto(null);
		Employee employee = employeeService.getEmployeeById(id);
		if(updateEmployee.getPhoto() != null) {
			String uploadDir = "./src/main/resources/static/image/" + id;
			Path uploadPath = Paths.get(uploadDir);
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			else {
				File file = new File(uploadDir);
				deleteFiles(file);
			}
			InputStream inputStream = multipartFile.getInputStream();
			Path filePath = uploadPath.resolve(fileName);
			System.out.println(filePath.toFile().getAbsolutePath());
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}
		
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
		Employee employee = employeeService.getEmployeeById(id);
		//if field photo of object not null, it means that the directory contains image of that object has been already exist
		if(employee.getPhoto() != null) {
			String uploadDir = "./src/main/resources/static/image/" + id;
			Path uploadPath = Paths.get(uploadDir);
			if(Files.exists(uploadPath)) {
				File file = new File(uploadDir);
				deleteDirectory(file);
				file.delete();
			}
		}
		employeeService.deleteEmployee(id);
		return "redirect:/index";
	}
	//delete all file in a directory
	public static void deleteFiles(File dirPath) {
		File filesList[] = dirPath.listFiles();
		for(File file:filesList) {
			if(file.isFile()) file.delete();
			else deleteFiles(file);
		}
	}
	//delete an entire directory
	public static void deleteDirectory(File dirPath) {
		for(File subfile : dirPath.listFiles()) {
			if(subfile.isDirectory()) {
				deleteDirectory(subfile);
			}
			subfile.delete();
		}
	}
}
