/**
 * 
 */
package com.afour.emgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.afour.emgmt.common.RoleEnum;
import com.afour.emgmt.entity.User;

/**
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("SELECT u FROM User AS u WHERE u.userName=:username")
	Optional<User> findByUserName(@Param("username") final String username);
	
	@Query("SELECT u FROM User AS u WHERE u.role.roleId=:roleId")
	List<User> findAllByRoleId(@Param("roleId") final RoleEnum roleId);
}
