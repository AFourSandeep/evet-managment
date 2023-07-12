/**
 * 
 */
package com.afour.emgmt.repository;

import com.afour.emgmt.common.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import com.afour.emgmt.entity.Role;

/**
 * 
 */
public interface RoleRepository extends JpaRepository<Role, RoleEnum>{

}
