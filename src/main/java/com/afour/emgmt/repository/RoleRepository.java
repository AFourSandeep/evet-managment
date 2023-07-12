/**
 * 
 */
package com.afour.emgmt.repository;

import com.afour.emgmt.common.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import com.afour.emgmt.entity.Role;

import java.util.Optional;

/**
 * 
 */
public interface RoleRepository extends JpaRepository<Role, RoleEnum>{

	Optional<Role> findByRoleName(String roleOrganizer);

}
