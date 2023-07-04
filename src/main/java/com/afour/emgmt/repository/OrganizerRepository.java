/**
 * 
 */
package com.afour.emgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afour.emgmt.entity.Organizer;

/**
 * 
 */
@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Integer>{
	
	public Optional<Organizer> findByUserName(final String username); 
}
