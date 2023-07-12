/**
 * 
 */
package com.afour.emgmt.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Event;
import com.afour.emgmt.entity.Role;
import com.afour.emgmt.entity.User;
import com.afour.emgmt.exception.NoDataFoundException;
import com.afour.emgmt.exception.UndefinedRoleException;
import com.afour.emgmt.exception.UserAlreadyExistException;
import com.afour.emgmt.mapper.EventMapper;
import com.afour.emgmt.mapper.UserMapper;
import com.afour.emgmt.model.EventDTO;
import com.afour.emgmt.model.UserDTO;
import com.afour.emgmt.model.UserRegistrationDTO;
import com.afour.emgmt.repository.EventRepository;
import com.afour.emgmt.repository.RoleRepository;
import com.afour.emgmt.repository.UserRepository;
import com.afour.emgmt.util.UtilConstant;

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
	public List<UserDTO> fetchAllVisitors() throws Exception {
		List<User> entities = repository.findAll();

		log.info("DB operation success! Fetched {} visitors!", entities.size());
		return mapper.entityToDTO(entities);
	}

	@Override
	public UserDTO findVisitorByID(final Integer ID) throws NoDataFoundException {
		Optional<User> optional = repository.findById(ID);

		return optional.map(visitor -> {
			UserDTO userDTO = dtoFromEntity(visitor);
			log.info("DB operation success! Fetched Visitor:{}", visitor.getUserId());
			return userDTO;
		}).orElseThrow(NoDataFoundException::new);
	}

	private UserDTO dtoFromEntity(User visitor) {
		UserDTO userDTO =mapper.entityToDTO(visitor);

		Set<Event> events = visitor.getEvents();
		Set<EventDTO> eventSet = eventMapper.entityToDTO(events);
		userDTO.setEvents(eventSet);
		return userDTO;
	}

	@Override
	public UserDTO findVisitorByUserName(final String USERNAME) throws NoDataFoundException {
		Optional<User> optional = repository.findByUserName(USERNAME);
		return optional.map(visitor -> {
			UserDTO userDTO = dtoFromEntity(visitor);
			log.info("DB operation success! Fetched Visitor:{} by username: {}", visitor.getUserId(), USERNAME);
			return userDTO;
		}).orElseThrow(NoDataFoundException::new);
	}

	@Override
	public UserDTO addVisitor(final UserDTO dto) throws UserAlreadyExistException, UndefinedRoleException {
		repository.findByUserName(dto.getUserName())
		.ifPresent(u->new UserAlreadyExistException());

		Set<EventDTO> newEventDtos = dto.getEvents();

		Set<Event> eventsToBeAdded = new HashSet<>();

		if (newEventDtos != null && !newEventDtos.isEmpty()) {
			Set<Integer> newEventIds = newEventDtos.stream().map(EventDTO::getEventId).collect(Collectors.toSet());
			List<Event> newEvents = eventRepository.findAllById(newEventIds);
			newEvents.stream().forEach(e -> eventsToBeAdded.add(e));
		}


		Role role = roleRepository.findByRoleName(UtilConstant.ROLE_ORGANIZER)
				.orElseThrow(() -> new UndefinedRoleException());

		User entity = mapper.prepareForCreate(dto);
		entity.setRole(role);

		entity.setEvents(eventsToBeAdded);

		entity = repository.save(entity);
		log.info("DB operation success! Added Visitor : {}", entity.getUserId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public UserDTO updateVisitor(final UserDTO dto) {
		return repository.findById(dto.getUserId()).map(entity -> {

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
		}).orElseThrow(NoDataFoundException::new);
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
		User entity = repository.findById(dto.getUserId()).orElseThrow(()->new NoDataFoundException());

		Set<Integer> newEventIds = dto.getEventIds();
		List<Event> newEvents = eventRepository.findAllById(newEventIds);
		Set<Event> existingEvents = entity.getEvents();
		newEvents.forEach(e -> existingEvents.add(e));

		entity.setEvents(existingEvents);
		User updated = repository.save(entity);

		UserDTO UserDTO = dtoFromEntity(updated);

		log.info("DB operation success! Registered the visitor : {} with events{}", dto.getUserId(), existingEvents);
		return UserDTO;
	}

}
