/**
 * 
 */
package com.afour.emgmt.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Esession;
import com.afour.emgmt.entity.Event;
import com.afour.emgmt.entity.Visitor;
import com.afour.emgmt.mapper.EventMapper;
import com.afour.emgmt.mapper.SessionMapper;
import com.afour.emgmt.mapper.VisitorMapper;
import com.afour.emgmt.model.EsessionDTO;
import com.afour.emgmt.model.EventDTO;
import com.afour.emgmt.model.VisitorDTO;
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
	SessionMapper sessionMapper;
	
	@Autowired
	VisitorMapper visitorMapper;

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
	public List<EventDTO> fetchEventsByStatus(final Boolean status) {
		List<Event> entities = repository.fetchEventsByStatus(!status);
		if (null == entities)
			return null;
		log.info("DB operation success! Fetched {} Open Events!", entities.size());
		return mapper.entityToDTO(entities);
	}

	@Override
	public EventDTO findEventByID(final Integer ID) {
		Optional<Event> optional = repository.findById(ID);
		if (optional.isEmpty())
			return null;
		Event event = optional.get();
		EventDTO  dto = mapper.entityToDTO(event);
		
		Set<Esession> sessions = event.getSessions();
		Set<EsessionDTO> sessionDtos = sessionMapper.entityToDTO(sessions);
		dto.setSessions(sessionDtos);
		
		Set<Visitor> visitors = event.getVisitors();
		Set<VisitorDTO> visitorDtos = visitorMapper.entityToDTO(visitors);
		dto.setVisitors(visitorDtos);
		log.info("DB operation success! Fetched Event:{} ", dto.getEventId());
		return dto;
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
