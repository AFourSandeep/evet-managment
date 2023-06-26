/**
 * 
 */
package com.afour.emgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.afour.emgmt.entity.Visitor;

/**
 * 
 */
@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Integer>{

	Visitor findByUserName(final String USERNAME);
	
	@Query(value="SELECT v from Visitor v WHERE v.event.eventId= :eventId")
	List<Visitor> findVisitorsByEventId(@Param("eventId") Integer eventId);
	
//	List<Visitor> findVisitorsByEvent(Integer eventID);

}
