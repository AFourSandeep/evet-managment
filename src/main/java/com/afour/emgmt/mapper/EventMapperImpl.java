/**
 * 
 */
package com.afour.emgmt.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afour.emgmt.entity.Event;
import com.afour.emgmt.model.EventDTO;

/**
 * 
 */
@Component
public class EventMapperImpl implements EventMapper {

	@Autowired
	ModelMapper modelMapper;

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
		
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy("System");
		return entity;
	}

}
