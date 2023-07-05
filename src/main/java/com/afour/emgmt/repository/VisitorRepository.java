/**
 * 
 */
package com.afour.emgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afour.emgmt.entity.Visitor;

/**
 * 
 */
@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Integer>{

	Optional<Visitor> findByUserName(final String USERNAME);
	
}
