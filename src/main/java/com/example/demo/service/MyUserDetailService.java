package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Roles;
import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService{
	@Autowired
	private UserService userService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = userService.getUserByUsername(username);
		if(users != null) {
			List<Roles> listRoles = userService.getRoleListByIdUser(users.getId());
			Set<String> setRoles = new HashSet<>();
			for (Roles r : listRoles) {
				setRoles.add(r.getRoleCode());
			}
			List<GrantedAuthority> authorities = setRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
			return new User(users.getUsername(), users.getPassword(), authorities);
		}
		else throw new UsernameNotFoundException("Invalid username or password.");
	}

}
