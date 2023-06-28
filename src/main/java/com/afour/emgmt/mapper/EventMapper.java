/**
 * 
 */
package com.afour.emgmt.mapper;

import java.util.List;
import java.util.Set;

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

	Event prepareForUpdate(Event entity, EventDTO dto);

	Set<EventDTO> entityToDTO(Set<Event> entities);

	Set<Event> DTOToEntity(Set<EventDTO> dtos);

}
