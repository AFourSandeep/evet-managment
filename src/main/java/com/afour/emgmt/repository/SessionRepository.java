/**
 * 
 */
package com.afour.emgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.afour.emgmt.entity.Esession;

/**
 * 
 */
@Repository
public interface SessionRepository extends JpaRepository<Esession, Integer>{
	
	@Query(value="SELECT es from Esession es WHERE es.event.eventId= :eventId")
	List<Esession> findSessionEventByID(@Param("eventId")Integer eventId);

}
