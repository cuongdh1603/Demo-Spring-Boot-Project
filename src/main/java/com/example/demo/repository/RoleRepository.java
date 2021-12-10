package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Roles;
@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
	@Query(value ="select r.* from roles r\r\n"
			+ "join users_roles ur on \r\n"
			+ "	r.id_roles = ur.id_roles\r\n"
			+ "join users u on \r\n"
			+ "	u.id_users = ur.id_users\r\n"
			+ "where u.id_users = :idUser",nativeQuery = true)
	public List<Roles> getListRoleOfUserByIdUser(@Param("idUser") long idUser);
}
