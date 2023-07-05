/**
 * 
 */
package com.afour.emgmt.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afour.emgmt.config.AuthenticationFacade;
import com.afour.emgmt.entity.Event;
import com.afour.emgmt.model.EventDTO;

/**
 * 
 */
@Component
public class EventMapperImpl implements EventMapper {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	AuthenticationFacade authentication;

	@Override
	public EventDTO entityToDTO(Event entity) {
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

	@Override
	public Event prepareForUpdate(Event entity, EventDTO dto) {
		if (null != dto.getEventName())
			entity.setEventName(dto.getEventName());
		if (null != dto.getIsClosed())
			entity.setClosed(dto.getIsClosed());
		if (null != dto.getStartAt())
			entity.setStartAt(dto.getStartAt());
		if (null != dto.getEndAt())
			entity.setEndAt(dto.getEndAt());
		if (null != dto.getLocation())
			entity.setLocation(dto.getLocation());
		
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy(authentication.getAuthentication().getName());
		return entity;
	}

	@Override
	public Set<EventDTO> entityToDTO(Set<Event> entities) {
		return entities
				.stream()
				.map(entity -> entityToDTO(entity))
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Event> DTOToEntity(Set<EventDTO> dtos) {
		return dtos
				.stream()
				.map(entity -> DTOToEntity(entity))
				.collect(Collectors.toSet());
	}
	
	@Override
	public Event prepareForCreate(EventDTO dto) {
		Event entity = this.DTOToEntity(dto);
		entity.setCreatedAt(LocalDateTime.now());
		entity.setCreatedBy(authentication.getAuthentication().getName());
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy(authentication.getAuthentication().getName());
		return entity;
	}

}
