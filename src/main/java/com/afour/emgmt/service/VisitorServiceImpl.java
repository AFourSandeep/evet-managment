/**
 * 
 */
package com.afour.emgmt.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.common.RoleEnum;
import com.afour.emgmt.entity.Event;
import com.afour.emgmt.entity.Role;
import com.afour.emgmt.entity.User;
import com.afour.emgmt.exception.NoDataFoundException;
import com.afour.emgmt.mapper.EventMapper;
import com.afour.emgmt.mapper.UserMapper;
import com.afour.emgmt.model.EventDTO;
import com.afour.emgmt.model.UserDTO;
import com.afour.emgmt.model.UserRegistrationDTO;
import com.afour.emgmt.repository.EventRepository;
import com.afour.emgmt.repository.RoleRepository;
import com.afour.emgmt.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Service
@Slf4j
public class VisitorServiceImpl implements VisitorService {

	@Autowired
	UserMapper mapper;

	@Autowired
	EventMapper eventMapper;

	@Autowired
	UserRepository repository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public List<UserDTO> fetchAllVisitors() throws NoDataFoundException {
		List<User> entities = repository.findAll();
		if (null == entities)
			throw new NoDataFoundException();
		log.info("DB operation success! Fetched {} visitors!", entities.size());
		return mapper.entityToDTO(entities);
	}

	@Override
	public UserDTO findVisitorByID(final Integer ID) throws NoDataFoundException {
		Optional<User> optional = repository.findById(ID);
		if (optional.isEmpty())
			throw new NoDataFoundException();
		
		User visitor=  optional.get();
		UserDTO UserDTO =mapper.entityToDTO(visitor);
			
		Set<Event> events = visitor.getEvents();
		Set<EventDTO> eventDtos = eventMapper.entityToDTO(events);
		UserDTO.setEvents(eventDtos);
 		log.info("DB operation success! Fetched Visitor:{}", visitor.getUserId());
		return UserDTO;
	}

	@Override
	public UserDTO findVisitorByUserName(final String USERNAME) throws NoDataFoundException {
		Optional<User> optional = repository.findByUserName(USERNAME);
		if (optional.isEmpty())
			throw new NoDataFoundException();
		User visitor = optional.get();
		UserDTO UserDTO =mapper.entityToDTO(visitor);
			
		Set<Event> events = visitor.getEvents();
		Set<EventDTO> eventDtos = eventMapper.entityToDTO(events);
		UserDTO.setEvents(eventDtos);
		log.info("DB operation success! Fetched Visitor:{} by username: {}", visitor.getUserId(), USERNAME);
		return UserDTO;
	}

	@Override
	@Transactional
	public UserDTO addVisitor(final UserDTO dto) {
		
		Set<EventDTO> newEventDtos = dto.getEvents();

		Set<Event> eventsToBeAdded = new HashSet<>();

		if (newEventDtos != null && !newEventDtos.isEmpty()) {
			Set<Integer> newEventIds = newEventDtos.stream().map(EventDTO::getEventId).collect(Collectors.toSet());
			List<Event> newEvents = eventRepository.findAllById(newEventIds);
			newEvents.stream().forEach(e -> eventsToBeAdded.add(e));
		}

		User entity = mapper.prepareForCreate(dto);
		
		Role role = roleRepository.findById(RoleEnum.VISITOR.getRoleId()).get();
		entity.setRole(role);

		entity.setEvents(eventsToBeAdded);

		entity = repository.save(entity);
		log.info("DB operation success! Added Visitor : {}", entity.getUserId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public UserDTO updateVisitor(final UserDTO dto) throws NoDataFoundException {
		User entity = repository.findById(dto.getUserId()).get();

		if (null == entity)
			throw new NoDataFoundException();

		Set<Event> existingEvents = entity.getEvents();

		Set<EventDTO> newEvents = dto.getEvents();
		if (newEvents != null && !newEvents.isEmpty()) {
			Set<Integer> newEventIds = newEvents.stream().map(EventDTO::getEventId).collect(Collectors.toSet());
			eventRepository.findAllById(newEventIds).stream().map(e -> existingEvents.add(e));
			entity.setEvents(existingEvents);
		}

		entity = mapper.prepareForUpdate(entity, dto);
		entity = repository.save(entity);

		log.info("DB operation success! Fetched Visitor : {}", entity.getUserId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public Boolean deleteVisitorByID(final Integer ID) throws NoDataFoundException {
		Boolean exist = repository.existsById(ID);

		if (!exist)
			throw new NoDataFoundException();
		
			repository.deleteById(ID);

		exist = repository.existsById(ID);
		log.info("DB operation success! Deleted the visitor : {}", !exist);

		return !exist;
	}

	@Override
	public UserDTO registerVisitorForEvent(UserRegistrationDTO dto) throws NoDataFoundException {
		Optional<User> optional = repository.findById(dto.getUserId());

		if (optional.isEmpty())
			throw new NoDataFoundException();

		User entity = optional.get();

		Set<Integer> newEventIds = dto.getEventIds();
		List<Event> newEvents = eventRepository.findAllById(newEventIds);
		Set<Event> existingEvents = entity.getEvents();
		newEvents.forEach(e -> existingEvents.add(e));

		entity.setEvents(existingEvents);
		User updated = repository.save(entity);
		
		UserDTO UserDTO =mapper.entityToDTO(updated);
		
		Set<Event> events = updated.getEvents();
		Set<EventDTO> eventDtos = eventMapper.entityToDTO(events);
		UserDTO.setEvents(eventDtos);
		
		log.info("DB operation success! Registered the visitor : {} with sessions{}", dto.getUserId(),
				existingEvents);
		return UserDTO;
	}

}
