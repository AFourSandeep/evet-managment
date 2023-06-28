/**
 * 
 */
package com.afour.emgmt.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Event;
import com.afour.emgmt.mapper.EventMapper;
import com.afour.emgmt.model.EventDTO;
import com.afour.emgmt.repository.EventRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Service
@Slf4j
public class EventServiceImpl implements EventService {

	@Autowired
	EventMapper mapper;

	@Autowired
	EventRepository repository;

	@Override
	public List<EventDTO> fetchAllEvents() {
		List<Event> entities = repository.findAll();
		if (null == entities)
			return null;
		log.info("DB operation success! Fetched {} Events!", entities.size());
		return mapper.entityToDTO(entities);
	}

	@Override
	public List<EventDTO> fetchAllOpenEvents() {
		List<Event> entities = repository.findAllOpenEvents();
		if (null == entities)
			return null;
		log.info("DB operation success! Fetched {} Open Events!", entities.size());
		return mapper.entityToDTO(entities);
	}

	@Override
	public EventDTO findEventByID(final Integer ID) {
		Event entity = repository.findById(ID).get();
		if (null == entity)
			return null;
		log.info("DB operation success! Fetched Event:{} ", entity.getEventId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public EventDTO addEvent(EventDTO dto) {
		Event entity = mapper.DTOToEntity(dto);
		entity.setCreatedAt(LocalDateTime.now());
		entity.setCreatedBy("System");
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy("System");

		entity = repository.save(entity);
		log.info("DB operation success! Added Event : {}", entity.getEventId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public EventDTO updateEvent(EventDTO dto) {
		Event entity = repository.findById(dto.getEventId()).get();

		if (null == entity)
			return null;

		entity = mapper.prepareForUpdate(entity, dto);
		entity = repository.save(entity);

		log.info("DB operation success! Updated Event : {}", entity.getEventId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public Boolean deleteEventByID(Integer ID) {
		Boolean exist = repository.existsById(ID);

		if (exist)
			repository.deleteById(ID);

		exist = repository.existsById(ID);
		log.info("DB operation success! Deleted the Eevent : {}", !exist);

		return !exist;
	}

	@Override
	public Set<Event> findAllById(Set<Integer> eventIds) {
		List<Event> entities = repository.findAllById(eventIds);
		if (null == entities)
			return null;
		log.info("DB operation success! Fetched total {} Events ", entities.size());
		return new HashSet<>(entities);
	}

}
