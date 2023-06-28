/**
 * 
 */
package com.afour.emgmt.service;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Event;
import com.afour.emgmt.entity.Role;
import com.afour.emgmt.entity.Visitor;
import com.afour.emgmt.mapper.EventMapper;
import com.afour.emgmt.mapper.VisitorMapper;
import com.afour.emgmt.model.EventDTO;
import com.afour.emgmt.model.VisitorDTO;
import com.afour.emgmt.model.VisitorRegistrationDTO;
import com.afour.emgmt.repository.EventRepository;
import com.afour.emgmt.repository.RoleRepository;
import com.afour.emgmt.repository.VisitorRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Service
@Slf4j
public class VisitorServiceImpl implements VisitorService {

	@Autowired
	VisitorMapper mapper;
	
	@Autowired
	EventMapper eventMapper;

	@Autowired
	VisitorRepository repository;

	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	EventService eventService;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public List<VisitorDTO> fetchAllVisitors() {
		List<Visitor> entities = repository.findAll();
		if (null == entities)
			return null;
		log.info("DB operation success! Fetched {} visitors!", entities.size());
		return mapper.entityToDTO(entities);
	}

	@Override
	public VisitorDTO findVisitorByID(final Integer ID) {
		Optional<Visitor> entity = repository.findById(ID);
		if (!entity.isPresent())
			return null;
		log.info("DB operation success! Fetched Visitor:{}", entity.get().getVisitorId());
		return mapper.entityToDTO(entity.get());
	}

	@Override
	public VisitorDTO findVisitorByUserName(final String USERNAME) {
		Visitor entity = repository.findByUserName(USERNAME);
		if (null == entity)
			return null;

		log.info("DB operation success! Fetched Visitor:{} by username: {}", entity.getVisitorId(), USERNAME);
		return mapper.entityToDTO(entity);
	}

	@Override
	@Transactional
	public VisitorDTO addVisitor(final VisitorDTO dto) {
		Set<EventDTO> newEventDtos = dto.getEventDtos();

		dto.setCreatedAt(LocalDateTime.now());
		dto.setCreatedBy("System");
		dto.setUpdatedAt(LocalDateTime.now());
		dto.setUpdatedBy("System");
		dto.setIsActive(true);
		
		Set<Event> eventsToBeAdded = new HashSet<>();

		if (newEventDtos != null && !newEventDtos.isEmpty()) {
			Set<Integer> newEventIds = newEventDtos.stream().map(EventDTO::getEventId).collect(Collectors.toSet());
			List<Event> newEvents = eventRepository.findAllById(newEventIds);
			newEvents.stream().forEach(e->eventsToBeAdded.add(e));
		}
		
		
		Visitor entity = mapper.DTOToEntity(dto);
		
		Integer ROLE_VISITOR = Integer.valueOf(2);
		Role role = roleRepository.findById(ROLE_VISITOR).get();
		entity.setRole(role);
		
		entity.setEvents(eventsToBeAdded);

		entity = repository.save(entity);
		log.info("DB operation success! Added Visitor : {}", entity.getVisitorId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public VisitorDTO updateVisitor(final VisitorDTO dto) {
		Visitor entity = repository.findById(dto.getVisitorId()).get();

		if (null == entity)
			return null;
		
		Set<Event> existingEvents = entity.getEvents();

		Set<EventDTO> newEvents = dto.getEventDtos();
		if (newEvents != null && !newEvents.isEmpty()) {
			Set<Integer> newEventIds = newEvents.stream().map(EventDTO::getEventId).collect(Collectors.toSet());
			eventRepository.findAllById(newEventIds).stream().map(e->existingEvents.add(e));
			entity.setEvents(existingEvents);
		}

		entity = mapper.prepareForUpdate(entity, dto);
		entity = repository.save(entity);

		log.info("DB operation success! Fetched Visitor : {}", entity.getVisitorId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public Boolean deleteVisitorByID(final Integer ID) {
		Boolean exist = repository.existsById(ID);

		if (exist)
			repository.deleteById(ID);

		exist = repository.existsById(ID);
		log.info("DB operation success! Deleted the visitor : {}", !exist);

		return !exist;
	}

	@Override
	public VisitorDTO registerVisitorForEvent(VisitorRegistrationDTO dto) {
		Optional<Visitor> optional = repository.findById(dto.getVisitorId());

		if (optional.isEmpty())
			return null;

		Visitor entity = optional.get();

		Set<Integer> newEventIds = dto.getEventIds();
		List<Event> newEvents = eventRepository.findAllById(newEventIds);
		Set<Event> existingEvents = entity.getEvents();
		newEvents.forEach(e->existingEvents.add(e));
		
		entity.setEvents(existingEvents);
		Visitor updated = repository.save(entity);
		log.info("DB operation success! Registered the visitor : {} with sessions{}", dto.getVisitorId(),
				existingEvents);
		return mapper.entityToDTO(updated);
	}

}
