/**
 * 
 */
package com.afour.emgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afour.emgmt.entity.Role;

/**
 * 
 */
public interface RoleRepository extends JpaRepository<Role, Integer>{

	Optional<Role> findByRoleName(String roleOrganizer);

}
