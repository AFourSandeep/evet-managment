/**
 * 
 */
package com.afour.emgmt.mapper;

import java.util.List;

import com.afour.emgmt.entity.Event;
import com.afour.emgmt.model.EventDTO;

/**
 * 
 */
public interface EventMapper {
	
	EventDTO entityToDTO(Event entity);
	
	Event DTOToEntity(EventDTO dto);
	
	List<EventDTO> entityToDTO(List<Event> entities);
	
	List<Event> DTOToEntity(List<EventDTO> dtos);

}
