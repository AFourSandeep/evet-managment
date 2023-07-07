/**
 * 
 */
package com.afour.emgmt.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Esession;
import com.afour.emgmt.entity.Event;
import com.afour.emgmt.entity.User;
import com.afour.emgmt.exception.NoDataFoundException;
import com.afour.emgmt.mapper.EventMapper;
import com.afour.emgmt.mapper.SessionMapper;
import com.afour.emgmt.mapper.UserMapper;
import com.afour.emgmt.model.EsessionDTO;
import com.afour.emgmt.model.EventDTO;
import com.afour.emgmt.model.UserDTO;
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
	UserMapper userMapper;

	@Autowired
	EventRepository repository;

	@Override
	public List<EventDTO> fetchAllEvents() throws NoDataFoundException {
		List<Event> entities = repository.findAll();
		if (null == entities)
			throw new NoDataFoundException();
		log.info("DB operation success! Fetched {} Events!", entities.size());
		return mapper.entityToDTO(entities);
	}

	@Override
	public List<EventDTO> fetchEventsByStatus(final Boolean status) throws NoDataFoundException {
		List<Event> entities = repository.fetchEventsByStatus(!status);
		if (null == entities)
			throw new NoDataFoundException();
		log.info("DB operation success! Fetched {} Open Events!", entities.size());
		return mapper.entityToDTO(entities);
	}

	@Override
	public EventDTO findEventByID(final Integer ID) throws NoDataFoundException {
		Optional<Event> optional = repository.findById(ID);
		if (optional.isEmpty())
			throw new NoDataFoundException();
		Event event = optional.get();
		EventDTO  dto = mapper.entityToDTO(event);
		
		Set<Esession> sessions = event.getSessions();
		Set<EsessionDTO> sessionDtos = sessionMapper.entityToDTO(sessions);
		dto.setSessions(sessionDtos);
		
		Set<User> visitors = event.getVisitors();
		Set<UserDTO> visitorDtos = userMapper.entityToDTO(visitors);
		dto.setVisitors(visitorDtos);
		log.info("DB operation success! Fetched Event:{} ", dto.getEventId());
		return dto;
	}	

	@Override
	public EventDTO addEvent(EventDTO dto) {
		Event entity = mapper.prepareForCreate(dto);

		entity = repository.save(entity);
		log.info("DB operation success! Added Event : {}", entity.getEventId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public EventDTO updateEvent(EventDTO dto) throws NoDataFoundException {
		Event entity = repository.findById(dto.getEventId()).get();

		if (null == entity)
			throw new NoDataFoundException();

		entity = mapper.prepareForUpdate(entity, dto);
		entity = repository.save(entity);

		log.info("DB operation success! Updated Event : {}", entity.getEventId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public Boolean deleteEventByID(Integer ID) throws NoDataFoundException {
		Boolean exist = repository.existsById(ID);

		if (!exist)
			throw new NoDataFoundException();
		
			repository.deleteById(ID);

		exist = repository.existsById(ID);
		log.info("DB operation success! Deleted the Eevent : {}", !exist);

		return !exist;
	}

	@Override
	public Set<Event> findAllById(Set<Integer> eventIds) throws NoDataFoundException {
		List<Event> entities = repository.findAllById(eventIds);
		if (null == entities)
			throw new NoDataFoundException();
		log.info("DB operation success! Fetched total {} Events ", entities.size());
		return new HashSet<>(entities);
	}

}
