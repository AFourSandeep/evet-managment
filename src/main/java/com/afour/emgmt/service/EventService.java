/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;
import java.util.Set;

import com.afour.emgmt.entity.Event;
import com.afour.emgmt.model.EventDTO;

/**
 * 
 */
public interface EventService {

	List<EventDTO> fetchAllEvents();

	List<EventDTO> fetchEventsByStatus(Boolean status);

	EventDTO findEventByID(Integer ID);

	EventDTO addEvent(EventDTO dto);

	EventDTO updateEvent(EventDTO dto);

	Boolean deleteEventByID(Integer ID);
	
	Set<Event> findAllById(Set<Integer> eventIds);

}
