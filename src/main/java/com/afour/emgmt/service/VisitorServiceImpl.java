/**
 * 
 */
package com.afour.emgmt.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.afour.emgmt.common.RoleEnum;
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

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Service
@Slf4j
public class VisitorServiceImpl implements VisitorService {

	private final UserMapper mapper;

	private final UserRepository repository;

	private final RoleRepository roleRepository;

	private final EventMapper eventMapper;
	
	private final EventRepository eventRepository;

	public VisitorServiceImpl(UserMapper mapper, UserRepository repository, RoleRepository roleRepository,
			EventMapper eventMapper, EventRepository eventRepository) {
		this.mapper = mapper;
		this.repository = repository;
		this.roleRepository = roleRepository;
		this.eventMapper = eventMapper;
		this.eventRepository = eventRepository;
	}

	@Override
	public List<UserDTO> fetchAllVisitors() {
		List<User> entities = repository.findAllByRoleId(RoleEnum.VISITOR);

		log.info("DB operation success! Fetched {} visitors!", entities.size());
		return mapper.entityToDTO(entities);
	}

	@Override
	public UserDTO findVisitorByID(final Integer ID) {
		Optional<User> optional = repository.findById(ID);

		return optional.map(visitor -> {
			UserDTO userDTO = dtoFromEntity(visitor);
			log.info("DB operation success! Fetched Visitor:{}", visitor.getUserId());
			return userDTO;
		}).orElseThrow(NoDataFoundException::new);
	}

	private UserDTO dtoFromEntity(User visitor) {
		UserDTO userDTO = mapper.entityToDTO(visitor);

		Set<Event> events = visitor.getEvents();
		Set<EventDTO> eventSet = eventMapper.entityToDTO(events);
		userDTO.setEvents(eventSet);
		return userDTO;
	}

	@Override
	public UserDTO findVisitorByUserName(final String USERNAME) {
		Optional<User> optional = repository.findByUserName(USERNAME);
		return optional.map(visitor -> {
			UserDTO userDTO = dtoFromEntity(visitor);
			log.info("DB operation success! Fetched Visitor:{} by username: {}", visitor.getUserId(), USERNAME);
			return userDTO;
		}).orElseThrow(NoDataFoundException::new);
	}

	@Override
	public UserDTO addVisitor(final UserDTO dto) {
		Optional<User> optional = repository.findByUserName(dto.getUserName());
		if (optional.isPresent())
			throw new UserAlreadyExistException();

		Set<EventDTO> newEventDtos = dto.getEvents();

		Set<Event> eventsToBeAdded = new HashSet<>();

		if (!CollectionUtils.isEmpty(newEventDtos)) {
			Set<Integer> newEventIds = newEventDtos.stream().map(EventDTO::getEventId).collect(Collectors.toSet());
			List<Event> newEvents = eventRepository.findAllById(newEventIds);
			eventsToBeAdded.addAll(newEvents);
		}

		Role role = roleRepository.findById(RoleEnum.ORGANIZER).orElseThrow(UndefinedRoleException::new);

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
			if (!CollectionUtils.isEmpty(newEvents)) {
				Set<Integer> newEventIds = newEvents.stream().map(EventDTO::getEventId).collect(Collectors.toSet());
				existingEvents.addAll(eventRepository.findAllById(newEventIds));
				entity.setEvents(existingEvents);
			}

			entity = mapper.prepareForUpdate(entity, dto);
			entity = repository.save(entity);

			log.info("DB operation success! Fetched Visitor : {}", entity.getUserId());
			return mapper.entityToDTO(entity);
		}).orElseThrow(NoDataFoundException::new);
	}

	@Override
	public boolean deleteVisitorByID(final Integer ID) {
		boolean exist = repository.existsById(ID);

		if (!exist)
			throw new NoDataFoundException();

		repository.deleteById(ID);

		exist = repository.existsById(ID);
		log.info("DB operation success! Deleted the visitor : {}", !exist);

		return !exist;
	}

	@Override
	public UserDTO registerVisitorForEvent(UserRegistrationDTO dto) {
		User entity = repository.findById(dto.getUserId()).orElseThrow(NoDataFoundException::new);

		Set<Integer> newEventIds = dto.getEventIds();
		List<Event> newEvents = eventRepository.findAllById(newEventIds);
		Set<Event> existingEvents = entity.getEvents();
		existingEvents.addAll(newEvents);

		entity.setEvents(existingEvents);
		User updated = repository.save(entity);

		UserDTO UserDTO = dtoFromEntity(updated);

		log.info("DB operation success! Registered the visitor : {} with events{}", dto.getUserId(), existingEvents);
		return UserDTO;
	}

}
