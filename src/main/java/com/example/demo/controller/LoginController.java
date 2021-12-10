package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.UserService;

@Controller
@RequestMapping
public class LoginController {
	@Autowired
	private UserService userService;
	@GetMapping("/signup")
	public String showRegister() {
		return "register";
	}
	@PostMapping("/post-signup")
	public String registerUserSuccess(@RequestParam("username") String username,@RequestParam("password") String password) {
		userService.creatNewUser(username, password);
		return "redirect:/signup?success";
	}
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
}
