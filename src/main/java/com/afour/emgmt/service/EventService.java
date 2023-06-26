/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;

import com.afour.emgmt.model.EventDTO;

/**
 * 
 */
public interface EventService {

	List<EventDTO> fetchAllEvents();

	List<EventDTO> fetchAllOpenEvents();

	EventDTO findEventByID(Integer ID);

	EventDTO addEvent(EventDTO dto);

	EventDTO updateEvent(EventDTO dto);

	Boolean deleteEventByID(Integer iD);

}
