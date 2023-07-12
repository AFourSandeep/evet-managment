/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;
import java.util.Set;

import com.afour.emgmt.entity.Event;
import com.afour.emgmt.model.EventDTO;

/**
 * Declared all the service methods for Event
 */
public interface EventService {

	/**
	 * Fetch all the existing events present in DB and transform then from entity to
	 * DTO
	 * 
	 * @return {@link List}
	 */
	List<EventDTO> fetchAllEvents();

	/**
	 * Fetch all the event based on their status and transform them from entity into
	 * DTO
	 * 
	 * @param status
	 * @return {@link List}
	 */
	List<EventDTO> fetchEventsByStatus(boolean status);

	/**
	 * Fetch a event based on its id and transform it from entity into
	 * DTO
	 * @param ID
	 * @return {@link List}
	 */
	EventDTO findEventByID(Integer ID);

	/**
	 * Takes input as an DTO and create and persists its entity
	 * and transform this entity back to DTO and returns
	 * @param dto {@link EventDTO}
	 * @return {@link EventDTO}
	 */
	EventDTO addEvent(EventDTO dto);

	/**
	 * Takes input as an DTO and update and persists its entity
	 * and transform this entity back to DTO and returns
	 * @param dto {@link EventDTO}
	 * @return {@link EventDTO}
	 */
	EventDTO updateEvent(EventDTO dto) ;

	/**
	 * Accepts one integer id of an existing Event and remove it from database.
	 * @param ID {@link Integer}
	 * @return boolean
	 */
	boolean deleteEventByID(Integer ID);

	/**
	 * Accepts a collection of event id and find all entities into database
	 * finally transform them into DTO and returns the DTO list.
	 * @param eventIds {@link Set&lt;Integer&gt;}
	 * @return {@link Set&lt;EventDTO&gt;} a set of {@link EventDTO}
	 */
	Set<Event> findAllById(Set<Integer> eventIds) ;

}
