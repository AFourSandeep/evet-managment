/**
 * 
 */
package com.afour.emgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.afour.emgmt.entity.Event;

/**
 * 
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
	
	@Query(value = "SELECT e from Event e WHERE e.isClosed=false")
	List<Event> findAllOpenEvents();

}
