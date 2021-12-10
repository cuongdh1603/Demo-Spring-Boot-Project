package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Roles {
	@Id
	@Column(name = "id_roles")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "role_name")
	private String roleName;
	@Column(name = "role_code")
	private String roleCode;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public Roles(long id, String roleName, String roleCode) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.roleCode = roleCode;
	}
	public Roles() {
		super();
	}
	@OneToMany(mappedBy = "roles")
	private List<UserRole> userRoles;
	public List<UserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
}
