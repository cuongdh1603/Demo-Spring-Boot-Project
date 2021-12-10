package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Roles;
import com.example.demo.model.UserRole;
import com.example.demo.model.Users;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;

@Service
public class UserService {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	public void creatNewUser(String username, String password) {
		if(!isExist(username)) {
			Users users = new Users();
			users.setUsername(username);
			users.setPassword(bCryptPasswordEncoder.encode(password));
			userRepository.save(users);
			
			Roles roles = new Roles();
			roles.setRoleName("normal user");
			roles.setRoleCode("USER");
			roleRepository.save(roles);
			
			UserRole userRole = new UserRole();
			userRole.setUsers(users);
			userRole.setRoles(roles);
			userRoleRepository.save(userRole);
		}
	}
	private boolean isExist(String username) {
		Users users = userRepository.findByUsername(username);
		return users != null;
	}
	public Users getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	public List<Roles> getRoleListByIdUser(long id) {
		return roleRepository.getListRoleOfUserByIdUser(id);
	}
}
