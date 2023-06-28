/**
 * 
 */
package com.afour.emgmt.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.emgmt.entity.Esession;
import com.afour.emgmt.mapper.SessionMapper;
import com.afour.emgmt.model.EsessionDTO;
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
	SessionMapper mapper;

	@Override
	public List<EsessionDTO> findSessionEventByID(final Integer eventId) {
		List<Esession> entities= repository.findSessionEventByID(eventId);
		if(entities ==null || entities.isEmpty())
		return null;
		
		log.info("DB operation success! Fetched {} Sessions using EventID:{}",entities.size(),eventId);
		return mapper.entityToDTO(entities);
	}

	@Override
	public EsessionDTO findSessionByID(final Integer ID) {
		Optional<Esession> optional= repository.findById(ID);
		if(optional.isEmpty())
		return null;
		
		Esession entity = optional.get();
		
		log.info("DB operation success! Fetched Session:{}",entity.getEsessionId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public EsessionDTO addSession(EsessionDTO dto) {
		Esession entity = mapper.DTOToEntity(dto);
		entity.setCreatedAt(LocalDateTime.now());
		entity.setCreatedBy("System");
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy("System");

		entity = repository.save(entity);
		log.info("DB operation success! Added Session: {}", entity.getEsessionId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public EsessionDTO updateSession(EsessionDTO dto) {
		Optional<Esession> optional = repository.findById(dto.getEsessionId());

		if (optional.isEmpty())
			return null;

		Esession entity  = mapper.prepareForUpdate(optional.get(), dto);
		entity = repository.save(entity);

		log.info("DB operation success! Updated Session: {}", entity.getEsessionId());
		return mapper.entityToDTO(entity);
	}

	@Override
	public Boolean deleteSessionByID(Integer ID) {
		Boolean exist = repository.existsById(ID);

		if (exist)
			repository.deleteById(ID);

		exist = repository.existsById(ID);
		log.info("DB operation success! Deleted the Session : {}", !exist);
		return !exist;
	}

}
