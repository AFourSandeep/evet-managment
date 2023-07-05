/**
 * 
 */
package com.afour.emgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afour.emgmt.entity.User;

/**
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	public Optional<User> findByUserName(final String username); 
}
