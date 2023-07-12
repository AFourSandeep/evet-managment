/**
 * 
 */
package com.afour.emgmt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Esession;
import com.afour.emgmt.entity.Event;
import com.afour.emgmt.exception.NoDataFoundException;
import com.afour.emgmt.mapper.EventMapper;
import com.afour.emgmt.mapper.SessionMapper;
import com.afour.emgmt.model.EsessionDTO;
import com.afour.emgmt.repository.EventRepository;
import com.afour.emgmt.repository.SessionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Service
@Slf4j
public class SessionServiceImpl implements SessionService {

	@Autowired
	SessionRepository repository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	SessionMapper mapper;

	@Autowired
	EventMapper eventMapper;

	@Override
	public List<EsessionDTO> findSessionEventByID(final Integer eventId)  {
		List<Esession> entities = repository.findSessionEventByID(eventId);
		log.info("DB operation success! Fetched {} Sessions using EventID:{}", entities.size(), eventId);
		return mapper.entityToDTO(entities);
	}

	@Override
	public EsessionDTO findSessionByID(final Integer ID) {
		Optional<Esession> optional = repository.findById(ID);
		return optional.map(entity -> {
			EsessionDTO dto = mapper.entityToDTO(entity);
			log.info("DB operation success! Fetched Session:{}", dto.getEsessionId());
			return dto;
		}).orElseThrow(() -> new NoDataFoundException());
	}

	@Override
	public EsessionDTO addSession(EsessionDTO dto) {
		Optional<Event> optional = eventRepository.findById(dto.getEvent().getEventId());
		if (optional.isEmpty())
			throw new NoDataFoundException();

		Esession entity = mapper.prepareForCreate(dto);

		Event event = optional.get();
		entity.setEvent(event);
		entity = repository.save(entity);

		EsessionDTO created = mapper.entityToDTO(entity);
		created.setEvent(eventMapper.entityToDTO(event));
		log.info("DB operation success! Added Session: {}", entity.getEsessionId());
		return created;
	}

	@Override
	public EsessionDTO updateSession(EsessionDTO dto) {
		Optional<Esession> optional = repository.findById(dto.getEsessionId());

		if (optional.isEmpty())
			throw new NoDataFoundException();

		Esession entity = mapper.prepareForUpdate(optional.get(), dto);

		Event event = entity.getEvent();

		entity = repository.save(entity);
		EsessionDTO updatedDTO = mapper.entityToDTO(entity);
		updatedDTO.setEvent(eventMapper.entityToDTO(event));
		log.info("DB operation success! Updated Session: {}", entity.getEsessionId());
		return updatedDTO;
	}

	@Override
	public Boolean deleteSessionByID(Integer ID) throws NoDataFoundException {
		Boolean exist = repository.existsById(ID);

		if (!exist)
			throw new NoDataFoundException();
		repository.deleteById(ID);

		exist = repository.existsById(ID);
		log.info("DB operation success! Deleted the Session : {}", !exist);
		return !exist;
	}

}
