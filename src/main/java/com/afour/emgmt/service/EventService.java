/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;
import java.util.Set;

import com.afour.emgmt.entity.Event;
import com.afour.emgmt.exception.NoDataFoundException;
import com.afour.emgmt.model.EventDTO;

/**
 * Declared all the service methods for Event
 */
public interface EventService {

	/**
	 * Fetch all the existing events present in DB and transform then from entity to
	 * DTO
	 * 
	 * @return java.util.List
	 */
	List<EventDTO> fetchAllEvents();

	/**
	 * Fetch all the event based on their status and transform them from entity into
	 * DTO
	 * 
	 * @param Boolean status
	 * @return java.util.List
	 * @throws NoDataFoundException 
	 */
	List<EventDTO> fetchEventsByStatus(Boolean status);

	/**
	 * Fetch a event based on its id and transform it from entity into
	 * DTO
	 * @param Integer ID
	 * @return java.util.List
	 * @throws NoDataFoundException 
	 */
	EventDTO findEventByID(Integer ID) throws NoDataFoundException;

	/**
	 * Takes input as an DTO and create and persists its entity
	 * and transform this entity back to DTO and returns
	 * @param com.afour.emgmt.model.EventDTO
	 * @return com.afour.emgmt.model.EventDTO
	 */
	EventDTO addEvent(EventDTO dto);

	/**
	 * Takes input as an DTO and update and persists its entity
	 * and transform this entity back to DTO and returns
	 * @param com.afour.emgmt.model.EventDTO
	 * @return com.afour.emgmt.model.EventDTO
	 * @throws NoDataFoundException 
	 */
	EventDTO updateEvent(EventDTO dto) throws NoDataFoundException;

	/**
	 * Accepts one integer id of an existing Event and remove it from database.
	 * @param Integer ID
	 * @return Boolean
	 * @throws NoDataFoundException 
	 */
	Boolean deleteEventByID(Integer ID) throws NoDataFoundException;

	/**
	 * Accepts a collection of event id  and find all entities into database
	 * finally transform them into DTO and returns the DTO list.
	 * @param java.util.Set<Integer>
	 * @return java.util.Set<com.afour.emgmt.model.EventDTO>
	 * @throws NoDataFoundException 
	 */
	Set<Event> findAllById(Set<Integer> eventIds) throws NoDataFoundException;

}
