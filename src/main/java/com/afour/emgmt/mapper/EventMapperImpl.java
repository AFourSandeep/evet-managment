/**
 * 
 */
package com.afour.emgmt.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afour.emgmt.entity.Event;
import com.afour.emgmt.model.EventDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Component
@Slf4j
public class EventMapperImpl implements EventMapper {

	@Autowired
	ModelMapper modelMapper;

	@Override
	public EventDTO entityToDTO(Event entity) {
		log.info("Organizer entity to DTO conversion for : {}",entity);
		return modelMapper.map(entity, EventDTO.class);
	}

	@Override
	public Event DTOToEntity(EventDTO dto) {
		return modelMapper.map(dto, Event.class);
	}

	@Override
	public List<EventDTO> entityToDTO(List<Event> entities) {
		return entities
				.stream()
				.map(entity -> entityToDTO(entity))
				.collect(Collectors.toList());
	}

	@Override
	public List<Event> DTOToEntity(List<EventDTO> dtos) {
		return dtos
				.stream()
				.map(dto -> DTOToEntity(dto))
				.collect(Collectors.toList());
	}

}
